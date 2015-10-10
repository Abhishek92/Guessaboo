package com.android.guessaboo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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
    private EditText mCPassword;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_login, mContainer);

        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        mCPassword = (EditText) findViewById(R.id.confirmPassword);

        findViewById(R.id.submit).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.submit){
            if(validate())
                doLogin();
        }
    }

    private void doLogin(){
        Map<String, String> request = new HashMap<>();
        request.put("email", mUsername.getText().toString());
        request.put("pwd", mPassword.getText().toString());

        if (Util.isNetworkConnected(this)) {
            dialog = getProgressDialog(this, "Please wait...");
            GuessabooRestClient.getGuessabooApi().doLogin("login", request, new Callback<LoginModel>() {
                @Override
                public void success(LoginModel loginModel, Response response) {
                    cancelDialog();
                    saveLoginData(loginModel);
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

    private void saveLoginData(LoginModel data){
        SavePreferences prefs = new SavePreferences(this);
        prefs.setIsLoggedIn(true);
        prefs.setEmail(data.getUserEmail());
        prefs.setUserId(data.getUserId());
        prefs.setUserName(data.getUserFullname());

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private boolean validate(){
        boolean flag = true;
        if (TextUtils.isEmpty(mUsername.getText().toString())) {
            mUsername.setError("Email address is required");
            flag = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mUsername.getText().toString()).matches()) {
            mUsername.setError("Email address is required");
            flag = false;
        } else if (TextUtils.isEmpty(mPassword.getText().toString())) {
            mPassword.setError("Password is required");
            flag = false;
        } else if(mPassword.getText().toString().length() < 5){
            mPassword.setError("Password length must be greater than 5 digit");
            flag = false;
        }else if(TextUtils.isEmpty(mCPassword.getText().toString())){
            mCPassword.setError("Incorrect password");
            flag = false;
        }else if(!mCPassword.getText().toString().equals(mPassword.getText().toString())){
            mCPassword.setError("Incorrect password");
            flag = false;
        }

        return flag;
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
