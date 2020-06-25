package com.example.syz.demo.screenPage.communityScreen.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.syz.demo.R;
import com.example.syz.demo.util.SearchOrderData;

public class CardInfoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_info_item, container, false);
        String data = getArguments().getString("searchData");
        Toast.makeText(getActivity(), "你搜索了"+data, Toast.LENGTH_SHORT).show();
        return view;
    }
}
