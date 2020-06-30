package com.android.fragment;

import android.support.v4.app.Fragment;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class BaseFG extends Fragment implements Callback.CommonCallback<String>{

    public void loadData(String path){
        RequestParams params = new RequestParams(path);
        x.http().get(params,this);
    }
    @Override
    public void onSuccess(String result) {

    }
    @Override
    public void onError(Throwable ex, boolean isOnCallback) {

    }
    @Override
    public void onCancelled(CancelledException cex) {

    }
    @Override
    public void onFinished() {

    }
}
