package com.android.guessaboo;

import android.app.Activity;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class PhotoMaskActivity extends BaseActivity implements View.OnClickListener,View.OnTouchListener, View.OnDragListener {

    private FrameLayout mWorkSpace;
    private TextView mDesc;
    private ImageView mImg;
    protected final static int MASK_CODE = 14;
    protected final static int STICKER_CODE = 15;
    protected final static int TEXT_CODE = 16;
    protected final static int MUSIC_CODE = 17;
    private String MUSIC_FILE_PATH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_photo_mask, mContainer);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int deviceHeight = displayMetrics.heightPixels;

        mWorkSpace = (FrameLayout) findViewById(R.id.workspace);
        mImg = (ImageView) findViewById(R.id.bgImage);
        mDesc = (TextView) findViewById(R.id.desc);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mWorkSpace.getLayoutParams();
        params.height = (int) (deviceHeight * .40f);
        mWorkSpace.setLayoutParams(params);

        mDesc.setText(getDescText());

        findViewById(R.id.musicBtn).setOnClickListener(this);
        findViewById(R.id.maskBtn).setOnClickListener(this);
        findViewById(R.id.stickerBtn).setOnClickListener(this);
        findViewById(R.id.textBtn).setOnClickListener(this);
        findViewById(R.id.uploadBtn).setOnClickListener(this);
        findViewById(R.id.musicBtn).setOnClickListener(this);

        mWorkSpace.setOnDragListener(this);

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
            if(!isImageSelected)
                Toast.makeText(this, "Workspace is empty!", Toast.LENGTH_LONG).show();
            else
                showSaveLayoutDialog();
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
            case R.id.musicBtn:
                startActivityForResult(new Intent(this, SongsActivity.class), MUSIC_CODE, null);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onImageSet() {
        super.onImageSet();
        Drawable drawable = getBitmapDrawable(mWorkSpace);
        if(drawable != null) {
            mImg.setImageDrawable(drawable);
            //mWorkSpace.setBackground(drawable);
            mDesc.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MASK_CODE && resultCode == MASK_CODE){
            if(data != null){
                int id = data.getIntExtra("image",0);
                addImageToWorkspace(id);
                mDesc.setVisibility(View.GONE);
            }
        }else if(requestCode == STICKER_CODE && resultCode == STICKER_CODE){
            if(data != null){
                int id = data.getIntExtra("image",0);
                addImageToWorkspace(id);
                mDesc.setVisibility(View.GONE);
            }
        }else if(requestCode == TEXT_CODE && resultCode == TEXT_CODE){
            addImageFromFileToWorkspace();
        }else if(requestCode == MUSIC_CODE && resultCode == MUSIC_CODE){
            if(data != null){
                MUSIC_FILE_PATH = data.getStringExtra("music");
            }
        }
    }

    @Override
    public boolean onDrag(View view, DragEvent event) {
        int action = event.getAction();
        View myView;
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
                myView = (View) event.getLocalState();
                if(myView != null) {
                    myView.setX(X - (myView.getWidth() / 2));
                    myView.setY(Y - (myView.getHeight() / 2));
                    myView.setVisibility(View.VISIBLE);
                }

                break;
            case DragEvent.ACTION_DRAG_ENDED:
            default:
                break;
        }
        return true;
    }

    private void addImageToWorkspace(int id){
        ImageView iv = new ImageView(this);
        iv.setLayoutParams(new ViewGroup.LayoutParams(convertDpToPx(90), convertDpToPx(90)));
        iv.setImageResource(id);
        iv.setOnTouchListener(this);
        mWorkSpace.addView(iv);
    }

    private void addImageFromFileToWorkspace(){
        File imgFile = new  File(Util.FILE_PATH);

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(new ViewGroup.LayoutParams(convertDpToPx(90), convertDpToPx(90)));
            iv.setImageBitmap(myBitmap);
            iv.setOnTouchListener(this);
            mWorkSpace.addView(iv);

        }
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

    private void showSaveLayoutDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Save layout");

        // Setting Dialog Message
        alertDialog.setMessage("Are you satisfied with object placement?");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Util.convertToPng(mWorkSpace);
                Intent intent = new Intent(PhotoMaskActivity.this, InvitationActivity.class);
                intent.putExtra("music", MUSIC_FILE_PATH);
                startActivity(intent);
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }

    private String getDescText(){

        String text = "1. Tap upload button to upload an image."+"\n"
                +"2. Select a mask to mask the subject or object."+"\n"
                +"3. Enter subject or object name behind the mask."+"\n"
                +"4. Add sticker, text and music as desire and click done.";

        return text;

    }

    private int convertDpToPx(int size){
        final float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (size * scale + 0.5f);
        return pixels;
    }
}
