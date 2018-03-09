package com.shark.app.business.singleactivity.module;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.shark.app.R;
import com.shark.app.business.singleactivity.home.smp.TabMainActivity;
import com.shark.app.business.statich.UrlHome;
import com.shark.app.business.urlentity.ELogin;
import com.shark.app.business.utils.SpHome;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpConfig;

/**
 * Created by King6rf on 2017/5/17.
 */

public class ActivityLogin extends FrameActivity implements View.OnClickListener{

    @Override
    public ORIENTATION getORIENTATION() {
        return ORIENTATION.PORTRAIT;
    }


    @Override
    public void handleMessage(Message msg) {

    }
    private ImageView logo;
    private ScrollView scrollView;
    private EditText et_username;
    private EditText et_password;
    private ImageView iv_clean_phone;
    private ImageView clean_password;
    private ImageView iv_show_pwd;
    private Button btn_login;
    private TextView forget_password;
    private int screenHeight = 0;//屏幕高度
    private int keyHeight = 0; //软件盘弹起后所占高度
    private float scale = 0.6f; //logo缩放比例
    private View service;
    private int height = 0 ;

    @Override
    public int getMenuid() {
        return -1;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置输入法不弹起
        setContentView(R.layout.app_activity_login);
        getSupportActionBar().hide();
//        AndroidBug5497Workaround.assistActivity(this);
        intiView();
    }

    private void intiView() {
        logo = (ImageView) findViewById(R.id.logo);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        et_username = (EditText) findViewById(R.id.et_mobile);
        et_password = (EditText) findViewById(R.id.et_password);
        iv_clean_phone = (ImageView) findViewById(R.id.iv_clean_phone);
        clean_password = (ImageView) findViewById(R.id.clean_password);
        iv_show_pwd = (ImageView) findViewById(R.id.iv_show_pwd);
        btn_login = (Button) findViewById(R.id.btn_login);
        forget_password = (TextView) findViewById(R.id.forget_password);
        service = findViewById(R.id.service);

        screenHeight = this.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度
        keyHeight = screenHeight / 3;//弹起高度为屏幕高度的1/3
        initListener();
    }
boolean needscale=true;
    private void initListener() {
        logo.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        iv_clean_phone.setOnClickListener(this);
        clean_password.setOnClickListener(this);
        iv_show_pwd.setOnClickListener(this);
        et_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && iv_clean_phone.getVisibility() == View.GONE) {
                    iv_clean_phone.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    iv_clean_phone.setVisibility(View.GONE);
                }
            }
        });
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && clean_password.getVisibility() == View.GONE) {
                    clean_password.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    clean_password.setVisibility(View.GONE);
                }
                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();

                    s.delete(temp.length() - 1, temp.length());
                    et_password.setSelection(s.length());
                }
            }
        });
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        findViewById(R.id.root).addOnLayoutChangeListener(new ViewGroup.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
              /* old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
              现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起*/
              if(needscale){
                  if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
                      Log.e("wenzhihao", "up------>"+(oldBottom - bottom));
                      new Handler().postDelayed(new Runnable() {
                          @Override
                          public void run() {
                              scrollView.smoothScrollTo(0, scrollView.getHeight());
                          }
                      }, 0);
                      zoomIn(logo, (oldBottom - bottom) - keyHeight);
                      service.setVisibility(View.INVISIBLE);
                  } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                      Log.e("wenzhihao", "down------>"+(bottom - oldBottom));
                      //键盘收回后，logo恢复原来大小，位置同样回到初始位置
                      new Handler().postDelayed(new Runnable() {
                          @Override
                          public void run() {
                              scrollView.smoothScrollTo(0, scrollView.getHeight());
                          }
                      }, 0);
                      zoomOut(logo, (bottom - oldBottom) - keyHeight);
                      service.setVisibility(View.VISIBLE);
                  }
              }

            }
        });
    }

    /**
     * 缩小
     * @param view
     */
    public void zoomIn(final View view, float dist) {
        view.setPivotY(view.getHeight());
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();
        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, scale);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, scale);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", 0.0f, -dist);

        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX);
        mAnimatorSet.play(mAnimatorScaleX).with(mAnimatorScaleY);
        mAnimatorSet.setDuration(200);
        mAnimatorSet.start();
    }
    Dialog menudialog;
    public void startMenuDialog() {
        StyledDialog.init(getContext());
        menudialog=
                StyledDialog.buildNormalInput("修改IP", "请输入IP", "", "确定", "取消",  new MyDialogListener() {
                    @Override
                    public void onFirst() {

                    }

                    @Override
                    public void onSecond() {

                    }

                    @Override
                    public void onGetInput(CharSequence input1, CharSequence input2) {
                        super.onGetInput(input1, input2);

                    }
                }).show();
        getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        menudialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                getWindow().clearFlags(
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                getWindow().clearFlags(
                        WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                menudialog=null;
            }
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_MENU){
            startMenuDialogImp();
        }
        return super.onKeyUp(keyCode, event);
    }

    private void startMenuDialogImp() {
        if(menudialog==null){

            startMenuDialog();
        }else {
            StyledDialog.dismiss(menudialog);
            menudialog = null;
        }
    }

    /**
     * f放大
     * @param view
     */
    public void zoomOut(final View view, float dist) {
        view.setPivotY(view.getHeight());
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();

        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", scale, 1.0f);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", scale, 1.0f);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", view.getTranslationY(), 0);

        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX);
        mAnimatorSet.play(mAnimatorScaleX).with(mAnimatorScaleY);
        mAnimatorSet.setDuration(200);
        mAnimatorSet.start();
    }
    public synchronized void toLogin(){
        if(!SpHome.needlogin){
            startActivity(new Intent(getContext(),TabMainActivity.class));
            finish();
        }else {
            HttpConfig.sCookie = "";
            Toast.makeText(getContext(), "正在登录", Toast.LENGTH_SHORT).show();
            ELogin eLogin = new ELogin(et_username.getText().toString(), et_password.getText().toString());
            SpHome.getSpHome().put("username", et_username.getText().toString());
            SpHome.getSpHome().put("password", et_password.getText().toString());
            httpGet(UrlHome.getUrl(this, UrlHome.login), UrlHome.entity2MapHashClassNoPrefix(eLogin), new HttpCallBack() {
                @Override
                public void onSuccess(String t) {
                    super.onSuccess(t);
                    try {
                        JSONObject jsonObject = new JSONObject(t);
                        if (jsonObject.getBoolean("status")) {
                            String sessionid = jsonObject.getString("sessionId");
                            HttpConfig.sCookie = sessionid;
                            startActivity(new Intent(getContext(), TabMainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int errorNo, String strMsg) {
                    super.onFailure(errorNo, strMsg);
                }

                @Override
                public void onCookieTimeOut() {

                }
            });
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_clean_phone:
                et_username.setText("");
                break;
            case R.id.clean_password:
                et_password.setText("");
                break;
            case R.id.iv_show_pwd:
                if (et_password.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iv_show_pwd.setImageResource(R.drawable.pass_visuable);
                } else {
                    et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iv_show_pwd.setImageResource(R.drawable.pass_gone);
                }
                String pwd = et_password.getText().toString();
                if (!TextUtils.isEmpty(pwd))
                    et_password.setSelection(pwd.length());
                break;
            case R.id.btn_login:
                toLogin();
                break;
            case R.id.logo:
                startMenuDialogImp();
                break;
        }
    }
}
