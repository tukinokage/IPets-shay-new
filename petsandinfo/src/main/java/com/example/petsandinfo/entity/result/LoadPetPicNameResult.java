package com.example.petsandinfo.entity.result;

import android.text.TextUtils;

import java.util.List;

public class LoadPetPicNameResult {
        private List<String> data;
        private String errorMsg = "";

        public boolean hasError(){
            if (TextUtils.isEmpty(errorMsg)){
                return false;
            }else {
                return true;
            }
        }

        public List<String> getData() {
            return data;
        }

        public void setData(List<String> data) {
            this.data = data;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }
}
