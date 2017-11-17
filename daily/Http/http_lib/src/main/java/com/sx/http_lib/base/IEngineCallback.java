package com.sx.http_lib.base;

import android.content.Context;

import java.util.Map;

/**
 * @author Administrator
 * @Date 2017/11/10 0010 上午 11:05
 * @Description
 */

public interface IEngineCallback {
    /**
     * 失败回调
     * @param e 异常信息
     */
    void onError(Exception e);

    /**
     * 成功回调
     * @param result 返回结果
     */
    void onSuccess(String result);

    /**
     * 执行之前回调
     * @param context 上下文
     * @param params 参数
     */
    void onPreExcete(Context context, Map<String, Object> params);


    IEngineCallback DEFAULT_ENGINE_CALLBACK = new IEngineCallback() {
        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onSuccess(String result) {

        }

        @Override
        public void onPreExcete(Context context, Map<String, Object> params) {

        }
    };



}
