package com.shay.baselibrary.NetUtil;

import com.shay.baselibrary.dto.BaseResponse;
import com.shay.baselibrary.dto.Result;
import com.shay.baselibrary.myexceptions.MyException;

public class RetrofitOnResponseUtil {
    public static Result parseBaseResponse(BaseResponse baseResponse){
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
            return result;
        }
    }
}
