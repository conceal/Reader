package com.example.syz.demo.screenPage;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.syz.demo.R;
import com.example.syz.demo.adapter.TextCommentAdapter;
import com.example.syz.demo.util.MyAppcation;
import com.example.syz.demo.util.TextShow;
import com.example.syz.demo.util.TextSome;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;

public class TextComment extends AppCompatActivity{
    private List<TextShow> textShowList = new ArrayList<>();
    ImageView back;
    ImageView star;
    Button redButton;
    ImageView header;
    TextView textSmile;
    TextView goodNumber;
    TextView badNumber;
    TextView shareNumber;
    ImageView goodImage;
    ImageView badImage;
    ImageView shareImage;
    ImageView commentPhoto;
    Button comment;
    RecyclerView textRecyclerView;
    public TextSome textSome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_comment);
        Intent intent = getIntent();
        textSome = intent.getParcelableExtra("textsome");
        addData();
        init();
    }

    public void init(){
        textRecyclerView = (RecyclerView)findViewById(R.id.textcomment_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        textRecyclerView.setLayoutManager(linearLayoutManager);
        TextCommentAdapter adapter = new TextCommentAdapter(textShowList);
        textRecyclerView.setAdapter(adapter);
         back = (ImageView)findViewById(R.id.textcomment_back);
         star = (ImageView)findViewById(R.id.textcomment_star);
         header = (ImageView)findViewById(R.id.textcomment_header);
         Glide.with(this)
                 .load(textSome.getHeaderImage())
                 .into(header);
         goodImage = (ImageView)findViewById(R.id.text_good_image_comment);
         badImage = (ImageView)findViewById(R.id.text_bad_image_comment);
         shareImage = (ImageView)findViewById(R.id.text_share_image_comment);
         redButton = (Button)findViewById(R.id.textcommet_redbutton);
         textSmile = (TextView)findViewById(R.id.text_smile_comment);
         textSmile.setText(textSome.getTextSmile());
         goodNumber = (TextView)findViewById(R.id.text_good_number_comment);
         goodNumber.setText(textSome.getGoodNumber()+"");
         badNumber = (TextView)findViewById(R.id.text_bad_number_comment);
         badNumber.setText(textSome.getBadNumber()+"");
         shareNumber = (TextView)findViewById(R.id.text_share_number_comment);
         shareNumber.setText(textSome.getShareNumber()+"");
         comment = (Button)findViewById(R.id.textcomment_comment);
         commentPhoto = (ImageView)findViewById(R.id.textcomment_photo);
    }
    private void addData(){
        for(int i = 0 ; i < 10; i++){
            TextShow textShow = new TextShow("hahahahahaghahaha","bluesky","http://ww1.sinaimg.cn/large/005T39qaly1g0nhyhgpaoj31hc0u0grg.jpg");
            textShowList.add(textShow);
        }
    }
    private void showShare(String text) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("请食用：");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl("https://github.com/XueTianGit/Git");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(text);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("http://ww1.sinaimg.cn/large/005T39qaly1g0ml0t8kkej30hs0hst92.jpg");//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl("https://github.com/XueTianGit/Git");
        // 启动分享GUI
        oks.show(MyAppcation.getContext());
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.textcomment_back:
                finish();
                break;
            case R.id.textcomment_star:
                star.setImageResource(R.drawable.star_full);
                star.setColorFilter(Color.RED);
                break;
            case R.id.textcommet_redbutton:
                Resources res = getResources();
                Drawable drawable1 = res.getDrawable(R.drawable.edit_shape);
                redButton.setBackground(drawable1);
                redButton.setText("关注");
                break;
            case R.id.text_good_image_comment:
                goodImage.setColorFilter(Color.RED);
                goodNumber.setText((textSome.getGoodNumber()+1)+"");
                break;
            case R.id.text_bad_image_comment:
                badImage.setColorFilter(Color.RED);
                badNumber.setText((textSome.getBadNumber()+1)+"");
                break;
            case R.id.textcomment_photo:
            case R.id.textcomment_comment:
                Intent intent = new Intent(this,com.example.syz.demo.screenPage.publishScreen.PublishActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_bottom_in, R.anim.silde_stay);
                break;
            case R.id.text_share_image_comment:
                showShare(textSome.getTextSmile());
                break;
            default:
                break;

        }
    }
}
