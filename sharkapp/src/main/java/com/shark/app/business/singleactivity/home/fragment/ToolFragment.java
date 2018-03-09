package com.shark.app.business.singleactivity.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shark.app.R;


public class ToolFragment extends BaseFragment {

    /**
     * 标志位，标志已经初始化完成
     */
    private boolean isPrepared;
    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    private boolean mHasLoaded;

    private TextView title, notice, contacts, video, production_standards, chemical_library, latent_danger, law_library, accident_case, production_regulations, notice_num;
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("----------------", "工具");
        if (mView == null) {
            // 需要inflate一个布局文件 填充Fragment
            mView = inflater.inflate(R.layout.part_tool, container, false);
            //StatusBarCompat.setStatusBarColor(getActivity(), getResources().getColor(R.color.colorPrimary), true);
            initView();
            isPrepared = true;
            // 实现懒加载
            lazyLoad();
        }
        //缓存的mView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个mView已经有parent的错误。
        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }

        return mView;
    }

    /**
     * 初始化控件
     */
    private void initView() {
        title = find(R.id.title);
        title.setText("辅助工具");
        toolbar = find(R.id.toolbar);
        notice = find(R.id.notice);
        contacts = find(R.id.contacts);
        video = find(R.id.video);
        production_standards = find(R.id.production_standards);
        chemical_library = find(R.id.chemical_library);
        latent_danger = find(R.id.latent_danger);
        law_library = find(R.id.law_library);
        accident_case = find(R.id.accident_case);
        production_regulations = find(R.id.production_regulations);
        notice_num = find(R.id.notice_num);
        notice_num.setText("3");

    }

    @Override
    public void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoaded) {
            return;
        }
        //填充各控件的数据
        mHasLoaded = true;
    }
}
