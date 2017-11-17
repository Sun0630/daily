package com.sx.http_lib.base;

import android.content.Context;

import java.util.Map;

/**
 * @author Sunxin
 * @Date 2017/11/10 0010 上午 11:03
 * @Description 网络引擎规范
 */

public interface IHttpEngine {

    /**
     * Get请求
     *
     * @param context
     * @param url
     * @param params
     * @param callback
     */
    void get(Context context, String url, Map<String, Object> params, IEngineCallback callback);


    /**
     * Post 请求
     *
     * @param context
     * @param url
     * @param params
     * @param callBack
     */
    void post(Context context, String url, Map<String, Object> params, IEngineCallback callBack);


    //上传

    //下载

    //HTTPS

}
