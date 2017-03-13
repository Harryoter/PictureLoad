package com.example.hasee.pictureload;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hasee on 2017/3/9.
 */

public class LoadPicture {
    private ImageView imageView;
    private String uri;
    private byte[] picByte;

    public void getPicture(String url,ImageView imageView){
        this.imageView=imageView;
        this.uri=url;
        new Thread(runnable).start();
    }

    @SuppressLint("HandlerLeak")
    Handler handle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (picByte != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(picByte, 0, picByte.length);
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
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

                    Message message = new Message();
                    message.what = 1;
                    handle.sendMessage(message);
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
}
