package com.example.jon.fangnews.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.example.jon.fangnews.app.App;
import com.example.jon.fangnews.app.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by jon on 2016/12/6.
 */

public class SystemUtil {
    public static long getPhoneInnerAvailiabeSpaceMB(){
        File root = Environment.getRootDirectory();
        StatFs statFs = new StatFs(root.getAbsolutePath());
        long availCount = statFs.getAvailableBlocksLong();
        long blockSize = statFs.getBlockSizeLong();
        return blockSize*availCount/1024/1024;
    }

    public static boolean isNetAvailable(){
        ConnectivityManager manager = (ConnectivityManager) App.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo() != null;
    }
    public static float dp2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return  (dp * scale + 0.5f);
    }


    public static void copyToClip(Context context,String content){
        ClipData data = ClipData.newPlainText("url",content);
        ClipboardManager manager = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(data);
        Toast.makeText(context,"已复制到剪切板",Toast.LENGTH_SHORT).show();
    }

    public static Uri saveBitmapToFile(Context context, String url, Bitmap bitmap, View container){
        String fileName = url.substring(url.lastIndexOf("/"),url.lastIndexOf("."))+".png";

        File imageFile = new File(Constants.PATH_DATA,fileName);
        Uri uri = Uri.fromFile(imageFile);
        if(imageFile.exists()){
            return uri;
        }
        FileOutputStream out = null;
        try {
             out = new FileOutputStream(imageFile);
            if(bitmap.compress(Bitmap.CompressFormat.PNG,90,out)){
                Snackbar.make(container,"保存成功",Snackbar.LENGTH_LONG).show();
            }else {
                Snackbar.make(container,"保存成功",Snackbar.LENGTH_SHORT).show();
            }
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), imageFile.getAbsolutePath(), fileName, null);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return uri;


    }

}
