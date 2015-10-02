package com.android.guessaboo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class PhotoShareActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_photo_share, mContainer);

        String challengeImagePath = Util.IMAGE_PATH;
        String musicPath = getIntent().getStringExtra("music");
        String decoyName = getIntent().getStringExtra("decoyName");
        String challengerMsg = getIntent().getStringExtra("challengerMsg");
        String congratsMsg = getIntent().getStringExtra("congratsMsg");
        String defeatMsg = getIntent().getStringExtra("defeatMsg");
        long timer = Util.getSongDurationInMillis(musicPath);

        findViewById(R.id.mail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
