package com.sx.http_lib.engine;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.sx.http_lib.base.IEngineCallback;
import com.sx.http_lib.base.IHttpEngine;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Sunxin
 * @Date 2017/11/10 0010 上午 11:22
 * @Description
 */

public class OkHttpEngine implements IHttpEngine {

    private static final String TAG = OkHttpEngine.class.getSimpleName();
    public static OkHttpClient okHttpClient = new OkHttpClient();
    private Handler handler = new Handler();

    @Override
    public void get(Context context, String url, Map<String, Object> params, final IEngineCallback callback) {
        final Request request = new Request.Builder()
                .get()
                .url(url)
                .tag(context)
                .build();

        okHttpClient
                .newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        callback.onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        final String resultJson = response.body().toString();
                        Log.e(TAG, "onResponse: " + resultJson);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onSuccess(resultJson);

                            }
                        });
                    }
                });

    }

    @Override
    public void post(Context context, String url, Map<String, Object> params, final IEngineCallback callBack) {
        RequestBody requestBody = appendBody(params);
        final Request request = new Request.Builder()
                .post(requestBody)
                .url(url)
                .tag(context)
                .build();

        okHttpClient
                .newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                        callBack.onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String resultJson = response.body().toString();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                callBack.onSuccess(resultJson);
                            }
                        });
                    }
                });
    }

    private RequestBody appendBody(Map<String, Object> params) {

        return null;
    }
}
