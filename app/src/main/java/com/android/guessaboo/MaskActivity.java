package com.android.guessaboo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.android.guessaboo.adapter.ImageAdapter;

public class MaskActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private GridView mGridView;
    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.mask1, R.drawable.mask2,
            R.drawable.mask3, R.drawable.mask4
    };

    private int IMAGE_ID;
    private EditText mMaskEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_mask, mContainer);
        mGridView = (GridView) findViewById(R.id.maskView);
        mMaskEt = (EditText) findViewById(R.id.maskName);
        ImageAdapter adapter = new ImageAdapter(this, mThumbIds);
        mGridView.setAdapter(adapter);
        mGridView.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
        mGridView.setOnItemClickListener(this);
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
            if(!TextUtils.isEmpty(mMaskEt.getText().toString())) {
                Intent intent = new Intent();
                intent.putExtra("image", IMAGE_ID);
                setResult(PhotoMaskActivity.MASK_CODE, intent);
                finish();
            }else
                mMaskEt.setError("Mask name is required");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        IMAGE_ID = (int) adapterView.getItemAtPosition(i);
    }
}
