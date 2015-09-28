package com.android.guessaboo;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PhotoMaskActivity extends BaseActivity implements View.OnClickListener,View.OnTouchListener, View.OnDragListener {

    private LinearLayout mWorkSpace;
    private ImageView mImg;
    protected final static int MASK_CODE = 14;
    protected final static int STICKER_CODE = 15;
    protected final static int TEXT_CODE = 16;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_photo_mask, mContainer);

        mWorkSpace = (LinearLayout) findViewById(R.id.workspace);
        //mImg = (ImageView) findViewById(R.id.img);

        findViewById(R.id.musicBtn).setOnClickListener(this);
        findViewById(R.id.maskBtn).setOnClickListener(this);
        findViewById(R.id.stickerBtn).setOnClickListener(this);
        findViewById(R.id.textBtn).setOnClickListener(this);
        findViewById(R.id.uploadBtn).setOnClickListener(this);

        mWorkSpace.setOnDragListener(this);
        /*mImg.setOnTouchListener(this);

        mImg.setVisibility(View.VISIBLE);*/
    }

    @Override
    protected void onResume() {
        super.onResume();
       // mImg.setVisibility(View.VISIBLE);
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
            startActivity(new Intent(this, InvitationActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.maskBtn:
                startActivityForResult(new Intent(this, MaskActivity.class), MASK_CODE, null);
                break;
            case R.id.stickerBtn:
                startActivityForResult(new Intent(this, StickerActivity.class), STICKER_CODE, null);
                break;
            case R.id.textBtn:
                startActivityForResult(new Intent(this, TextActivity.class), TEXT_CODE, null);
                break;
            case R.id.uploadBtn:
                showPicker();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onImageSet() {
        super.onImageSet();
        Drawable drawable = getBitmapDrawable(mWorkSpace);
        if(drawable != null)
            mWorkSpace.setBackground(drawable);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MASK_CODE || requestCode == Activity.RESULT_OK){
            if(data != null){
                int id = data.getIntExtra("image",0);
                addImageToWorkspace(id);
            }
        }else if(requestCode == STICKER_CODE){
            if(data != null){
                int id = data.getIntExtra("image",0);
                addImageToWorkspace(id);
            }
        }
    }

    @Override
    public boolean onDrag(View view, DragEvent event) {
        int action = event.getAction();
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                // do nothing
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                break;
            case DragEvent.ACTION_DROP:
                // Dropped, reassign View to ViewGroup
                float X = event.getX();
                float Y = event.getY();

                Log.d("Log", "X " + (int) X + "Y " + (int) Y);
                View myView = (View) event.getLocalState();
                myView.setX(X - (myView.getWidth() / 2));
                myView.setY(Y - (myView.getHeight() / 2));
                myView.setVisibility(View.VISIBLE);

                break;
            case DragEvent.ACTION_DRAG_ENDED:
            default:
                break;
        }
        return true;
    }

    private void addImageToWorkspace(int id){
        ImageView iv = new ImageView(this);
        iv.setLayoutParams(new ViewGroup.LayoutParams(80,80));
        iv.setImageResource(id);
        iv.setOnTouchListener(this);
        mWorkSpace.addView(iv);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);
            return true;
        } else {
            return false;
        }
    }
}
