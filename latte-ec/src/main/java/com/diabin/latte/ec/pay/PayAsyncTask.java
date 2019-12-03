package com.diabin.latte.ec.pay;

import android.app.Activity;
import android.os.AsyncTask;

import com.alipay.sdk.app.PayTask;
import com.diabin.latte.core.ui.loader.LatteLoader;
import com.diabin.latte.core.util.log.LatteLogger;

/**
 * 描述：
 * 作者：johnyin2015
 * 日期：2019/12/2 06:29
 */
public class PayAsyncTask extends AsyncTask<String, Void, String> {

    //订单支付成功
    public static final String AL_PAY_STATE_SCUESS = "9000";

    //订单支付中
    public static final String AL_PAY_STATE_PAYING = "8000";

    //订单支付失败
    public static final String AL_PAY_STATE_FAIL = "4000";

    //用户取消订单支付
    public static final String AL_PAY_STATE_CANCEL = "6001";

    //订单支付时网络连接异常
    public static final String AL_PAY_STATE_CONNECT_ERROR = "6002";

    private final Activity ACTIVITY;
    private final IAlPayResultListener LISTENER;

    public PayAsyncTask(Activity activity, IAlPayResultListener listener) {
        this.ACTIVITY = activity;
        this.LISTENER = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        final String alPaySign = params[0];
        final PayTask payTask = new PayTask(ACTIVITY);
        return payTask.pay(alPaySign, true);
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        LatteLoader.stopLoading();//关闭进度条
        //返回支付结果以及加签
        final PayResult payResult = new PayResult(response);

        final String resultInfo = payResult.getResult();
        final String resultStatus = payResult.getResultStatus();
        LatteLogger.d("AL_PAY_RESULT", resultInfo);
        LatteLogger.d("AL_PAY_RESULT", resultStatus);

        switch (resultStatus) {
            case AL_PAY_STATE_SCUESS:
                if (LISTENER != null) {
                    LISTENER.onPaySuccess();
                }
                break;
            case AL_PAY_STATE_PAYING:
                if (LISTENER != null) {
                    LISTENER.onPaying();
                }
                break;
            case AL_PAY_STATE_FAIL:
                if (LISTENER != null) {
                    LISTENER.onPayFail();
                }
                break;
            case AL_PAY_STATE_CANCEL:
                if (LISTENER != null) {
                    LISTENER.onPayCancel();
                }
                break;
            case AL_PAY_STATE_CONNECT_ERROR:
                if (LISTENER != null) {
                    LISTENER.onPayConnectError();
                }
                break;
            default:
                break;
        }
    }
}
