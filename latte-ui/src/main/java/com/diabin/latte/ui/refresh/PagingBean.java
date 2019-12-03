package com.diabin.latte.ui.refresh;

/**
 * 作者：johnyin2015
 * 日期：2019/11/15 13:08
 */
public class PagingBean {
    //页号
    private int mPageIndex = 0;
    //总数据条数
    private int mTotalCount = 0;
    //每页显示多少条
    private int mPageSize = 0;
    //当前显示的条数
    private int mCurrentCount = 0;
    //加载延迟
    private int mDelay = 0;

    public int getPageIndex() {
        return mPageIndex;
    }

    public PagingBean setPageIndex(int pageIndex) {
        this.mPageIndex = pageIndex;
        return this;
    }

    public int getTotalCount() {
        return mTotalCount;
    }

    public PagingBean setTotalCount(int totalCount) {
        this.mTotalCount = totalCount;
        return this;
    }

    public int getPageSize() {
        return mPageSize;
    }

    public PagingBean setPageSize(int pageSize) {
        this.mPageSize = pageSize;
        return this;
    }

    public int getCurrentCount() {
        return mCurrentCount;
    }

    public PagingBean setCurrentCount(int currentCount) {
        this.mCurrentCount = currentCount;
        return this;
    }

    public int getDelay() {
        return mDelay;
    }

    public PagingBean setDelayd(int delay) {
        this.mDelay = delay;
        return this;
    }

    PagingBean addIndex() {
        mPageIndex++;
        return this;
    }
}
