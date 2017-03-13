package com.example.hasee.pictureload.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hasee.pictureload.R;

import java.util.List;

/**
 * Created by hasee on 2017/3/12.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    private Context mContext;

    private List<Bitmap> mBitmaps;

    public MyRecyclerAdapter(List<Bitmap> mBitmaps) {
        this.mBitmaps = mBitmaps;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Bitmap bitmap = mBitmaps.get(position);
        holder.mImage.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return mBitmaps.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView mImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            mImage = (ImageView)itemView.findViewById(R.id.image_item);
        }
    }
}
