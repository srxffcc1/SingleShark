package com.shark.app.business.ui.home.fragment;

import java.util.HashMap;

/**
 * Created by michael on 2018/2/23.
 */

public class FragmentFactory {

    private static HashMap<Integer, BaseFragment> mFragmentMap = new HashMap<>();

    public static BaseFragment createFragment(int pos) {
        // 先从集合中取, 如果没有,才创建对象, 提高性能
        BaseFragment fragment = mFragmentMap.get(pos);

        if (fragment == null) {
            switch (pos) {
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new ChatFragment();
                    break;
                case 2:
                    fragment = new CountFragment();
                    break;
                case 3:
                    fragment = new MineFragment();
                    break;

                default:
                    break;
            }

            mFragmentMap.put(pos, fragment);// 将fragment保存在集合中
        }

        return fragment;
    }
}
