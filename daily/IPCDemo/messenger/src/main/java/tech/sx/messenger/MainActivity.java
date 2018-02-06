package tech.sx.messenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * @author sunxin
 */
public class MainActivity extends AppCompatActivity {


    /*客户端向服务端发送数据*/

    private static final String TAG = "MessengerService";

    private Messenger mMessenger;

    public static final int MSG_FROM_SERVER = 2;

   private static class MessageHandler extends Handler{
       @Override
       public void handleMessage(Message msg) {
           switch (msg.what){
               case MSG_FROM_SERVER:
                   Log.i(TAG, "receive msg from Service:" + msg.getData().getString("reply"));
                   break;
           }
           super.handleMessage(msg);

       }
   }


    private Messenger mReplyMsg = new Messenger(new MessageHandler());


    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMessenger = new Messenger(service);
            Message msg = Message.obtain(null, MessengerService.MSG_FROM_CLIENT);
            Bundle bundle = new Bundle();
            bundle.putString("msg", "hello this is Client Msg");
            msg.replyTo = mReplyMsg;
            msg.setData(bundle);
            try {
                //使用Messenger 发送数据到服务端
                mMessenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, MessengerService.class);
        //绑定Service
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        //解绑服务
        unbindService(mServiceConnection);
        super.onDestroy();
    }
}
