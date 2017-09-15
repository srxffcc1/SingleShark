/*
 * Copyright (C) 2011 The Android Open Source Project, 张涛
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kymjs.kjframe.http;

import org.kymjs.kjframe.utils.KJLoger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 网络请求执行器，将传入的Request使用HttpStack客户端发起网络请求，并返回一个NetworkRespond结果
 *
 * @author kymjs (http://www.kymjs.com/) .
 */
public class Network {
    protected static final boolean DEBUG = HttpConfig.DEBUG;
    protected final HttpStack mHttpStack;

    public Network(HttpStack httpStack) {
        mHttpStack = httpStack;
    }

    /**
     * 实际执行一个请求的方法
     *
     * @param request 一个请求任务
     * @return 一个不会为null的响应
     * @throws KJHttpException
     */
    public NetworkResponse performRequest(Request<?> request)
            throws KJHttpException {
        while (true) {
            KJHttpResponse httpResponse = null;
            byte[] responseContents = null;
            Map<String, String> responseHeaders = new HashMap<String, String>();
            try {
                // 标记Http响应头在Cache中的tag
                Map<String, String> headers = new HashMap<String, String>();
                addCacheHeaders(headers, request.getCacheEntry());
                httpResponse = mHttpStack.performRequest(request, headers);

                int statusCode = httpResponse.getResponseCode();
                System.out.println("Code:"+statusCode);
                responseHeaders = httpResponse.getHeaders();
                if (statusCode == HttpStatus.SC_NOT_MODIFIED) { // 304
                    System.out.println("Code304");
                    return new NetworkResponse(HttpStatus.SC_NOT_MODIFIED,
                            request.getCacheEntry() == null ? null : request
                                    .getCacheEntry().data,
                            responseHeaders, true);
                }

                if (httpResponse.getContentStream() != null) {
                    if (request instanceof FileRequest) {
                        responseContents = ((FileRequest) request)
                                .handleResponse(httpResponse);
                    } else {
                        System.out.println("start tobyte");
                        responseContents = entityToBytes(httpResponse);
                        System.out.println("end tobyte");
                    }
                } else {
                    responseContents = new byte[0];
                }

                if (statusCode < 200 || statusCode > 299) {
                    throw new IOException();
                }
                return new NetworkResponse(statusCode, responseContents, responseHeaders, false);
            } catch (SocketTimeoutException e) {
                throw new KJHttpException(new SocketTimeoutException("socket timeout"));
            } catch (MalformedURLException e) {
                throw new RuntimeException("Bad URL " + request.getUrl(), e);
            } catch (IOException e) {
                int statusCode = 0;
                NetworkResponse networkResponse = null;
                if (httpResponse != null) {
                    statusCode = httpResponse.getResponseCode();
                } else {
                    throw new KJHttpException("NoConnection error", e);
                }
                KJLoger.debug("Unexpected response code %d for %s", statusCode, request.getUrl());
                if (responseContents != null) {
                    networkResponse = new NetworkResponse(statusCode,
                            responseContents, responseHeaders, false);
                    if (statusCode == HttpStatus.SC_UNAUTHORIZED
                            || statusCode == HttpStatus.SC_FORBIDDEN) {
                        throw new KJHttpException("auth error");
                    } else {
                        throw new KJHttpException(
                                "server error, Only throw ServerError for 5xx status codes.",
                                networkResponse);
                    }
                } else {
                    throw new KJHttpException();
                }
            }
        }
    }

    /**
     * 标记Respondeader响应头在Cache中的tag
     *
     * @param headers
     * @param entry
     */
    private void addCacheHeaders(Map<String, String> headers, Cache.Entry entry) {
        if (entry == null) {
            return;
        }
        if (entry.etag != null) {
            headers.put("If-None-Match", entry.etag);
        }
        if (entry.serverDate > 0) {
            Date refTime = new Date(entry.serverDate);
            DateFormat sdf = SimpleDateFormat.getDateTimeInstance();
            headers.put("If-Modified-Since", sdf.format(refTime));

        }
    }

    /**
     * 把HttpEntry转换为byte[] 2017-8-2 优化为StringBuilder
     *
     * @throws IOException
     * @throws KJHttpException
     */
    private byte[] entityToBytes(KJHttpResponse kjHttpResponse) throws IOException,
            KJHttpException {
//        PoolingByteArrayOutputStream bytes = new PoolingByteArrayOutputStream(
//                ByteArrayPool.get(), (int) kjHttpResponse.getContentLength());\
            StringBuilder jsonResults = new StringBuilder();
            BufferedReader in = new BufferedReader (new InputStreamReader(kjHttpResponse.getContentStream()));
            if (in == null) {
                throw new KJHttpException("server error");
            }
            int read;
            char[] buff = new char[512];
            long oldtime=System.currentTimeMillis();
            while ((read = in.read(buff)) != -1) {

                jsonResults.append(buff, 0, read);
            }
            in.close();
//        try {
//            BufferedReader in = new BufferedReader (new InputStreamReader(kjHttpResponse.getContentStream()));
//            if (in == null) {
//                throw new KJHttpException("server error");
//            }
//            int read;
//            char[] buff = new char[512];
//            long oldtime=System.currentTimeMillis();
//            while ((read = in.read(buff)) != -1) {
////                if(System.currentTimeMillis()-oldtime>5000){
////                   return new byte[0];
////                }
//                jsonResults.append(buff, 0, read);
//            }
//            in.close();
//
//        } finally {
//            try {
////                entity.consumeContent();
//                kjHttpResponse.getContentStream().close();
//            } catch (IOException e) {
//                KJLoger.debug("Error occured when calling consumingContent");
//            }
//        }
        return jsonResults.toString().getBytes();
    }
}
