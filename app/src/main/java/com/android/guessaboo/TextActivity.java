package com.android.guessaboo;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class TextActivity extends BaseActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener {

    private EditText mText;
    private Typeface academyTf;
    private Typeface agencyTf;
    private Typeface albaTf ;
    private Typeface arialTf;
    private RadioGroup fontGroup;
    private RadioButton academy;
    private RadioButton agency;
    private RadioButton alba;
    private RadioButton arial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_text, mContainer);
        mText = (EditText) findViewById(R.id.text);
        fontGroup = (RadioGroup) findViewById(R.id.points_radio_group);
        academy = (RadioButton) findViewById(R.id.academy);
        agency = (RadioButton) findViewById(R.id.agency);
        alba = (RadioButton) findViewById(R.id.alab);
        arial = (RadioButton) findViewById(R.id.arial);

        initFont();
        setTextFont();

        mText.setDrawingCacheEnabled(true);

        findViewById(R.id.center).setOnClickListener(this);
        findViewById(R.id.left).setOnClickListener(this);
        findViewById(R.id.right).setOnClickListener(this);
        findViewById(R.id.bold).setOnClickListener(this);
        findViewById(R.id.italic).setOnClickListener(this);
        findViewById(R.id.underline).setOnClickListener(this);
        fontGroup.setOnCheckedChangeListener(this);

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
            if(!TextUtils.isEmpty(mText.getText().toString())) {
                mText.setBackgroundResource(android.R.color.transparent);
                Bitmap bitmap = mText.getDrawingCache();
                Util.saveBitmap(bitmap);
                setResult(PhotoMaskActivity.TEXT_CODE);
                finish();
                return true;
            }else
                mText.setError("Text is required");
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
                mText.setGravity(Gravity.RIGHT | Gravity.CENTER);
                break;
            case R.id.left:
                mText.setGravity(Gravity.LEFT | Gravity.CENTER);
                break;
            case R.id.underline:
                SpannableString content = new SpannableString(mText.getText().toString());
                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                mText.setText(content);
                break;
            case R.id.bold:
                removeUnderline(mText.getText().toString());
                mText.setTypeface(null, Typeface.BOLD);
                break;
            case R.id.italic:
                removeUnderline(mText.getText().toString());
                mText.setTypeface(null, Typeface.ITALIC);
                break;
            default:
                break;
        }
    }

    private void removeUnderline(String text){
        SpannableString ss= new SpannableString(text);
        ss.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        },  0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void setTextFont(){
        academy.setTypeface(academyTf);
        agency.setTypeface(agencyTf);
        arial.setTypeface(arialTf);
        alba.setTypeface(albaTf);
    }

    private void initFont(){
        academyTf = Typeface.createFromAsset(getApplicationContext().getAssets(), "academy.ttf");
        agencyTf = Typeface.createFromAsset(getApplicationContext().getAssets(), "agency_fb.ttf");
        albaTf = Typeface.createFromAsset(getApplicationContext().getAssets(), "alba.ttf");
        arialTf = Typeface.createFromAsset(getApplicationContext().getAssets(), "arial.ttf");

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.arial:
                mText.setTypeface(arialTf);
                break;
            case R.id.academy:
                mText.setTypeface(academyTf);
                break;
            case R.id.agency:
                mText.setTypeface(agencyTf);
                break;
            case R.id.alab:
                mText.setTypeface(albaTf);
                break;
            default:
                break;
        }
    }
}
