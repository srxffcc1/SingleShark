package com.shark.app.test;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Looper;
import android.os.RemoteException;
import android.widget.Toast;

import com.morgoo.droidplugin.pm.PluginManager;

import java.io.File;

import static com.morgoo.helper.compat.PackageManagerCompat.INSTALL_FAILED_NOT_SUPPORT_ABI;
import static com.morgoo.helper.compat.PackageManagerCompat.INSTALL_SUCCEEDED;

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
                    if(PluginHelp.checkPluginInstall("com.dynamixsoftware.printershare")){

                        PackageManager pm = activity.getPackageManager();
                        Intent intent = pm.getLaunchIntentForPackage("com.dynamixsoftware.printershare");
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Uri fileuri = Uri.fromFile(new File(pdfpath));
                        intent.setDataAndType(fileuri, "application/pdf");
                        activity.startActivity(intent);
//                        Uri fileuri = Uri.fromFile(new File(pdfpath));
//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        intent.setClassName(activity, "com.dynamixsoftware.printershare.ActivityPrintPDF");
//                        intent.setDataAndType(fileuri, "application/pdf");
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        activity.startActivity(intent);
                    }else{
                        try {
                            final int re = PluginManager.getInstance().installPackage(Environment.getExternalStorageDirectory()+"/PrinterShare-11.10.0.apk", 0);//安装apk
                            switch (re) {
                                case PluginManager.INSTALL_FAILED_NO_REQUESTEDPERMISSION:
                                    Toast.makeText(activity, "安装失败，文件请求的权限太多", Toast.LENGTH_SHORT).show();
                                    break;
                                case INSTALL_FAILED_NOT_SUPPORT_ABI:
                                    Toast.makeText(activity, "宿主不支持插件的abi环境，可能宿主运行时为64位，但插件只支持32位", Toast.LENGTH_SHORT).show();
                                    break;
                                case INSTALL_SUCCEEDED:
                                    Toast.makeText(activity, "安装完成", Toast.LENGTH_SHORT).show();
                                    PackageManager pm = activity.getPackageManager();
                                    Intent intent = pm.getLaunchIntentForPackage("com.dynamixsoftware.printershare");
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    Uri fileuri = Uri.fromFile(new File(pdfpath));
                                    intent.setDataAndType(fileuri, "application/pdf");
                                    activity.startActivity(intent);

//                                    Uri fileuri = Uri.fromFile(new File(pdfpath));
//                                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                                    intent.setClassName(activity, "com.dynamixsoftware.printershare.ActivityPrintPDF");
//                                    intent.setDataAndType(fileuri, "application/pdf");
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    activity.startActivity(intent);
                                    break;
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }

                    Looper.loop();
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
