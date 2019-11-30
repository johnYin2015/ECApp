package com.diabin.latte.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.diabin.latte.core.delegates.LatteDelegate;
import com.diabin.latte.core.net.RestClient;
import com.diabin.latte.core.net.callback.IError;
import com.diabin.latte.core.net.callback.IFailure;
import com.diabin.latte.core.net.callback.ISuccess;
import com.diabin.latte.core.util.log.LatteLogger;
import com.diabin.latte.core.wechat.LatteWeChat;
import com.diabin.latte.core.wechat.callbacks.IWeChatSigninCallback;
import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;

import butterknife.BindView;
import butterknife.OnClick;

public class SigninDelegate extends LatteDelegate {

    @BindView(R2.id.edit_sign_in_email)
    TextInputEditText mEtEmail;
    @BindView(R2.id.edit_sign_in_pwd)
    TextInputEditText mEtPwd;
    @BindView(R2.id.btn_sign_in)
    AppCompatButton mBtnSingin;
    @BindView(R2.id.tv_link_sign_up)
    AppCompatTextView mTvSignup;
    private ISignListener mSignListener;
    private String mEmail;
    private String mPwd;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mSignListener = (ISignListener) activity;
        }
    }

    private boolean checkForm() {
        mEmail = mEtEmail.getText().toString();
        mPwd = mEtPwd.getText().toString();

        boolean isPass = true;

        if (mEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            mEtEmail.setError("错误的邮箱格式");
            isPass = false;
        } else {
            mEtEmail.setError(null);
        }

        if (mPwd.isEmpty() || mPwd.length() < 6) {
            mEtPwd.setError("请输入至少6位的密码");
            isPass = false;
        } else {
            mEtPwd.setError(null);
        }

        return isPass;
    }

    @OnClick(R2.id.btn_sign_in)
    void onClickSignin() {
        if (checkForm()) {
            RestClient.builder()
                    .url("http://mock.fulingjie.com/mock-android/data/user_profile.json")
                    .params("email", mEmail)
                    .params("password", mPwd)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            LatteLogger.json("USER_PROFILE", response);
                            SignHandler.onSignin(response,mSignListener);
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            Toast.makeText(getContext(), "注册失败", Toast.LENGTH_LONG).show();
                        }
                    })
                    .error(new IError() {
                        @Override
                        public void onError() {
                            Toast.makeText(getContext(), "signup error", Toast.LENGTH_LONG).show();
                        }
                    })
                    .build()
                    .post();
        }
    }

    @OnClick(R2.id.tv_link_sign_up)
    void onClickLinkSignup() {
        start(new SignupDelegate());
    }

    @OnClick(R2.id.btn_sign_in)
    void onClickIconWechat() {
        LatteWeChat.getInstance().onSigninSuccess(new IWeChatSigninCallback() {
            @Override
            public void onSinginSuccess(String userInfo) {
                Toast.makeText(getContext(), userInfo, Toast.LENGTH_LONG).show();
            }
        }).signIn();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_signin;
    }

    @Override
    public void onBindView(View rootView, @Nullable Bundle savedInstanceState) {
        super.onBindView(rootView, savedInstanceState);
    }
}
