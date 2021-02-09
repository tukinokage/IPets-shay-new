package com.shay.loginandregistermodule.data.repository;

import com.shay.baselibrary.NetUtil.RetrofitOnErrorUtil;
import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.dto.response.BaseResponse;
import com.shay.baselibrary.myexceptions.MyException;
import com.shay.loginandregistermodule.data.datasource.PhoneSmsDataSource;
import com.shay.loginandregistermodule.data.entity.responsedata.PhoneReponseData;
import com.shay.loginandregistermodule.data.entity.responsedata.SmsResponse;
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
    private SmsResultListener getPhoneTokenResultListener;

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

   public void sendMs(HashMap<String, Object> paramsMap, final SmsResultListener smsResultListener) throws Exception{
        this.smsResultListener = smsResultListener;
            phoneSmsDataSource.sendMsg(paramsMap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponse<SmsResponse>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponse<SmsResponse> baseResponse) {

                            Result result = null;
                            try {
                                if(baseResponse == null){
                                    throw new MyException("操作出错");
                                }

                                if("".equals(baseResponse.getErrorMsg())){

                                    result = new Result.Success(baseResponse.getData());

                                }else {
                                    throw new MyException("服务器出错：" + baseResponse.getErrorMsg());
                                }


                            } catch (MyException e) {
                                e.printStackTrace();
                                result =new Result.Error(e);
                            }
                            catch (Exception e){

                                e.printStackTrace();
                                result = new Result.Error(new MyException("操作错误"));
                            }finally {
                                setResult(result);
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


   public void getPhoneToken(HashMap<String, Object> paramsMap, final SmsResultListener getPhoneTokenResultListener)throws Exception{
        this.getPhoneTokenResultListener = getPhoneTokenResultListener;
            phoneSmsDataSource.sendPhoneNum(paramsMap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponse<PhoneReponseData>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponse<PhoneReponseData> baseResponse) {
                            Result result = null;
                            try {
                                if(baseResponse == null){
                                    throw new MyException("操作出错");
                                }

                                if("".equals(baseResponse.getErrorMsg())){

                                    result = new Result.Success(baseResponse.getData());

                                }else {
                                    throw new MyException("服务器出错：" + baseResponse.getErrorMsg());
                                }


                            } catch (MyException e) {
                                e.printStackTrace();
                                result =new Result.Error(e);
                            }
                            catch (Exception e){

                                e.printStackTrace();
                                result = new Result.Error(new MyException("操作错误"));
                            }finally {
                                setPhoneTokenResult(result);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                            Result result = RetrofitOnErrorUtil.OnError(e);
                            setPhoneTokenResult(result);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
   }

   private void setResult(Result result){
        smsResultListener.getSmsResult(result);
   }
   private void setPhoneTokenResult(Result result){
       getPhoneTokenResultListener.getSmsResult(result);
   }
}
