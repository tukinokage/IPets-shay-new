package com.shay.baselibrary.SharedPreferencesUtil;

import android.content.Context;
import android.content.SharedPreferences;

import com.shay.baselibrary.AppContext;
import com.shay.baselibrary.myexceptions.MyException;

import java.nio.file.NoSuchFileException;
import java.util.Map;
import java.util.NoSuchElementException;

public class SharedPrefenccesUtil {



    public static SharedPreferences getSharedPreferences(String filename){
        return AppContext.getContext().getSharedPreferences(filename, Context.MODE_PRIVATE);
    }

    synchronized public static <T>boolean saveValue(String fileName, String key, T value){
        SharedPreferences sharedPreferences = getSharedPreferences(fileName);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        try {
            if(value instanceof Integer){
                editor.putInt(key, (Integer) value);
            }else if (value instanceof String){
                editor.putString(key, (String) value);
            }else if(value instanceof Float){
                editor.putFloat(key, (Float) value);
            }else {
                return false;
            }

            editor.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**只需传入key可获取任何类型
     *
     * */
    public static <T> T getValue(String fileName, String key) throws Exception{
        SharedPreferences sharedPreferences = getSharedPreferences(fileName);

            Map<String, ?> map = sharedPreferences.getAll();
            for (Map.Entry<String, ?> entry:map.entrySet()
                 ) {
                if (entry.getKey().equals(key)) {
                    return (T) entry.getValue();
                }
            }

           throw new MyException(" not exist " + key);


    }

}
