package com.sx.http_lib.base;

import android.content.Context;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * @author Sunxin
 * @Date 2017/11/10 0010 上午 9:42
 * @Description
 */

public class HttpUtils {

    private Context context;
    /**
     * Url
     */
    private String url;
    /**
     * Get请求
     */
    private static final int GET_TYPE = 0x0011;
    /**
     * Post请求
     */
    private static final int POST_TYPE = 0x0012;
    /**
     * 默认为Get请求
     */
    private int type = GET_TYPE;
    private final HashMap<String, Object> params;

    private static IHttpEngine mHttpEngine = null;

    private HttpUtils(Context context) {
        this.context = context;
        params = new HashMap<>();
    }

    public static void init(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
    }

    public static HttpUtils with(Context context) {
        return new HttpUtils(context);
    }

    public HttpUtils url(String url) {
        this.url = url;
        return this;
    }

    public HttpUtils get() {
        this.type = GET_TYPE;
        return this;
    }

    public HttpUtils post() {
        this.type = POST_TYPE;
        return this;
    }

    public HttpUtils addParams(String key, Object value) {
        params.put(key, value);
        return this;
    }


    public static void exchangeHttpEngine(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
    }

    public HttpUtils excute(IEngineCallback callback) {
        callback.onPreExcete(context, params);

        //如果没有传callback，就使用默认callback
        if (callback == null) {
            callback = IEngineCallback.DEFAULT_ENGINE_CALLBACK;
        }

        if (type == GET_TYPE) {
            get(url, params, callback);
        }

        if (type == POST_TYPE) {
            post(url, params, callback);
        }

        return this;
    }


    public void excute() {
        excute(null);
    }

    private void get(String url, HashMap<String, Object> params, IEngineCallback callback) {
        mHttpEngine.get(context, url, params, callback);
    }

    private void post(String url, HashMap<String, Object> params, IEngineCallback callback) {
        mHttpEngine.post(context, url, params, callback);
    }


    /**
     * 解析Class信息，根据一个类实体得到一个Class对象
     *
     * @param object
     * @return
     */
    public static Class<?> analysisClassInfo(Object object) {
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class<?>) params[0];
    }

}
