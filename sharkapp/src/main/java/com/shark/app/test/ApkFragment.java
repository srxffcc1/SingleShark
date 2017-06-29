package com.shark.app.test;

import com.businessframehelp.app.BaseFragment;

/**
 * Created by Administrator on 2017/5/14.
 */

public class ApkFragment extends BaseFragment{// implements ServiceConnection {
//    private ArrayAdapter<ApkItem> adapterall;
//    private ArrayAdapter<ApkItem> adapteralready;
//    final Handler handler = new Handler();
//    boolean isViewCreated = false;
//    ListView allapklist;
//    ListView alreadyapklist;
//    private ApkBroadcastReceiver mMyBroadcastReceiver = new ApkBroadcastReceiver();
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mMyBroadcastReceiver.registerReceiver(getActivity().getApplication());
//        adapteralready = new ArrayAdapter<ApkItem>(getActivity(), 0) {
//            @Override
//            public View getView(final int position, View convertView, ViewGroup parent) {
//                if (convertView == null) {
//                    convertView = LayoutInflater.from(getActivity()).inflate(R.layout.apk_item, null);
//                }
//                ApkItem item = getItem(position);
//
//                ImageView icon = (ImageView) convertView.findViewById(R.id.imageView);
//                icon.setImageDrawable(item.icon);
//
//                TextView title = (TextView) convertView.findViewById(R.id.textView1);
//                title.setText(item.title);
//
//                final TextView version = (TextView) convertView.findViewById(R.id.textView2);
//                version.setText(String.format("%s(%s)", item.versionName, item.versionCode));
//
//                TextView btn = (TextView) convertView.findViewById(R.id.button2);
//                btn.setText("打开");
//                btn.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View view) {
//                        onAlreadyListItemClick(alreadyapklist, view, position, getItemId(position));
//                    }
//                });
//
//                btn = (TextView) convertView.findViewById(R.id.button3);
//                btn.setText("卸载");
//                btn.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View view) {
//                        onAlreadyListItemClick(alreadyapklist, view, position, getItemId(position));
//                    }
//                });
//
//                return convertView;
//            }
//        };
//        adapterall = new ArrayAdapter<ApkItem>(getActivity(), 0) {
//            @Override
//            public View getView(final int position, View convertView, final ViewGroup parent) {
//                if (convertView == null) {
//                    convertView = LayoutInflater.from(getActivity()).inflate(R.layout.apk_item, null);
//                }
//                ApkItem item = getItem(position);
//
//                ImageView icon = (ImageView) convertView.findViewById(R.id.imageView);
//                icon.setImageDrawable(item.icon);
//
//                TextView title = (TextView) convertView.findViewById(R.id.textView1);
//                title.setText(item.title);
//
//                TextView version = (TextView) convertView.findViewById(R.id.textView2);
//                version.setText(String.format("%s(%s)", item.versionName, item.versionCode));
//
//                TextView btn3 = (TextView) convertView.findViewById(R.id.button3);
//                btn3.setText("删除");
//                btn3.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View view) {
//                        onAllListItemClick(allapklist, view, position, getItemId(position));
//                    }
//                });
//                TextView btn = (TextView) convertView.findViewById(R.id.button2);
//                try {
//                    if (item.installing) {
//                        btn.setText("安装中ing");
//                    } else {
//                        if (PluginManager.getInstance().isConnected()) {
//                            btn.setText(PluginManager.getInstance().getPackageInfo(item.packageInfo.packageName, 0) != null ? "已经安装" : "安装");
//                        } else {
//                            btn.setText("等待初始化服务");
//                        }
//                    }
//                } catch (Exception e) {
//                    btn.setText("安装1");
//                }
//                btn.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View view) {
//                        onAllListItemClick(allapklist, view, position, getItemId(position));
//                    }
//                });
//
//
//                return convertView;
//            }
//        };
//    }
//    public void onAlreadyListItemClick(ListView l, View v, int position, long id) {
//        ApkItem item = adapteralready.getItem(position);
//        if (v.getId() == R.id.button2) {
//
//            PackageManager pm = getActivity().getPackageManager();
//            Intent intent = pm.getLaunchIntentForPackage(item.packageInfo.packageName);
//
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        } else if (v.getId() == R.id.button3) {
//            doUninstall(item);
//        }
//    }
//    private void doUninstall(final ApkItem item) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("警告，你确定要删除么？");
//        builder.setMessage("警告，你确定要删除" + item.title + "么？");
//        builder.setNegativeButton("删除", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (!PluginManager.getInstance().isConnected()) {
//                    Toast.makeText(getActivity(), "服务未连接", Toast.LENGTH_SHORT).show();
//                } else {
//                    try {
//                        PluginManager.getInstance().deletePackage(item.packageInfo.packageName, 0);
//                        Toast.makeText(getActivity(), "删除完成", Toast.LENGTH_SHORT).show();
//                    } catch (RemoteException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        builder.setNeutralButton("取消", null);
//        builder.show();
//    }
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        View view=inflater.inflate(R.layout.plugin_fragment,container,false);
//        allapklist= (ListView) view.findViewById(R.id.allapk);
//        allapklist.setAdapter(adapterall);
//
//        alreadyapklist= (ListView) view.findViewById(R.id.alreadyapk);
//        alreadyapklist.setAdapter(adapteralready);
//        return view;
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        isViewCreated = true;
//        if (PluginManager.getInstance().isConnected()) {
//            startLoad();
//        } else {
//            PluginManager.getInstance().addServiceConnection(this);
//        }
//    }
//    public void startLoad(){
//        if (!isViewCreated) {
//            return;
//        }
//
//        new Thread("ApkScanner") {
//            @Override
//            public void run() {
//                try {
//                    final List<PackageInfo> infos = PluginManager.getInstance().getInstalledPackages(0);
//                    final PackageManager pm = getActivity().getPackageManager();
//
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            adapteralready.clear();
//                        }
//                    });
//                    for (final PackageInfo info : infos) {
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                adapteralready.add(new ApkItem(pm, info, info.applicationInfo.publicSourceDir));
//                            }
//                        });
//                    }
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }.start();
//
//        new Thread("ApkScanner") {
//            @Override
//            public void run() {
//                File file = Environment.getExternalStorageDirectory();
//                List<File> apks = new ArrayList<File>(10);
//                File[] files = file.listFiles();
//                if (files != null) {
//                    for (File apk : files) {
//                        if (apk.exists() && apk.getPath().toLowerCase().endsWith(".apk")) {
//                            apks.add(apk);
//                        }
//                    }
//                }
//                file = new File(Environment.getExternalStorageDirectory(), "360Download");
//                if (file.exists() && file.isDirectory()) {
//                    File[] files1 = file.listFiles();
//                    if (files1 != null) {
//                        for (File apk : files1) {
//                            if (apk.exists() && apk.getPath().toLowerCase().endsWith(".apk")) {
//                                apks.add(apk);
//                            }
//                        }
//                    }
//                }
//                PackageManager pm = getActivity().getPackageManager();
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        adapterall.clear();
//                    }
//                });
//
//                for (final File apk : apks) {
//                    try {
//                        if (apk.exists() && apk.getPath().toLowerCase().endsWith(".apk")) {
//                            final PackageInfo info = pm.getPackageArchiveInfo(apk.getPath(), 0);
//                            if (info != null && isViewCreated) {
//                                try {
//                                    handler.post(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            adapterall.add(new ApkItem(getActivity(), info, apk.getPath()));
//                                        }
//                                    });
//                                } catch (Exception e) {
//                                }
//                            }
//                        }
//                    } catch (Exception e) {
//                    }
//                }
//            }
//        }.start();
//    }
//    @Override
//    public void onServiceConnected(ComponentName name, IBinder service) {
//        startLoad();
//
//    }
//    @Override
//    public void onDestroy() {
//        PluginManager.getInstance().removeServiceConnection(this);
//        mMyBroadcastReceiver.unregisterReceiver(getActivity().getApplication());
//        super.onDestroy();
//    }
//    @Override
//    public void onServiceDisconnected(ComponentName name) {
//
//    }
//    public void onAllListItemClick(ListView l, View v, int position, long id) {
//        final ApkItem item = adapterall.getItem(position);
//        if (v.getId() == R.id.button2) {
//            if (item.installing) {
//                return;
//            }
//            if (!PluginManager.getInstance().isConnected()) {
//                Toast.makeText(getActivity(), "插件服务正在初始化，请稍后再试。。。", Toast.LENGTH_SHORT).show();
//            }
//            try {
//                if (PluginManager.getInstance().getPackageInfo(item.packageInfo.packageName, 0) != null) {
//                    Toast.makeText(getActivity(), "已经安装了，不能再安装", Toast.LENGTH_SHORT).show();
//                } else {
//                    new Thread() {
//                        @Override
//                        public void run() {
//                            doInstall(item);
//                        }
//                    }.start();
//
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                try {
//                    PluginManager.getInstance().installPackage(item.apkfile, 0);
//                } catch (RemoteException e1) {
//                    e1.printStackTrace();
//                }
//                adapterall.remove(item);
//            }
//        } else if (v.getId() == R.id.button3) {
//            doRemove(item);
//        }
//    }
//    private void doRemove(final ApkItem item) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("警告，你确定要删除么？");
//        builder.setMessage("警告，你确定要删除" + item.title + "么？");
//        builder.setNegativeButton("删除", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                new File(item.apkfile).delete();
//                adapterall.remove(item);
//                Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
//            }
//        });
//        builder.setNeutralButton("取消", null);
//        builder.show();
//    }
//    private synchronized void doInstall(ApkItem item) {
//        item.installing = true;
//
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                adapterall.notifyDataSetChanged();
//            }
//        });
//        try {
//            final int re = PluginManager.getInstance().installPackage(item.apkfile, 0);
//            item.installing = false;
//
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    switch (re) {
//                        case PluginManager.INSTALL_FAILED_NO_REQUESTEDPERMISSION:
//                            Toast.makeText(getActivity(), "安装失败，文件请求的权限太多", Toast.LENGTH_SHORT).show();
//                            break;
//                        case INSTALL_FAILED_NOT_SUPPORT_ABI:
//                            Toast.makeText(getActivity(), "宿主不支持插件的abi环境，可能宿主运行时为64位，但插件只支持32位", Toast.LENGTH_SHORT).show();
//                            break;
//                        case INSTALL_SUCCEEDED:
//                            Toast.makeText(getActivity(), "安装完成", Toast.LENGTH_SHORT).show();
//                            adapterall.notifyDataSetChanged();
//                            break;
//                    }
//
//                }
//            });
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//    }
//    private class ApkBroadcastReceiver extends BroadcastReceiver {
//
//        void registerReceiver(Context con) {
//            IntentFilter f = new IntentFilter();
//            f.addAction(PluginManager.ACTION_PACKAGE_ADDED);
//            f.addAction(PluginManager.ACTION_PACKAGE_REMOVED);
//            f.addDataScheme("package");
//            con.registerReceiver(this, f);
//        }
//
//        void unregisterReceiver(Context con) {
//            con.unregisterReceiver(this);
//        }
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (PluginManager.ACTION_PACKAGE_ADDED.equals(intent.getAction())) {
//                    startLoad();
//            } else if (PluginManager.ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {
//                    startLoad();
//            }
//        }
//    }
}
