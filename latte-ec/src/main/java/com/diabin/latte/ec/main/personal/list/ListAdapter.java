package com.diabin.latte.ec.main.personal.list;

import android.support.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.diabin.latte.ec.R;

import java.util.List;

/**
 * 描述：
 * 作者：johnyin2015
 * 日期：2019/12/4 04:12
 */
public class ListAdapter extends BaseMultiItemQuickAdapter<ListBean, BaseViewHolder> {


    public ListAdapter(List<ListBean> data) {
        super(data);
        addItemType(ListItemType.ITEM_NORMAL, R.layout.arrow_item_layout);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ListBean item) {
        switch (helper.getItemViewType()) {
            case ListItemType.ITEM_NORMAL:
                helper.setText(R.id.tv_arrow_text,item.getText());
                helper.setText(R.id.tv_arrow_value,item.getValue());
                break;
            default:
                break;
        }
    }
}
