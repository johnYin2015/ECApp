package com.diabin.latte.ec.main.personal.address;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.diabin.latte.ui.recycler.DataConverter;
import com.diabin.latte.ui.recycler.MultipleFields;
import com.diabin.latte.ui.recycler.MultipleItemEntity;
import com.diabin.latte.ui.recycler.MultipleItemEntityBuilder;

import java.util.ArrayList;

/**
 * Created by 傅令杰
 */

public class AddressDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final JSONArray array = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = array.size();
        for (int i = 0; i < size; i++) {

            final JSONObject data = array.getJSONObject(i);
            final int id = data.getInteger("id");
            final String name = data.getString("name");
            final String phone = data.getString("phone");
            final String address = data.getString("address");
            final boolean isDefault = data.getBoolean("default");

            final MultipleItemEntity entity = MultipleItemEntityBuilder.builder()
                    .setItemType(AddressItemType.ITEM_ADDRESS)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.NAME, name)
                    .setField(MultipleFields.TAG, isDefault)
                    .setField(AddressItemFields.ADDRESS, address)
                    .setField(AddressItemFields.PHONE, phone)
                    .build();
            ENTITIES.add(entity);
        }

        return ENTITIES;
    }
}
