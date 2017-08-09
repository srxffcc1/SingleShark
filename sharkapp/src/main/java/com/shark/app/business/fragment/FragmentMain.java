package com.shark.app.business.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.businessframehelp.app.BaseSupportFragment;
import com.businessframehelp.design.DividerGridItemDecoration;
import com.shark.app.R;
import com.shark.app.business.entity.MainMenuEntity;
import com.shark.app.business.singleactivity.ActivityEnterpriseList;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */

public class FragmentMain extends BaseSupportFragment {

    private List<MainMenuEntity> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appmain,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recycleview);
        GridLayoutManager manager=new GridLayoutManager(getContext(),2, LinearLayoutManager.HORIZONTAL,false);
        StaggeredGridLayoutManager manager2=new StaggeredGridLayoutManager(6,LinearLayoutManager.HORIZONTAL);
//        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        manager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerGridItemDecoration(getContext()).setSpace(1,1,20,1));

        list = new ArrayList<>();
        list.add(new MainMenuEntity().setImageRid(R.drawable.main_enterpiseinfo).setClickpassclass(ActivityEnterpriseList.class));
        list.add(new MainMenuEntity().setImageRid(R.drawable.main_printer).setClickpassclass(null));
        list.add(new MainMenuEntity().setImageRid(R.drawable.main_checkup).setClickpassclass(null));
        list.add(new MainMenuEntity().setImageRid(R.drawable.main_checkuprecord).setClickpassclass(null));
        list.add(new MainMenuEntity().setImageRid(R.drawable.main_law).setClickpassclass(null));
        list.add(new MainMenuEntity().setImageRid(R.drawable.main_collect).setClickpassclass(null));
        list.add(new MainMenuEntity().setImageRid(R.drawable.main_troubleinvestigation).setClickpassclass(null));
        list.add(new MainMenuEntity().setImageRid(R.drawable.main_dangerousorg).setClickpassclass(null));

//        list.add(new MainMenuEntity().setImageRid(R.drawable.main_oneenterpriseonerule));
//        list.add(new MainMenuEntity().setImageRid(R.drawable.main_orderlist));
//        list.add(new MainMenuEntity().setImageRid(R.drawable.main_paperseach));
//        list.add(new MainMenuEntity().setImageRid(R.drawable.main_selfcheckup));

        recyclerView.setAdapter(new MainAdapter(this));
    }
    class MainAdapter extends RecyclerView.Adapter<MainAdapter.SingelViewHolder>{
        public Activity mactivity;
        public Fragment mfragment;
        public android.support.v4.app.Fragment msupportfragment;

        public MainAdapter(Fragment mfragment) {
            this.mfragment = mfragment;
        }

        public MainAdapter(android.support.v4.app.Fragment msupportfragment) {
            this.msupportfragment = msupportfragment;
        }

        public MainAdapter(Activity mactivity) {
            this.mactivity = mactivity;
        }

        @Override
        public SingelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView=LayoutInflater.from(getContext()).inflate(R.layout.item_fragmentcard,parent,false);
            AutoUtils.autoSize(itemView);
            return new SingelViewHolder(itemView);
        }



        @Override
        public void onBindViewHolder(SingelViewHolder holder, int position) {
            holder.autoClickImageView.setImageResource(list.get(position).getImageRid());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class SingelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
            ImageView autoClickImageView;
            public SingelViewHolder(View itemView) {
                super(itemView);
                autoClickImageView= (ImageView) itemView.findViewById(R.id.autoClickImageView);
                autoClickImageView.setOnClickListener(this);
                autoClickImageView.setOnLongClickListener(this);
            }

            @Override
            public void onClick(View v) {
//                System.out.println("点击项目"+getAdapterPosition());
                Class desclass=list.get(getAdapterPosition()).getClickpassclass();
                if(desclass!=null){
                    if(mactivity!=null){
                        mactivity.startActivity(new Intent(getActivity(),desclass));
                    }else if(mfragment!=null){
                        mfragment.startActivity(new Intent(getActivity(),desclass));
                    }else{
                        msupportfragment.startActivity(new Intent(getActivity(),desclass));
                    }
                }
            }

            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        }
    }
//    public class SpaceItemDecoration extends RecyclerView.ItemDecoration{
//
//        private int space;
//
//        public SpaceItemDecoration(int space) {
//            this.space = space;
//        }
//
//        @Override
//        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//
//            if(parent.getChildPosition(view) != 0)
//                outRect.top = space;
//        }
//    }



}
