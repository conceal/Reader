package com.example.syz.demo.screenPage.communityScreen.search;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.syz.demo.R;
import com.example.syz.demo.adapter.SearchCardFragmentAdapter;
import com.example.syz.demo.util.SearchOrderData;

import java.util.ArrayList;
import java.util.List;

public class CardSelectFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<SearchOrderData> mList = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    CardInfoFragment cardInfoFragment = new CardInfoFragment();
                    Bundle bundle = msg.getData();
                    cardInfoFragment.setArguments(bundle);
                    showFragment(cardInfoFragment);
                    break;
            }
        }
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
//        if (!issearch) {
//            view = inflater.inflate(R.layout.card_fragment, container, false);
//            initData();
//            recyclerView = (RecyclerView) view.findViewById(R.id.search_recyclerview);
//            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//            recyclerView.setLayoutManager(layoutManager);
//            SearchCardFragmentAdapter adapter = new SearchCardFragmentAdapter(mList);
//            recyclerView.setAdapter(adapter);
//        } else {
//            view = inflater.inflate(R.layout.search_info_fragment, container, false);
//        }
//
//        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
//        intentFilter = new IntentFilter();
//        intentFilter.addAction("search");
//        localReceiver = new LocalReceiver();
//        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
        View view = inflater.inflate(R.layout.card_selected_fragment, container, false);
        initData();
        recyclerView = (RecyclerView) view.findViewById(R.id.search_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        SearchCardFragmentAdapter adapter = new SearchCardFragmentAdapter(mList, handler);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void initData() {
        SearchOrderData data1 = new SearchOrderData(1, "球");
        mList.add(data1);
        SearchOrderData data2 = new SearchOrderData(2, "音乐");
        mList.add(data2);
        SearchOrderData data3 = new SearchOrderData(3, "男人");
        mList.add(data3);
        SearchOrderData data4 = new SearchOrderData(4, "翻唱");
        mList.add(data4);
        SearchOrderData data5 = new SearchOrderData(5, "相声");
        mList.add(data5);
        SearchOrderData data6 = new SearchOrderData(6, "电影");
        mList.add(data6);
        SearchOrderData data7 = new SearchOrderData(7, "结婚");
        mList.add(data7);
        SearchOrderData data8 = new SearchOrderData(8, "吴亦凡");
        mList.add(data8);
        SearchOrderData data9 = new SearchOrderData(9, "李云龙");
        mList.add(data9);
        SearchOrderData data10 = new SearchOrderData(10, "动图");
        mList.add(data10);
        SearchOrderData data11 = new SearchOrderData(11, "BGM");
        mList.add(data11);
        SearchOrderData data12 = new SearchOrderData(12, "回家");
        mList.add(data12);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        localBroadcastManager.unregisterReceiver(localReceiver);
    }

//    class LocalReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            issearch = true;
//            Toast.makeText(getActivity(), "nihao", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void showFragment(Fragment fragment2) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.card_fragment, fragment2)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }




}
