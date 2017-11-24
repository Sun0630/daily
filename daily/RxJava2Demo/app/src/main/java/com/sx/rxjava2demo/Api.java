package com.sx.rxjava2demo;

import com.sx.rxjava2demo.entity.LoginRequest;
import com.sx.rxjava2demo.entity.LoginResponse;
import com.sx.rxjava2demo.entity.RegisterEntity;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;

/**
 * @author Administrator
 * @Date 2017/11/16 0016 下午 3:41
 * @Description
 */

public interface Api {

    /**
     * 登陆
     *
     * @param request
     * @return
     */
    @GET
    Observable<LoginResponse> login(@Body LoginRequest request);

    /**
     * 注册
     *
     * @param response
     * @return
     */
    @GET
    Observable<RegisterEntity> register(@Body RegisterEntity response);


}
