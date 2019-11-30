package com.diabin.latte.core.ui.recycler;

import java.util.LinkedHashMap;
import java.util.WeakHashMap;

/**
 * 作者：johnyin2015
 * 日期：2019/11/13 17:27
 */
public class MultipleItemEntityBuilder {

    private static final LinkedHashMap<Object, Object> FIELDS = new LinkedHashMap<>();

    private MultipleItemEntityBuilder() {
        FIELDS.clear();
    }

    public static MultipleItemEntityBuilder builder(){
        return new MultipleItemEntityBuilder();
    }

    public final MultipleItemEntityBuilder setItemType(int itemType) {
        FIELDS.put(MultipleFields.ITEM_TYPE, itemType);
        return this;
    }

    //插
    public final MultipleItemEntityBuilder setField(Object key, Object value) {
        FIELDS.put(key, value);
        return this;
    }

    public final MultipleItemEntityBuilder setFields(LinkedHashMap<Object, Object> fields) {
        FIELDS.putAll(FIELDS);
        return this;
    }

    public final MultipleItemEntity build() {
        return new MultipleItemEntity(FIELDS);
    }
}
