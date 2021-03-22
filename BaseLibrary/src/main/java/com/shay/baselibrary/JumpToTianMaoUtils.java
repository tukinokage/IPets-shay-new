package com.shay.baselibrary;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class JumpToTianMaoUtils {


    /**
     * 跳转到淘宝 淘宝包含天猫，所以天猫的uri也可以
     * @param context
     * @param url          跳转链接
     */
    public static void totaoBao(Context context, String url) {
        if (!checkPackage(context, "com.taobao.taobao")) { //未安装淘宝
            Toast.makeText(context, "手机没有安装淘宝", Toast.LENGTH_SHORT).show();
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
            return;
        }

        Intent intent = new Intent();
        intent.setAction("Android.intent.action.VIEW");
        Uri uri = Uri.parse(url); // 商品地址
        intent.setData(uri);
        intent.setClassName("com.taobao.taobao",
                "com.taobao.tao.detail.activity.DetailActivity");
        context.startActivity(intent);
        Toast.makeText(context, "即将为您跳转到天猫", Toast.LENGTH_SHORT).show();

    }


    private static boolean checkPackage(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager
                    .GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;

        }
    }
}