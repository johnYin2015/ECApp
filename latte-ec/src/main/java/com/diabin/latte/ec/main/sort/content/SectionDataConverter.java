package com.diabin.latte.ec.main.sort.content;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

/**
 * 作者：johnyin2015
 * 日期：2019/11/16 10:29
 */
public final class SectionDataConverter {

    public ArrayList<SectionBean> dataList = new ArrayList<>();

    final ArrayList<SectionBean> convert(String json) {
        final JSONArray dataArray = JSON.parseObject(json).getJSONArray("data");
        final int size = dataArray.size();

        //section循环开始
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final int id = data.getInteger("id");
            //商品分类名称
            final String title = data.getString("section");
            final SectionBean sectionTitleBean = new SectionBean(true, title);
            sectionTitleBean.setId(id);
            sectionTitleBean.setIsMore(true);

            //添加分类
            dataList.add(sectionTitleBean);

            final JSONArray goodsJsonArr = data.getJSONArray("goods");
            final int goodsSize = goodsJsonArr.size();
            //商品列表循环
            for (int j = 0; j < goodsSize; j++) {
                final JSONObject contentItem = goodsJsonArr.getJSONObject(j);
                final int goodsId = contentItem.getInteger("goods_id");
                final String goodsName = contentItem.getString("goods_name");
                final String goodsThumb = contentItem.getString("goods_thumb");
                final SectionContentItemEntity itemEntity = new SectionContentItemEntity();
                itemEntity.setGoodsId(goodsId);
                itemEntity.setGoodsName(goodsName);
                itemEntity.setGoodsThumb(goodsThumb);

                //添加内容
                ;dataList.add(new SectionBean(itemEntity));
            }
        }
        //section循环结束

        return dataList;


    }

}
