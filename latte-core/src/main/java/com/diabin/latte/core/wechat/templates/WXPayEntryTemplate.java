package com.diabin.latte.core.wechat.templates;

import android.widget.Toast;

import com.diabin.latte.core.activities.ProxyActivity;
import com.diabin.latte.core.delegates.LatteDelegate;
import com.diabin.latte.core.wechat.BaseWXPayEntryActivity;

public class WXPayEntryTemplate extends BaseWXPayEntryActivity {

    @Override
    protected void onPaySuccess() {
        Toast.makeText(this, "支付成功", Toast.LENGTH_LONG).show();
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onPayFail() {
        Toast.makeText(this, "支付失败", Toast.LENGTH_LONG).show();
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onPayCancel() {
        Toast.makeText(this, "支付取消", Toast.LENGTH_LONG).show();
        finish();
        overridePendingTransition(0, 0);
    }
}
