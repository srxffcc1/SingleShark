package com.shark.app.business.singleactivity.home.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.shark.app.R;
import com.shark.app.business.singleactivity.module.ActivityEnterpriseList;
import com.shark.app.business.singleactivity.tab.ActivityCheckHost2Guest;


public class HomeFragment extends BaseFragment implements View.OnClickListener {

    /**
     * 标志位，标志已经初始化完成
     */
    private boolean isPrepared;
    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    private boolean mHasLoaded;

    private View title, enterprise_info, law_enforcement, latent_danger, case_handling, notice, publisher, time, danger_type, danger_level, law_enforcement_time, law_enforcer;
    private Toolbar toolbar;
    private ImageView iv_more;

    PopupWindow mPopupWindow;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("----------------", "首页");
        if (mView == null) {
            // 需要inflate一个布局文件 填充Fragment
            mView = inflater.inflate(R.layout.part_home, container, false);
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
        toolbar = find(R.id.toolbar);
        title = find(R.id.title);
        ((TextView)title).setText("移动智慧安监平台");
        iv_more = find(R.id.iv_more);
//        iv_more.setVisibility(View.VISIBLE);
        enterprise_info = find(R.id.enterprise_info);
        law_enforcement = find(R.id.law_enforcement);
        latent_danger = find(R.id.latent_danger);
        case_handling = find(R.id.case_handling);
        notice = find(R.id.notice);
        publisher = find(R.id.publisher);
        time = find(R.id.time);
        danger_type = find(R.id.danger_type);
        danger_level = find(R.id.danger_level);
        law_enforcement_time = find(R.id.law_enforcement_time);
        law_enforcer = find(R.id.law_enforcer);
        enterprise_info.setOnClickListener(this);
        law_enforcement.setOnClickListener(this);
        iv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpMyOverflow();
            }
        });

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }


    @Override
    public void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoaded) {
            return;
        }
        //填充各控件的数据
        mHasLoaded = true;
    }


    /**
     * 弹出自定义的popWindow
     */
    public void popUpMyOverflow() {
        //获取状态栏高度
        Rect frame = new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        //状态栏高度+toolbar的高度
        int yOffset = frame.top + toolbar.getHeight();
        if (null == mPopupWindow) {
            //初始化PopupWindow的布局
            View popView = getActivity().getLayoutInflater().inflate(R.layout.action_overflow_popwindow, null);
            //popView即popupWindow的布局，ture设置focusAble.
            mPopupWindow = new PopupWindow(popView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
            //必须设置BackgroundDrawable后setOutsideTouchable(true)才会有效
            mPopupWindow.setBackgroundDrawable(new ColorDrawable());
            //点击外部关闭。
            mPopupWindow.setOutsideTouchable(true);
            //设置一个动画。
            mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
            //设置Gravity，让它显示在右上角。
            mPopupWindow.showAtLocation(toolbar, Gravity.RIGHT | Gravity.TOP, 0, yOffset);
            //设置item的点击监听
            popView.findViewById(R.id.ll_item1).setOnClickListener(this);
            popView.findViewById(R.id.ll_item2).setOnClickListener(this);
            popView.findViewById(R.id.ll_item3).setOnClickListener(this);
            popView.findViewById(R.id.ll_item4).setOnClickListener(this);
        } else {
            mPopupWindow.showAtLocation(toolbar, Gravity.RIGHT | Gravity.TOP, 0, yOffset);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_item1:
                Toast.makeText(getActivity(), "文书打印", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_item2:
                Toast.makeText(getActivity(), "通讯录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_item3:
                Toast.makeText(getActivity(), "关于我们", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_item4:
                Toast.makeText(getActivity(), "系统更新", Toast.LENGTH_SHORT).show();
                break;
            case R.id.law_enforcement:
                startActivityForResult(new Intent(getContext(), ActivityCheckHost2Guest.class),111);
                break;
            case R.id.enterprise_info:
                startActivityForResult(new Intent(getContext(), ActivityEnterpriseList.class),111);
                break;
            default:
                break;
        }
        //点击PopWindow的item后,关闭此PopWindow
        if (null != mPopupWindow && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }
}
