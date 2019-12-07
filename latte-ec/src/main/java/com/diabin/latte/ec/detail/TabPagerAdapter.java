package com.diabin.latte.ec.detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

/**
 * Created by 傅令杰
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {//v4 v13,和PagerAdapter本质区别：把每一个Pager状态，即pager状态销毁了，数据状态
    //也毁了，避免重复数据。可以用来销毁数据。

    private final ArrayList<String> TAB_TITLES = new ArrayList<>();
    private final ArrayList<ArrayList<String>> PICTURES = new ArrayList<>();//一组数据的集合
    //数据关系+逻辑 app最难

    public TabPagerAdapter(FragmentManager fm, JSONObject data) {
        super(fm);
        //获取tabs信息，注意，这里的tabs是一条信息
        final JSONArray tabs = data.getJSONArray("tabs");//name pictures 和
        final int size = tabs.size();
        for (int i = 0; i < size; i++) {
            final JSONObject eachTab = tabs.getJSONObject(i);
            final String name = eachTab.getString("name");
            final JSONArray pictureUrls = eachTab.getJSONArray("pictures");
            final ArrayList<String> eachTabPicturesArray = new ArrayList<>();
            //存储每个图片
            final int pictureSize = pictureUrls.size();
            for (int j = 0; j < pictureSize; j++) {
                eachTabPicturesArray.add(pictureUrls.getString(j));
            }
            TAB_TITLES.add(name);
            PICTURES.add(eachTabPicturesArray);
        }
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return ImageDelegate.create(PICTURES.get(0));
        } else if (position == 1) {
            return ImageDelegate.create(PICTURES.get(1));
        }
        return null;
    }

    @Override
    public int getCount() {
        return TAB_TITLES.size();
    }//几页

    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES.get(position);
    }//tab 文字
}
