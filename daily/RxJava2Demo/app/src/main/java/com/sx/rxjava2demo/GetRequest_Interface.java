package com.sx.rxjava2demo;

import com.sx.rxjava2demo.entity.Translation;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @Author Administrator
 * @Date 2018/1/12 0012 上午 9:44
 * @Description
 */

public interface GetRequest_Interface {

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    Observable<Translation> getCall();

}
