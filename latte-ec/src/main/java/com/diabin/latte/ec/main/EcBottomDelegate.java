package com.diabin.latte.ec.main;

import android.graphics.Color;

import com.diabin.latte.core.delegates.bottom.BaseBottomDelegate;
import com.diabin.latte.core.delegates.bottom.BottomItemBuilder;
import com.diabin.latte.core.delegates.bottom.BottomItemDelegate;
import com.diabin.latte.core.delegates.bottom.BottomTabBean;
import com.diabin.latte.ec.main.cart.ShopCartDelegate;
import com.diabin.latte.ec.main.discover.DiscoverDelegate;
import com.diabin.latte.ec.main.index.IndexDelegate;
import com.diabin.latte.ec.main.sort.SortDelegate;

import java.util.LinkedHashMap;

/**
 * 作者：johnyin2015
 * 日期：2019/11/13 00:21
 */
public class EcBottomDelegate extends BaseBottomDelegate {

    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(BottomItemBuilder bottomItemBuilder) {
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}","主页"),new IndexDelegate());//首页
        items.put(new BottomTabBean("{fa-sort}","分类"),new SortDelegate());//分类
        items.put(new BottomTabBean("{fa-compass}","发现"),new DiscoverDelegate());//发现
        items.put(new BottomTabBean("{fa-shopping-cart}","购物车"),new ShopCartDelegate());//购物车
        items.put(new BottomTabBean("{fa-user}","我的"),new SortDelegate());//我的
        return bottomItemBuilder.builder().addAll(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#ffff8800");
    }
}
