package com.diabin.latte.ui.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.diabin.latte.core.R;
import com.diabin.latte.ui.banner.BannerCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Recyclerview根据setSpanSizeLookup实现复杂布局
 * 作者：johnyin2015
 * 日期：2019/11/13 20:11
 */
public class MultipleRecyclerAdapter
        extends BaseMultiItemQuickAdapter<MultipleItemEntity, MultipleViewHolder>
        implements BaseQuickAdapter.SpanSizeLookup, OnItemClickListener {

    private static final RequestOptions OPTIONS =
            new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate();

    private boolean mIsInitBanner = false;

    protected MultipleRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        init();
    }

    public static MultipleRecyclerAdapter create(List<MultipleItemEntity> data) {
        return new MultipleRecyclerAdapter(data);
    }

    public static MultipleRecyclerAdapter create(DataConverter converter) {
        return new MultipleRecyclerAdapter(converter.convert());
    }

    private void init() {
        //设置不同布局
        addItemType(ItemType.TEXT, R.layout.item_multiple_text);
        addItemType(ItemType.IMAGE, R.layout.item_multiple_image);
        addItemType(ItemType.TEXT_IMAGE, R.layout.item_multiple_img_text);
        addItemType(ItemType.BANNER, R.layout.item_multiple_banner);

        //给宽度设置监听
        setSpanSizeLookup(this);
        //动画
        openLoadAnimation();
        //只执行一次
        isFirstOnly(true);
    }

    @Override
    protected void convert(@NonNull MultipleViewHolder helper, MultipleItemEntity entity) {
        final String text;
        final String imgUrl;
        final List<String> bannerImgs;
        switch (entity.getItemType()) {
            case ItemType.TEXT:
                text = entity.getField(MultipleFields.TEXT);
                helper.setText(R.id.text_single, text);
                break;
            case ItemType.IMAGE:
                imgUrl = entity.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imgUrl)
                        .apply(OPTIONS)
                        .into((ImageView) helper.getView(R.id.img_single));
                /*GlideApp.with()
                        .load()
                        .apply()
                        .into()*/
                break;
            case ItemType.TEXT_IMAGE:
                text = entity.getField(MultipleFields.TEXT);
                helper.setText(R.id.text_multiple, text);
                imgUrl = entity.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imgUrl)
                        .apply(OPTIONS)
                        .into((ImageView) helper.getView(R.id.image_multiple));
                break;
            case ItemType.BANNER:
                if (!mIsInitBanner) {
                    final ArrayList<String> bannerImages = entity.getField(MultipleFields.BANNERS);
                    final ConvenientBanner<String> convenientBanner = helper.getView(R.id.banner_multiple_item);
                    BannerCreator.setDefault(convenientBanner, bannerImages, this);
                    mIsInitBanner = true;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(MultipleFields.SPAN_SIZE);
    }

    public MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }

    @Override
    public void onItemClick(int position) {

    }
}
