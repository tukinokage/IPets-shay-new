package com.shay.baselibrary.NetUtil;

import android.util.Log;

import androidx.annotation.MainThread;

import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.BaseResponse;
import com.shay.baselibrary.dto.TestUser;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.HashMap;
import java.util.concurrent.Flow;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/** 基于retrofit的http请求框架
 * @create by shay
 * @date 2020.12.4
 * */

public class HttpUtil {

    private  OkHttpClient okHttpClient;

    //default
    public <T extends IHttpService> T getService(Class<T> clazz, String baseUrl){
        okHttpClient = new OkHttpClient.Builder().
                connectTimeout(30, TimeUnit.SECONDS).
                readTimeout(30, TimeUnit.SECONDS).
                writeTimeout(30, TimeUnit.SECONDS).build();

        return createService(clazz, baseUrl, okHttpClient);
    }


    public<T extends IHttpService> T getService(Class<T> clazz, String  baseUrl,int connectTimeout, int readTimeout, int writeTimeout){
        okHttpClient = new OkHttpClient.Builder().
                connectTimeout(connectTimeout, TimeUnit.SECONDS).
                readTimeout(readTimeout, TimeUnit.SECONDS).
                writeTimeout(writeTimeout, TimeUnit.SECONDS).build();

        return createService(clazz, baseUrl, okHttpClient);

    }


    private <T extends IHttpService> T createService(Class<T> clazz, String baseUrl,OkHttpClient client){

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                //添加GSON解析：返回数据转换成GSON类型
                .addConverterFactory(GsonConverterFactory.create())
                  // 针对rxjava2.x
                  .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();

        T service = retrofit.create(clazz);
        return service;
       // Call<BaseResponse> demo = api.test(new HashMap<String, Object>(){{put("userName", "bkun")}});

       /* demo.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                Log.e("TAG", "请求成功信息: "+response.body().toString());
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("TAG", "请求失败信息: " +t.getMessage());
            }
        });*/



    /*    api.test(new HashMap<String, Object>(){{put("userName", "bkun");}})
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<TestUser>>(){

                    @Override
                    public void onSubscribe(Disposable d) {


                    }

                    @Override
                    public void onNext(BaseResponse<TestUser> testUserBaseResponse) {

                    }


                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });*/
    }
}
