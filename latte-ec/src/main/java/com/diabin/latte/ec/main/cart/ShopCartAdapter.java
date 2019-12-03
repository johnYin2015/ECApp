package com.diabin.latte.ec.main.cart;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.diabin.latte.core.app.Latte;
import com.diabin.latte.core.net.RestClient;
import com.diabin.latte.core.net.callback.ISuccess;
import com.diabin.latte.ui.recycler.MultipleFields;
import com.diabin.latte.ui.recycler.MultipleItemEntity;
import com.diabin.latte.ui.recycler.MultipleRecyclerAdapter;
import com.diabin.latte.ui.recycler.MultipleViewHolder;
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

    private double mTotalPrice = 0.00;//购物车中所有商品的总价

    private ICartItemClickListener mCartItemClickListener = null;

    ShopCartAdapter(List<MultipleItemEntity> data) {
        super(data);
        //添加item布局
        addItemType(ShopCartItemType.SHOP_CART_ITEM_TYPE, R.layout.item_shop_cart);
        //初始化总价
        for (MultipleItemEntity bean : data) {
            final int num = bean.getField(ShopCartItemFields.COUNT);
            final double price = bean.getField(ShopCartItemFields.PRICE);
            mTotalPrice = mTotalPrice + num * price;
        }
    }

    public void setIsSelectedAll(boolean isSelectedAll) {
        this.mIsSelectedAll = isSelectedAll;
    }

    public void setCartItemClickListener(ICartItemClickListener cartItemClickListener) {
        this.mCartItemClickListener = cartItemClickListener;
    }

    public double getTotalPrice() {
        return mTotalPrice;
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
                final String title = entity.getField(MultipleFields.TITLE);
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

                //添加数量加减事件
                //一般每次修改数量都会请求服务器，服务器收到post数据，然后验证用户，然后修改数据库关系，然后返回结果json，我过段时间会出服务端的解决方案视频，想干全栈来听听
                iconMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //同步到服务器
                        final int currentCount = entity.getField(ShopCartItemFields.COUNT);//后台返回的
                        if (Integer.parseInt(tvCount.getText().toString()) > 1) {//前端显示的
                            RestClient.builder()
                                    .url("index_data.json")//?没有对应接口，暂时用这个
                                    //.params("count",currentCount)//?没有对应接口，暂时用这个
                                    .loader(mContext)
                                    .success(new ISuccess() {
                                        @Override
                                        public void onSuccess(String response) {
                                            int countNum = Integer.parseInt(tvCount.getText().toString());
                                            countNum--;
                                            tvCount.setText(String.valueOf(countNum));
                                            //计算总价并回调,交由delegate处理
                                            if (mCartItemClickListener != null) {
                                                mTotalPrice = mTotalPrice - price;
                                                final double itemTotal = countNum * price;
                                                mCartItemClickListener.onItemClick(itemTotal);
                                            }
                                        }
                                    })
                                    .build()
                                    .get();//?没有对应接口，暂时用get
                        }
                    }
                });

                iconPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //同步到服务器
                        final int currentCount = entity.getField(ShopCartItemFields.COUNT);//后台返回的
                        RestClient.builder()
                                .url("index_data.json")//?没有对应接口，暂时用这个
                                //.params("count",currentCount)//?没有对应接口，暂时用这个
                                .loader(mContext)
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        int countNum = Integer.parseInt(tvCount.getText().toString());
                                        countNum++;
                                        tvCount.setText(String.valueOf(countNum));
                                        //计算总价并回调,交由delegate处理
                                        if (mCartItemClickListener != null) {
                                            mTotalPrice = mTotalPrice + price;
                                            final double itemTotal = countNum * price;
                                            mCartItemClickListener.onItemClick(itemTotal);
                                        }
                                    }
                                })
                                .build()
                                .get();//?没有对应接口，暂时用get
                    }

                });
                break;
            default:
                break;
        }
    }
}
