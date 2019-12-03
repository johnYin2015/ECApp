package com.diabin.latte.ui.recycler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

/**
 * 作者：johnyin2015
 * 日期：2019/11/13 17:51
 */
public class IndexDataConverter extends DataConverter {

    private static final ArrayList<MultipleItemEntity> ENTITIES = new ArrayList<>();

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);

            final int goodsId = data.getInteger("goodsId");
            final String text = data.getString("text");
            final String imageUrl = data.getString("imageUrl");
            final int spanSize = data.getInteger("spanSize");
            final JSONArray banners = data.getJSONArray("banners");

            final ArrayList<String> bannerList = new ArrayList<>();

            int type = 0;
            if (imageUrl == null && text != null) {
                type = ItemType.TEXT;
            } else if (text == null && imageUrl != null) {
                type = ItemType.IMAGE;
            } else if (imageUrl != null) {
                type = ItemType.TEXT_IMAGE;
            } else if (banners != null) {
                type = ItemType.BANNER;
                //banners初始化
                final int bannerSize = banners.size();
                for (int j = 0; j < bannerSize; j++) {
                    final String banner = banners.getString(j);
                    bannerList.add(banner);
                }
            }


            final MultipleItemEntity multipleItemEntity = MultipleItemEntityBuilder.builder()
                    .setField(MultipleFields.ITEM_TYPE,type)
                    .setField(MultipleFields.ID,goodsId)
                    .setField(MultipleFields.TEXT,text)
                    .setField(MultipleFields.IMAGE_URL,imageUrl)
                    .setField(MultipleFields.SPAN_SIZE,spanSize)
                    .setField(MultipleFields.BANNERS,bannerList)
                    .build();

            ENTITIES.add(multipleItemEntity);

        }
        return ENTITIES;
    }
}
