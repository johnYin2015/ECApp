package com.diabin.latte.ui.recycler;

import java.util.ArrayList;

/**
 * 数据转换的约束
 * 作者：johnyin2015
 * 日期：2019/11/13 16:50
 */
public abstract class DataConverter {
    protected ArrayList<MultipleItemEntity> ENTITIES = new ArrayList<>();
    private String mJsonData = null;

    public DataConverter setJsonData(String jsonData){
        this.mJsonData = jsonData;
        return this;
    }

    protected String getJsonData() {
        if (mJsonData==null || mJsonData.isEmpty()) {
            throw new RuntimeException("DATA IS NULL");
        }
        return mJsonData;
    }

    public abstract ArrayList<MultipleItemEntity> convert();

    public void clearData(){
        ENTITIES.clear();
    }
}
