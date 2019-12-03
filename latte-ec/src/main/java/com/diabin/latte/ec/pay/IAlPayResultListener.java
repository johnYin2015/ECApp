package com.diabin.latte.ec.pay;

/**
 * 描述：
 * 作者：johnyin2015
 * 日期：2019/12/2 04:53
 */
public interface IAlPayResultListener {

    void onPaySuccess();

    void onPaying();

    void onPayFail();

    void onPayCancel();

    void onPayConnectError();

}
