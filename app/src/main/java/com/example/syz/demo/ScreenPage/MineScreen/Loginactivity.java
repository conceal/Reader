package com.example.syz.demo.screenPage.mineScreen;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.syz.demo.MainActivity;
import com.example.syz.demo.R;
import com.example.syz.demo.screenPage.mineScreen.RegisterActivity;
import com.example.syz.demo.util.HttpUtil;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Loginactivity extends AppCompatActivity implements View.OnClickListener {


    private EditText loginphone;
    private EditText loginpassword;
    private Button loginButton;
    private TextView register;
    private ImageView loginBack;
    private ImageView loginPhoneLine;
    private ImageView loginPasswordLine;
    private View bindingView;
    private String loginUri = "https://www.apiopen.top/login?key=00d91e8e0cca2b76f515926a36db68f5";
    private String TAG = "LoginActivity";
    private String backMsg;
    private String nickname;
    private String motto;
    private String headPhoto;
    private String Sex;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        initView();
    }

    private void initView() {
        loginphone = (EditText) findViewById(R.id.phone_number);
        loginpassword = (EditText) findViewById(R.id.password_text);
        loginButton = (Button) findViewById(R.id.login_button);
        register = (TextView) findViewById(R.id.register);
        loginBack = (ImageView) findViewById(R.id.login_back);
        loginPhoneLine = (ImageView) findViewById(R.id.login_phone_line);
        loginPasswordLine = (ImageView) findViewById(R.id.login_password_line);
        bindingView = (View) findViewById(R.id.bindingView);

        loginButton.setOnClickListener(this);
        register.setOnClickListener(this);
        loginBack.setOnClickListener(this);
        loginphone.addTextChangedListener(new EditChangeListener());
        loginpassword.addTextChangedListener(new EditChangeListener());

        LightStatusbar();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                if (!TextUtils.isEmpty(loginphone.getText()) && !TextUtils.isEmpty(loginpassword.getText())) {
                    loginApp();
                } else {
                    Toast.makeText(this, "请填写账号密码", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.register:
                Intent registerIntent = new Intent(this, RegisterActivity.class);
                startActivity(registerIntent);
                break;
            case R.id.login_back:
                finish();
                break;
            case R.id.phone_number:
                if (!(TextUtils.isEmpty(loginphone.getText().toString()) && TextUtils.isEmpty(loginpassword.getText().toString()))) {
                    loginButton.setBackgroundResource(R.drawable.red_button);
                }
                break;
            case R.id.password_text:
                if (!(TextUtils.isEmpty(loginphone.getText().toString()) && TextUtils.isEmpty(loginpassword.getText().toString()))) {
                    loginButton.setBackgroundResource(R.drawable.red_button);
                    Toast.makeText(this, "nihao", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void loginApp() {
        RequestBody requestBody = new FormBody.Builder()
                .add("phone", loginphone.getText().toString())
                .add("passwd", loginpassword.getText().toString())
                .build();

        HttpUtil.postOkHttpRequest(loginUri, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,Log.getStackTraceString(e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                try{
                    JSONObject jsonObject = new JSONObject(responseData);
                    backMsg = jsonObject.getString("msg");
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    nickname = jsonObject1.getString("name");
                    motto = jsonObject1.getString("text");
                    Sex = jsonObject1.getString("other");
                    headPhoto = jsonObject1.getString("img").substring(24);
                } catch ( Exception e) {
                    Log.e(TAG,Log.getStackTraceString(e));
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (backMsg.equals("成功!")) {
//                            try {
//                                if (phone.equals(loginphone.getText().toString()) && password.equals(loginpassword.getText().toString())) {
//                                    Intent intent = new Intent(Loginactivity.this, MainActivity.class);
//                                    intent.putExtra("id", 3);
//                                    startActivity(intent);
//                                } else {
//                                    Toast.makeText(getApplicationContext(), "账号密码不正确", Toast.LENGTH_SHORT).show();
//                                }
//                            } catch (Exception e) {
//                                Log.e(TAG, Log.getStackTraceString(e));
//                            }
                            localBroadcastManager = LocalBroadcastManager.getInstance(getApplication());
                            Intent intent = new Intent("login");
                            intent.putExtra("islogin", true);
                            intent.putExtra("nickName", nickname);
                            intent.putExtra("motto", motto);
                            intent.putExtra("sex", Sex);
                            intent.putExtra("img", headPhoto);
                            localBroadcastManager.sendBroadcast(intent);
//                            setResult(RESULT_OK, intent);
                            Intent intent1 = new Intent(Loginactivity.this, MainActivity.class);
                            intent1.putExtra("id", 3);
                            startActivity(intent1);
                        } else if (backMsg.equals("用户名或者密码错误！")) {
                            Toast.makeText(getApplication(), backMsg, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), backMsg, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    class EditChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!(TextUtils.isEmpty(loginphone.getText().toString()) || TextUtils.isEmpty(loginpassword.getText().toString()))) {
                loginButton.setBackgroundResource(R.drawable.pink_button);
            } else {
                loginButton.setBackgroundResource(R.drawable.login_in);
            }

            if (!(TextUtils.isEmpty(loginphone.getText().toString()))) {
                loginPhoneLine.setBackgroundResource(R.drawable.pink_button);
            } else {
                loginPhoneLine.setBackgroundResource(R.drawable.line_color);
            }

            if (!(TextUtils.isEmpty(loginpassword.getText().toString()))) {
                loginPasswordLine.setBackgroundResource(R.drawable.pink_button);
            } else {
                loginPasswordLine.setBackgroundResource(R.drawable.line_color);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private void LightStatusbar() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option =  View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        int statusBarHeight = getStatusBarHeight(this);
        ViewGroup.LayoutParams params = bindingView.getLayoutParams();
        params.height = statusBarHeight;
        bindingView.setVisibility(View.VISIBLE);
        bindingView.setLayoutParams(params);
    }

    private int getStatusBarHeight(Activity activity) {
        int statusBarHeight = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        }

        return statusBarHeight;
    }

}
