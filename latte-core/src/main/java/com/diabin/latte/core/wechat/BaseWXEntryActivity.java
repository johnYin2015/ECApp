package com.diabin.latte.core.wechat;

import com.alibaba.fastjson.JSONObject;
import com.diabin.latte.core.net.RestClient;
import com.diabin.latte.core.net.callback.IError;
import com.diabin.latte.core.net.callback.IFailure;
import com.diabin.latte.core.net.callback.ISuccess;
import com.diabin.latte.core.util.log.LatteLogger;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

/**
 * 作者：johnyin2015
 * 日期：2019/11/12 05:10
 */
public abstract class BaseWXEntryActivity extends BaseWXActivity {

    //登录成功后的回调
    public abstract void onSigninSuccess(String userInfo);

    //微信发送请求到第三方应用
    @Override
    public void onReq(BaseReq baseReq) {

    }

    //第三方应用发送请求到微信
    @Override
    public void onResp(BaseResp baseResp) {
        final String code = ((SendAuth.Resp) baseResp).code;

        //拼接授权url
        final StringBuilder authUrl = new StringBuilder();
        authUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=")
                .append(LatteWeChat.APP_ID)
                .append("&secret=")
                .append(LatteWeChat.APP_SECRET)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");

        LatteLogger.d("authUrl", authUrl.toString());

        getAuth(authUrl.toString());
    }

    private void getAuth(String authUrl) {
        //access token request or auth req
        RestClient.builder()
                .url(authUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //response json data
                        final JSONObject authObj = JSONObject.parseObject(response);
                        final String accessToken = authObj.getString("access_token");
                        final String openid = authObj.getString("openid");

                        final StringBuilder userinfoUrl = new StringBuilder();
                        userinfoUrl.append("https://api.weixin.qq.com/sns/userinfo?access_token=")
                                .append(accessToken)
                                .append("&openid=")
                                .append(openid)
                                .append("&lang=")
                                .append("zh_CN");

                        //debug log
                        LatteLogger.d("userinfoUrl", userinfoUrl);

                        getUserinfo(userinfoUrl.toString());
                    }
                })
                .build()
                .get();
    }

    private void getUserinfo(String userinfoUrl) {
        //IError 业务逻辑错误等
        RestClient.builder()
                .url(userinfoUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        onSigninSuccess(response);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError() {

                    }
                })
                .build()
                .get();
    }
}
