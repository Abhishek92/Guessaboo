package com.android.guessaboo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.guessaboo.api.GuessabooRestClient;
import com.android.guessaboo.model.CommonResponse;
import com.android.guessaboo.model.LoginModel;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PhotoShareActivity extends BaseActivity {


    private String challengeImagePath;
    private String musicPath;
    private String decoyName;
    private String challengerMsg;
    private String congratsMsg;
    private String defeatMsg;
    private long timer;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_photo_share, mContainer);

        challengeImagePath = Util.IMAGE_PATH;
        musicPath = getIntent().getStringExtra("music");
        decoyName = getIntent().getStringExtra("decoyName");
        challengerMsg = getIntent().getStringExtra("challengerMsg");
        congratsMsg = getIntent().getStringExtra("congratsMsg");
        defeatMsg = getIntent().getStringExtra("defeatMsg");
        if(!TextUtils.isEmpty(musicPath))
            timer = Util.getSongDurationInMillis(musicPath);

        findViewById(R.id.mail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImage();
            }
        });
    }

    private void shareImage(){
        if (Util.isNetworkConnected(this)) {
            dialog = getProgressDialog(this, "Please wait...");
            GuessabooRestClient.getGuessabooApi().doShareImage("inviteChaleganes", getRequestParams(), new Callback<CommonResponse>() {
                @Override
                public void success(CommonResponse commonResponse, Response response) {
                    cancelDialog();
                }

                @Override
                public void failure(RetrofitError error) {
                    cancelDialog();
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }else
            Toast.makeText(this, "No internet connection!", Toast.LENGTH_SHORT).show();
    }


    private Map<String, Object> getRequestParams(){
        Map<String, Object> params = new HashMap<>();
        SavePreferences prefs = new SavePreferences(this);
        params.put("userId",prefs.getUserId());
        params.put("touser", "");
        params.put("fromusername", prefs.getEmail());
        params.put("song","");
        params.put("songduration","");
        params.put("challengemessage", challengerMsg);
        params.put("congurationmessage", congratsMsg);
        params.put("defeedmessage", defeatMsg);
        params.put("decayname", decoyName);
        params.put("shareimage", Util.getBase64ChallengeImage());

        return params;
    }

    public ProgressDialog getProgressDialog(Context context,String message)
    {
        dialog = new ProgressDialog(context);
        dialog.setMessage(message);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        return dialog;
    }

    private void cancelDialog(){
        if(dialog != null && dialog.isShowing())
            dialog.cancel();
    }
}
