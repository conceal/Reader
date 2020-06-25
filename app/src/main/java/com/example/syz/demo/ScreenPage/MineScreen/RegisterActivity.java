
package com.example.syz.demo.screenPage.mineScreen;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.syz.demo.R;
import com.example.syz.demo.util.Text;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText register_phone;
    private EditText check_number;
    private EditText register_password;
    private Button register_button;
    private TextView check_text;
    private TextView secret_text;
    private ImageView register_back;
    private ImageView registerPhoneLine;
    private ImageView checkLine;
    private ImageView registerPasswordLine;
    private View bindingView;
    private String register_uri = "https://www.apiopen.top/createUser?key=00d91e8e0cca2b76f515926a36db68f5";
    private String backMsg = "nihao";
    public static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        initView();
    }

    public void initView() {
        register_phone = (EditText) findViewById(R.id.register_phone);
        check_number = (EditText) findViewById(R.id.check_number);
        register_password = (EditText) findViewById(R.id.register_password);
        register_button = (Button) findViewById(R.id.register_button);
        register_back = (ImageView) findViewById(R.id.register_back);
        check_text = (TextView) findViewById(R.id.check_text);
        secret_text = (TextView) findViewById(R.id.secret_text);
        registerPhoneLine = (ImageView) findViewById(R.id.register_phone_line);
        checkLine = (ImageView) findViewById(R.id.check_line);
        registerPasswordLine = (ImageView) findViewById(R.id.register_password_line);
        bindingView = (View) findViewById(R.id.bindingView);

        check_text.setOnClickListener(this);
        register_button.setOnClickListener(this);
        secret_text.setOnClickListener(this);
        register_back.setOnClickListener(this);
        register_phone.addTextChangedListener(new EditChangeListener());
        register_password.addTextChangedListener(new EditChangeListener());
        check_number.addTextChangedListener(new EditChangeListener());

        LightStatusbar();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_button:
                if (register_phone.getText().length() > 0 && register_password.getText().length() > 0 && check_number.getText().length() > 0) {
                    actionStart(this, register_phone.getText().toString(), register_password.getText().toString());
                } else {
                    if (TextUtils.isEmpty(register_phone.getText().toString())) {
                        Toast.makeText(this, "请填写账号", Toast.LENGTH_SHORT).show();
                    } else {
                        if (TextUtils.isEmpty(check_number.getText().toString())) {
                            Toast.makeText(this, "请填写验证码", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "请填写密码", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case R.id.check_text:
                Toast.makeText(this, "验证码功能暂未实现可以随便输入6为数字", Toast.LENGTH_SHORT).show();
                break;
            case R.id.secret_text:
                Toast.makeText(this, "暂未实现其功能", Toast.LENGTH_SHORT).show();
                break;
            case R.id.register_back:
                finish();
                break;
            default:
                break;
        }
    }

    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, PersonInfoActivity.class);
        intent.putExtra("phone", data1);
        intent.putExtra("password", data2);
        context.startActivity(intent);
    }

    class EditChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!(TextUtils.isEmpty(register_phone.getText().toString()) ||
                    TextUtils.isEmpty(check_number.getText().toString()) ||
                    TextUtils.isEmpty(register_password.getText().toString()))) {
                register_button.setBackgroundResource(R.drawable.pink_button);
            } else {
                register_button.setBackgroundResource(R.drawable.login_in);
            }

            if (!(TextUtils.isEmpty(register_phone.getText().toString()))) {
                registerPhoneLine.setBackgroundResource(R.drawable.pink_button);
            } else {
                registerPhoneLine.setBackgroundResource(R.drawable.line_color);
            }

            if (!(TextUtils.isEmpty(check_number.getText().toString()))) {
                checkLine.setBackgroundResource(R.drawable.pink_button);
            } else {
                checkLine.setBackgroundResource(R.drawable.line_color);
            }

            if (!(TextUtils.isEmpty(register_password.getText().toString()))) {
                registerPasswordLine.setBackgroundResource(R.drawable.pink_button);
            } else {
                registerPasswordLine.setBackgroundResource(R.drawable.line_color);
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
