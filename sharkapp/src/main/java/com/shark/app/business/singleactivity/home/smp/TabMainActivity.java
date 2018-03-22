package com.shark.app.business.singleactivity.home.smp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioGroup;

import com.githang.statusbar.StatusBarCompat;
import com.shark.app.R;
import com.shark.app.business.singleactivity.home.fragment.BaseFragment;
import com.shark.app.business.singleactivity.home.fragment.FragmentFactory;
import com.shark.app.business.singleactivity.home.view.NoScrollViewPager;


public class TabMainActivity extends AppCompatActivity {

    private MyAdapter mAdapter;
    private NoScrollViewPager mViewPager;
    private RadioGroup rgGroup;
    private boolean isshow=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题
        setContentView(R.layout.tab_activity_main);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary), true);
        Log.i("----------------", "主Activity");

        mViewPager = (NoScrollViewPager) findViewById(R.id.vp_content);
        rgGroup = (RadioGroup) findViewById(R.id.rg_group);
        final Animation mShowAction = AnimationUtils.loadAnimation(this,R.anim.photo_dialog_in_anim);

        final Animation mHiddenAction = AnimationUtils.loadAnimation(this,R.anim.photo_dialog_out_anim);

        mAdapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

        View center=findViewById(R.id.testshow);
        center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isshow){
                    v.startAnimation(mHiddenAction);
                    v.setVisibility(View.GONE);
                }else{
                    v.startAnimation(mShowAction);
                    v.setVisibility(View.VISIBLE);
                }
                isshow=!isshow;
            }
        });
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
