package com.android.guessaboo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class TextActivity extends BaseActivity implements View.OnClickListener {

    private EditText mText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_text, mContainer);
        mText = (EditText) findViewById(R.id.text);
        mText.setDrawingCacheEnabled(true);

        findViewById(R.id.center).setOnClickListener(this);
        findViewById(R.id.left).setOnClickListener(this);
        findViewById(R.id.right).setOnClickListener(this);
        findViewById(R.id.bold).setOnClickListener(this);
        findViewById(R.id.italic).setOnClickListener(this);
        findViewById(R.id.underline).setOnClickListener(this);

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
            Bitmap bitmap = mText.getDrawingCache();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.center:
                mText.setGravity(Gravity.CENTER);
                break;
            case R.id.right:
                mText.setGravity(Gravity.RIGHT|Gravity.CENTER);
                break;
            case R.id.left:
                mText.setGravity(Gravity.LEFT|Gravity.CENTER);
                break;
            case R.id.underline:
                SpannableString content = new SpannableString(mText.getText().toString());
                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                mText.setText(content);
                break;
            case R.id.bold:
                mText.setTypeface(null, Typeface.BOLD);
                break;
            case R.id.italic:
                mText.setTypeface(null, Typeface.ITALIC);
                break;
            default:
                break;
        }
    }
}
