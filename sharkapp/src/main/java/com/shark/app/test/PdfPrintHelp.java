package com.shark.app.test;

import android.app.Activity;

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
//                    Looper.prepare();
//                    InstallResult res=VirtualCore.get().installPackage(Environment.getExternalStorageDirectory()+"/PrinterShare-11.10.0.apk", InstallStrategy.TERMINATE_IF_EXIST);
////                    if (res.isSuccess) {
////                        try {
////                            VirtualCore.get().preOpt(res.packageName);
////                        } catch (IOException e) {
////                            e.printStackTrace();
////                        }
////                        if (res.isUpdate) {
////                            Toast.makeText(activity, "Update: " + res.packageName + " success!", Toast.LENGTH_SHORT).show();
////                        } else {
////                            Toast.makeText(activity, "Install: " + res.packageName + " success!", Toast.LENGTH_SHORT).show();
////                        }
////                    } else {
////                        Toast.makeText(activity, "Install failed: " + res.error, Toast.LENGTH_SHORT).show();
////                    }
//                        ComponentName comp = new ComponentName("com.dynamixsoftware.printershare","com.dynamixsoftware.printershare.ActivityPrintPDF");
//                        Uri fileuri = Uri.fromFile(new File(pdfpath));
//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        intent.setComponent(comp);
//                        intent.setDataAndType(fileuri, "application/pdf");
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        VActivityManager.get().startActivity(intent, 0);
//                        Looper.loop();

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
