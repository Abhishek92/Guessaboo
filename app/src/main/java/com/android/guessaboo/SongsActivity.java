package com.android.guessaboo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.android.guessaboo.adapter.MusicAdapter;
import com.android.guessaboo.adapter.MusicItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SongsActivity extends BaseActivity implements View.OnClickListener {

    private final int MUSIC_REQ_CODE = 101;
    private ListView mSongsList;
    private MusicAdapter mAdapter;
    private List<MusicItem> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_songs, mContainer);
        findViewById(R.id.uploadMusic).setOnClickListener(this);
        mSongsList = (ListView) findViewById(R.id.songsList);
        mSongsList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        getData();

    }

    private void getData(){
        try {
            String[] str = Util.getData(this).split("\\" +Util.FILE_SEPERATOR);
            for(int i = 0; i < str.length; i++){

                MusicItem item = new MusicItem();
                item.setFilePath(str[i]);
                item.setDuration(getDuration(str[i]));
                data.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        setAdapter();
    }

    private void setAdapter(){
        mAdapter = new MusicAdapter(this, data);
        mSongsList.setAdapter(mAdapter);
    }

    private String getDuration(String source) throws IOException {
        MediaPlayer mp = new MediaPlayer();
        mp.setDataSource(source);
        int duration = mp.getDuration();

        int seconds = (int) (duration / 1000) % 60 ;
        int minutes = (int) ((duration / (1000*60)) % 60);

        return minutes+" : "+seconds;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        setMenuItem(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.uploadMusic){
            Intent intent = new Intent();
            intent.setType("audio/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Select Audio "), MUSIC_REQ_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == MUSIC_REQ_CODE){
            if(resultCode == RESULT_OK){
                //the selected audio.
                Uri uri = data.getData();
                String path = Util.getPath(this, uri);
                path = path + Util.FILE_SEPERATOR;
                System.out.println(path);
                Util.saveData(this, path);
                getData();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
