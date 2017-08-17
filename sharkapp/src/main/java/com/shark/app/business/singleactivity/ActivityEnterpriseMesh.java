package com.shark.app.business.singleactivity;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.design.DividerGridItemDecoration;
import com.businessframehelp.enums.ORIENTATION;
import com.shark.app.R;
import com.shark.app.business.adapter.ImageTextClickRecycleAdapter;
import com.shark.app.business.entity.ImageTextMenuEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */

public class ActivityEnterpriseMesh extends FrameActivity {

    private List<ImageTextMenuEntity> list_1;
    @Override
    public ORIENTATION getORIENTATION() {
        return null;
    }

    @Override
    public void handleMessage(Message msg) {

    }

    @Override
    public int getMenuid() {
        return -1;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.include_recycle);
        GridLayoutManager manager=new GridLayoutManager(this,3, LinearLayoutManager.VERTICAL,false);
        RecyclerView recycler_view= (RecyclerView) findViewById(R.id.recycler_view);
        list_1 = new ArrayList<>();
        list_1.add(new ImageTextMenuEntity("基本信息").setImageRid(R.drawable.mesh_xx).setClickpassclass(ActivityEnterpriseBaseDetail.class));
        list_1.add(new ImageTextMenuEntity("处罚记录").setImageRid(R.drawable.mesh_cfjl).setClickpassclass(null));
        list_1.add(new ImageTextMenuEntity("隐患排查").setImageRid(R.drawable.mesh_yhpc).setClickpassclass(null));
        list_1.add(new ImageTextMenuEntity("检查情况").setImageRid(R.drawable.mesh_jcjl).setClickpassclass(null));
        list_1.add(new ImageTextMenuEntity("许可证").setImageRid(R.drawable.mesh_xkz).setClickpassclass(null));
        list_1.add(new ImageTextMenuEntity("人员资质").setImageRid(R.drawable.mesh_zz).setClickpassclass(null));
        list_1.add(new ImageTextMenuEntity("职业卫生").setImageRid(R.drawable.mesh_ws).setClickpassclass(null));
        list_1.add(new ImageTextMenuEntity("危险源").setImageRid(R.drawable.mesh_wxy).setClickpassclass(null));
        list_1.add(new ImageTextMenuEntity("其他").setImageRid(R.drawable.mesh_qt).setClickpassclass(null));

        manager.setAutoMeasureEnabled(true);
        recycler_view.setLayoutManager(manager);
        recycler_view.addItemDecoration(new DividerGridItemDecoration(getContext()).setSpace(0,0,0,70));
        recycler_view.setAdapter(new ImageTextClickRecycleAdapter(this,list_1) {
            @Override
            protected int getImageWidth() {
                return -1;
            }

            @Override
            protected int getImageHeight() {
                return 170;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_searchs){
//            //System.out.println("点击了综合查询");
        }
        return super.onOptionsItemSelected(item);
    }


}
