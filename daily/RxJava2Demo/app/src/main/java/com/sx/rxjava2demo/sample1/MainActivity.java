package com.sx.rxjava2demo.sample1;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.sx.rxjava2demo.R;
import com.sx.rxjava2demo.sample1.entity.Translation;
import com.sx.rxjava2demo.sample1.entity.Translation1;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
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
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Observable<Translation> mObservable;
    private String mUrl = "http://fy.iciba.com/";


    /**
     * 模拟轮询的次数
     */
    private int i = 0;

    /**
     * 最多重试次数
     */
    private int mMaxRetryCount = 10;

    /**
     * 当前重试次数
     */
    private int mCurrentRetryCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //- 1. 基础
//        Test1();
        //- 2. 线程切换
//        Test2();
        //- 3. 网络请求
//        request();
        //- 4. map 操作符
//        test3();
        //- 5. flatmap 操作符
//        test4();
        //- 6. zip 操作符
//        test5();
        //- 7. Flowable
//        test6();


        /**
         * 实例1：无条件轮询请求接口
         */
//        intervalRequest();

        /**
         * 实例2：有条件轮询请求接口，轮询5次后停止
         */

//        intervalRequest5Times();

        /**
         * 实例3：网络请求出错重试
         */

//        retryConnect();


        /**
         * 实例4: 处理嵌套网络请求。例如：先请求注册接口，成功之后直接调用登陆
         */

//        nestingRequest();

        /**
         * 实例5：模拟从磁盘/内存/网络获取数据。先从磁盘/内存中读取数据，如果磁盘和内存中都没有数据，那么再从网络上拉取数据。提交获取数据的效率。
         */
//        getDataFrom();

        /**
         * 实例6：合并数据源，从不同的数据源获取的数据（本地 or 网络），通过Merger  or  zip 操作符合并之后统一展示在客户端
         */
//        mergerData();
//        zipData();

        /**
         * 实例7：联合判断，填写表单数据的时候，只有当所有选项都填完才能提交
         */
//        initView();

        /**
         * 实例8：功能防抖 用户在规定时间内多次触发该功能，只会响应第一次触发操作
         */
//        justOnd();

        /**
         * 实例9：联想搜索优化
         */
//        search();


        testCombinelatest();


        Flowable
                .create(new FlowableOnSubscribe<String>() {
                    @Override
                    public void subscribe(FlowableEmitter<String> emitter) throws Exception {

                        emitter.requested();
                    }
                },BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(1000);
                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void testCombinelatest() {
        Observable
                .combineLatest(Observable.just(1L, 2L, 3L),
                        Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS),
                        new BiFunction<Long, Long, Long>() {
                            @Override
                            public Long apply(Long aLong, Long aLong2) throws Exception {
                                Log.e(TAG, "合并的数据：" + aLong + "--" + aLong2);
                                return aLong + aLong2;
                            }
                        })
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e(TAG, "最后的合并结果：" + aLong);
                    }
                });
    }

    private void search() {
        EditText editText = findViewById(R.id.ed);
        final TextView textView = findViewById(R.id.tv);


        RxTextView
                .textChanges(editText)
                .debounce(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CharSequence>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CharSequence sequence) {
                        textView.setText("发送给服务器的数据：" + sequence);
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

    private void justOnd() {
        btn = findViewById(R.id.list);
        btn.setEnabled(true);

        RxView
                .clicks(btn)
                // 规定2S内点击，只响应第一次点击事件
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.e(TAG, "发送了网络请求");
                    }
                });
    }

    EditText name, age, job;
    Button btn;

    private void initView() {
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        job = findViewById(R.id.job);
        btn = findViewById(R.id.list);


        /**
         * 使用RxBinding
         */
        Observable<CharSequence> nameObservable = RxTextView
                //对控件数据变更进行监听，传入EditText控件，点击EditText进行编辑的时候，都会发送一个数据事件Function3
                .textChanges(name)
                //跳过一开始无任何输入值的时候的空值
                .skip(1);

        Observable<CharSequence> ageObservable = RxTextView
                .textChanges(age)
                .skip(1);

        Observable<CharSequence> jobObservable = RxTextView
                .textChanges(job)
                .skip(1);


        /**
         * 通过 combineLastest() 进行合并事件和联合判断
         */

        Observable
                .combineLatest(nameObservable, ageObservable, jobObservable, new Function3<CharSequence, CharSequence, CharSequence, Boolean>() {
                    @Override
                    public Boolean apply(CharSequence sequence, CharSequence sequence2, CharSequence sequence3) throws Exception {
                        //判断表单信息是否为空
                        boolean isNameVaild = !TextUtils.isEmpty(name.getText());

                        boolean isAgeVaild = !TextUtils.isEmpty(age.getText());

                        boolean isJobVaild = !TextUtils.isEmpty(job.getText());

                        return isNameVaild && isAgeVaild && isJobVaild;
                    }
                })
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.e(TAG, "accept: 提交按钮是否可点击 -> " + aBoolean);
                        btn.setEnabled(aBoolean);

                    }
                });


    }

    private void zipData() {
        //1.创建Retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // 创建网络请求接口
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);


        Observable<Translation> observable1 = request.getCall().subscribeOn(Schedulers.io());
        Observable<Translation1> observable2 = request.getCall2().subscribeOn(Schedulers.io());

        // 合并两个数据源
        Observable
                .zip(observable1, observable2, new BiFunction<Translation, Translation1, String>() {
                    @Override
                    public String apply(Translation translation, Translation1 translation1) throws Exception {
                        return translation.print() + " & " + translation1.print();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e(TAG, "accept: 最终结果 -->" + s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "出错了！");
                    }
                });
    }


    /**
     * 展示最终的数据结果
     */
    private String result = "";

    private void mergerData() {
        //1.模拟从网络获取数据
        Observable<String> netObservable = Observable
                .just("网络数据")
                .subscribeOn(Schedulers.io());

        //2.模拟从本地获取数据
        Observable<String> localObservable = Observable
                .just("本地数据")
                .subscribeOn(Schedulers.io());

        //3.合并两个数据源
        Observable
                .merge(netObservable, localObservable)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.e(TAG, "数据源  - >" + s);
                        result += s + " ";
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "数据合并完成");
                        Log.e(TAG, "最终数据  - >" + result);
                    }
                });
    }

    /**
     * 设置内存缓存为空
     */
    private String memoryData = null;

    /**
     * 设置磁盘缓存有数据
     */
    private String diskData = null;

    /**
     * 模拟从磁盘/内存/网络获取数据
     */
    private void getDataFrom() {
        /**
         * 1.先创建3个被观察者，磁盘，内存，网络
         * 2.使用组合操作符 concat() 合并三个事件，然后依次进行处理
         */


        //1, 先从内存中读取数据，创建内存缓存事件
        Observable<String> memroyObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                // 判断是否有内存缓存
                if (memoryData != null) {
                    //有数据，就发送
                    emitter.onNext(memoryData);
                } else {
                    //没数据，就发送完成
                    emitter.onComplete();
                }
            }
        });


        //2. 创建磁盘缓存事件
        Observable<String> diskObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                if (diskData != null) {
                    emitter.onNext(diskData);
                } else {
                    emitter.onComplete();
                }
            }
        });

        //3. 创建从网络获取数据事件，模拟。

        Observable<String> netObservable = Observable.just("网络数据");


        //4，组合三个事件
        Observable
                // 组合操作符，合并三个事件，并排成序列
                .concat(memroyObservable, diskObservable, netObservable)
                // 从串联队列中取出并发送第一个有效的事件，即onNext事件。
                .firstElement()
                //订阅
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e(TAG, "我们最终获取数据来源是：" + s);
                    }
                });


    }

    Observable<Translation> registObservable;
    Observable<Translation1> loginObservable;


    private void nestingRequest() {
        GetRequest_Interface request = new Retrofit.Builder()
                .baseUrl(mUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(GetRequest_Interface.class);

        //封装两个网络请求
        registObservable = request.getCall();
        loginObservable = request.getCall2();


        registObservable
                //切换到子线程进行网络请求
                .subscribeOn(Schedulers.io())
                //切换到主线程处理注册的网络请求
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Translation>() {
                    @Override
                    public void accept(Translation translation) throws Exception {
                        //根据注册的结果去做一些操作
                        Log.e(TAG, "第一次网络请求：注册成功");
                        translation.show();
                    }
                })
                //切换到子线程进行下一个登陆的网络请求。新观察者，所以使用 observeOn 来切换线程
                .observeOn(Schedulers.io())
                .flatMap(new Function<Translation, ObservableSource<Translation1>>() {
                    @Override
                    public ObservableSource<Translation1> apply(Translation translation) throws Exception {
                        //转换成登陆请求
                        return loginObservable;
                    }
                })
                .subscribe(new Consumer<Translation1>() {
                    @Override
                    public void accept(Translation1 translation1) throws Exception {
                        Log.e(TAG, "第二次网络请求：登陆成功");
                        translation1.show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "登陆失败..." + throwable.toString());
                    }
                });


    }

    /**
     * 网络请求出错重试
     */
    private void retryConnect() {
        new Retrofit.Builder()
                .baseUrl(mUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(GetRequest_Interface.class)
                .getCall()
                // 请求重试
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> observable) throws Exception {
                        /**
                         * 1.根据异常类型选择是否重试，如果是网络连接错误就进行重试
                         * 2.最大的重试次数为10次
                         * 3.延迟一段时间在进行重试
                         * 4.重试的次数越多，等待的时间就越长
                         */
                        return observable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Throwable throwable) throws Exception {

                                Log.e(TAG, "发生了异常" + throwable.getMessage());
                                //1,判断异常类型
                                if (throwable instanceof IOException) {
                                    Log.e(TAG, "是网络异常 需要重试");

                                    // 2.最多重试10次
                                    if (mCurrentRetryCount < mMaxRetryCount) {

                                        Log.e(TAG, "重试次数：" + mCurrentRetryCount);

                                        mCurrentRetryCount++;

                                        //设置等待时间,没重试一次，等待时间+1s。
                                        int retryWaitTime = 1000 + mCurrentRetryCount * 1000;

                                        return Observable.just(1).delay(retryWaitTime, TimeUnit.MILLISECONDS);
                                    } else {
                                        //重试次数> 10 次了，就不在重试
                                        return Observable.error(new Throwable("重试次数大于" + mCurrentRetryCount + "不在重试!"));
                                    }


                                } else {
                                    Log.e(TAG, "不是网络异常 ");
                                    return Observable.error(new Throwable("不是网络异常"));
                                }
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Translation>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Translation translation) {
                        translation.show();
                        Log.e(TAG, "onNext: 发送成功！");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void intervalRequest5Times() {
        //创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        // 创建网络请求接口的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        request
                .getCall()
                // 进行轮询请求操作
                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    // 重新对这个Observable进行处理
                    @Override
                    public ObservableSource<?> apply(final Observable<Object> observable) throws Exception {
                        // 使用flatMap操作符接收上游的数据
                        return observable.flatMap(new Function<Object, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Object o) throws Exception {
                                // 判断条件，当轮询 5 次之后结束轮询

                                if (i > 3) {
                                    // 发送onError事件结束轮询
                                    return Observable.error(new Throwable("轮询结束"));
                                }
                                // 发送事件，继续轮询。延迟2S继续轮询
                                return Observable.just(1).delay(2000, TimeUnit.MILLISECONDS);
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Translation>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Translation translation) {
                        translation.show();
                        Log.e(TAG, "轮询次数：" + i);
                        i++;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * 请求轮询
     */
    private void intervalRequest() {
        // 无限次轮询 ，如果是有限次轮询使用 intervalRange
        // 第一次延迟2秒发送事件，之后每隔1秒发送一次事件。无限次
        Observable.interval(2, 1, TimeUnit.SECONDS)
                // doOnNext（）在执行Next事件前调用,在产生数字之前就发送一次网络请求
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e(TAG, "accept次数--->" + aLong);
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(mUrl)
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

                        mObservable = request.getCall();

                        mObservable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Translation>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        Log.e(TAG, "onSubscribe: " + "开始");
                                    }

                                    @Override
                                    public void onNext(Translation translation) {
                                        translation.show();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e(TAG, "onError: " + e.getMessage());
                                    }

                                    @Override
                                    public void onComplete() {
                                        Log.e(TAG, "onComplete");
                                    }
                                });
                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError:---> " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete:---> ");
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
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


        Observable
                .just("AAA")
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                    }
                })
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

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
                Log.e(TAG, "onSubscribe: ");
                //响应式拉取。request是一种能力，下游处理事件的能力。
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext: " + integer);
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
