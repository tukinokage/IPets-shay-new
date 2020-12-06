package com.shay.baselibrary.NetUtil;

import android.util.Log;

import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.myexceptions.MyException;

import java.io.IOException;

import retrofit2.HttpException;

/**@Create by shay
 * @Date 2020.12.5
 * 用于retrofit + rxjava的onError过滤
 * */
public class RetrofitOnErrorUtil {
    public static Result OnError(Throwable e){

        Result result = null;
        if (e instanceof HttpException) {
            try {
                String json = ((HttpException) e).response().errorBody().string();
                result = new Result.Error(new Exception("网络访问出错"));
                Log.d( "httpException信息：", json);
            } catch (IOException ex) {
                ex.printStackTrace();
                 result = new Result.Error(new Exception("应用错误101"));
            }
        }
        else if(e instanceof MyException){
            result = new Result.Error((MyException)e);
        } else {
            e.printStackTrace();
            result = new Result.Error(new Exception("应用错误100"));
        }

        return result;
    }

}
