package com.sx.leakcanary;

/**
 * @Author Administrator
 * @Date 2017/9/19 0019 下午 2:15
 * @Description
 */

public class LHelper {
    private static LHelper instance;

    

    public static LHelper getInstance() {
        if (instance == null){
            return new LHelper();
        }
        return instance;
    }
}
