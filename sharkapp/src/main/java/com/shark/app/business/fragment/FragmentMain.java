package com.shark.app.business.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.businessframehelp.app.BaseSupportFragment;
import com.businessframehelp.design.DividerGridItemDecoration;
import com.shark.app.R;
import com.shark.app.business.adapter.ImageClickRecycleAdapter;
import com.shark.app.business.entity.ImageMenuEntity;
import com.shark.app.business.singleactivity.ActivityCollect;
import com.shark.app.business.singleactivity.ActivityEnterpriseList;
import com.shark.app.business.singleactivity.tab.ActivityCheckUpList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */

public class FragmentMain extends BaseSupportFragment {

    private List<ImageMenuEntity> list_1;
    private List<ImageMenuEntity> list_2;
    private RecyclerView recycleview_main_1;
    private RecyclerView recycleview_main_2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appmain,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recycleview_main_1 = (RecyclerView) findViewById(R.id.recycleview_main_1);
        GridLayoutManager manager=new GridLayoutManager(getContext(),2, LinearLayoutManager.HORIZONTAL,false);
        manager.setAutoMeasureEnabled(true);
        recycleview_main_1.setLayoutManager(manager);
        recycleview_main_1.addItemDecoration(new DividerGridItemDecoration(getContext()).setSpace(0,0,0,0));
        list_1 = new ArrayList<>();
        list_1.add(new ImageMenuEntity().setImageRid(R.drawable.main_enterpiseinfo).setClickpassclass(ActivityEnterpriseList.class));
        list_1.add(new ImageMenuEntity().setImageRid(R.drawable.main_printer).setClickpassclass(null));
        list_1.add(new ImageMenuEntity().setImageRid(R.drawable.main_checkup).setClickpassclass(ActivityCheckUpList.class));
        list_1.add(new ImageMenuEntity().setImageRid(R.drawable.main_checkuprecord).setClickpassclass(null));
        list_1.add(new ImageMenuEntity().setImageRid(R.drawable.main_law).setClickpassclass(null));
        list_1.add(new ImageMenuEntity().setImageRid(R.drawable.main_collect).setClickpassclass(ActivityCollect.class));
        list_1.add(new ImageMenuEntity().setImageRid(R.drawable.main_troubleinvestigation).setClickpassclass(null));
        list_1.add(new ImageMenuEntity().setImageRid(R.drawable.main_dangerousorg).setClickpassclass(null));
//        list_1.add(new MainMenuEntity().setImageRid(R.drawable.main_oneenterpriseonerule));
//        list_1.add(new MainMenuEntity().setImageRid(R.drawable.main_orderlist));
//        list_1.add(new MainMenuEntity().setImageRid(R.drawable.main_paperseach));
//        list_1.add(new MainMenuEntity().setImageRid(R.drawable.main_selfcheckup));

        recycleview_main_1.setAdapter(new ImageClickRecycleAdapter(this, list_1){

            @Override
            protected int getImageHeight() {
                return -1;
            }

            @Override
            protected int getImageWidth() {
                return 180;
            }
        }.setLongclicksameclick(true));
        GridLayoutManager manager2=new GridLayoutManager(getContext(),2, LinearLayoutManager.HORIZONTAL,false);
        manager2.setAutoMeasureEnabled(true);
        recycleview_main_2 = (RecyclerView) findViewById(R.id.recycleview_main_2);
        recycleview_main_2.setLayoutManager(manager2);
        recycleview_main_2.addItemDecoration(new DividerGridItemDecoration(getContext()).setSpace(0,0,0,0).setSpaceColor(Color.parseColor("#FF9E99")));
        list_2 = new ArrayList<>();
        list_2.add(new ImageMenuEntity().setImageRid(R.drawable.library_aqscbz).setClickpassclass(null));
        list_2.add(new ImageMenuEntity().setImageRid(R.drawable.library_aqscflfg).setClickpassclass(null));
        list_2.add(new ImageMenuEntity().setImageRid(R.drawable.library_dxsgal).setClickpassclass(null));
        list_2.add(new ImageMenuEntity().setImageRid(R.drawable.library_wxhxptz).setClickpassclass(null));
        list_2.add(new ImageMenuEntity().setImageRid(R.drawable.library_yhpcbz).setClickpassclass(null));
        list_2.add(new ImageMenuEntity().setImageRid(R.drawable.library_zfyj).setClickpassclass(null));
        recycleview_main_2.setAdapter(new ImageClickRecycleAdapter(this, list_2){

            @Override
            protected int getImageHeight() {
                return -1;
            }

            @Override
            protected int getImageWidth() {
                return 230;
            }
        });

    }





}
