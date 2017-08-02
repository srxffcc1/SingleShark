package com.example.cameratest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.os.Environment;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressLint("ShowToast")
public class CustomCameraView extends FrameLayout implements SurfaceHolder.Callback, AutoFocusCallback {
  public static final String CUSTOMECAMERA_PHOTO_PATH = "/cameraviewpicture/";
  private Context context = null;
  private Camera camera = null;
  private SurfaceHolder surface_holder = null;
  private SurfaceView surface_camera = null;
  private int viewWidth = 0;
  private int viewHeight = 0;
  private OnTakePictureInfo onTakePictureInfo = null;
  private View view_focus = null;
  private PreviewFrameLayout frameLayout = null;

  /**
   * 模式 NONE：无 FOCUSING：正在聚焦. FOCUSED:聚焦成功 FOCUSFAIL：聚焦失败
   * 
   * 
   */
  private enum MODE {
    NONE, FOCUSING, FOCUSED, FOCUSFAIL

  };

  private MODE mode = MODE.NONE;// 默认模式

  public CustomCameraView(Context context) {
    super(context);
  }

  public CustomCameraView(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.context = context;
    LayoutInflater.from(context).inflate(R.layout.preview_frame, this);
    frameLayout = (PreviewFrameLayout) findViewById(R.id.frame_layout);
    surface_camera = (SurfaceView) findViewById(R.id.camera_preview);
    view_focus = findViewById(R.id.view_focus);
    surface_holder = surface_camera.getHolder();
    surface_holder.addCallback(this);
    frameLayout.setOnTouchListener(onTouchListener);
  }

  @Override
  public void surfaceCreated(SurfaceHolder holder) {
    if (checkCameraHardware()) {
      camera = getCameraInstance();
    }
    try {
      camera.setPreviewDisplay(surface_holder);
    } catch (IOException e) {
    }
    updateCameraParameters();
    camera.startPreview();
  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    // try {
    // camera.setPreviewDisplay(surface_holder);
    // updateCameraParameters();
    // camera.startPreview();
    // } catch (IOException e) {
    // // TODO 自动生成的 catch 块
    // e.printStackTrace();
    // }
  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {
    if (camera != null && holder != null) {
      camera.stopPreview();
      camera.release();
    }

  }

  @Override
  protected void onMeasure(int widthSpec, int heightSpec) {

    viewWidth = MeasureSpec.getSize(widthSpec);
    viewHeight = MeasureSpec.getSize(heightSpec);

    super.onMeasure(MeasureSpec.makeMeasureSpec(viewWidth, MeasureSpec.EXACTLY),
        MeasureSpec.makeMeasureSpec(viewHeight, MeasureSpec.EXACTLY));
  }

  private boolean checkCameraHardware() {
    if (context != null && context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
      return true;
    } else {
      return false;
    }
  }

  private Camera getCameraInstance() {
    Camera c = null;
    try {
      int cameraCount = 0;

      Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
      cameraCount = Camera.getNumberOfCameras(); // get cameras number

      for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
        Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) { // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
          try {
            c = Camera.open(camIdx);
          } catch (RuntimeException e) {
          }
        }
      }
      if (c == null) {
        c = Camera.open(0); // attempt to get a Camera instance
      }
    } catch (Exception e) {
      // Toast.makeText(context, "摄像头打开失败！", Toast.LENGTH_SHORT);
    }
    return c;
  }

  private void updateCameraParameters() {
    if (camera != null) {
      Camera.Parameters p = camera.getParameters();

      long time = new Date().getTime();
      p.setGpsTimestamp(time);

      Size previewSize = findBestPreviewSize(p);
      p.setPreviewSize(previewSize.width, previewSize.height);
      p.setPictureSize(previewSize.width, previewSize.height);

      // Set the preview frame aspect ratio according to the picture size.

      frameLayout.setAspectRatio((double) previewSize.width / previewSize.height);

      if (context.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
        camera.setDisplayOrientation(90);
        p.setRotation(90);
      }
      try {
        camera.setParameters(p);
      } catch (Exception e) {
        System.out.println(p.getPreviewSize());

        System.out.println(p.getPictureSize());
        e.printStackTrace();
      }
    }
  }

  /**
   * 找到最合适的显示分辨率 （防止预览图像变形）
   * @param parameters
   * @return
   */
  private Size findBestPreviewSize(Camera.Parameters parameters) {

    //系统支持的所有预览分辨率
    String previewSizeValueString = null;
    previewSizeValueString = parameters.get("preview-size-values");

    if (previewSizeValueString == null) {
      previewSizeValueString = parameters.get("preview-size-value");
    }

    if (previewSizeValueString == null) { // 有些手机例如m9获取不到支持的预览大小 就直接返回屏幕大小
      return camera.new Size(getScreenWH().widthPixels, getScreenWH().heightPixels);
    }
    float bestX = 0;
    float bestY = 0;

    float tmpRadio = 0;
    float viewRadio = 0;

    if (viewWidth != 0 && viewHeight != 0) {
      viewRadio = Math.min((float) viewWidth, (float) viewHeight) / Math.max((float) viewWidth, (float) viewHeight);
    }
    // System.out.println("CustomCameraView previewSizeValueString COMMA_PATTERN = "
    // + previewSizeValueString);

    String[] COMMA_PATTERN = previewSizeValueString.split(",");
    for (String prewsizeString : COMMA_PATTERN) {
      prewsizeString = prewsizeString.trim();

      int dimPosition = prewsizeString.indexOf('x');
      if (dimPosition == -1) {
        continue;
      }

      float newX = 0;
      float newY = 0;

      try {
        newX = Float.parseFloat(prewsizeString.substring(0, dimPosition));
        newY = Float.parseFloat(prewsizeString.substring(dimPosition + 1));
      } catch (NumberFormatException e) {
        continue;
      }

      float radio = Math.min(newX, newY) / Math.max(newX, newY);
      if (tmpRadio == 0) {
        tmpRadio = radio;
        bestX = newX;
        bestY = newY;
      } else if (tmpRadio != 0 && (Math.abs(radio - viewRadio)) < (Math.abs(tmpRadio - viewRadio))) {
        tmpRadio = radio;
        bestX = newX;
        bestY = newY;
      }
    }

    if (bestX > 0 && bestY > 0) {
      // System.out.println("CustomCameraView previewSizeValueString bestX = " +
      // bestX + ", bestY = " + bestY);
      return camera.new Size((int) bestX, (int) bestY);
    }
    return null;
  }

  /**
   * 点击显示焦点区域
   */
  OnTouchListener onTouchListener = new OnTouchListener() {

    @SuppressWarnings("deprecation")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
      if (event.getAction() == MotionEvent.ACTION_DOWN) {
        // System.out.println("CustomCameraView view_focus.getWidth() " +
        // view_focus.getWidth() + ",  view_focus.getHeight() = " +
        // view_focus.getHeight());
        // System.out.println("CustomCameraView event.getX() " + event.getX() +
        // ",  event.getY() = " + event.getY());

        int width = view_focus.getWidth();
        int height = view_focus.getHeight();
        view_focus.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_focus_focusing));
        view_focus.setX(event.getX() - (width / 2));
        view_focus.setY(event.getY() - (height / 2));
      } else if (event.getAction() == MotionEvent.ACTION_UP) {
        mode = MODE.FOCUSING;
        focusOnTouch(event);
      }

      return true;
    }
  };

  /**
   * 设置焦点和测光区域
   * @param event
   */
  public void focusOnTouch(MotionEvent event) {

    int[] location = new int[2];
    frameLayout.getLocationOnScreen(location);

    Rect focusRect = calculateTapArea(view_focus.getWidth(), view_focus.getHeight(), 1f, event.getRawX(), event.getRawY(),
        location[0], location[0] + frameLayout.getWidth(), location[1], location[1] + frameLayout.getHeight());
    Rect meteringRect = calculateTapArea(view_focus.getWidth(), view_focus.getHeight(), 1.5f, event.getRawX(), event.getRawY(),
        location[0], location[0] + frameLayout.getWidth(), location[1], location[1] + frameLayout.getHeight());

    Camera.Parameters parameters = camera.getParameters();
    parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

    // System.out.println("CustomCameraView getMaxNumFocusAreas = " +
    // parameters.getMaxNumFocusAreas());
    if (parameters.getMaxNumFocusAreas() > 0) {
      List<Camera.Area> focusAreas = new ArrayList<Camera.Area>();
      focusAreas.add(new Camera.Area(focusRect, 1000));

      parameters.setFocusAreas(focusAreas);
    }

    // System.out.println("CustomCameraView getMaxNumMeteringAreas = " +
    // parameters.getMaxNumMeteringAreas());
    if (parameters.getMaxNumMeteringAreas() > 0) {
      List<Camera.Area> meteringAreas = new ArrayList<Camera.Area>();
      meteringAreas.add(new Camera.Area(meteringRect, 1000));

      parameters.setMeteringAreas(meteringAreas);
    }

    try {
      camera.setParameters(parameters);
    } catch (Exception e) {
    }
    camera.autoFocus(this);
  }

  /**
   * 计算焦点及测光区域
   * @param focusWidth
   * @param focusHeight
   * @param areaMultiple
   * @param x
   * @param y
   * @param previewleft
   * @param previewRight
   * @param previewTop
   * @param previewBottom
   * @return Rect(left,top,right,bottom) :  left、top、right、bottom是以显示区域中心为原点的坐标
   */
  public Rect calculateTapArea(int focusWidth, int focusHeight, float areaMultiple,
      float x, float y, int previewleft, int previewRight, int previewTop, int previewBottom) {
    int areaWidth = (int) (focusWidth * areaMultiple);
    int areaHeight = (int) (focusHeight * areaMultiple);
    int centerX = (previewleft + previewRight) / 2;
    int centerY = (previewTop + previewBottom) / 2;
    double unitx = ((double) previewRight - (double) previewleft) / 2000;
    double unity = ((double) previewBottom - (double) previewTop) / 2000;
    int left = clamp((int) (((x - areaWidth / 2) - centerX) / unitx), -1000, 1000);
    int top = clamp((int) (((y - areaHeight / 2) - centerY) / unity), -1000, 1000);
    int right = clamp((int) (left + areaWidth / unitx), -1000, 1000);
    int bottom = clamp((int) (top + areaHeight / unity), -1000, 1000);

    return new Rect(left, top, right, bottom);
  }

  public int clamp(int x, int min, int max) {
    if (x > max)
      return max;
    if (x < min)
      return min;
    return x;
  }

  protected DisplayMetrics getScreenWH() {
    DisplayMetrics dMetrics = new DisplayMetrics();
    dMetrics = this.getResources().getDisplayMetrics();
    return dMetrics;
  }

  /**
   * 拍照
   */
  public void takePicture() {
    if (mode == MODE.FOCUSFAIL || mode == MODE.FOCUSING) {
      if (onTakePictureInfo != null) {
        onTakePictureInfo.onTakePictureInofo(false, null);
      }
      return;
    }

    if (camera != null) {
      camera.takePicture(null, null, new PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
          // System.out.println("CustomCameraView onPictureTaken " );

          File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + context.getPackageName() + CUSTOMECAMERA_PHOTO_PATH);
          if (folder.exists() == false) {
            folder.mkdirs();
          }
          File pictureFile = new File(folder.getAbsoluteFile(), "IMG_" + System.currentTimeMillis() + ".jpg");
          try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(data);
            fos.close();
            if (onTakePictureInfo != null) {
              onTakePictureInfo.onTakePictureInofo(true, pictureFile);
            }
          } catch (Exception e) {
            if (onTakePictureInfo != null) {
              mode = MODE.NONE;
              onTakePictureInfo.onTakePictureInofo(false, null);
            }
          }
          camera.startPreview();
        }
      });

      mode = MODE.NONE;
    }
  }

  public void setOnTakePictureInfo(OnTakePictureInfo _onTakePictureInfo) {
    this.onTakePictureInfo = _onTakePictureInfo;
  }

  public interface OnTakePictureInfo {

    /**
     * 拍照后返回拍照信息
     * 
     * @param _success
     *          拍照成功
     * @param _file
     *          照片文件
     */
    public void onTakePictureInofo(boolean _success, File _file);
  }

  @SuppressWarnings("deprecation")
  @Override
  public void onAutoFocus(boolean success, Camera _camera) {
    // System.out.println("CustomCameraView onAutoFocus success = " + success);
    if (success) {
      mode = MODE.FOCUSED;
      // Camera.Parameters parameters = _camera.getParameters();
      // float[] output = new float[3];
      // parameters.getFocusDistances(output);
      // List<Camera.Area> area = parameters.getFocusAreas();
      // Rect rect = area.get(0).rect;
      //
      // System.out.println("CustomCameraView onAutoFocus output1 = " +
      // output[1] + " ,output[2] = " + output[2] + " ,output[3]" + output[2]);
      // System.out.println("CustomCameraView onAutoFocus rect.top = " +
      // rect.top + " , rect.left = " + rect.left + " ,rect.right" + rect.right
      // +
      // " ,rect.bottom" + rect.bottom);

      view_focus.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_focus_focused));
    } else {
      mode = MODE.FOCUSFAIL;
      view_focus.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_focus_failed));
    }

    setFocusView();
  }

  private void setFocusView() {
    new Handler().postDelayed(new Runnable() {

      @SuppressWarnings("deprecation")
      @Override
      public void run() {
        view_focus.setBackgroundDrawable(null);

      }
    }, 1 * 1000);
  }
}
