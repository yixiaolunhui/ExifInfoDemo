package com.dalong.exifinfodemo;

import android.content.Intent;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dalong.exifinfodemo.utils.ExifInterfaceUtils;
import com.dalong.exifinfodemo.utils.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private  String TAG="MainActivity";
    private final int REQUEST_FILE = 1;
    private EditText exif_edit;
    private Button exif_save;
    private Button exif_select;
    private ImageView exif_img;
    private TextView exif_tv;
    private String imgPath="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    /**
     *  初始化view
     */
    private void initView() {
        exif_edit=(EditText)findViewById(R.id.exif_edit);
        exif_save=(Button)findViewById(R.id.exif_save);
        exif_select=(Button)findViewById(R.id.exif_select);
        exif_img=(ImageView)findViewById(R.id.exif_img);
        exif_tv=(TextView)findViewById(R.id.exif_tv);
        exif_save.setOnClickListener(this);
        exif_select.setOnClickListener(this);
        exif_img.setOnClickListener(this);
    }

    /**
     * 调用相册图片
     */
    private void loadImage() {
        Intent intent = new Intent( Intent.ACTION_GET_CONTENT );
        intent.setType( "image/*" );
        Intent chooser = Intent.createChooser( intent, "Choose picture" );
        startActivityForResult( chooser, REQUEST_FILE );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exif_save:
                saveExif();
                break;
            case R.id.exif_select:
                loadImage();
                break;
        }
    }

    /**
     * 保存exif信息
     */
    private void saveExif() {
        String exif=exif_edit.getText().toString();
        Log.v(TAG,"imgPath:"+imgPath);
        if(new File(imgPath).exists()){
            try {
                ExifInterfaceUtils.setExifByTag(imgPath, ExifInterface.TAG_MAKE,exif);
                exif_tv.setText(ExifInterfaceUtils.getExifByTag(imgPath,it.sephiroth.android.library.exif2.ExifInterface.TAG_MAKE));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data ) {

        if( resultCode == RESULT_OK ) {
            if( requestCode == REQUEST_FILE ) {
                Log.v(TAG,"uri:"+data.getData());
                showExifInfo( data.getData());
            }
        }
    }

    /**
     * 显示exif信息
     * @param data
     */
    private void showExifInfo(Uri data) {
        String exif="";
        imgPath = IOUtils.getImageAbsolutePath( this, data );
        Log.v(TAG,"imgPath:"+imgPath);
        if( null == imgPath ) {
            try {
                exif= ExifInterfaceUtils.getExifByTag(getContentResolver().openInputStream( data ), it.sephiroth.android.library.exif2.ExifInterface.TAG_MAKE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            exif=ExifInterfaceUtils.getExifByTag(imgPath,it.sephiroth.android.library.exif2.ExifInterface.TAG_MAKE);
        }
        exif_tv.setText(exif);
    }

}
