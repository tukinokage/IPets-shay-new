package com.shay.loginandregistermodule.data.repository;

import com.shay.baselibrary.NetUtil.RetrofitOnErrorUtil;
import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.myexceptions.MyException;
import com.shay.loginandregistermodule.data.datasource.PhoneSmsDataSource;
import com.shay.loginandregistermodule.data.entity.responsedata.AliSmsResponse;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//ali验证码登录注册功能
public class PhoneSmsRepository {

    private static volatile PhoneSmsRepository instance;

    private PhoneSmsDataSource phoneSmsDataSource;

    private SmsResultListener smsResultListener;

    public PhoneSmsRepository(PhoneSmsDataSource phoneSmsDataSource){
        this.phoneSmsDataSource = phoneSmsDataSource;
    }

    synchronized static public PhoneSmsRepository getInstance(PhoneSmsDataSource phoneSmsDataSource) {
        if(instance == null){
          instance = new PhoneSmsRepository(phoneSmsDataSource);
        }

        return instance;
    }

    //回调给getSmsResult

    public interface SmsResultListener{
         void getSmsResult(Result result);
   }

   public void sendMs(HashMap<String, Object> paramsMap, final SmsResultListener smsResultListener)throws Exception{
        this.smsResultListener = smsResultListener;
            phoneSmsDataSource.sendAliApiMsg(paramsMap)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<AliSmsResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(AliSmsResponse aliSmsResponse) {

                            try{
                                if(aliSmsResponse == null){
                                    throw new MyException("201处理请求结果出错");
                                }

                                if("OK".equals(aliSmsResponse.getCode())){
                                    setResult(new Result.Success(aliSmsResponse));
                                }else {
                                    throw new MyException("202无法获取验证码");
                                }


                            } catch (MyException e){
                                setResult(new Result.Error(e));
                            } catch (Exception e){
                                setResult(new Result.Error(new MyException("程序出错203")));
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                            Result result = RetrofitOnErrorUtil.OnError(e);
                            setResult(result);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
   }

   private void setResult(Result result){
        smsResultListener.getSmsResult(result);
   }
}
