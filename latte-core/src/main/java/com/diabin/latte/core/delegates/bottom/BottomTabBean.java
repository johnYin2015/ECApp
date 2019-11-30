package com.diabin.latte.core.delegates.bottom;

/**
 * 作者：johnyin2015
 * 日期：2019/11/12 20:08
 */
public class BottomTabBean {

    //多线程并发，线程安全
    private final String ICON;
    private final String TITLE;

    public BottomTabBean(String ICON, String TITLE) {
        this.ICON = ICON;
        this.TITLE = TITLE;
    }

    public String getICON() {
        return ICON;
    }

    public String getTITLE() {
        return TITLE;
    }
}
