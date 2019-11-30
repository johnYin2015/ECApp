package com.diabin.latte.core.app;

/**
 * 仅仅是接口而已，与业务无关
 * 作者：johnyin2015
 * 日期：2019/11/11 00:25
 */
public interface IUserChecker {
    //登录
    void onSignin();

    //未登录
    void onNotSignin();
}
