package com.example.hasee.pictureload.Utility;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hasee on 2017/3/10.
 */

public  class GetBitmap {
    public static Bitmap getBitmap(String uri){
        byte[] picByte;
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            if (connection.getResponseCode() == 200){
                InputStream in = connection.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] bytes = new byte[1024];
                int length = -1;
                while ((length = in.read(bytes)) != -1){
                    bos.write(bytes,0,length);
                }
                picByte = bos.toByteArray();
                in.close();
                bos.close();
                Bitmap bitmap = BitmapFactory.decodeByteArray(picByte, 0, picByte.length);

                return bitmap;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap reverseBitmapSize(Activity a,Bitmap bitmap){

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        //放大為屏幕的1/2大小
        float screenWidth  = a.getWindowManager().getDefaultDisplay().getWidth();     // 屏幕宽（像素，如：480px）
        float screenHeight = a.getWindowManager().getDefaultDisplay().getHeight();        // 屏幕高（像素，如：800p）

        float scaleWidth = screenWidth/2/width;
        float scaleHeight = screenWidth/2/width;

        // 取得想要缩放的matrix參數
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的圖片
        Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,true);

        return newbm;
    }
}
