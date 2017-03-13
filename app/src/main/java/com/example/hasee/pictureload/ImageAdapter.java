package com.example.hasee.pictureload;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by hasee on 2017/3/10.
 */

public class ImageAdapter extends ArrayAdapter<Bitmap> {
    private int resourceId;

    public ImageAdapter(Context context, int resource, List<Bitmap> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Bitmap bitmap = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView imageView = (ImageView)view.findViewById(R.id.image_item);
        imageView.setImageBitmap(bitmap);
        return view;
    }

}
