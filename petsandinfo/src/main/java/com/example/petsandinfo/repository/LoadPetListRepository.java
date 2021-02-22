package com.example.petsandinfo.repository;

import com.example.petsandinfo.datasource.LoadPetListDataSource;
import com.shay.baselibrary.dto.Pet;
import com.shay.baselibrary.NetUtil.RetrofitOnErrorUtil;
import com.shay.baselibrary.dto.response.BaseResponse;
import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.myexceptions.MyException;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoadPetListRepository {
    private volatile static LoadPetListRepository instance;
    private LoadPetListDataSource petListDataSource;
    private GetResultListener getResultListener;
    public LoadPetListRepository(LoadPetListDataSource loadPetListDataSource) {
        this.petListDataSource = loadPetListDataSource;
    }

    synchronized public static LoadPetListRepository getInstance(LoadPetListDataSource loadPetListDataSource) {
        if(instance == null){
            instance = new LoadPetListRepository(loadPetListDataSource);
        }

        return instance;
    }

    public void loadPetList(HashMap<String, Object> params, GetResultListener getResultListener){
        this.getResultListener = getResultListener;

            petListDataSource.loadPetList(params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponse<List<Pet>>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponse<List<Pet>> baseResponse) {

                            Result result = null;
                            try {
                                if(baseResponse == null){
                                    throw new MyException("操作出错：300");
                                }

                                if("".equals(baseResponse.getErrorMsg())){

                                    result = new Result.Success(baseResponse.getData());

                                }else {
                                    throw new MyException("服务器出错：" + baseResponse.getErrorMsg());
                                }


                            } catch (MyException e) {
                                e.printStackTrace();
                                result =new Result.Error(e);
                            } catch (Exception e){

                                e.printStackTrace();
                                result = new Result.Error(new MyException("操作错误：301"));
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

    //需要回调在主线程
    private void setResult(Result result){
        getResultListener.getResult(result);
    }
    public interface GetResultListener{
        void getResult(Result result);
    }

}
