package com.android.guessaboo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChallengeActivity extends BaseActivity {

    private LinearLayout mWorkSpace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_challenge, mContainer);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int deviceHeight = displayMetrics.heightPixels;

        mWorkSpace = (LinearLayout) findViewById(R.id.workspace);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mWorkSpace.getLayoutParams();
        params.height = (int) (deviceHeight * .40f);
        mWorkSpace.setLayoutParams(params);

    }
}
