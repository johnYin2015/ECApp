package com.diabin.latte.ec.icon;

import com.joanzapata.iconify.Icon;

/**
 * 作者：johnyin2015
 * 日期：2019/11/1 21:51
 */
public enum  EcIcons implements Icon {

    icon_scan('\ue602'),
    icon_ali_pay('\ue606');

    private char character;

   EcIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
