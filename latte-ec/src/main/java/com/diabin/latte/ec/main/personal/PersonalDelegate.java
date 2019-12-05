package com.diabin.latte.ec.main.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.diabin.latte.core.delegates.bottom.BottomItemDelegate;
import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;
import com.diabin.latte.ec.main.personal.address.AddressDelegate;
import com.diabin.latte.ec.main.personal.list.ListAdapter;
import com.diabin.latte.ec.main.personal.list.ListBean;
import com.diabin.latte.ec.main.personal.list.ListItemType;
import com.diabin.latte.ec.main.personal.order.OrderListDelegate;
import com.diabin.latte.ec.main.personal.profile.UserProfileDelegate;
import com.diabin.latte.ec.main.personal.setting.SettingsDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 描述：个人中心
 * 作者：johnyin2015
 * 日期：2019/12/3 20:57
 */
public class PersonalDelegate extends BottomItemDelegate {

    public static final String ORDER_TYPE = "order_type";
    private Bundle mArgs = null;

    @BindView(R2.id.img_user_avatar)
    CircleImageView mAvatar = null;

    @BindView(R2.id.tv_all_order)
    TextView mTvAllOrder = null;

    @BindView(R2.id.rv_personal_setting)
    RecyclerView mRvSettings = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_personal;
    }

    @Override
    public void onBindView(View rootView, @Nullable Bundle savedInstanceState) {
        super.onBindView(rootView, savedInstanceState);

        ListBean addrBean = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(1)
                .setText("收货地址")
                .setDelegate(new AddressDelegate())
                .build();

        ListBean settingBean = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setText("系统设置")
                .setDelegate(new SettingsDelegate())
                .build();

        List<ListBean> data = new ArrayList<>();
        data.add(addrBean);
        data.add(settingBean);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        final ListAdapter adapter = new ListAdapter(data);
        mRvSettings.setLayoutManager(layoutManager);
        mRvSettings.setAdapter(adapter);
        mRvSettings.addOnItemTouchListener(new PersonalClickListener(this));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArgs = new Bundle();
    }

    @OnClick(R2.id.img_user_avatar)
    public void onClickUserAvatar() {
        getParentDelegate().getSupportDelegate().start(new UserProfileDelegate());
    }

    @OnClick(R2.id.tv_all_order)
    public void onClickAllOrder() {
        mArgs.putString(ORDER_TYPE, "all");
        startOrderListByType();
    }

    private void startOrderListByType() {
        final OrderListDelegate orderListDelegate = new OrderListDelegate();
        orderListDelegate.setArguments(mArgs);
        getParentDelegate().getSupportDelegate().start(orderListDelegate);
    }
}
