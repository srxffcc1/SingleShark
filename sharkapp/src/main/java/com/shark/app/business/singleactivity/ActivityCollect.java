package com.shark.app.business.singleactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.design.DividerGridItemDecoration;
import com.businessframehelp.enums.ORIENTATION;
import com.businessframehelp.staticlib.StaticAppInfo;
import com.businessframehelp.utils.BarUtil;
import com.businessframehelp.utils.FrameUtil;
import com.kymjs.common.FileUtils;
import com.shark.app.R;
import com.shark.app.business.adapter.Adapter_CollectList;
import com.shark.app.business.entity.EntityS_File;
import com.shark.app.business.entity.ExpandMap;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */

public class ActivityCollect extends FrameActivity {

    RecyclerView sourceGrid;
    GridLayoutManager staggeredGridLayoutManager;
    LinearLayout needshowunder;
    private List<ExpandMap> expandList;
    private Adapter_CollectList adapter;
    @Override
    public ORIENTATION getORIENTATION() {
        return null;
    }

    @Override
    public void handleMessage(Message msg) {

    }

    @Override
    public int getMenuid() {
        return R.menu.collect_menu;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        BarUtil.initBar(this,"调查取证");
        new File(StaticAppInfo.getInstance().getProjcetDir() + "/ZhiCollect/" + "/tmp/").mkdirs();
        RecyclerView sourceGrid=(RecyclerView) findViewById(R.id.sourceGrid);
        expandList=new ArrayList<ExpandMap>();
        adapter = new Adapter_CollectList(this, expandList);
        staggeredGridLayoutManager = new GridLayoutManager(this,4, OrientationHelper.VERTICAL,false);
        sourceGrid.addItemDecoration(new DividerGridItemDecoration(getContext()).setSpace(3,3,3,3));
        adapter = new Adapter_CollectList(this, expandList);
        sourceGrid.setLayoutManager(staggeredGridLayoutManager);
        sourceGrid.setAdapter(adapter);
        init();
    }
    public void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File delete = new File(StaticAppInfo.getInstance().getProjcetDir() + "/ZhiCollect/" + "/tmp" + "/" + ".jpg");
                if (delete.exists()) {
                    delete.delete();
                }
                File dir = new File(StaticAppInfo.getInstance().getProjcetDir() + "/ZhiCollect/" + "/tmp/");
                expandList.clear();
                if (dir.exists()) {
                    List<File> file = FileUtils.getFilesSortAsc(dir);
                    for (int i = 0; i < file.size(); i++) {
                        String datastring = new SimpleDateFormat("yyyy-MM-dd")
                                .format(file.get(i).lastModified());
                        if (expandList.size() > 0) {
                            if (expandList.get(expandList.size() - 1).getName().equals(datastring)) {
                                expandList.add(new ExpandMap(datastring).setViewtype(1).setEntityvalue(new EntityS_File(getContext(), file.get(i))));
                            } else {
                                expandList.add(new ExpandMap(datastring).setViewtype(0));
                                expandList.add(new ExpandMap(datastring).setViewtype(1).setEntityvalue(new EntityS_File(getContext(), file.get(i))));
                            }
                        } else {
                            expandList.add(new ExpandMap(datastring).setViewtype(0));
                            expandList.add(new ExpandMap(datastring).setViewtype(1).setEntityvalue(new EntityS_File(getContext(), file.get(i))));
                        }
                    }

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();


//		expandList.add(new ExpandMap("2017-06-14").setViewtype(0));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_searchs){
            System.out.println("点击了综合查询");
        }
        if(item.getItemId()==R.id.action_camera){
            FrameUtil.instance().startCameraIntent(this, 100, StaticAppInfo.getInstance().getProjcetDir() + "/ZhiCollect/" + "/tmp" + "/shark_"+System.currentTimeMillis()+".jpg");
        }
        if(item.getItemId()==R.id.action_video){
            FrameUtil.instance().startVideoIntent(this, 100, StaticAppInfo.getInstance().getProjcetDir() + "/ZhiCollect/" + "/tmp" + "/shark_"+System.currentTimeMillis()+".mp4");
        }
        if(item.getItemId()==R.id.action_voice){
            FrameUtil.instance().startRecord(this, 100, StaticAppInfo.getInstance().getProjcetDir() + "/ZhiCollect/" + "/tmp" + "/");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            init();
        }
    }
}
