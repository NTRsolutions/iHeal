package com.sismatix.iheal.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sismatix.iheal.Preference.Login_preference;
import com.sismatix.iheal.R;

import static com.sismatix.iheal.Activity.Navigation_drawer_activity.bottom_navigation;


/**
 * A simple {@link Fragment} subclass.
 */

public class Home extends Fragment implements View.OnClickListener {

    ImageView iv_iteamdeails,iv_hair_care,iv_nine;
    LinearLayout lv_withoutlogin,lv_withlogin,lv_creatnewaccount,lv_loginaccount;
    Button btn_contact;
    String loginflag;
    TextView tv_hometitlename;

    public Home() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_home, container, false);
        bottom_navigation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        loginflag=Login_preference.getLogin_flag(getActivity());
        iv_iteamdeails=(ImageView)v.findViewById(R.id.iv_iteamdeails);
        iv_hair_care=(ImageView)v.findViewById(R.id.iv_hair_care);
        lv_withoutlogin=(LinearLayout) v.findViewById(R.id.lv_withoutlogin);
        lv_withlogin=(LinearLayout) v.findViewById(R.id.lv_withlogin);
        lv_creatnewaccount=(LinearLayout) v.findViewById(R.id.lv_creatnewaccount);
        lv_loginaccount=(LinearLayout) v.findViewById(R.id.lv_loginaccount);
        btn_contact=(Button) v.findViewById(R.id.btn_contact);
        tv_hometitlename=(TextView) v.findViewById(R.id.tv_hometitlename);

        iv_iteamdeails.setOnClickListener(this);
        iv_hair_care.setOnClickListener(this);
        lv_creatnewaccount.setOnClickListener(this);
        lv_loginaccount.setOnClickListener(this);

        if(loginflag.equalsIgnoreCase("1") || loginflag == "1"){
            lv_withlogin.setVisibility(View.GONE);
            lv_withoutlogin.setVisibility(View.VISIBLE);
            btn_contact.setVisibility(View.GONE);
        }else{
            String fullname=Login_preference.getfullname(getActivity());
            tv_hometitlename.setText(getString(R.string.welcomeback)+fullname);
            lv_withlogin.setVisibility(View.VISIBLE);
            lv_withoutlogin.setVisibility(View.GONE);
            btn_contact.setVisibility(View.VISIBLE);
        }
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
                if(loginflag.equalsIgnoreCase("1") || loginflag == "1"){
                    loadFragment(new Account());
                }else{
                    loadFragment(new TransParant_Hair_care_freg());
                }
                break;
            case R.id.iv_hair_care:
                if(loginflag.equalsIgnoreCase("1") || loginflag == "1"){
                    loadFragment(new Account());
                }else{
                    loadFragment(new TransParant_Hair_care_freg());
                }
                break;
            case R.id.lv_creatnewaccount:
                loadFragment(new Account());
                break;
            case R.id.lv_loginaccount:
                loadFragment(new EmailLogin());
                break;
            default:
                break;
        }

    }
}