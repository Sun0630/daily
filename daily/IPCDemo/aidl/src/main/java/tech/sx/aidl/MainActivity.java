package tech.sx.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    /*客户端*/

    private static final String TAG = "MainActivity";
    private static final int MESSAGE_NEW_ARRIVED = 0x0011;
    private IBookManager mBookManager;



    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //运行在UI线程
            mBookManager = IBookManager.Stub.asInterface(service);
            try {
                List<Book> bookList = mBookManager.getBookList();
                //java.util.ArrayList
                Log.e(TAG, "query book list is : " + bookList.toString());
                //[Book{bookId=1, bookName='Android'}, Book{bookId=2, bookName='iOS'}]
                Log.e(TAG, "query book list type is : " + bookList.getClass().getCanonicalName());
                //再添加一本书
                Book newBook = new Book(3, "Android 开发艺术探索");
                mBookManager.addBook(newBook);
                //再次查询
                List<Book> newList = mBookManager.getBookList();
                Log.e(TAG, "query new book list is : " + newList.toString());

                //注册监听，当有新书来的时候通知
                mBookManager.registerListener(mListener);


            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //运行在UI线程
            mBookManager = null;
            Log.e(TAG, "onServiceDisconnected: binder died!");
        }
    };


    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_NEW_ARRIVED:
                    Log.e(TAG, "handleMessage: receive new book " + msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };


    private IOnNewBookArrivedListener mListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            //在Binder线程池中执行，是在子线程，所以需要切换到主线程刷新UI
            mHandler.obtainMessage(MESSAGE_NEW_ARRIVED, book).sendToTarget();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }


    @Override
    protected void onDestroy() {
        //解除注册
        if (mBookManager != null && mBookManager.asBinder().isBinderAlive()) {
            try {
                Log.e(TAG, "onDestroy: " + mListener);
                //这种方法是没有办法反注册成功的，因为多进程下监听器对象不是一个，而是两个
                mBookManager.unregisterListener(mListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mConnection);
        super.onDestroy();
    }
}
