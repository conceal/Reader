package com.example.syz.demo.screenPage.communityScreen.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.syz.demo.R;

public class CardFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.card_fragment, container, false);
        showFragment(new CardSelectFragment());
        return view;
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.card_fragment, fragment, "fragment").commit();
        Log.d("fragment","fragment:"+getChildFragmentManager().findFragmentByTag("fragment"));
    }
}
