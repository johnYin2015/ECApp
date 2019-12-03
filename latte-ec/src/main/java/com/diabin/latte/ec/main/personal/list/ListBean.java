package com.diabin.latte.ec.main.personal.list;

import android.widget.CompoundButton;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.diabin.latte.core.delegates.LatteDelegate;

/**
 * 描述：
 * 作者：johnyin2015
 * 日期：2019/12/3 22:43
 */
public class ListBean implements MultiItemEntity {
    private int mId = 0;
    private String mImageUrl = null;
    private String mText = null;
    private String mValue = null;
    private int mItemType = 0;
    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = null;
    private LatteDelegate mDelegate = null;

    public ListBean(int id, String imageUrl, String text, String value, int itemType,
                    CompoundButton.OnCheckedChangeListener onCheckedChangeListener,
                    LatteDelegate delegate) {
        this.mId = id;
        this.mImageUrl = imageUrl;
        this.mText = text;
        this.mValue = value;
        this.mItemType = itemType;
        this.mOnCheckedChangeListener = onCheckedChangeListener;
        this.mDelegate = delegate;
    }

    public int getId() {
        return mId;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getText() {
        return mText;
    }

    public String getValue() {
        return mValue;
    }

    public int getItemType() {
        return mItemType;
    }

    public CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener() {
        return mOnCheckedChangeListener;
    }

    public LatteDelegate getDelegate() {
        return mDelegate;
    }

    public static class Builder {

        private int mId = 0;
        private String mImageUrl = null;
        private String mText = null;
        private String mValue = null;
        private int mItemType = 0;
        private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = null;
        private LatteDelegate mDelegate = null;

        public Builder setId(int id) {
            this.mId = id;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            this.mImageUrl = imageUrl;
            return this;
        }

        public Builder setText(String text) {
            this.mText = text;
            return this;
        }

        public Builder setValue(String value) {
            this.mValue = value;
            return this;
        }

        public Builder setItemType(int itemType) {
            this.mItemType = itemType;
            return this;
        }

        public Builder setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
            this.mOnCheckedChangeListener = onCheckedChangeListener;
            return this;
        }

        public Builder setDelegate(LatteDelegate delegate) {
            this.mDelegate = delegate;
            return this;
        }

        public ListBean build() {
            return
                    new ListBean(mId, mImageUrl, mText, mValue, mItemType, mOnCheckedChangeListener,
                            mDelegate);
        }
    }
}
