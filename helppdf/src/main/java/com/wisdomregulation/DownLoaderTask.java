package com.wisdomregulation;

import net.lingala.zip4j.exception.ZipException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by King6rf on 2017/7/12.
 */
public class DownLoaderTask {
    public static final String qiniuhost="http://optlspedt.bkt.clouddn.com/";
    DownLoaderListener listener=new DownLoaderListener() {
        @Override
        public void downloadstart() {
            System.out.println("开始下载");
        }

        @Override
        public void downloadein(int now, int total) {
            float jindu=now/total;
            System.out.println("下载进度"+now+"/"+total);
        }

        @Override
        public void downloadend() {
            System.out.println("结束下载");
        }

        @Override
        public void compressionstart() {
            System.out.println("开始解压");
        }

        @Override
        public void compressionin(int now, int total) {
            System.out.println("解压进度"+now+"/"+total);
        }

        @Override
        public void compressionend() {
            System.out.println("结束解压");
        }
    };
    private static final DownLoaderTask instance=new DownLoaderTask();
    private DownLoaderTask(){

    }

    public DownLoaderTask setListener(DownLoaderListener listener) {
        if(listener!=null){
            this.listener = listener;
        }

        return  this;
    }

    public static DownLoaderTask getInstance(){
        return  instance;
    }
    /**
     *
     * @param urlPath
     *            下载路径
     * @param downloadDir
     *            下载存放目录
     * @return 返回下载文件
     */
    public File downloadFile(String urlPath, String downloadDir) {
        File file = null;
        try {
            // 统一资源
            if(listener!=null){
                listener.downloadstart();
            }
            URL url = new URL(urlPath);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            // 设定请求的方法，默认是GET
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Accept-Encoding", "identity");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
            httpURLConnection.connect();

            // 文件大小
            int fileLength = httpURLConnection.getContentLength();

            // 文件名
            String filePathUrl = httpURLConnection.getURL().getFile();
            String fileFullName = filePathUrl.substring(filePathUrl.lastIndexOf(File.separatorChar) + 1);

            System.out.println("file length---->" + fileLength);

            URLConnection con = url.openConnection();

            BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());

            String path = downloadDir + File.separatorChar + fileFullName;
            file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(file);
            int size = 0;
            int len = 0;
            byte[] buf = new byte[1024];
            while ((size = bin.read(buf)) != -1) {
                len += size;
                out.write(buf, 0, size);
                if(listener!=null){
                    listener.downloadein(len,fileLength);
                }
                // 打印下载百分比
                // System.out.println("下载了-------> " + len * 100 / fileLength +
                // "%\n");
            }
            bin.close();
            out.close();
            if(listener!=null){
                listener.downloadend();
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            if(file!=null){
                file.delete();
            }
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            if(file!=null){
                file.delete();
            }
            e.printStackTrace();
        } finally {

        }
        return file;

    }
    /**
     *
     * @param urlPath
     *            下载路径
     * @param downloadDir
     *            下载存放目录
     * @return 返回下载文件
     */
    public DownLoaderTask downloadFileandcompression(String urlPath, String downloadDir) {
        File file = downloadFile(urlPath,downloadDir);
        decompression(file.getAbsolutePath(),downloadDir);
        return  this;

    }
    private DownLoaderTask decompression(String zipfilestring,String dir){
        try {
            if(listener!=null){
                listener.compressionstart();
            }
            net.lingala.zip4j.core.ZipFile zipFile=new net.lingala.zip4j.core.ZipFile(zipfilestring);
            List fileHeaderList = zipFile.getFileHeaders();
                int number = fileHeaderList.size();
                for (int i = 0; i < number; i++) {
                    zipFile.extractFile((net.lingala.zip4j.model.FileHeader) fileHeaderList.get(i), dir);
                    if(listener!=null){
                        listener.compressionin(i+1,number);
                    }
                }

        } catch (ZipException e) {
            e.printStackTrace();
        }
        if(listener!=null){
            listener.compressionend();
        }
        return  this;

    }
    public interface  DownLoaderListener{
        void  downloadstart();
        void downloadein(int now,int total);
        void  downloadend();
        void compressionstart();
        void compressionin(int now,int total);
        void compressionend();
    }
}
