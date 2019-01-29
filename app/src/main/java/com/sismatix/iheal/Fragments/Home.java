package com.sismatix.iheal.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sismatix.iheal.R;

import static com.sismatix.iheal.Activity.Navigation_drawer_activity.bottom_navigation;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment implements View.OnClickListener {

    ImageView iv_iteamdeails,iv_hair_care;

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_home, container, false);
        bottom_navigation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        iv_iteamdeails=(ImageView)v.findViewById(R.id.iv_iteamdeails);
        iv_hair_care=(ImageView)v.findViewById(R.id.iv_hair_care);
        iv_iteamdeails.setOnClickListener(this);
        iv_hair_care.setOnClickListener(this);
        return v;
    }

    private void loadFragment(Fragment fragment) {
        Log.e("clickone", "");
        android.support.v4.app.FragmentManager manager = getFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.rootLayout, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_iteamdeails:
                loadFragment(new TransParant_Hair_care_freg());
                break;
            case R.id.iv_hair_care:
                loadFragment(new TransParant_Hair_care_freg());
                break;

            default:
                break;
        }

    }
}
