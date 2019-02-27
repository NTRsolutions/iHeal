package com.sismatix.iheal.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sismatix.iheal.Activity.Navigation_drawer_activity;
import com.sismatix.iheal.Preference.Login_preference;
import com.sismatix.iheal.R;

import static com.sismatix.iheal.Activity.Navigation_drawer_activity.bottom_navigation;


/**
 * A simple {@link Fragment} subclass.
 */

public class Home extends Fragment implements View.OnClickListener {

    ImageView iv_lung, iv_hair_care, iv_brain, iv_eye, iv_nose, iv_mouth, iv_teeth, iv_bones, iv_stomach, iv_liver, iv_kidney, iv_UT;
    LinearLayout lv_withoutlogin, lv_withlogin, lv_creatnewaccount, lv_loginaccount;
    Button btn_contact;
    String loginflag;
    TextView tv_hometitlename;

    String value;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        bottom_navigation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        loginflag = Login_preference.getLogin_flag(getActivity());
        iv_lung = (ImageView) v.findViewById(R.id.iv_lung);
        iv_hair_care = (ImageView) v.findViewById(R.id.iv_hair_care);
        lv_withoutlogin = (LinearLayout) v.findViewById(R.id.lv_withoutlogin);
        lv_withlogin = (LinearLayout) v.findViewById(R.id.lv_withlogin);
        lv_creatnewaccount = (LinearLayout) v.findViewById(R.id.lv_creatnewaccount);
        lv_loginaccount = (LinearLayout) v.findViewById(R.id.lv_loginaccount);
        btn_contact = (Button) v.findViewById(R.id.btn_contact);
        tv_hometitlename = (TextView) v.findViewById(R.id.tv_hometitlename);

        iv_brain = (ImageView) v.findViewById(R.id.iv_brain);
        iv_eye = (ImageView) v.findViewById(R.id.iv_eye);
        iv_nose = (ImageView) v.findViewById(R.id.iv_nose);
        iv_mouth = (ImageView) v.findViewById(R.id.iv_mouth);
        iv_teeth = (ImageView) v.findViewById(R.id.iv_teeth);
        iv_bones = (ImageView) v.findViewById(R.id.iv_bones);
        iv_stomach = (ImageView) v.findViewById(R.id.iv_stomach);
        iv_liver = (ImageView) v.findViewById(R.id.iv_liver);
        iv_kidney = (ImageView) v.findViewById(R.id.iv_kidney);
        iv_UT = (ImageView) v.findViewById(R.id.iv_UT);

        iv_lung.setOnClickListener(this);
        iv_hair_care.setOnClickListener(this);
        lv_creatnewaccount.setOnClickListener(this);
        lv_loginaccount.setOnClickListener(this);

        iv_brain.setOnClickListener(this);
        iv_eye.setOnClickListener(this);
        iv_nose.setOnClickListener(this);
        iv_mouth.setOnClickListener(this);
        iv_teeth.setOnClickListener(this);
        iv_bones.setOnClickListener(this);
        iv_stomach.setOnClickListener(this);
        iv_liver.setOnClickListener(this);
        iv_kidney.setOnClickListener(this);
        iv_UT.setOnClickListener(this);

        Navigation_drawer_activity.item_count.setText(Login_preference.getCart_item_count(getActivity()));

        if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {
            String fullname = Login_preference.getfullname(getActivity());
            tv_hometitlename.setText(getString(R.string.welcomeback) + fullname);
            lv_withlogin.setVisibility(View.VISIBLE);
            lv_withoutlogin.setVisibility(View.GONE);
            btn_contact.setVisibility(View.VISIBLE);
        } else {
            lv_withlogin.setVisibility(View.GONE);
            lv_withoutlogin.setVisibility(View.VISIBLE);
            btn_contact.setVisibility(View.GONE);
        }


        return v;
    }

    private void loadFragment(Fragment fragment, String value) {
        Log.e("clickone", "");
       /* android.support.v4.app.FragmentManager manager = getFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.rootLayout, fragment);
        transaction.addToBackStack(null);

        transaction.commit();*/

        Bundle b = new Bundle();
        b.putString("cat_id", value);
        b.putString("name", value);
        //Fragment myFragment = new TransParant_Hair_care_freg();
        fragment.setArguments(b);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment).addToBackStack(null).commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_hair_care:
                if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {
                    Toast.makeText(getActivity(), "product is not ", Toast.LENGTH_SHORT).show();
                } else {

                }
                /*if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {
                    loadFragment(new Account(), value);
                } else {
                    loadFragment(new TransParant_Hair_care_freg(), value);
                }*/
                break;

            case R.id.lv_creatnewaccount:
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                        loadFragment(new Account(), value);
                    }
                }, 1000);


                break;

            case R.id.lv_loginaccount:

                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    public void run() {
                        loadFragment(new EmailLogin(), value);
                    }
                }, 1000);


                break;

            case R.id.iv_brain:
                if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {
                    value = "20";
                    loadFragment(new Hair_Cair_fregment(), value);
                } else {
                    loadFragment(new Account(), value);
                }

                break;

            case R.id.iv_lung:
                if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {
                    value = "26";
                    loadFragment(new Hair_Cair_fregment(), value);
                } else {
                    loadFragment(new Account(), value);
                }
                break;

            case R.id.iv_eye:
                if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {
                    value = "21";
                    loadFragment(new Hair_Cair_fregment(), value);
                } else {
                    loadFragment(new Account(), value);
                }

                break;

            case R.id.iv_nose:
                if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {
                    value = "22";
                    loadFragment(new Hair_Cair_fregment(), value);
                } else {
                    loadFragment(new Account(), value);
                }
                break;

            case R.id.iv_mouth:
                if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {
                    value = "23";
                    loadFragment(new Hair_Cair_fregment(), value);
                } else {
                    loadFragment(new Account(), value);
                }
                break;

            case R.id.iv_teeth:
                if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {
                    value = "24";
                    loadFragment(new Hair_Cair_fregment(), value);
                } else {
                    loadFragment(new Account(), value);
                }
                break;

            case R.id.iv_bones:
                if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {
                    value = "25";
                    loadFragment(new Hair_Cair_fregment(), value);
                } else {
                    loadFragment(new Account(), value);
                }
                break;

            case R.id.iv_stomach:
                if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {
                    value = "27";
                    loadFragment(new Hair_Cair_fregment(), value);
                } else {
                    loadFragment(new Account(), value);
                }
                break;

            case R.id.iv_liver:
                if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {
                    value = "28";
                    loadFragment(new Hair_Cair_fregment(), value);
                } else {
                    loadFragment(new Account(), value);
                }
                break;

            case R.id.iv_kidney:
                if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {
                    value = "29";
                    loadFragment(new Hair_Cair_fregment(), value);
                } else {
                    loadFragment(new Account(), value);
                }
                break;

            case R.id.iv_UT:
                if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {
                    value = "30";
                    loadFragment(new Hair_Cair_fregment(), value);
                } else {
                    loadFragment(new Account(), value);
                }
                break;

            default:
                break;

        }

    }
    /*public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_lung:
                if(loginflag.equalsIgnoreCase("1") || loginflag == "1"){

                    loadFragment(new TransParant_Hair_care_freg());
                }else{
                    loadFragment(new Account());
                }
                break;
            case R.id.iv_hair_care:
                if(loginflag.equalsIgnoreCase("1") || loginflag == "1"){
                    loadFragment(new TransParant_Hair_care_freg());
                }else{
                    loadFragment(new Account());
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

    }*/
}