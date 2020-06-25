package com.example.syz.demo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.syz.demo.R;
import com.example.syz.demo.util.Page1Data;
import com.example.syz.demo.util.Text;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment1Adapter extends RecyclerView.Adapter<Fragment1Adapter.ViewHolder> {

    private List<Page1Data> mPage1List;
    private Context mContext;
    private String TAG = "Page1Adapter";

    static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView headerImg;
        TextView userName;
        TextView contentText;
        ImageView contentImage;
        TextView good_count;
        TextView commity_count;
        TextView share_count;

        public ViewHolder(View view) {
            super(view);
            headerImg = (CircleImageView) view.findViewById(R.id.header_img);
            userName = (TextView) view.findViewById(R.id.uesrname);
            contentText = (TextView) view.findViewById(R.id.content_text);
            contentImage = (ImageView) view.findViewById(R.id.content_image);
            good_count = (TextView) view.findViewById(R.id.good_count);
            commity_count = (TextView) view.findViewById(R.id.commity_count);
            share_count = (TextView) view.findViewById(R.id.share_count);
        }
    }


    public Fragment1Adapter(Context context, List<Page1Data> list) {
        this.mPage1List = list;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.page1_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Page1Data data = mPage1List.get(position);
        Glide.with(mContext.getApplicationContext()).load(data.getHeaderId()).into(holder.headerImg);
        holder.userName.setText(data.getUsername());
        holder.contentText.setText(data.getText());
        holder.good_count.setText(data.getUp()+ "");
        holder.commity_count.setText(data.getComment()+"");
        holder.share_count.setText(data.getForward()+"");
        if (data.getType().equals("image")) {
            Glide.with(mContext.getApplicationContext()).load(data.getImage()).asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            setBitmapToImage(resource, holder);
                        }
                    });
        } else if (data.getType().equals("gif")) {
            Glide.with(mContext.getApplicationContext()).load(data.getGif()).asGif()
                    .into(holder.contentImage);
        } else {
            holder.contentImage.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mPage1List.size();
    }

    private void setBitmapToImage(Bitmap resource, ViewHolder holder) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            resource.compress(Bitmap.CompressFormat.PNG, 100, baos);

            InputStream isBm = new ByteArrayInputStream(baos.toByteArray());

            //BitmapRegionDecoder newInstance(InputStream is, boolean isShareable)
            //用于创建BitmapRegionDecoder，isBm表示输入流，只有jpeg和png图片才支持这种方式，
            // isShareable如果为true，那BitmapRegionDecoder会对输入流保持一个表面的引用，
            // 如果为false，那么它将会创建一个输入流的复制，并且一直使用它。即使为true，程序也有可能会创建一个输入流的深度复制。
            // 如果图片是逐步解码的，那么为true会降低图片的解码速度。如果路径下的图片不是支持的格式，那就会抛出异常

            BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(isBm, true);

            final int imgWidth = decoder.getWidth();
            final int imgHeight = decoder.getHeight();

            BitmapFactory.Options options = new BitmapFactory.Options();

            //计算图片要被切分成几个整块，
            // 如果sum=0 说明图片的长度不足3000px，不进行切分 直接添加
            // 如果sum>0 先添加整图，再添加多余的部分，否则多余的部分不足3000时底部会有空白
            int sum = imgHeight/3000;
            int redundant = imgHeight%3000;
            List<Bitmap> bitmapList = new ArrayList<>();

            if (sum == 0) {
                bitmapList.add(resource);
            } else {
                for (int i = 0; i < sum; i++) {
                    //需要注意：mRect.set(left, top, right, bottom)的第四个参数，
                    //也就是图片的高不能大于这里的4096
                    Rect mRect = new Rect();
                    mRect.set(0, i*3000, imgWidth, (i+1)*3000);
                    Bitmap bitmap = decoder.decodeRegion(mRect, options);
                    bitmapList.add(bitmap);
                }

                if (redundant > 0) {
                    Rect mRect = new Rect();
                    mRect.set(0, sum*3000, imgWidth, imgHeight);
                    Bitmap bitmap = decoder.decodeRegion(mRect, options);
                    bitmapList.add(bitmap);
                }
            }

            Bitmap bigBitmap = Bitmap.createBitmap(imgWidth, imgHeight, Bitmap.Config.ARGB_4444);
            Canvas bigCanvas = new Canvas(bigBitmap);

            Paint paint = new Paint();
            int iHeight = 0;

            for (int i =0; i < bitmapList.size(); i++) {
                Bitmap bm = bitmapList.get(i);
                bigCanvas.drawBitmap(bm, 0, iHeight, paint);
                iHeight += bm.getHeight();

                bm.recycle();
                bm = null;
            }
            holder.contentImage.setImageBitmap(bigBitmap);
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }
}
