package com.shark.app.business.ui.home.smp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.githang.statusbar.StatusBarCompat;
import com.shark.app.R;
import com.shark.app.business.ui.home.fragment.BaseFragment;
import com.shark.app.business.ui.home.fragment.FragmentFactory;
import com.shark.app.business.ui.home.view.NoScrollViewPager;


public class TabMainActivity extends AppCompatActivity {

    private MyAdapter mAdapter;
    private NoScrollViewPager mViewPager;
    private RadioGroup rgGroup;
    private boolean animcan=true;
    private boolean isshow=false;
    private Animation mShowAction;
    private Animation mHiddenAction;
    private RelativeLayout outside;
    private LinearLayout needshow;
    private ImageView rb_center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题
        setContentView(R.layout.tab_activity_main);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary), true);
        Log.i("----------------", "主Activity");
        mShowAction = AnimationUtils.loadAnimation(this, R.anim.photo_dialog_in_anim);

        mHiddenAction = AnimationUtils.loadAnimation(this, R.anim.photo_dialog_out_anim);
        mViewPager = (NoScrollViewPager) findViewById(R.id.vp_content);
        rgGroup = (RadioGroup) findViewById(R.id.rg_group);
         mShowAction = AnimationUtils.loadAnimation(this,R.anim.photo_dialog_in_anim);

         mHiddenAction = AnimationUtils.loadAnimation(this,R.anim.photo_dialog_out_anim);
        outside = (RelativeLayout) findViewById(R.id.outside);
        needshow = (LinearLayout) findViewById(R.id.needshow);
        rb_center = (ImageView) findViewById(R.id.rb_center);

//        outside.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(needshow.getVisibility()==View.VISIBLE){
//                    needshow.startAnimation(mHiddenAction);
//                    needshow.setVisibility(View.GONE);
//                    mHiddenAction.setAnimationListener(new Animation.AnimationListener() {
//                        @Override
//                        public void onAnimationStart(Animation animation) {
//
//                        }
//
//                        @Override
//                        public void onAnimationEnd(Animation animation) {
//
//                            outside.setVisibility(View.GONE);
//                        }
//
//                        @Override
//                        public void onAnimationRepeat(Animation animation) {
//
//                        }
//                    });
//                    isshow=false;
//                }
//
//            }
//        });
//        needshow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        rb_center.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(animcan){
//                    if(isshow){
//                        needshow.startAnimation(mHiddenAction);
//                        needshow.setVisibility(View.GONE);
//                        mHiddenAction.setAnimationListener(new Animation.AnimationListener() {
//                            @Override
//                            public void onAnimationStart(Animation animation) {
//                                animcan=false;
//                            }
//
//                            @Override
//                            public void onAnimationEnd(Animation animation) {
//
//                                animcan=true;
//                                outside.setVisibility(View.GONE);
//                            }
//
//                            @Override
//                            public void onAnimationRepeat(Animation animation) {
//
//                            }
//                        });
//                    }else{
//                        needshow.startAnimation(mShowAction);
//                        mShowAction.setAnimationListener(new Animation.AnimationListener() {
//                            @Override
//                            public void onAnimationStart(Animation animation) {
//                                animcan=false;
//                            }
//
//                            @Override
//                            public void onAnimationEnd(Animation animation) {
//                                animcan=true;
//                            }
//
//                            @Override
//                            public void onAnimationRepeat(Animation animation) {
//
//                            }
//                        });
//                        needshow.setVisibility(View.VISIBLE);
//                        outside.setVisibility(View.VISIBLE);
//                    }
//                    isshow=!isshow;
//                }
//
//
//            }
//        });
        mAdapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

        // 底栏标签切换监听
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        mViewPager.setCurrentItem(0, false);// 参2:表示是否具有滑动动画
                        break;
                    case R.id.rb_tools:
                        mViewPager.setCurrentItem(1, false);
                        break;
                    case R.id.rb_count:
                        mViewPager.setCurrentItem(2, false);
                        break;
                    case R.id.rb_mine:
                        mViewPager.setCurrentItem(3, false);
                        break;
                    default:
                        break;
                }
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                BaseFragment fragment = FragmentFactory.createFragment(position);
                // 开始加载数据
                //com.shark.app.business.singleactivity.part_home.fragment.loadData();
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * FragmentPagerAdapter是PagerAdapter的子类, 如果viewpager的页面是fragment的话,就继承此类
     */
    class MyAdapter extends FragmentPagerAdapter {

        private String[] mTabNames = {"首页", "工具", "统计", "我的"};
        ;

        public MyAdapter(FragmentManager fm) {
            super(fm);
            //mTabNames = UIUtils.getStringArray(R.array.tab_names);// 加载页面标题数组
        }

        // 返回页签标题
        @Override
        public CharSequence getPageTitle(int position) {
            return mTabNames[position];
        }

        // 返回当前页面位置的fragment对象
        @Override
        public Fragment getItem(int position) {
            BaseFragment fragment = FragmentFactory.createFragment(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return mTabNames.length;
        }

    }
}
