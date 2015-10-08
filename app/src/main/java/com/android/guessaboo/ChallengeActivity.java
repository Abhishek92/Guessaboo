package com.android.guessaboo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.guessaboo.model.ChallengeModel;

public class ChallengeActivity extends BaseActivity {

    private LinearLayout mWorkSpace;
    private TextView mDecoyNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_challenge, mContainer);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int deviceHeight = displayMetrics.heightPixels;

        mWorkSpace = (LinearLayout) findViewById(R.id.workspace);
        mDecoyNames = (TextView) findViewById(R.id.names);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mWorkSpace.getLayoutParams();
        params.height = (int) (deviceHeight * .40f);
        mWorkSpace.setLayoutParams(params);

        showChallengeDialog();
        Drawable drawable = getBitmapDrawable(Util.IMAGE_PATH);
        if(drawable != null) {
            mWorkSpace.setBackground(drawable);
        }
        //setData();

    }

    private void setData(){
        ChallengeModel data = getIntent().getParcelableExtra("challengeData");
        mDecoyNames.setText(data.getDecayname());

    }

    private void showChallengeDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Challenge");

        // Setting Dialog Message
        alertDialog.setMessage("Do you want to accept this challenge?");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        // Showing Alert Message
        alertDialog.setCancelable(false);
        alertDialog.show();
    }


}
