package com.diabin.latte.ec.sign;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.diabin.latte.core.app.AccountManager;
import com.diabin.latte.ec.database.DatabaseManager;
import com.diabin.latte.ec.database.UserProfile;

public class SignHandler {

    public static void onSignup(String json, ISignListener signListener) {
        final JSONObject userProfileJson = JSON.parseObject(json).getJSONObject("data");
        final long userId = userProfileJson.getLong("userId");
        final String name = userProfileJson.getString("name");
        final String avatar = userProfileJson.getString("avatar");
        final String gender = userProfileJson.getString("gender");
        final String address = userProfileJson.getString("address");

        final UserProfile userProfile = new UserProfile(userId, name, avatar, gender, address);

        DatabaseManager.getInstance().getDao().insert(userProfile);

        //已经注册并登录成功
        //用到sharepreference
        AccountManager.setSignState(true);
        //调用注册成功方法，回调到activity中
        signListener.onSignupSuccess();
    }

    public static void onSignin(String json, ISignListener signListener) {
        final JSONObject userProfileJson = JSON.parseObject(json).getJSONObject("data");
        final long userId = userProfileJson.getLong("userId");
        final String name = userProfileJson.getString("name");
        final String avatar = userProfileJson.getString("avatar");
        final String gender = userProfileJson.getString("gender");
        final String address = userProfileJson.getString("address");

        final UserProfile userProfile = new UserProfile(userId, name, avatar, gender, address);

        DatabaseManager.getInstance().getDao().insert(userProfile);

        //已经注册并登录成功
        //用到sharepreference
        AccountManager.setSignState(true);
        //调用注册成功方法，回调到activity中
        signListener.onSigninSuccess();
    }
}
