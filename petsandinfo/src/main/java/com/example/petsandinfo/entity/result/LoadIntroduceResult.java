package com.example.petsandinfo.entity.result;

import android.text.TextUtils;

import com.example.petsandinfo.model.PetIntroduce;

import java.util.List;

public class LoadIntroduceResult {
        private PetIntroduce data;
        private String errorMsg = "";


    public boolean hasError(){
        if (TextUtils.isEmpty(errorMsg)){
            return false;
        }else {
            return true;
        }
    }
        public PetIntroduce getData() {
            return data;
        }

        public void setData(PetIntroduce data) {
            this.data = data;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }
}
