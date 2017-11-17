package com.sx.http_lib.callback;

import android.content.Context;

import com.google.gson.Gson;
import com.sx.http_lib.base.HttpUtils;
import com.sx.http_lib.base.IEngineCallback;

import java.util.Map;

/**
 * @author Administrator
 * @Date 2017/11/10 0010 下午 1:43
 * @Description
 */

public abstract class HttpCallBack<T> implements IEngineCallback {


    @Override
    public void onSuccess(String result) {
        T objresult = (T) new Gson().fromJson(result, HttpUtils.analysisClassInfo(this));
        success(objresult);
    }

    @Override
    public void onPreExcete(Context context, Map<String, Object> params) {
        //添加请求时的公用参数，与业务逻辑有关的
    }

    /**
     * 成功回调
     *
     * @param reslut
     */
    public abstract void success(T reslut);

}
