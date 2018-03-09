package com.shark.app.business.singleactivity.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shark.app.R;
import com.shark.app.business.singleactivity.home.view.CircleImageView;

public class MineFragment extends BaseFragment {

    /**
     * 标志位，标志已经初始化完成
     */
    private boolean isPrepared;
    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    private boolean mHasLoaded;

    private TextView update_pwd, mine_info_settings, portrait_settings, custom_settings, exit;
    private RelativeLayout mine, mine_head;
    private CircleImageView iv_mine_head;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("----------------", "我的");
        if (mView == null) {
            // 需要inflate一个布局文件 填充Fragment
            mView = inflater.inflate(R.layout.part_mine, container, false);
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
        update_pwd = find(R.id.update_pwd);
        mine_info_settings = find(R.id.mine_info_settings);
        portrait_settings = find(R.id.portrait_settings);
        custom_settings = find(R.id.custom_settings);
        mine = find(R.id.mine);
        mine_head = find(R.id.mine_head);
        iv_mine_head = find(R.id.iv_mine_head);
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
