package com.example.hasee.pictureload;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.example.hasee.pictureload.Adapter.MyRecyclerAdapter;
import com.example.hasee.pictureload.Utility.GetBitmap;
import com.example.hasee.pictureload.Utility.ImageLoader;
import com.example.hasee.pictureload.Utility.ParseJsonWithHttpURLConnection;
import com.example.hasee.pictureload.json.Results;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int mCount=1;

    private MyRecyclerAdapter adapter;

    private RecyclerView recyclerView;

    private List<Results> meizis;

    private List<Bitmap> list = new ArrayList<>();

    private String meizi= "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/8/";

    private static final String TAG = "MainActivity";

    String jsonData ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        new Thread(runnalbe).start();
        Log.d(TAG, jsonData+"11");

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isSlideToBottom(recyclerView)){
                    new Thread(runnalbe).start();
                }
            }
        });
    }

    protected boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

    Handler handle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                adapter = new MyRecyclerAdapter(list);
                recyclerView.setAdapter(adapter);
            }else if (msg.what == 2){
                adapter.notifyDataSetChanged();
            }
        }
    };

    Runnable runnalbe = new Runnable() {
        @Override
        public void run() {
            try {

                ImageLoader imageLoader = ImageLoader.getInstance();

                jsonData = ParseJsonWithHttpURLConnection.getJsonData(meizi+mCount);
                mCount++;
                meizis = ParseJsonWithHttpURLConnection.parseMeiziJSON(jsonData);

                for (int i=0;i< meizis.size();i++){
                    Bitmap bm;
                    String url = meizis.get(i).getUrl();
                    String id = meizis.get(i).getId();
                    bm = imageLoader.getBitmapFromMemoryCache(id);
                    if (bm != null){
                        list.add(bm);
                    }else {
                        bm = GetBitmap.getBitmap(url);
                        bm = GetBitmap.reverseBitmapSize(MainActivity.this,bm);
                        list.add(bm);
                        imageLoader.addBitmapToMemoryCache(id,bm);
                    }
                }

                if (mCount<=2){
                    Message message = new Message();
                    message.what = 1;
                    handle.sendMessage(message);
                }else {
                    Message message = new Message();
                    message.what = 2;
                    handle.sendMessage(message);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

}
