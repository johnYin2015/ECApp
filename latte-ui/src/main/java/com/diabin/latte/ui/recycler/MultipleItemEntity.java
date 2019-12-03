package com.diabin.latte.ui.recycler;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

/**
 * Entity即bean
 * 作者：johnyin2015
 * 日期：2019/11/13 16:51
 */
public class MultipleItemEntity implements MultiItemEntity {

    //softReference weakReference
    private ReferenceQueue<LinkedHashMap<Object, Object>> ITEM_QUEUE = new ReferenceQueue<>();
    //LinkedHashMap是有序数组
    private LinkedHashMap<Object, Object> MULTIPLE_FIELDS = new LinkedHashMap<>();
    private SoftReference<LinkedHashMap<Object, Object>> FIELDS_REFERENCES =
            new SoftReference<LinkedHashMap<Object, Object>>(MULTIPLE_FIELDS, ITEM_QUEUE);

    MultipleItemEntity(LinkedHashMap<Object, Object> fields) {
        FIELDS_REFERENCES.get().putAll(fields);
    }


    @Override
    public int getItemType() {
        //item type
        return (int) FIELDS_REFERENCES.get().get(MultipleFields.ITEM_TYPE);
    }

    @SuppressWarnings({"unused", "unchecked"})
    public <T> T getField(Object key) {
        return (T) FIELDS_REFERENCES.get().get(key);
    }

    public LinkedHashMap<Object, Object> getFields() {
        return FIELDS_REFERENCES.get();
    }

    //插入数据即field
    public final MultipleItemEntity setField(Object key, Object value) {
        FIELDS_REFERENCES.get().put(key, value);
        return this;
    }
}
