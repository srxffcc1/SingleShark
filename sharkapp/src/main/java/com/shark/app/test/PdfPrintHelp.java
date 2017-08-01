package com.shark.app.test;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

import com.lody.virtual.client.core.InstallStrategy;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.remote.InstallResult;

import java.io.File;
import java.io.IOException;

public class PdfPrintHelp {
    /**
     * @param pdfpath   pdf路劲
     * @param activity
     * @param result startactivity的resquestcode
     */
    private static  final boolean isplugin = true;//插件不稳定

    public static void print(final String pdfpath, final Activity activity, int resquestcode) {
        if (isplugin) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    InstallResult res=VirtualCore.get().installPackage(Environment.getExternalStorageDirectory()+"/PrinterShare-11.10.0.apk", InstallStrategy.TERMINATE_IF_EXIST);
                    if (res.isSuccess) {
                        try {
                            VirtualCore.get().preOpt(res.packageName);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (res.isUpdate) {
                            Toast.makeText(activity, "Update: " + res.packageName + " success!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, "Install: " + res.packageName + " success!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(activity, "Install failed: " + res.error, Toast.LENGTH_SHORT).show();
                    }
                        ComponentName comp = new ComponentName("com.dynamixsoftware.printershare","com.dynamixsoftware.printershare.ActivityPrintPDF");
                        Uri fileuri = Uri.fromFile(new File(pdfpath));
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setComponent(comp);
                        intent.setDataAndType(fileuri, "application/pdf");
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        VActivityManager.get().startActivity(intent, 0);
                        Looper.loop();







//                    Looper.prepare();
//                    if(PluginHelp.checkPluginInstall("com.dynamixsoftware.printershare")){
//
////                        PackageManager pm = activity.getPackageManager();
////                        Intent intent = pm.getLaunchIntentForPackage("com.dynamixsoftware.printershare");
////                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                        Uri fileuri = Uri.fromFile(new File(pdfpath));
////                        activity.startActivity(intent);
//                        ComponentName comp = new ComponentName("com.dynamixsoftware.printershare","com.dynamixsoftware.printershare.ActivityPrintPDF");
//                        Uri fileuri = Uri.fromFile(new File(pdfpath));
//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        intent.setComponent(comp);
//                        intent.setDataAndType(fileuri, "application/pdf");
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        activity.startActivity(intent);
//                    }else{
//                        try {
//                            final int re = PluginManager.getInstance().installPackage(Environment.getExternalStorageDirectory()+"/PrinterShare-11.10.0.apk", 0);//安装apk
//                            switch (re) {
//                                case PluginManager.INSTALL_FAILED_NO_REQUESTEDPERMISSION:
//                                    Toast.makeText(activity, "安装失败，文件请求的权限太多", Toast.LENGTH_SHORT).show();
//                                    break;
//                                case INSTALL_FAILED_NOT_SUPPORT_ABI:
//                                    Toast.makeText(activity, "宿主不支持插件的abi环境，可能宿主运行时为64位，但插件只支持32位", Toast.LENGTH_SHORT).show();
//                                    break;
//                                case INSTALL_SUCCEEDED:
//                                    Toast.makeText(activity, "安装完成", Toast.LENGTH_SHORT).show();
////                                    PackageManager pm = activity.getPackageManager();
////                                    Intent intent = pm.getLaunchIntentForPackage("com.dynamixsoftware.printershare");
////                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                                    Uri fileuri = Uri.fromFile(new File(pdfpath));
////                                    intent.setDataAndType(fileuri, "application/pdf");
////                                    activity.startActivity(intent);
//                                    ComponentName comp = new ComponentName("com.dynamixsoftware.printershare","com.dynamixsoftware.printershare.ActivityPrintPDF");
//                                    Uri fileuri = Uri.fromFile(new File(pdfpath));
//                                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                                    intent.setComponent(comp);
//                                    intent.setDataAndType(fileuri, "application/pdf");
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    activity.startActivity(intent);
//                                    break;
//                            }
//                        } catch (RemoteException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    Looper.loop();
                }
            }).start();

        } else {
//            Uri fileuri = Uri.fromFile(new File(pdfpath));
//            if (Util_Apk.appIsInstalled13(Static_InfoApp.create().getContext(), Static_ConstantLib.pack)) {
//                File file = new File(fileuri.toString());
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                i.setPackage(Static_ConstantLib.pack);
//                i.setDataAndType(fileuri, "application/pdf");
//                activity.startActivity(i);
//            } else {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Looper.prepare();
//                        Toast.makeText(Static_InfoApp.create().getContext(), "安装打印插件", Toast.LENGTH_SHORT).show();
//                        Util_Apk.appInstall13(activity, Static_ConstantLib.pack, Static_ConstantLib.apk);
//                        Looper.loop();
//                    }
//                }).start();
//
//            }
        }

    }
}
