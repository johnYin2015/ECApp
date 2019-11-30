package com.diabin.latte.core.app;

import com.diabin.latte.core.ui.storage.LattePreference;


/**
 * 作者：johnyin2015
 * 日期：2019/11/11 00:25
 */
public class AccountManager {

    private enum SignTag {
        SIGN_TAG
    }

    /**
     * 保存用户登录状态，用户登录后调用
     * @param state 状态
     */
    public static void setSignState(boolean state) {
        LattePreference.setAppFlag(SignTag.SIGN_TAG.name(), state);
    }

    public static boolean isSignin() {
        return LattePreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static void checkAccount(IUserChecker userChecker) {
        if (isSignin()) {
            userChecker.onSignin();
        } else {
            userChecker.onNotSignin();
        }
    }
}
