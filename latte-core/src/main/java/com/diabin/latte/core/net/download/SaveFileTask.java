package com.diabin.latte.core.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.diabin.latte.core.app.Latte;
import com.diabin.latte.core.net.callback.IRequest;
import com.diabin.latte.core.net.callback.ISuccess;
import com.diabin.latte.core.util.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

public class SaveFileTask extends AsyncTask<Object, Void, File> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    public SaveFileTask(IRequest request, ISuccess success) {
        this.REQUEST = request;
        this.SUCCESS = success;
    }

    @Override
    protected File doInBackground(Object... params) {
        String downloadDir = (String) params[0];
        //扩展名
        String extension = (String) params[1];
        //服务器返回的响应体
        ResponseBody body = (ResponseBody) params[2];
        InputStream is = body.byteStream();
        //name
        String name = (String) params[3];

        //校验
        if (downloadDir == null || downloadDir.equals("")) {
            downloadDir = "down_loads";
        }
        if (extension == null) {
            extension = "";
        }
        if (name == null) {//写入磁盘，时间+扩展名为name
            FileUtil.writeToDisk(is, downloadDir, extension.toUpperCase(), extension);
        } else {
            FileUtil.writeToDisk(is, downloadDir, name);
        }

        return null;
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }

        if (SUCCESS != null) {
            SUCCESS.onSuccess(file.getPath());
        }

        autoInstallApk(file);
    }

    private void autoInstallApk(File file) {
        if (FileUtil.getExtension(file.getPath()).equals("apk")) {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            Latte.getApplicationContext().startActivity(intent);
        }
    }
}
