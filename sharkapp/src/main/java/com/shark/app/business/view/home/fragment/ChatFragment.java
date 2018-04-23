package com.shark.app.business.view.home.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.shark.app.R;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;


public class ChatFragment extends BaseFragment {

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

    private ImageView iv_more;
    PopupWindow mPopupWindow;
    String[] ids = {"10010", "10086"};
    List mLists = new ArrayList();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        setHasOptionsMenu(true);
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
        for(int i = 0;i<ids.length;i++){
            mLists.add(ids[i]);
        }
        ConversationListFragment listFragment = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationList")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")     // 设置私聊会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
                //.appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")    // 公共服务号
                //.appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")// 公共服务号
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")  // 设置私聊会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")      // 设置私聊会是否聚合显示
                .build();
        listFragment.setUri(uri);
        getFragmentManager().beginTransaction().replace(R.id.chatgroup,listFragment,"chatgroup").commit();
        return mView;
    }

    /**
     * 初始化控件
     */
    private void initView() {
        title = find(R.id.title);
        title.setText("会话");
        toolbar = find(R.id.toolbar);
        iv_more = find(R.id.iv_more);
        iv_more.setVisibility(View.VISIBLE);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        iv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RongIM.getInstance().getRongIMClient().createDiscussion("测试"+System.currentTimeMillis(), mLists, new RongIMClient.CreateDiscussionCallback() {
                    @Override
                    public void onSuccess(String s) {
                        Toast.makeText(getActivity(), "创建成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        Log.i("创建失败",errorCode.getValue()+":"+errorCode.getMessage());
                        Toast.makeText(getActivity(), "创建失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        Log.e(TAG, "onCreateOptionsMenu()");
//        inflater.inflate(R.menu.chat_menu, menu);
//    }
    @Override
    public void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoaded) {
            return;
        }
        //填充各控件的数据
        mHasLoaded = true;
    }
}
