package com.diabin.latte.core.wechat.templates;

import com.diabin.latte.core.delegates.LatteDelegate;
import com.diabin.latte.core.wechat.BaseWXEntryActivity;
import com.diabin.latte.core.wechat.LatteWeChat;
import com.diabin.latte.core.wechat.callbacks.IWeChatSigninCallback;

public class WXEntryTemplate extends BaseWXEntryActivity {

    @Override
    protected void onResume() {
        super.onResume();
        finish();//登录完成马上finish
        overridePendingTransition(0,0);//悄悄消失动画
    }

    @Override
    public void onSigninSuccess(String userInfo) {
        LatteWeChat.getInstance().getWeChatSigninCallback().onSinginSuccess(userInfo);
    }
}
