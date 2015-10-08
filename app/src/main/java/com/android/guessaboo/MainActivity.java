package com.android.guessaboo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.guessaboo.api.GuessabooRestClient;
import com.android.guessaboo.model.ChallengeModel;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends BaseActivity {

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_main, mContainer);

        Typeface albaTf = Typeface.createFromAsset(getApplicationContext().getAssets(), "alba.ttf");
        TextView tv = (TextView) findViewById(R.id.title);
        tv.setTypeface(albaTf);

        findViewById(R.id.homeIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProjectActivity.class));
            }
        });



        SavePreferences prefs = new SavePreferences(this);
        if(prefs.isLoggedIn())
            getChallenge();
        if(!prefs.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    private void getChallenge(){
        String email = new SavePreferences(this).getEmail();
        if(Util.isNetworkConnected(this)){
            dialog = getProgressDialog(this,"Please wait...");
            GuessabooRestClient.getGuessabooApi().getChallenges("challengesByUser", email, new Callback<List<ChallengeModel>>() {
                @Override
                public void success(List<ChallengeModel> challengeModels, Response response) {
                    cancelDialog();
                    if(challengeModels != null && challengeModels.size() != 0) {
                        ChallengeModel model = challengeModels.get(0);
                        openChallenge(model);
                    }else
                        Toast.makeText(getApplicationContext(), "No challenges yet", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void failure(RetrofitError error) {
                    cancelDialog();
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else
            Toast.makeText(this, "No internet connection!", Toast.LENGTH_SHORT).show();

    }

    private void openChallenge(ChallengeModel data){
        Intent intent = new Intent(this, ChallengeActivity.class);
        intent.putExtra("challengeData", data);
        startActivity(intent);
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
