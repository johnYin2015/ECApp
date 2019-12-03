package com.diabin.latte.core.wechat;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;

/**
 * 描述：
 * 作者：johnyin2015
 * 日期：2019/12/3 18:50
 */
public abstract class BaseWXPayEntryActivity extends BaseWXActivity {

    private final static int PAY_SUCCESS = 0;
    private final static int PAY_FAIL = -1;
    private final static int PAY_CANCEL = -2;

    protected abstract void onPaySuccess();

    protected abstract void onPayFail();

    protected abstract void onPayCancel();

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType()== ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (baseResp.errCode) {
                case PAY_SUCCESS:
                    onPaySuccess();
                    break;
                case PAY_FAIL:
                    onPayFail();
                    break;
                case PAY_CANCEL:
                    onPayCancel();
                    break;
                default:
                    break;
            }
        }
    }
}
