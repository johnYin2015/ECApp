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
import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;

import butterknife.BindView;
import butterknife.OnClick;

public class SignupDelegate extends LatteDelegate {

    @BindView(R2.id.edit_sign_up_name)
    TextInputEditText mEtName;
    @BindView(R2.id.edit_sign_up_email)
    TextInputEditText mEtEmail;
    @BindView(R2.id.edit_sign_up_phone)
    TextInputEditText mEtPhone;
    @BindView(R2.id.edit_sign_up_pwd)
    TextInputEditText mEtPwd;
    @BindView(R2.id.edit_sign_up_re_pwd)
    TextInputEditText mEtRePwd;
    @BindView(R2.id.btn_sign_up)
    AppCompatButton mBtnSingup;
    @BindView(R2.id.tv_link_sign_in)
    AppCompatTextView mTvSignin;
    private String mName;
    private String mEmail;
    private String mPhone;
    private String mPwd;
    private String mRePwd;

    private ISignListener mSignListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mSignListener = (ISignListener) activity;
        }
    }

    private boolean checkForm() {
        mName = mEtName.getText().toString();
        mEmail = mEtEmail.getText().toString();
        mPhone = mEtPhone.getText().toString();
        mPwd = mEtPwd.getText().toString();
        mRePwd = mEtRePwd.getText().toString();

        boolean isPass = true;

        if (mName.isEmpty()) {
            mEtName.setError("请输入姓名");
            isPass = false;
        } else {
            mEtName.setError(null);
        }

        if (mEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            mEtEmail.setError("错误的邮箱格式");
            isPass = false;
        } else {
            mEtEmail.setError(null);
        }

        if (mPhone.isEmpty() || mPhone.length() != 11) {
            mEtPhone.setError("手机号码错误");
            isPass = false;
        } else {
            mEtPhone.setError(null);
        }

        if (mPwd.isEmpty() || mPwd.length() < 6) {
            mEtPwd.setError("请输入至少6位的密码");
            isPass = false;
        } else {
            mEtPwd.setError(null);
        }

        if (mRePwd.isEmpty() || mRePwd.length() < 6 || !mRePwd.equals(mPwd)) {
            mEtRePwd.setError("请验证密码");
            isPass = false;
        } else {
            mEtRePwd.setError(null);
        }

        return isPass;
    }


    @OnClick(R2.id.btn_sign_up)
    void onClickSignup() {
        if (checkForm()) {

            //php最终还是请求json
            RestClient.builder()
                    .url("http://mock.fulingjie.com/mock-android/data/user_profile.json")
                    .params("name", mName)
                    .params("email", mEmail)
                    .params("phone", mPhone)
                    .params("password", mPwd)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            LatteLogger.json("USER_PROFILE", response);
                            //注册请求成功后，持久化到本地，以数据库形式
                            SignHandler.onSignup(response,mSignListener);
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

    @Override
    public Object setLayout() {
        return R.layout.delegate_signup;
    }

    @Override
    public void onBindView(View rootView, @Nullable Bundle savedInstanceState) {
        super.onBindView(rootView, savedInstanceState);
    }
}
