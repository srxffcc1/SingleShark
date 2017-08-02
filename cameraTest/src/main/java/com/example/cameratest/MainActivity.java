package com.example.cameratest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.example.cameratest.CustomCameraView.OnTakePictureInfo;


import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

  private CustomCameraView cameraView = null;
  private Button btn = null;
  private ImageView imv_pic = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    cameraView = (CustomCameraView) findViewById(R.id.cc_camera);
    imv_pic = (ImageView) findViewById(R.id.imv_pic);
    btn = (Button) findViewById(R.id.btn_showcamera);
    btn.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        cameraView.takePicture();
      }
    });

    cameraView.setOnTakePictureInfo(new OnTakePictureInfo() {

      @Override
      public void onTakePictureInofo(boolean _success, File _file) {
        if (_success) {
          Bitmap bitmap = getLoacalBitmap(_file.getAbsolutePath());
          imv_pic.setImageBitmap(bitmap);
        }
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  /**
   * 加载本地图片
   * 
   * @param url
   * @return
   */
  public Bitmap getLoacalBitmap(String url) {
    try {
      FileInputStream fis = new FileInputStream(url);
      return BitmapFactory.decodeStream(fis); // /把流转化为Bitmap图片

    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

}
