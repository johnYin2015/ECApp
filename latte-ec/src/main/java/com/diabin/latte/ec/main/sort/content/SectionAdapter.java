package com.diabin.latte.ec.main.sort.content;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.diabin.latte.core.ui.image.GlideApp;
import com.diabin.latte.ec.R;

import java.util.List;

/**
 * 作者：johnyin2015
 * 日期：2019/11/16 14:50
 */
public class SectionAdapter extends BaseSectionQuickAdapter<SectionBean, BaseViewHolder> {

    private static final RequestOptions REQUEST_OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate();

    public SectionAdapter(int layoutResId, int sectionHeadResId, List<SectionBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    //对头部的转换
    @Override
    protected void convertHead(BaseViewHolder helper, SectionBean item) {
        helper.setText(R.id.tv_section_header, item.header);
        helper.setVisible(R.id.more_section_header, item.isMore());
        helper.addOnClickListener(R.id.more_section_header);
    }

    //对商品的转换
    @Override
    protected void convert(@NonNull BaseViewHolder helper, SectionBean item) {
        final SectionContentItemEntity contentItem = item.t;
        final int goodsId = contentItem.getGoodsId();
        final String goodsName = contentItem.getGoodsName();
        final String goodsThumb = contentItem.getGoodsThumb();

        //设置数据
        helper.setText(R.id.tv_item_section_content, goodsName);
        final AppCompatImageView contentImageView = helper.getView(R.id.iv_item_section_content);
        GlideApp.with(mContext)
                .load(goodsThumb)
                .apply(REQUEST_OPTIONS)
                .into(contentImageView);
    }
}
