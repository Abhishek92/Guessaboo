package com.android.guessaboo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.guessaboo.api.GuessabooRestClient;
import com.android.guessaboo.model.LoginModel;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText mUsername;
    private EditText mPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_login, mContainer);

        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);

        findViewById(R.id.submit).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.submit){
            doLogin();
        }
    }

    private void doLogin(){
        Map<String, String> request = new HashMap<>();
        request.put("email", mUsername.getText().toString());
        request.put("pwd", mPassword.getText().toString());
        GuessabooRestClient.getGuessabooApi().doLogin("login", request, new Callback<LoginModel>() {
            @Override
            public void success(LoginModel loginModel, Response response) {
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
