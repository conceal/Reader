package com.example.syz.demo.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.syz.demo.R;
import com.example.syz.demo.screenPage.communityScreen.community.Community;

import java.util.List;

public class SelectePlateAdapter extends RecyclerView.Adapter<SelectePlateAdapter.ViewHolder> {
    private List<Community> mCommunityList;
    private Context mContext;
    private byte[] b;
    private Handler handler;
    public static final int DELETE = 1;
    public static final String POSITION = "POSITION";

    static class ViewHolder extends RecyclerView.ViewHolder {
        View communityItem;
        ImageView community_item_img;
        TextView community_item_title;
        TextView community_item_text;

        public ViewHolder(View view) {
            super(view);
            communityItem = (View) view.findViewById(R.id.commity_item);
            community_item_img = (ImageView) view.findViewById(R.id.community_item_img);
            community_item_title = (TextView) view.findViewById(R.id.commity_item_title);
            community_item_text = (TextView) view.findViewById(R.id.commity_item_text);
        }
    }

    public SelectePlateAdapter(Context context, List<Community> communityList, Handler handler) {
        this.mCommunityList = communityList;
        this.mContext = context;
        this.handler = handler;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.communityItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = DELETE;
                msg.arg1 = holder.getAdapterPosition();
                handler.sendMessage(msg);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Community community = mCommunityList.get(position);
//        try {
//            byte[] bitmapArray = Base64.decode(community.getCommunityItemUrl(), Base64.DEFAULT);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
//            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(mContext.getResources(), bitmap);
//            roundedBitmapDrawable.setCornerRadius(200);
//            holder.community_item_img.setImageDrawable(roundedBitmapDrawable);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        Glide.with(mContext).load(community.getCommunityItemUrl()).into(holder.community_item_img);
        holder.community_item_title.setText(community.getCommunityItemTitle());
        holder.community_item_text.setText(community.getCommunityItemText());
    }

    @Override
    public int getItemCount() {
        return mCommunityList.size();
    }
}
