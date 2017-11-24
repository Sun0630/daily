package com.sx.rxjava2demo;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sx.rxjava2demo.entity.LoginRequest;
import com.sx.rxjava2demo.entity.LoginResponse;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //- 基础
//        Test1();
        //- 线程切换
//        Test2();
        //- 网络请求
//        request();
        //-map 操作符
//        test3();
        //-flatmap 操作符
//        test4();
        //-zip 操作符
//        test5();
        //-Flowable
        test6();
    }


    private void Test1() {
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        Log.e(TAG, "Emitter: 1");
                        e.onNext("1");
                        Log.e(TAG, "Emitter: 2");
                        e.onNext("2");
                        Log.e(TAG, "Emitter: 3");
                        e.onNext("3");
                        Log.e(TAG, "Emitter: complete");
                        e.onComplete();
                        Log.e(TAG, "Emitter: 4");
                        e.onNext("4");
                    }
                }).subscribe(new Observer<String>() {

            //用于切断水管
            private Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe: ");
                disposable = d;
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "onNext: " + s);
                if (s.equals("2")) {
                    Log.e(TAG, "dispose");
                    disposable.dispose();
                    Log.e(TAG, "isDisposed" + disposable.isDisposed());
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: ");
            }
        });
    }


    private void Test2() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Log.e(TAG, "Observable thread is : " + Thread.currentThread().getName());
                Log.e(TAG, "emit 1");
                e.onNext("1");

            }
        });

        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "Observer thread is :" + Thread.currentThread().getName());
                Log.e(TAG, "onNext: " + s);
            }
        };

        observable
                //指定上游发送线程
                .subscribeOn(Schedulers.newThread())
                //执行下游接收线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);

    }


    private void request() {
        Api api = RetrofitProvider.get().create(Api.class);
        api
                .login(new LoginRequest())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginResponse response) {

                    }

                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(MainActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void login(View view) {
        request();
    }


    private void test3() {
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        e.onNext(1);
                        e.onNext(2);
                        e.onNext(3);
                    }
                })
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {

                        return "this is a string " + integer;
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e(TAG, "accept: " + s);
                    }
                });
    }

    private void test4() {
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        e.onNext(1);
                        e.onNext(2);
                        e.onNext(3);
                    }
                })
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            list.add("is value " + integer);
                        }
                        return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e(TAG, "accept: " + s);
                    }
                });
    }


    private void test5() {
        /**
         * 创建两根水管
         */
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "Emitter:1 ");
                e.onNext(1);
                SystemClock.sleep(1000);


                Log.e(TAG, "Emitter:2 ");
                e.onNext(2);
                SystemClock.sleep(1000);

                Log.e(TAG, "Emitter:3 ");
                e.onNext(3);
                SystemClock.sleep(1000);

                Log.e(TAG, "Emitter:4 ");
                e.onNext(4);
                SystemClock.sleep(1000);

                Log.e(TAG, "Emitter:complete1 ");
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Log.e(TAG, "Emitter:A ");
                e.onNext("A");
                SystemClock.sleep(1000);

                Log.e(TAG, "Emitter:B ");
                e.onNext("B");
                SystemClock.sleep(1000);

                Log.e(TAG, "Emitter:C ");
                e.onNext("C");
                SystemClock.sleep(1000);

                Log.e(TAG, "Emitter:conmlete2 ");
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        /**
         * 组合两根水管
         */
        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: ");
            }
        });

    }

    private void test6() {
        Flowable<Integer> upStream = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "Emitter:1 ");
                e.onNext(1);

                Log.e(TAG, "Emitter:2 ");
                e.onNext(2);

                Log.e(TAG, "Emitter:3 ");
                e.onNext(3);

                Log.e(TAG, "Emitter:complete1 ");
                e.onComplete();
            }
            /*增加一个背压策略参数。*/
        }, BackpressureStrategy.ERROR);


        Subscriber<Integer> downStream = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.e(TAG, "onSubscribe: " );
                //响应式拉取。request是一种能力，下游处理事件的能力。
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext: "+integer );
            }

            @Override
            public void onError(Throwable t) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: ");
            }
        };


        upStream.subscribe(downStream);

    }






}
