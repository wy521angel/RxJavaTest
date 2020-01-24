package com.example.rxjavatest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.rxjavatest.model.Repo;
import com.example.rxjavatest.network.Api;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //将请求放在 io 线程，这样写下面网络请求就可以省略subscribeOn(Schedulers.io())代码
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
        Api api = retrofit.create(Api.class);

//        api.getRepos("wy521angel")
//                .subscribeOn(Schedulers.io())//切到后台做网络请求
//                .observeOn(AndroidSchedulers.mainThread())//再次切回前台做控件更新
//                .subscribe(new SingleObserver<List<Repo>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        textView.setText("正在请求");
//                        disposable = d;
//                    }
//
//                    @Override
//                    public void onSuccess(List<Repo> repos) {
//                        textView.setText(repos.get(0).name);
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        String message = e.getMessage();
//                        if (message == null) {
//                            message = e.getClass().getName();
//                        }
//                        textView.setText(message);
//                    }
//                });

//        Single<String> single = Single.just("1");
////        single = single.subscribeOn(Schedulers.io());
////        single = single.observeOn(AndroidSchedulers.mainThread());
//        single.subscribe(new SingleObserver<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onSuccess(String s) {
//                textView.setText(s);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//        });

        Single.just(1).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) {
                return String.valueOf(integer);
            }
        }).subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String s) {
                textView.setText(s);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
