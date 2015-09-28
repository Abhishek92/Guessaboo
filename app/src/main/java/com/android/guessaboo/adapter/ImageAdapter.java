package com.android.guessaboo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.guessaboo.R;

/**
 * Created by hp pc on 20-09-2015.
 */
public class ImageAdapter extends BaseAdapter {

    Context mCtx;
    Integer[] mImageArr;
    public ImageAdapter(Context ctx, Integer[] images){
        mCtx = ctx;
        mImageArr = images;
    }
    @Override
    public int getCount() {
        return mImageArr.length;
    }

    @Override
    public Object getItem(int i) {
        return mImageArr[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        final CustomViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mCtx).inflate(
                    R.layout.sticker_item_layout, parent, false);
            holder = new CustomViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.thumbnail);
            convertView.setTag(holder);
        } else {
            holder = (CustomViewHolder) convertView.getTag();
        }
        holder.imageView.setImageResource(mImageArr[pos]);
        return convertView;
    }

    class CustomViewHolder {
        protected ImageView imageView;
    }
}
