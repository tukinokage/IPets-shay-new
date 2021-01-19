package com.shay.baselibrary.NetUtil;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/** 基于retrofit的http请求框架
 * @create by shay
 * @date 2020.12.4
 * */

public class HttpUtil {

    private  OkHttpClient okHttpClient;

    //日志显示级别
    HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
//
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            Log.i("Http请求参数：", message);
        }
    });

    //default
    public <T> T getService(Class<T> clazz, String baseUrl){
        loggingInterceptor.setLevel(level);
        okHttpClient = new OkHttpClient.Builder().
                connectTimeout(30, TimeUnit.SECONDS).
                readTimeout(30, TimeUnit.SECONDS).
                writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor).
                        build();

        return createService(clazz, baseUrl, okHttpClient);
    }


    public<T extends IHttpService> T getService(Class<T> clazz, String  baseUrl,int connectTimeout, int readTimeout, int writeTimeout){
        loggingInterceptor.setLevel(level);
        okHttpClient = new OkHttpClient.Builder().
                connectTimeout(connectTimeout, TimeUnit.SECONDS).
                readTimeout(readTimeout, TimeUnit.SECONDS).
                writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();

        return createService(clazz, baseUrl, okHttpClient);

    }


    private <T> T createService(Class<T> clazz, String baseUrl,OkHttpClient client){

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
