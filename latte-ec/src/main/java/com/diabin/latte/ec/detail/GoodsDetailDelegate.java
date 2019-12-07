package com.diabin.latte.ec.detail;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.androidanimations.library.YoYo;
import com.diabin.latte.core.delegates.LatteDelegate;
import com.diabin.latte.core.net.RestClient;
import com.diabin.latte.core.util.log.LatteLogger;
import com.diabin.latte.ec.R;
import com.diabin.latte.ui.animation.BezierAnimation;
import com.diabin.latte.ui.animation.BezierUtil;
import com.diabin.latte.ui.banner.HolderCreator;
import com.diabin.latte.ui.widget.CircleTextView;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者：johnyin2015
 * 日期：2019/11/15 20:10
 */
public class GoodsDetailDelegate extends LatteDelegate
        implements AppBarLayout.OnOffsetChangedListener, BezierUtil.AnimationListener {

    private TabLayout mTabLayout = null;
    private ViewPager mViewPager = null;
    private ConvenientBanner<String> mBanner = null;
    private CircleTextView mCircleTextView = null;
    private RelativeLayout mRlAddShopCart = null;
    private IconTextView mIconShopCart = null;

    private static final String ARG_GOODS_ID = "ARG_GOODS_ID";
    private int mGoodsId = -1;

    private String mGoodsThumbUrl = null;
    private int mShopCount = 0;

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate()
            .override(100, 100);

    public static GoodsDetailDelegate create(int goodsId) {
        final Bundle args = new Bundle();
        args.putInt(ARG_GOODS_ID, goodsId);
        final GoodsDetailDelegate delegate = new GoodsDetailDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mGoodsId = args.getInt(ARG_GOODS_ID);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_goods_detail;
    }

    @Override
    public void onBindView(View rootView, @Nullable Bundle savedInstanceState) {
        mTabLayout = $(R.id.tab_layout);
        mViewPager = $(R.id.view_pager);
        mBanner = $(R.id.detail_banner);
        final CollapsingToolbarLayout mCollapsingToolbarLayout = $(R.id.collapsing_toolbar_detail);
        final AppBarLayout mAppBar = $(R.id.app_bar_detail);

        //底部
        mCircleTextView = $(R.id.tv_shopping_cart_amount);
        mRlAddShopCart = $(R.id.rl_add_shop_cart);
        mIconShopCart = $(R.id.icon_shop_cart);

        //上滑时根据设置的颜色变色
        mCollapsingToolbarLayout.setContentScrimColor(Color.WHITE);

        //设置滚动监听
        mAppBar.addOnOffsetChangedListener(this);

        mCircleTextView.setCircleBackground(Color.RED);

        initData();
        initTabLayout();

        //加入购物车点击事件
        $(R.id.rl_add_shop_cart).setOnClickListener(view -> onClickAddShopCart());
    }

    private void onClickAddShopCart() {
        final CircleImageView animImg = new CircleImageView(getContext());
        Glide.with(this)
                .load(mGoodsThumbUrl)
                .apply(OPTIONS)
                .into(animImg);
        //添加动画
        BezierAnimation.addCart(this, mRlAddShopCart, mIconShopCart, animImg, this);
    }

    //获取缩略图，和count一块处理
    private void setShopCartCount(JSONObject data) {
        mGoodsThumbUrl = data.getString("thumb");
        if (mShopCount == 0) {//没有商品加入购物车
            mCircleTextView.setVisibility(View.GONE);
        }
    }

    private void initData() {
        RestClient.builder()
                .url("goods_detail_data_1.json")
                .params("goods_id", mGoodsId)
                .loader(getContext())
                .success(response -> {
                    LatteLogger.json("GOODS_DETAIL",response);
                    final JSONObject data =
                            JSON.parseObject(response).getJSONObject("data");
                    initBanner(data);
                    initGoodsInfo(data);
                    initPager(data);
                    setShopCartCount(data);
                })
                .build()
                .get();
    }

    private void initBanner(JSONObject data) {
        final JSONArray array = data.getJSONArray("banners");
        final List<String> images = new ArrayList<>();
        final int size = array.size();
        for (int i = 0; i < size; i++) {
            images.add(array.getString(i));
        }
        mBanner
                .setPages(new HolderCreator(), images)//抽取成工具 复用
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPageTransformer(new DefaultTransformer())//动画设置
                .startTurning(3000)
                .setCanLoop(true);//循环
    }

    private void initGoodsInfo(JSONObject data) {
        final String goodsData = data.toJSONString();
        getSupportDelegate().
                loadRootFragment(R.id.frame_goods_info, GoodsInfoDelegate.create(goodsData));//单独的delegate填坑
    }

    private void initPager(JSONObject data) {
        final PagerAdapter adapter = new TabPagerAdapter(getFragmentManager(), data);
        mViewPager.setAdapter(adapter);
    }


    private void initTabLayout() {
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//分开 平均
        final Context context = getContext();
        if (context != null) {
            mTabLayout.setSelectedTabIndicatorColor
                    (ContextCompat.getColor(context, R.color.app_main));
        }
        mTabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
        mTabLayout.setBackgroundColor(Color.WHITE);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

    }

    @Override
    public void onAnimationEnd() {
        YoYo.with(new ScaleUpAnimator())
                .duration(500)
                .playOn(mIconShopCart);
        //通知服务器更新数据
        RestClient.builder()
                .url("add_shop_cart.json")
                .success(response -> {
                    LatteLogger.json("ADD", response);
                    final boolean isAdded = JSON.parseObject(response).getBoolean("data");
                    if (isAdded) {
                        mShopCount++;
                        mCircleTextView.setVisibility(View.VISIBLE);
                        mCircleTextView.setText(String.valueOf(mShopCount));
                    }
                })
                .params("count", mShopCount)
                .build()
                .post();
    }
}
