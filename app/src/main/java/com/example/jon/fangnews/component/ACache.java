package com.example.jon.fangnews.component;

import com.example.jon.fangnews.app.Constants;

import java.io.File;

/**
 * Created by jon on 2016/12/17.
 */

public class ACache {
    public static String getCacheSizeString(){
        File dir = new File(Constants.PATH_CACHE);
        long size = getSizeOfDir(dir);
        long kb = size/8/1024;
        if(kb>1500){
            return kb/1024 + "MB";
        }
        return kb +"KB";
    }

    public static void deleteCache(){
        File dir = new File(Constants.PATH_CACHE);
        if(dir.exists()){
            deleteFile(dir);
        }

    }

    private static void deleteFile(File file){
        if(file.isFile()){
            file.delete();
        }else {
            for(File f : file.listFiles()){
                deleteFile(f);
            }
        }
    }
    private static long getSizeOfDir(File dir){
        if(!dir.exists()) return -1;
        if(dir.isFile()){
            return dir.length();
        }else {
            int size = 0;
            for(File file:dir.listFiles()){
                size+=getSizeOfDir(file);
            }

            return size;

        }
    }
}
