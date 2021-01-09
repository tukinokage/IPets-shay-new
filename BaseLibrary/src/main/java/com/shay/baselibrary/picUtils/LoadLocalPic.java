package com.shay.baselibrary.picUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LoadLocalPic {
    public static Bitmap loadPic(String uri) throws FileNotFoundException {
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        FileInputStream fileInputStream = new FileInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream, null, options);
        return bitmap;
    }
}
