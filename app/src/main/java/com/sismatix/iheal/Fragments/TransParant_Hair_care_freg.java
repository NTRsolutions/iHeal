package com.sismatix.iheal.Fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toolbar;

import com.sismatix.iheal.Activity.Navigation_drawer_activity;
import com.sismatix.iheal.R;

import static com.sismatix.iheal.Activity.Navigation_drawer_activity.bottom_navigation;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransParant_Hair_care_freg extends Fragment {
    View v;

    android.support.v7.widget.Toolbar toolbar_hair_care;
    AppBarLayout appbar;
    Button btn_haircare_ex_prod;
    public TransParant_Hair_care_freg() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_trans_parant__hair_care_freg, container, false);
        toolbar_hair_care = (android.support.v7.widget.Toolbar) v.findViewById(R.id.toolbar_hair_care);
        btn_haircare_ex_prod=(Button)v.findViewById(R.id.btn_haircare_ex_prod);
        appbar = (AppBarLayout) v.findViewById(R.id.appbar_transparent);
        setHasOptionsMenu(true);

        btn_haircare_ex_prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new Hair_Cair_fregment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, myFragment).addToBackStack(null).commit();

            }
        });

        if (toolbar_hair_care != null) {
            ((Navigation_drawer_activity) getActivity()).setSupportActionBar(toolbar_hair_care);
        }

        if (toolbar_hair_care != null) {
            ((Navigation_drawer_activity) getActivity()).getSupportActionBar()
                    .setDisplayHomeAsUpEnabled(true);

            toolbar_hair_care.setNavigationIcon(R.drawable.ic_drawer);

        }

        toolbar_hair_care.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Navigation_drawer_activity) getActivity()).getmDrawerLayout()
                        .openDrawer(GravityCompat.START);
            }
        });

        bottom_navigation.setBackgroundColor(getResources().getColor(R.color.trans));
        appbar.setBackgroundColor(getResources().getColor(R.color.trans));
        toolbar_hair_care.setBackgroundColor(getResources().getColor(R.color.trans));







        return v;
    }


}
