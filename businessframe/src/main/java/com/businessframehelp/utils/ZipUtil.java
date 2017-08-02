package com.businessframehelp.utils;

import com.hzy.archiver.ArchiverManager;
import com.hzy.archiver.IArchiverListener;

/**
 * Created by King6rf on 2017/5/12.
 * 总的压缩类支持多种解压
 */

public class ZipUtil {
    public static void zip(final String srcfile, final String unrarPath, final String password, final IArchiverListener listener){
        ArchiverManager.getInstance().doUnArchiver(srcfile,unrarPath,password,listener);
    }
}
