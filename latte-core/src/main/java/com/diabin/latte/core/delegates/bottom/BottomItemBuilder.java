package com.diabin.latte.core.delegates.bottom;

import java.util.LinkedHashMap;

/**
 * 构建bottomitem.该类不能被继承
 * 作者：johnyin2015
 * 日期：2019/11/12 20:11
 */
public final class BottomItemBuilder {

    //保存了记录的插入顺序
    private static final LinkedHashMap<BottomTabBean,BottomItemDelegate> ITEMS
            = new LinkedHashMap<>();

    public BottomItemBuilder builder() {
        return this;
    }

    //存值
    public BottomItemBuilder add(BottomTabBean tab,BottomItemDelegate delegate){
        ITEMS.put(tab,delegate);
        return this;
    }

    public BottomItemBuilder addAll(LinkedHashMap<BottomTabBean,BottomItemDelegate> items){
        //添加之前先清空之前添加的
        ITEMS.clear();
        ITEMS.putAll(items);
        return this;
    }

    public LinkedHashMap<BottomTabBean,BottomItemDelegate> build(){
        return ITEMS;
    }
}
