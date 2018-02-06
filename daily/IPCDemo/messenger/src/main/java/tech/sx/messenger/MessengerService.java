package tech.sx.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @Author Administrator
 * @Date 2018/1/29 0029 下午 4:44
 * @Description 服务端
 */

public class MessengerService extends Service {

    private static final String TAG = "MessengerService";

    public static final int MSG_FROM_CLIENT = 1;

    private static class MessagerHandler extends Handler {


        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_FROM_CLIENT:
                    Log.e(TAG, "receiver msg from client: " + msg.getData().getString("msg"));
                    Messenger replyTo = msg.replyTo;
                    Message message = Message.obtain(null, MainActivity.MSG_FROM_SERVER);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply","ok copy that");
                    message.setData(bundle);
                    try {
                        replyTo.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private final Messenger mMessenger = new Messenger(new MessagerHandler());

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
