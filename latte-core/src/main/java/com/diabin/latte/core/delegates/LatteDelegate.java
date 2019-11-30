package com.diabin.latte.core.delegates;

public abstract class LatteDelegate extends PermissionCheckerDelegate {

    //声明泛型，T
    //返回类型：T
    @SuppressWarnings("unchecked")
    public <T extends LatteDelegate> T getParentDelegate() {
        return (T) getParentFragment();
    }
}
