package com.diabin.latte.ec.main.cart;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.diabin.latte.core.app.Latte;
import com.diabin.latte.core.ui.recycler.MultipleFields;
import com.diabin.latte.core.ui.recycler.MultipleItemEntity;
import com.diabin.latte.core.ui.recycler.MultipleRecyclerAdapter;
import com.diabin.latte.core.ui.recycler.MultipleViewHolder;
import com.diabin.latte.ec.R;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.List;

/**
 * 描述：
 * 作者：johnyin2015
 * 日期：2019/11/30 11:04
 */
public class ShopCartAdapter extends MultipleRecyclerAdapter {

    private static final RequestOptions OPTIONS = new RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate();

    private boolean mIsSelectedAll = false;

    ShopCartAdapter(List<MultipleItemEntity> data) {
        super(data);
        //添加item布局
        addItemType(ShopCartItemType.SHOP_CART_ITEM_TYPE, R.layout.item_shop_cart);
    }

    public void setIsSelectedAll(boolean isSelectedAll) {
        this.mIsSelectedAll = isSelectedAll;
    }

    @Override
    protected void convert(@NonNull MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        //noinspection SwitchStatementWithTooFewBranches
        switch (holder.getItemViewType()) {
            case ShopCartItemType.SHOP_CART_ITEM_TYPE:
                //获取所有值
                final int id = entity.getField(MultipleFields.ID);
                final String thumbUrl = entity.getField(MultipleFields.IMAGE_URL);
                final String title = entity.getField(ShopCartItemFields.TITLE);
                final String desc = entity.getField(ShopCartItemFields.DESC);
                final int count = entity.getField(ShopCartItemFields.COUNT);
                final double price = entity.getField(ShopCartItemFields.PRICE);
                //取出所有控件
                final IconTextView iconIsSelect = holder.getView(R.id.icon_item_shop_cart_check);//勾勾
                final AppCompatImageView imageThumb = holder.getView(R.id.image_item_shop_cart);
                final AppCompatTextView tvTitle = holder.getView(R.id.tv_item_shop_cart_good_title);
                final AppCompatTextView tvDesc = holder.getView(R.id.tv_item_shop_cart_good_desc);
                final AppCompatTextView tvPrice = holder.getView(R.id.tv_item_shop_cart_price);
                final IconTextView iconMinus = holder.getView(R.id.icon_item_shop_cart_minus);
                final IconTextView iconPlus = holder.getView(R.id.icon_item_shop_cart_plus);
                final AppCompatTextView tvCount = holder.getView(R.id.tv_item_shop_cart_count);
                //赋值
                tvTitle.setText(title);
                tvDesc.setText(desc);
                tvPrice.setText(String.valueOf(price));
                tvCount.setText(String.valueOf(count));
                Glide.with(mContext)
                        .load(thumbUrl)
                        .into(imageThumb);

                //在左侧勾勾渲染之前改变全选与否状态
                entity.setField(ShopCartItemFields.IS_SELECTED,mIsSelectedAll);//全选与否会影响item状态
                final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
                //根据数据状态显示左侧勾勾
                if (isSelected) {
                    iconIsSelect.setTextColor(ContextCompat.getColor(Latte.getApplicationContext(), R.color.app_main));
                } else {
                    iconIsSelect.setTextColor(Color.GRAY);
                }

                //添加左侧勾勾点击事件
                iconIsSelect.setOnClickListener(v -> {
                    final boolean currentSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
                    if (currentSelected) {
                        iconIsSelect.setTextColor(Color.GRAY);
                        entity.setField(ShopCartItemFields.IS_SELECTED, false);
                    } else {
                        iconIsSelect.setTextColor(ContextCompat.getColor(Latte.getApplicationContext(), R.color.app_main));
                        entity.setField(ShopCartItemFields.IS_SELECTED, true);
                    }
                });
                break;
            default:
                break;
        }
    }
}
