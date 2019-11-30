package com.diabin.latte.ec.main.sort.content;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * 作者：johnyin2015
 * 日期：2019/11/16 10:30
 */
public class SectionBean extends SectionEntity<SectionContentItemEntity> {

    private boolean mIsMore = false;
    private int mId = -1;//有的item id为0

    public SectionBean(SectionContentItemEntity sectionContentItemEntity) {
        super(sectionContentItemEntity);
    }

    public SectionBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public boolean isMore() {
        return mIsMore;
    }

    public void setIsMore(boolean mIsMore) {
        this.mIsMore = mIsMore;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }
}
