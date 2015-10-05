package com.android.guessaboo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;

public class BaseActivity extends AppCompatActivity {

    protected RelativeLayout mContainer;
    protected AdView mAdView;
    protected View customActionBarView;
    final CharSequence[] items = {"Take Photo", "Choose from gallery",
            "Cancel"};
    public static final int GALLERY_REQUEST = 1002;
    public static final int CAMERA_REQUEST = 1005;
    public static final int GALLERY_KITKAT_INTENT_CALLED = 1003;
    private Uri mCapturedImageURI;
    private String IMAGE_PATH;
    private MenuItem menuItem;
    protected boolean isImageSelected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* final LayoutInflater inflater = (LayoutInflater) getSupportActionBar().getThemedContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        customActionBarView = inflater.inflate(
                R.layout.action_bar_done_view, null);



        // Show the custom action bar view and hide the normal Home icon and title.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(
                ActionBar.DISPLAY_SHOW_CUSTOM,
                ActionBar.DISPLAY_SHOW_CUSTOM *//*| ActionBar.DISPLAY_SHOW_HOME
                        | ActionBar.DISPLAY_SHOW_TITLE*//*);
        actionBar.setCustomView(customActionBarView,
                new ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));*/
        setContentView(R.layout.activity_base);
        mContainer = (RelativeLayout) findViewById(R.id.container);
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        //mAdView.loadAd(adRequest);
        mAdView.setVisibility(View.GONE);


       // setDoneBar(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menuItem = menu.findItem(R.id.action_done);
        setMenuItem(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    protected void setMenuItem(boolean flag){
        if(flag)
            menuItem.setVisible(true);
        else
            menuItem.setVisible(false);
    }

    public void showPicker() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Your Option!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    cameraClick();
                } else if (items[item].equals("Choose from gallery")) {
                    galleryClick();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void cameraClick() {
        // dialog.dismiss();
        String fileName = "temp.jpg";
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        mCapturedImageURI = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
        intent.putExtra("return-data", false);

        startActivityForResult(intent, CAMERA_REQUEST);
    }

    public void galleryClick() {
        // dialog.dismiss();
        if (Build.VERSION.SDK_INT < 19) {
            try {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, GALLERY_REQUEST);
            } catch (ActivityNotFoundException ex) {
                ex.printStackTrace();
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_KITKAT_INTENT_CALLED);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (data != null) {
                mCapturedImageURI = data.getData();
            }

            if ((requestCode == GALLERY_REQUEST || requestCode == GALLERY_KITKAT_INTENT_CALLED)
                    && resultCode == Activity.RESULT_OK) {
                if (requestCode == GALLERY_REQUEST) {
                    mCapturedImageURI = data.getData();
                    String[] column = {MediaStore.Images.Media.DATA};
                    Cursor c = getContentResolver().query(mCapturedImageURI,
                            column, null, null, null);
                    c.moveToFirst();
                    String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                    IMAGE_PATH = FileUtils.getPath(this, mCapturedImageURI);
                    isImageSelected = true;
                    onImageSet();

                    c.close();
                    Log.d("Uri Got Path", "" + path);

                } else if (requestCode == GALLERY_KITKAT_INTENT_CALLED) {

                    IMAGE_PATH = FileUtils.getPath(this, mCapturedImageURI);
                    isImageSelected = true;
                    mCapturedImageURI = Uri.fromFile(new File(IMAGE_PATH));
                    onImageSet();

                }
            } else if (requestCode == CAMERA_REQUEST
                    && resultCode == Activity.RESULT_OK) {
                String[] projection = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(mCapturedImageURI,
                        projection, null, null, null);
                int column_index_data = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                IMAGE_PATH = cursor.getString(column_index_data);
                isImageSelected = true;
                onImageSet();
                cursor.close();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Drawable getBitmapDrawable(ViewGroup parent){
        if(!TextUtils.isEmpty(IMAGE_PATH)){
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(IMAGE_PATH,bmOptions);
            //bitmap = Bitmap.createScaledBitmap(bitmap,parent.getWidth(),parent.getHeight(),true);
            return new BitmapDrawable(getResources(), bitmap);
        }else
            return null;
    }

    protected void onImageSet(){

    }
}
