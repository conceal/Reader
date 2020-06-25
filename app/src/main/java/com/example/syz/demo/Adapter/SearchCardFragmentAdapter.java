package com.example.syz.demo.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.syz.demo.R;
import com.example.syz.demo.util.SearchOrderData;

import java.util.List;

public class SearchCardFragmentAdapter extends RecyclerView.Adapter<SearchCardFragmentAdapter.ViewHolder> {

    private List<SearchOrderData> mList;
    private Handler handler;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView order_number;
        TextView order_text;
        View searchOrderItem;

        public ViewHolder(View view) {
            super(view);
            order_number = (TextView) view.findViewById(R.id.order_number);
            order_text = (TextView) view.findViewById(R.id.order_text);
            searchOrderItem = (View) view.findViewById(R.id.search_order_item);
        }
    }

    public SearchCardFragmentAdapter(List<SearchOrderData> list, Handler handler) {
        this.mList = list;
        this.handler = handler;
    }

    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_recycleview, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.searchOrderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Message message = new Message();
                Bundle bundle = new Bundle();
                SearchOrderData selectData = mList.get(position);
                bundle.putString("searchData", selectData.getOrderText());
                message.setData(bundle);
                message.what = 1;
                handler.sendMessage(message);
            }
        });
        return holder;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        SearchOrderData data = mList.get(position);
        holder.order_number.setText(data.getOrderNumber()+"");
        holder.order_text.setText(data.getOrderText());
    }

    public int getItemCount() {
        return mList.size();
    }

}
