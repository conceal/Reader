package com.example.syz.demo.screenPage.publishScreen;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.syz.demo.R;

public class PublishActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView cancelButton;
    private TextView selectPlate;
    private TextView publishButton;
    private View bindingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_activity);

        initView();
    }

    private void initView() {

        cancelButton = (TextView) findViewById(R.id.cancel);
        selectPlate = (TextView) findViewById(R.id.select_plate);
        bindingView = (View) findViewById(R.id.bindingView);
        publishButton = (TextView) findViewById(R.id.publish_text);

        cancelButton.setOnClickListener(this);
        selectPlate.setOnClickListener(this);
        publishButton.setOnClickListener(this);

        LightStatusbar();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                finish();
                overridePendingTransition(R.anim.silde_stay,R.anim.silde_bottom_out);
                break;
            case R.id.select_plate:
                Intent intent = new Intent(this, SelectPlateActivity.class);
                startActivityForResult(intent, 1);
            case R.id.publish_text:
                Toast.makeText(this, "发表成功", Toast.LENGTH_SHORT).show();
                finish();
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            String platText = data.getStringExtra("title");
            selectPlate.setText(platText);

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
