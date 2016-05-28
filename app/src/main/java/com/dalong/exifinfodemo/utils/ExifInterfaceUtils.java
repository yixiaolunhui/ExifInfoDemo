package com.dalong.exifinfodemo.utils;

import android.media.ExifInterface;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import it.sephiroth.android.library.exif2.ExifTag;

/**
 * Created by zhouweilong on 16/5/27.
 */

public class ExifInterfaceUtils {

    /**
     * 保存exif值
     * @param jpgPath
     * @param tag
     * @param values
     * @throws Exception
     */
    public static void  setExifByTag(String jpgPath,String tag,String values) throws Exception{
        // 获取图片Exif
        ExifInterface exif = new ExifInterface(jpgPath);
        // 保存指定tag的值
        exif.setAttribute(tag,values);
        // 把Exif信息写入目标图片
        exif.saveAttributes();
    }

    /**
     * 读取图片
     * @param jpgPath
     * @param tag
     * @return
     */
    public static String  getExifByTag(String jpgPath,int tag){
        String exifString="";
        if(!new File(jpgPath).exists())return exifString;
        it.sephiroth.android.library.exif2.ExifInterface exifInterface=new
                it.sephiroth.android.library.exif2.ExifInterface();
        try {
            exifInterface.readExif(jpgPath, it.sephiroth.android.library.exif2.ExifInterface.Options.OPTION_ALL);
        } catch (IOException e) {
            e.printStackTrace();
            return exifString;
        }
        ExifTag mExifTag = exifInterface.getTag(tag);
        if(null!=mExifTag){
            exifString=exifInterface.getTagStringValue(tag);
        }
        return exifString;
    }

    /**
     * 读取图片 exif
     * @param stream
     * @param tag
     * @return
     */
    public static String  getExifByTag(InputStream stream, int tag){
        String exifString="";
        if(null!=stream)return exifString;
        it.sephiroth.android.library.exif2.ExifInterface exifInterface=new
                it.sephiroth.android.library.exif2.ExifInterface();
        try {
            exifInterface.readExif(stream, it.sephiroth.android.library.exif2.ExifInterface.Options.OPTION_ALL);
        } catch (IOException e) {
            e.printStackTrace();
            return exifString;
        }
        ExifTag mExifTag = exifInterface.getTag(tag);
        if(null!=mExifTag){
            exifString=exifInterface.getTagStringValue(tag);
        }
        return exifString;
    }

}
