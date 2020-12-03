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

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpUtil {
    public void creatRetorfit(){
        Retrofit retrofit = new Retrofit.Builder()
                //添加GSON解析：返回数据转换成GSON类型
                .addConverterFactory(GsonConverterFactory.create())
                  // 针对rxjava2.x
                  .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(UrlUtil.BASE_URL.BASE_URL)
                .build();

        ApiUrl api = retrofit.create(ApiUrl.class);
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


        api.test(new HashMap<String, Object>(){{put("userName", "bkun")}})
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
                });
    }
}
