package com.example.syz.demo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.syz.demo.R;
import com.example.syz.demo.util.MyAppcation;
import com.example.syz.demo.util.TextShow;

import java.util.List;

public class TextCommentAdapter extends RecyclerView.Adapter<TextCommentAdapter.ViewHolder> {
    private final static int HEAD = 1000;
    private List<TextShow> mTextList;
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameText;
        TextView contentText;
        ImageView headerImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = (TextView)itemView.findViewById(R.id.text_show_name);
            contentText = (TextView)itemView.findViewById(R.id.text_show_content);
            headerImage = (ImageView)itemView.findViewById(R.id.text_show_header);
        }
    }
    public TextCommentAdapter(List<TextShow> textShowList) {
        mTextList = textShowList;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        TextShow textShow = mTextList.get(i);
        Glide.with(MyAppcation.getContext())
                .load(textShow.getUrl())
                .into(viewHolder.headerImage);
        viewHolder.contentText.setText(textShow.getText());
        viewHolder.nameText.setText(textShow.getName());
    }

    @Override
    public int getItemCount() {
        return mTextList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.text_comment,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
}
