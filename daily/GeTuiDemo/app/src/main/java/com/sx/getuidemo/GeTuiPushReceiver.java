package com.sx.getuidemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;

public class GeTuiPushReceiver extends BroadcastReceiver {

    public static String KEY_CLIENT_ID = "push_notification_client_id";

    public static String message;

    public static void init(Context context){

        /**
         * 初始化对象
         * 可以进行推送控制,设置标签,设置别名,设置默认时间等
         * 所有接口都由该对象调用
         * */
//        PushManager.getInstance().initialize();

        /**
         * 获取ClientId
         * */
        String clientId = PushManager.getInstance().getClientid(context);
        if(clientId != null){
            KEY_CLIENT_ID = clientId;
        }
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        //相关处理
        Bundle bundle = intent.getExtras();
        switch (bundle.getInt(PushConsts.CMD_ACTION)){


            /**
             * 推送通知
             * */
            case PushConsts.GET_CLIENTID:
                String cid = bundle.getString("clientid");
                break;

            /**
             * 透传消息,传递过来的是Json字符串.一般而言需要客户端进行解析.
             * */
            case PushConsts.GET_MSG_DATA:
                String appid = bundle.getString("appid");
                byte[] payload = bundle.getByteArray("payload");
                String taskid = bundle.getString("taskid");
                String messageid = bundle.getString("messageid");
                if(payload != null){
                    /**
                     * 如果拿到的数据不为空.那么做相关的处理
                     * */
                    message = new String(payload);
                    onGetPushMessageListener.getOstfMessage(message);
                }else{
                    message = "";
                }
                break;
        }
    }

    private static OnGetPushMessageListener onGetPushMessageListener;

    public static void setOnGetPushMessageListener(OnGetPushMessageListener onGetPushMessageListener) {
        GeTuiPushReceiver.onGetPushMessageListener = onGetPushMessageListener;
    }

    /**
     * 对外暴露接口
     * */

    public interface OnGetPushMessageListener{
        void getOstfMessage(String message);
    }

}