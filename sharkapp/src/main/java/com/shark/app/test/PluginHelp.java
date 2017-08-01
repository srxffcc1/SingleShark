package com.shark.app.test;

import android.content.pm.PackageInfo;
import android.os.RemoteException;

import com.morgoo.droidplugin.pm.PluginManager;

import java.util.List;

/**
 * Created by King6rf on 2017/8/1.
 */

public class PluginHelp {
    public static  boolean checkPluginInstall(String checkpackagename){
        boolean result=false;
        try {
            final List<PackageInfo> infos = PluginManager.getInstance().getInstalledPackages(0);
            for (int i = 0; i <infos.size() ; i++) {
                if(infos.get(i).packageName.equals(checkpackagename)){
                    return true;
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }
}
