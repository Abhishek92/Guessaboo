package com.android.guessaboo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.guessaboo.R;
import com.android.guessaboo.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gspl on 30-09-2015.
 */
public class MusicAdapter extends BaseAdapter {
    List<MusicItem> data = new ArrayList<>();
    Context context;
    public MusicAdapter(Context context,List<MusicItem> data ){
        this.context = context;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        MusicItem item = (MusicItem) getItem(i);
        View convertView = LayoutInflater.from(context).inflate(
                R.layout.music_item_layout, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.songName);
        TextView duration = (TextView) convertView.findViewById(R.id.songDuration);
        String fileName = item.getFilePath().substring(item.getFilePath().lastIndexOf("/") + 1);
        name.setText(fileName);
        duration.setText(Util.getSongDuration(item.getFilePath()));
        return convertView;
    }
}
