package com.diabin.latte.core.delegates.bottom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.RelativeLayout;

import com.diabin.latte.core.R;
import com.diabin.latte.core.R2;
import com.diabin.latte.core.delegates.LatteDelegate;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 作者：johnyin2015
 * 日期：2019/11/12 18:45
 */
public abstract class BaseBottomDelegate extends LatteDelegate implements View.OnClickListener {
    private final ArrayList<BottomTabBean> TAB_BEANS = new ArrayList<>();
    private final ArrayList<BottomItemDelegate> ITEMS_DELEGATES = new ArrayList<>();

    private final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap();

    private int mCurrentDelegate = 0;

    //第一个展示的delegate
    private int mIndexDelegate = 0;

    //点击变色 过去时
    private int mClickedColor = Color.RED;

    //导航栏
    @BindView(R2.id.bottom_bar)
    LinearLayoutCompat mBottomBar = null;

    //需要子类实现的
    public abstract LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(BottomItemBuilder bottomItemBuilder);

    //需要子类赋值
    public abstract int setIndexDelegate();

    public abstract int setClickedColor();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIndexDelegate = setIndexDelegate();

        if (setClickedColor() != 0) {
            mClickedColor = setClickedColor();
        }

        final BottomItemBuilder builder = new BottomItemBuilder().builder();
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> map = setItems(builder);
        ITEMS.putAll(map);
        for (Map.Entry<BottomTabBean, BottomItemDelegate> entry : ITEMS.entrySet()) {
            final BottomTabBean tabBean = entry.getKey();
            final BottomItemDelegate delegate = entry.getValue();
            TAB_BEANS.add(tabBean);
            ITEMS_DELEGATES.add(delegate);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_bottom;
    }

    @Override
    public void onBindView(View rootView, @Nullable Bundle savedInstanceState) {

        final int size = ITEMS.size();
        for (int i = 0; i < size; i++) {
            getLayoutInflater().inflate(R.layout.bottom_item_icon_txt_layout, mBottomBar);
            final RelativeLayout bottomItemView = (RelativeLayout) mBottomBar.getChildAt(i);
            //给每个item添加tag
            bottomItemView.setTag(i);
            bottomItemView.setOnClickListener(this);

            //初始化数据
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            final IconTextView icon = (IconTextView) item.getChildAt(0);
            final AppCompatTextView title = (AppCompatTextView) item.getChildAt(1);
            final BottomTabBean tabBean = TAB_BEANS.get(i);
            icon.setText(tabBean.getICON());
            title.setText(tabBean.getTITLE());

            //当前界面底部item设置
            if (i == mIndexDelegate) {
                icon.setTextColor(mClickedColor);
                title.setTextColor(mClickedColor);
            }
        }


        //处理fragment
        final SupportFragment[] bottomItemDelegates =
                ITEMS_DELEGATES.toArray(new SupportFragment[size]);
        loadMultipleRootFragment(R.id.bottom_bar_delegate_container,
                mIndexDelegate, bottomItemDelegates);

    }

    private void resetColor() {
        final int count = mBottomBar.getChildCount();
        for (int i = 0; i < count; i++) {
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            final IconTextView icon = (IconTextView) item.getChildAt(0);
            final AppCompatTextView title = (AppCompatTextView) item.getChildAt(1);
            icon.setTextColor(Color.GRAY);
            title.setTextColor(Color.GRAY);
        }
    }

    //当前的，底部item选中状态，对应的fragment显示
    @Override
    public void onClick(View v) {
        final int tag = (int) v.getTag();

        //充值
        resetColor();

        //点击的那个item
        final RelativeLayout item = (RelativeLayout) v;
        final IconTextView iconView = (IconTextView) item.getChildAt(0);
        final AppCompatTextView titleView = (AppCompatTextView) item.getChildAt(1);
        iconView.setTextColor(mClickedColor);
        titleView.setTextColor(mClickedColor);
        showHideFragment(ITEMS_DELEGATES.get(tag), ITEMS_DELEGATES.get(mCurrentDelegate));
        mCurrentDelegate = tag;
    }
}
