package com.android.guessaboo;

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
        String email = "sjain@componence.in";/*new SavePreferences(this).getEmail();*/
        GuessabooRestClient.getGuessabooApi().getChallenges("challengesByUser", email, new Callback<List<ChallengeModel>>() {
            @Override
            public void success(List<ChallengeModel> challengeModels, Response response) {
                if(challengeModels != null && challengeModels.size() != 0) {
                    ChallengeModel model = challengeModels.get(0);
                    openChallenge(model);
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openChallenge(ChallengeModel data){
        Intent intent = new Intent(this, ChallengeActivity.class);
        intent.putExtra("challengeData", data);
        startActivity(intent);
    }
}
