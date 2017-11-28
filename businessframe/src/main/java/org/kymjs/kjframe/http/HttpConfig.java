/*
 * Copyright (c) 2014, 张涛.
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

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;



import java.io.File;

import javax.net.ssl.SSLSocketFactory;

/**
 * Http配置器
 *
 * @author kymjs (http://www.kymjs.com/) .
 */
public final class HttpConfig {

    public static boolean DEBUG = true;

    /**
     * 缓存文件夹
     **/
    public static String CACHEPATH = "KJLibrary/cache";
    /**
     * 线程池大小
     **/
    public static int NETWORK_POOL_SIZE = 4;
    /**
     * Http请求超时时间
     **/
    public static int TIMEOUT = 2000;

    /**
     * 磁盘缓存大小
     */
    public static int DISK_CACHE_SIZE = 5 * 1024 * 1024;
    /**
     * 缓存有效时间: 默认5分钟
     */
    public int cacheTime = 5;

    /**
     * 在Http请求中，如果服务器也声明了对缓存时间的控制，那么是否优先使用服务器设置: 默认false
     */
    public static boolean useServerControl = false;

    /**
     * 为了更真实的模拟网络请求。如果启用，在读取完成以后，并不立即返回而是延迟500毫秒再返回
     */
    public boolean useDelayCache = false;
    /**
     * 如果启用了useDelayCache，本属性才有效。单位:ms
     */
    public long delayTime = 500;

    /**
     * 同时允许多少个下载任务，建议不要太大(注意：本任务最大值不能超过NETWORK_POOL_SIZE)
     */
    public static int MAX_DOWNLOAD_TASK_SIZE = 2;


    public static String sCookie;
    public static boolean needPHPSESSID=true;
    public static boolean needJSESSIONID=true;


    @Deprecated
    public void setCookieString(String cookie) {
        sCookie = cookie;
    }

    @Deprecated
    public String getCookieString() {
        return sCookie;
    }
}
