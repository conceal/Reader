package com.example.syz.demo.screenPage.mineScreen;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.ActionMenuView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.syz.demo.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar setting_toolbar;
//    private ActionMenuView actionMenuView;
    private TextView quitAcount;
    private ViewStub viewStub;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_avtivity);
        setting_toolbar = (Toolbar) findViewById(R.id.setting_toolbar);
        setting_toolbar.setNavigationIcon(R.mipmap.back);
        setSupportActionBar(setting_toolbar);
        viewStub = (ViewStub) findViewById(R.id.quit_item);
        localBroadcastManager = LocalBroadcastManager.getInstance(getApplication());
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent.getBooleanExtra("login", false)) {
            try {
                View quitItem = viewStub.inflate();
                View quitView = (View) findViewById(R.id.quit);
                quitView.setOnClickListener(this);
            } catch (Exception e) {
                viewStub.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.quit:
                Intent intent = new Intent("login");
                intent.putExtra("islogin", false);
                localBroadcastManager.sendBroadcast(intent);
                Toast.makeText(this, "退出登录成功", Toast.LENGTH_SHORT).show();
                viewStub.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
