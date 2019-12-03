package com.diabin.latte.ec.main.cart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.diabin.latte.core.delegates.bottom.BottomItemDelegate;
import com.diabin.latte.core.net.RestClient;
import com.diabin.latte.core.net.callback.ISuccess;
import com.diabin.latte.core.util.log.LatteLogger;
import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;
import com.diabin.latte.ui.recycler.MultipleItemEntity;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class ShopCartDelegate extends BottomItemDelegate implements ISuccess, ICartItemClickListener {

    @BindView(R2.id.rv_shop_cart)
    RecyclerView mRvShopCart = null;

    @BindView(R2.id.icon_shop_cart_select_all)
    IconTextView mIconSelectAll = null;
    private ShopCartAdapter mAdapter;

    @BindView(R2.id.tv_shop_cart_total_price)
    AppCompatTextView mTvTotalPrice = null;

    @OnClick(R2.id.icon_shop_cart_select_all)
    public void onClickSelectAll() {
        final int tag = (int) mIconSelectAll.getTag();
        if (tag == 0) {
            mIconSelectAll.setTextColor(ContextCompat.getColor(getContext(), R.color.app_main));
            mIconSelectAll.setTag(1);//数据状态改变即tag改变
            mAdapter.setIsSelectedAll(true);
            //mAdapter.notifyItemRangeChanged(0,mAdapter.getItemCount());
            mAdapter.notifyDataSetChanged();
        } else {
            mIconSelectAll.setTextColor(Color.GRAY);
            mIconSelectAll.setTag(0);
            mAdapter.setIsSelectedAll(false);
            //更新recyclerview的显示状态
            //mAdapter.notifyItemRangeChanged(0,mAdapter.getItemCount());//?为什么调用它界面出现闪烁
            mAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R2.id.tv_top_shop_cart_remove_selected)
    public void onClickRemoveSelectedItems() {
        final List<MultipleItemEntity> data = mAdapter.getData();

        //把待删除的item放到一个容器中
        final List<MultipleItemEntity> deleteEntities = new ArrayList<>();
        for (MultipleItemEntity entity : data) {
            final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
            if (isSelected) {
                deleteEntities.add(entity);
            }
        }

        if (deleteEntities.size() <= 0)
            return;

        //遍历待删除的item的容器
        for (MultipleItemEntity deleteEntity : deleteEntities) {
            int removePosition;
            final int entityPosition = deleteEntity.getField(ShopCartItemFields.POSITION);
            if (entityPosition > mCurrentCount - 1) {//entityPosition从1开始,所以减一
                removePosition = entityPosition - (mTotalCount - mCurrentCount);//?mTotalCount没有赋值
            } else {
                removePosition = entityPosition;
            }

            if (removePosition <= mAdapter.getItemCount()) {
                mAdapter.remove(removePosition);
                //移除指定item后，总的个数
                mCurrentCount = mAdapter.getItemCount();
                mAdapter.notifyItemRangeChanged(removePosition, mCurrentCount);//渲染
                updateDataPosition();
            }
        }


    }

    private void updateDataPosition() {
        final List<MultipleItemEntity> list = mAdapter.getData();
        for (int i = 0; i < list.size(); i++) {
            final MultipleItemEntity bean = list.get(i);
            bean.setField(ShopCartItemFields.POSITION, i);
        }
    }

    @OnClick(R2.id.tv_top_shop_cart_clear)
    public void onClickClear() {
        mAdapter.getData().clear();
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R2.id.tv_shop_cart_pay)
    public void onClickPay() {
        createOrder();
    }

    private int mItemSelectedCount = 0;
    //购物车数量标记
    private int mCurrentCount = 0;
    private int mTotalCount = 0;

    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_cart;
    }

    @Override
    public void onBindView(View rootView, @Nullable Bundle savedInstanceState) {
        super.onBindView(rootView, savedInstanceState);
        mIconSelectAll.setTag(0);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url("shop_cart_data.json")
                .loader(getContext())
                .success(this)
                .build()
                .get();
    }

    @Override
    public void onSuccess(String response) {
        final ArrayList<MultipleItemEntity> data =
                new ShopCartDataConverter()
                        .setJsonData(response).convert();
        mAdapter = new ShopCartAdapter(data);
        final LinearLayoutManager manager =
                new LinearLayoutManager(getContext(),
                        LinearLayoutManager.VERTICAL, false);
        mRvShopCart.setLayoutManager(manager);
        mRvShopCart.setAdapter(mAdapter);
        mAdapter.setCartItemClickListener(this);
        final double totalPrice = mAdapter.getTotalPrice();
        mTvTotalPrice.setText(String.valueOf(totalPrice));
    }

    @Override
    public void onItemClick(double itemTotal) {
        final double totalPrice = mAdapter.getTotalPrice();
        mTvTotalPrice.setText("￥" + totalPrice);
    }

    private void createOrder() {
        final String orderUrl = "您生成订单的url";
        final WeakHashMap<String, Object> params = new WeakHashMap<>();
        params.put("userid", "111122223333444");
        params.put("amount", 0.01);
        params.put("comment", "测试支付");
        params.put("type", 1);
        params.put("ordertype", 0);
        params.put("isAnoymous", true);
        params.put("followeduser", 0);
        RestClient.builder()
                .url(orderUrl)
                .params(params)
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //进行具体的支付
                    }
                })
                .build()
                .post();
    }
}
