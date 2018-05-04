package com.sx.rxjava2demo.sample1;

import com.sx.rxjava2demo.sample1.entity.Translation;
import com.sx.rxjava2demo.sample1.entity.Translation1;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @Author Administrator
 * @Date 2018/1/12 0012 上午 9:44
 * @Description
 */

public interface GetRequest_Interface {

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20regist")
    Observable<Translation> getCall();


    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20login")
    Observable<Translation1> getCall2();

}
