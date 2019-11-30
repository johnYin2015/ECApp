package com.diabin.latte.ec.main.sort.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.diabin.latte.core.ui.recycler.DataConverter;
import com.diabin.latte.core.ui.recycler.ItemType;
import com.diabin.latte.core.ui.recycler.MultipleFields;
import com.diabin.latte.core.ui.recycler.MultipleItemEntity;
import com.diabin.latte.core.ui.recycler.MultipleItemEntityBuilder;

import java.util.ArrayList;

/**
 * class前也可以加final
 * 作者：johnyin2015
 * 日期：2019/11/15 22:27
 */
public final class VerticalListDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final ArrayList<MultipleItemEntity> dataList = new ArrayList();

        final JSONArray dataArray = JSON.parseObject(getJsonData())
                .getJSONObject("data")
                .getJSONArray("list");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final int id = data.getInteger("id");
            final String name = data.getString("name");

            final MultipleItemEntity entity = MultipleItemEntityBuilder.builder()
                    .setField(MultipleFields.ITEM_TYPE, ItemType.VERTICAL_MENU_LIST)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.TEXT, name)
                    .setField(MultipleFields.TAG, false)//false:默认未点击
                    .build();

            dataList.add(entity);
        }

        //设定第一个item 点击状态
        dataList.get(0).setField(MultipleFields.TAG, true);

        return dataList;
    }
}
