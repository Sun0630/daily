package tech.sx.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author Administrator
 * @Date 2018/1/30 0030 上午 10:00
 * @Description 远程服务端Service
 */

public class BookManagerService extends Service {
    private static final String TAG = "MainActivity";
    /**
     * 支持并发读写，当多个客户端链接的时候处理多线程情况。AIDL方法是在服务端的Binder池中执行的
     */
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();


    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);

//    private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListeners = new CopyOnWriteArrayList<>();

    private RemoteCallbackList<IOnNewBookArrivedListener> mlisteners = new RemoteCallbackList<>();


    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if (!mListeners.contains(listener)) {
//                mListeners.add(listener);
//
//            } else {
//                Log.e(TAG, "already exits");
//            }

            mlisteners.register(listener);


            int N = mlisteners.beginBroadcast();
            mlisteners.finishBroadcast();
            Log.e(TAG, "registerlistener size is " + N);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if (mListeners.contains(listener)) {
//                mListeners.remove(listener);
//                Log.e(TAG, "unregisterListener succeed");
//            } else {
//                Log.e(TAG, "unregisterListener 404");
//            }

            mlisteners.unregister(listener);

            //begin 和  finish 必须成对出现
            int N = mlisteners.beginBroadcast();
            mlisteners.finishBroadcast();

            Log.e(TAG, "unregisterListener: current size " + N);
        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1, "Android"));
        mBookList.add(new Book(2, "iOS"));
        new Thread(new ServiceWorker()).start();
    }

    private class ServiceWorker implements Runnable {
        @Override
        public void run() {
            //服务没有销毁的时候,每隔5s就向集合中添加一本书
            while (!mIsServiceDestoryed.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId = mBookList.size() + 1;
                Book newBook = new Book(bookId, "newBook@" + bookId);
                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        mBookList.add(book);

//        for (int i = 0; i < mListeners.size(); i++) {
//            IOnNewBookArrivedListener listener = mListeners.get(i);
//            if (listener != null) {
//                Log.e(TAG, "onNewBookArrived: " + listener);
//                listener.onNewBookArrived(book);
//            }
//        }


        int N = mlisteners.beginBroadcast();

        for (int i = 0; i < N; i++) {
            IOnNewBookArrivedListener item = mlisteners.getBroadcastItem(i);
            if (item != null) {
                item.onNewBookArrived(book);
            }
        }

        mlisteners.finishBroadcast();

    }


    @Override
    public void onDestroy() {
        mIsServiceDestoryed.set(true);
        super.onDestroy();
    }
}
