package com.diabin.latte.core.wechat;

import android.app.Activity;

import com.diabin.latte.core.app.ConfigKeys;
import com.diabin.latte.core.app.Latte;
import com.diabin.latte.core.wechat.callbacks.IWeChatSigninCallback;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 懒汉式
 * 作者：johnyin2015
 * 日期：2019/11/12 03:16
 */
public class LatteWeChat {

    public static final String APP_ID
            = (String) Latte.getConfigurations().get(ConfigKeys.WE_CHAT_APP_ID);
    public static final String APP_SECRET
            = (String) Latte.getConfigurations().get(ConfigKeys.WE_CHAT_APP_SECRET);

    private final IWXAPI WXAPI;
    private IWeChatSigninCallback mWeChatSigninCallback = null;

    private LatteWeChat() {
        final Activity activity = (Activity) Latte.getConfigurations().get(ConfigKeys.ACTIVITY);
        WXAPI = WXAPIFactory.createWXAPI(activity, APP_ID, true);
        WXAPI.registerApp(APP_ID);
    }

    private static final class Holder {
        private static final LatteWeChat INSTANCE = new LatteWeChat();
    }

    public static LatteWeChat getInstance() {
        return Holder.INSTANCE;
    }

    //final 不能重写，虚拟机会优化
    public final IWXAPI getWXAPI() {
        return WXAPI;
    }

    //scope、state来自老师之前做的项目
    //签名
    public final void signIn() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "random_state";
        WXAPI.sendReq(req);
    }

    public LatteWeChat onSigninSuccess(IWeChatSigninCallback iWeChatSigninCallback) {
        this.mWeChatSigninCallback = iWeChatSigninCallback;
        return this;
    }

    public IWeChatSigninCallback getWeChatSigninCallback() {
        return mWeChatSigninCallback;
    }
}
