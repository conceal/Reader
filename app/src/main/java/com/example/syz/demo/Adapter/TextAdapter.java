package com.example.syz.demo.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.syz.demo.R;
import com.example.syz.demo.homeFragment.gif.GifShowActivity;
import com.example.syz.demo.screenPage.TextComment;
import com.example.syz.demo.util.MyAppcation;
import com.example.syz.demo.util.Text;
import com.example.syz.demo.util.TextSome;


import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;
import de.hdodenhof.circleimageview.CircleImageView;

public class TextAdapter extends RecyclerView.Adapter<TextAdapter.ViewHolder> {
    private List<Text> mTextList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView headerImage;
        CircleImageView commentUserImage;
        ImageView commentGood;
        ImageView goodImage;
        ImageView commentImage;
        ImageView shareImage;
        TextView username;
        TextView textSmile;
        TextView commentUsername;
        TextView commentGoodNumber;
        TextView commentText;
        TextView goodNumber;
        TextView commentNumber;
        TextView shareNumber;
        LinearLayout commentView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            headerImage = (CircleImageView) itemView.findViewById(R.id.text_header);
            commentUserImage = (CircleImageView) itemView.findViewById(R.id.text_comment_header);
            commentGood = (ImageView)itemView.findViewById(R.id.text_comment_good);
            goodImage = (ImageView)itemView.findViewById(R.id.text_good_image);
            username = (TextView)itemView.findViewById(R.id.text_username);
            textSmile = (TextView)itemView.findViewById(R.id.text_smile);
            commentUsername = (TextView)itemView.findViewById(R.id.text_comment_username);
            commentGoodNumber = (TextView)itemView.findViewById(R.id.text_comment_number_good);
            commentText = (TextView)itemView.findViewById(R.id.text_comment_text);
            goodNumber = (TextView)itemView.findViewById(R.id.text_good_number);
            commentNumber = (TextView)itemView.findViewById(R.id.text_comment_number);
            shareNumber = (TextView)itemView.findViewById(R.id.text_share_number);
            commentImage = (ImageView)itemView.findViewById(R.id.text_comment_image);
            shareImage = (ImageView)itemView.findViewById(R.id.text_share_image);
            commentView = (LinearLayout)itemView.findViewById(R.id.text_comment_view);
        }
    }

    public TextAdapter(List<Text> textList) {
        mTextList = textList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_text,viewGroup,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.goodImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Text mText = mTextList.get(position);
                int mUp = mText.getUp() + 1;
                holder.goodImage.setImageResource(R.drawable.text_good_fill);
                holder.goodImage.setColorFilter(Color.RED);
                holder.goodNumber.setText(mUp+" ");
            }
        });
        holder.commentGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Text mText = mTextList.get(position);
                int mDown = mText.getDown() + 1;
                holder.commentGood.setImageResource(R.drawable.text_good_fill);
                holder.commentGood.setColorFilter(Color.RED);
                holder.commentGoodNumber.setText(mDown+" ");
            }
        });
        holder.textSmile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Text mText = mTextList.get(position);
                TextSome textSome = new TextSome();
                textSome.setTextSmile(mText.getText());
                textSome.setHeaderImage(mText.getHeader());
                textSome.setGoodNumber(mText.getUp());
                textSome.setShareNumber(mText.getForward());
                textSome.setBadNumber(mText.getDown());
                Intent intent = new Intent(view.getContext(),TextComment.class);
                intent.putExtra("textsome",textSome);
                view.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Text text = mTextList.get(position);
        Glide.with(MyAppcation.getContext())
              .load(text.getHeader())
              .into(holder.headerImage);
        Glide.with(MyAppcation.getContext())
                .load(text.getTop_commentsHeader())
                .into(holder.commentUserImage);
        holder.username.setText(text.getUsername());
        holder.textSmile.setText(text.getText());
        holder.shareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare(text.getText());
            }
        });
        if(text.getTop_commentsContent().equals("null") ){
            holder.commentView.setLayoutParams(new LinearLayout.LayoutParams(0,0));
            Log.d("haha",text.getTop_commentsContent());
        }else{
            holder.commentUsername.setText(text.getTop_commentsName());
            holder.commentGoodNumber.setText(text.getDown()+" ");
            holder.commentText.setText(text.getTop_commentsContent());
            holder.commentGood.setImageResource(R.drawable.text_good);
        }
        holder.goodNumber.setText(text.getUp()+" ");
        holder.commentNumber.setText(text.getComment()+" ");
        holder.shareNumber.setText(text.getForward()+" ");
    }

    @Override
    public int getItemCount() {
        return mTextList.size();
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

}
