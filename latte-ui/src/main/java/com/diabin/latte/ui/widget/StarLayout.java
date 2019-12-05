package com.diabin.latte.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.diabin.latte.ui.R;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;

/**
 * 一键式评分控件
 * Created by 傅令杰
 */

public class StarLayout extends LinearLayoutCompat implements View.OnClickListener {

    //星星
    private static final CharSequence ICON_UN_SELECT = "{fa-star-o}";
    private static final CharSequence ICON_SELECTED = "{fa-star}";

    //star上限
    private static final int STAR_TOTAL_COUNT = 5;

    private static final ArrayList<IconTextView> STARS = new ArrayList<>();

    public StarLayout(Context context) {
        this(context, null);
    }

    public StarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStarIcon();
    }

    //初始化图标
    private void initStarIcon() {
        for (int i = 0; i < STAR_TOTAL_COUNT; i++) {
            final IconTextView star = new IconTextView(getContext());
            star.setGravity(Gravity.CENTER);
            final LayoutParams lp =
                    new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
            lp.weight = 1;
            star.setLayoutParams(lp);
            star.setText(ICON_UN_SELECT);//不能随意起Id，避免冲突
            star.setTag(R.id.star_count, i);//打标记 索引
            star.setTag(R.id.star_is_select, false);//ids.xml管理key
            star.setOnClickListener(this);
            STARS.add(star);
            this.addView(star);
        }
    }

    public int getStarCount() {
        int count = 0;
        //循环编码中特别重要
        for (int i = 0; i < STAR_TOTAL_COUNT; i++) {
            final IconTextView star = STARS.get(i);
            final boolean isSelect = (boolean) star.getTag(R.id.star_is_select);
            if (isSelect) {
                count++;
            }
        }
        return count;
    }

    //点哪个，取它及之前的，让它们选中
    private void selectStar(int count) {
        for (int i = 0; i <= count; i++) {
            if (i <= count) {
                final IconTextView star = STARS.get(i);
                star.setText(ICON_SELECTED);
                star.setTextColor(Color.RED);
                star.setTag(R.id.star_is_select, true);
            }
        }
    }

    private void unSelectStar(int count) {
        for (int i = 0; i < STAR_TOTAL_COUNT; i++) {
            if (i >= count) {
                final IconTextView star = STARS.get(i);
                star.setText(ICON_UN_SELECT);
                star.setTextColor(Color.GRAY);
                star.setTag(R.id.star_is_select, false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        final IconTextView star = (IconTextView) v;
        //获取第几个星星
        final int count = (int) star.getTag(R.id.star_count);
        //获取点击状态
        final boolean isSelect = (boolean) star.getTag(R.id.star_is_select);
        if (!isSelect) {
            selectStar(count);
        } else {
            unSelectStar(count);//全部置空
        }
    }
}
