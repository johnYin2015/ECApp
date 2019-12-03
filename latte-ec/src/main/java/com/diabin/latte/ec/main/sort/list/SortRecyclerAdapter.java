package com.diabin.latte.ec.main.sort.list;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.diabin.latte.ec.R;
import com.diabin.latte.ec.main.sort.SortDelegate;
import com.diabin.latte.ec.main.sort.content.ContentDelegate;
import com.diabin.latte.ui.recycler.ItemType;
import com.diabin.latte.ui.recycler.MultipleFields;
import com.diabin.latte.ui.recycler.MultipleItemEntity;
import com.diabin.latte.ui.recycler.MultipleRecyclerAdapter;
import com.diabin.latte.ui.recycler.MultipleViewHolder;

import java.util.List;

import me.yokeyword.fragmentation.SupportHelper;

/**
 * 作者：johnyin2015
 * 日期：2019/11/16 02:09
 */
public class SortRecyclerAdapter extends MultipleRecyclerAdapter {

    //sortDel
    private final SortDelegate DELEGATE;
    private int mPrePosition = 0;

    protected SortRecyclerAdapter(List<MultipleItemEntity> data, SortDelegate delegate) {
        super(data);
        this.DELEGATE = delegate;
        addItemType(ItemType.VERTICAL_MENU_LIST, R.layout.item_vertical_list_container);
    }

    @Override
    protected void convert(@NonNull MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case ItemType.VERTICAL_MENU_LIST:
                final String text = entity.getField(MultipleFields.TEXT);
                final boolean isClicked = entity.getField(MultipleFields.TAG);

                final AppCompatTextView tvName = holder.getView(R.id.tv_vertical_list_name);
                final View line = holder.getView(R.id.view_line);
                final View itemView = holder.itemView;

                //未点击
                if (!isClicked) {
                    line.setVisibility(View.INVISIBLE);
                    tvName.setTextColor(ContextCompat.getColor(mContext, R.color.we_chat_black));
                    itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_background));
                } else {
                    line.setVisibility(View.VISIBLE);
                    line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.app_main));
                    tvName.setTextColor(ContextCompat.getColor(mContext, R.color.app_main));
                    itemView.setBackgroundColor(Color.WHITE);
                }

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //获取选中的item的位置
                        final int currentPosition = holder.getAdapterPosition();//选中的item的位置
                        if (mPrePosition != currentPosition) {
                            //还原上一个
                            getData().get(mPrePosition).setField(MultipleFields.TAG, false);
                            notifyItemChanged(mPrePosition);

                            //更新选中的
                            getData().get(currentPosition).setField(MultipleFields.TAG, true);
                            notifyItemChanged(currentPosition);

                            mPrePosition = currentPosition;

                            final int contentId =
                                    getData().get(currentPosition).getField(MultipleFields.ID);
                            showContent(contentId);
                        }
                    }
                });

                holder.setText(R.id.tv_vertical_list_name, text);
                break;
            default:
                break;
        }
    }


    //显示内容界面
    public void showContent(int contentId) {
        final ContentDelegate delegate = ContentDelegate.newInstance(contentId);
        if (delegate != null) {
            switchContent(delegate);
        }
    }

    //切换/替换content界面
    public void switchContent(ContentDelegate delegate) {
        //DELEGATE:sortDel
        final ContentDelegate contentDelegate = SupportHelper.findFragment(DELEGATE.getChildFragmentManager(),ContentDelegate.class);
        if (contentDelegate != null) {
            contentDelegate.getSupportDelegate().replaceFragment(delegate, false);//param1:toFragment
        }

        //FastEC
        /*final LatteDelegate contentDelegate =
                SupportHelper.findFragment(DELEGATE.getChildFragmentManager(), ContentDelegate.class);
        if (contentDelegate != null) {
            contentDelegate.getSupportDelegate().replaceFragment(delegate, false);
        }*/
    }
}
