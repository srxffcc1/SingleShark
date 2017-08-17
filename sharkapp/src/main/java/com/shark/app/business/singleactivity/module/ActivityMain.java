package com.shark.app.business.singleactivity.module;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;

import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.businessframehelp.utils.ViewFindUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.shark.app.R;
import com.shark.app.business.entity.TabEntity;
import com.shark.app.business.fragment.FragmentManager;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 */

 public class ActivityMain extends FrameActivity {
   private ArrayList<Fragment> mFragments = new ArrayList<>();
   private String[] mTitles = {"首页", "消息"};
   private int[] mIconUnselectIds = {
           R.drawable.tab_home_unselect, R.drawable.tab_speech_unselect
           };
   private int[] mIconSelectIds = {
           R.drawable.tab_home_select, R.drawable.tab_speech_select
           };
   private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
   private View mDecorView;
   private CommonTabLayout mTabLayout_3;
    private SlidingRootNav menu;

    @Override
    public ORIENTATION getORIENTATION() {
        return null;
    }

    @Override

    public void handleMessage(Message msg) {

    }



//    /**
//     * 显示menu的icon,通过反射,设置Menu的icon显示.
//     * @param view
//     * @param menu
//     * @return
//     */
//    @Override
//    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
//        if (menu != null) {
//            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
//                try{
//                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
//                    m.setAccessible(true);
//                    m.invoke(menu, false);
//                } catch (Exception e) {
//                }
//            }
//        }
//        return super.onPrepareOptionsPanel(view, menu);
//    }
    Dialog menudialog;
    public void startMenuDialog() {
        final List<String> strings = new ArrayList<>();
        strings.add("版本更新");
        strings.add("反馈");
        strings.add("退出");
    StyledDialog.init(getContext());
        menudialog=
                StyledDialog.buildBottomItemDialog( strings, "cancle",  new MyItemDialogListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position) {
                        if(position==2){
                            onBackPressedCopy();
                        }
                    }

                    @Override
                    public void onBottomBtnClick() {

                    }
                }).show();
        menudialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                menudialog=null;
            }
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_MENU){
            if(menudialog==null){

                startMenuDialog();
            }else {
                StyledDialog.dismiss(menudialog);
                menudialog = null;
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public int getMenuid() {
        return R.menu.mainmenu_menu;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        menu = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(getSupportToolbar())
                .setBackgroundRid(R.drawable.img_frame_background)
                .withMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.activity_leftmenu)
                .inject();
        for (String title : mTitles) {
            mFragments.add(FragmentManager.getInstance().factory(title));
        }


        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        mDecorView = getWindow().getDecorView();

        mTabLayout_3 = ViewFindUtils.find(mDecorView, R.id.tl_3);
        mTabLayout_3.setTabData(mTabEntities, this, R.id.fl_change, mFragments);
        mTabLayout_3.setVisibility(View.GONE);
    }
    public void syncBackGround(){

    }
    public void sync(){

    }
    public void showMenu(){

    }
    public void addPlugin(){

    }
    public void update(){

    }
    public void logout(){

    }


    @Override
    public void onBackPressed() {
        if(!menu.isMenuHidden()){
            menu.closeMenu();
        }else{
            moveTaskToBack(true);
//            super.onBackPressed();
        }


    }
}
