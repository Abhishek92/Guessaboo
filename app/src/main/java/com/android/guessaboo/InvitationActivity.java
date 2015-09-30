package com.android.guessaboo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InvitationActivity extends BaseActivity implements View.OnClickListener {

    private TextView timerView;
    private EditText mDecoyName;
    private TextView mChallengerMsg;
    private TextView mCongratsMsg;
    private TextView mDefetMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_invitation, mContainer);
        timerView = (TextView) findViewById(R.id.timer);
        mDecoyName = (EditText) findViewById(R.id.decoyName);
        mChallengerMsg = (TextView) findViewById(R.id.challengerMessage);
        mCongratsMsg = (TextView) findViewById(R.id.congratulationMessage);
        mDefetMsg = (TextView) findViewById(R.id.defeatMessage);
        findViewById(R.id.setTimer).setOnClickListener(this);
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
            if(validate())
                startActivity(new Intent(this, PhotoShareActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.setTimer){
            Util.showTimePicker(this, timerView);
        }
    }

    private boolean validate(){
        if(TextUtils.isEmpty(mDecoyName.getText().toString())){
            mDecoyName.setError("Decoy names is required");
            return false;
        }
        else if(TextUtils.isEmpty(mChallengerMsg.getText().toString())) {
            mChallengerMsg.setError("Challenger message is required");
            return false;
        }
        else if(TextUtils.isEmpty(mCongratsMsg.getText().toString())) {
            mCongratsMsg.setError("Congrats message is required");
            return false;
        }
        else if(TextUtils.isEmpty(mDefetMsg.getText().toString())) {
            mDefetMsg.setError("Defeat message is required");
            return false;
        }
        else if(timerView.getText().toString().equals("00:00")) {
            Toast.makeText(this, "Please set time first", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;
    }
}
