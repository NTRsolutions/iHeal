package com.sismatix.iheal.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sismatix.iheal.R;

import static com.sismatix.iheal.Activity.Navigation_drawer_activity.bottom_navigation;


/**
 * A simple {@link Fragment} subclass.
 */
public class Account extends Fragment {

    LinearLayout lv_email_singup;
    TextView tv_login;
    View v;
    public Account() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       v =  inflater.inflate(R.layout.fragment_account, container, false);
        bottom_navigation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        lv_email_singup = (LinearLayout)v.findViewById(R.id.lv_email_singup);
        tv_login = (TextView)v.findViewById(R.id.tv_login);

        lv_email_singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Signup nextFrag= new Signup();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.rootLayout, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();

            }
        });
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EmailLogin nextFrag= new EmailLogin();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.rootLayout, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

      ///  BACK_EVENT();
        return v;

    }

    private void BACK_EVENT() {
       v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP
                        && keyCode == KeyEvent.KEYCODE_BACK) {

                  /*  Utils.switchContent(R.id.frag_container,
                            Utils.HOME_FRAGMENT,
                            ((ECartHomeActivity) (getContext())),
                            AnimationType.SLIDE_DOWN);*/
                  loadFragment(new Home(),"Home_fragment");
                }
                return true;
            }
        });

    }
    public void loadFragment(Fragment fragment,String s) {
        Log.e("clickone", "");
        android.support.v4.app.FragmentManager manager = getFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.rootLayout, fragment);
        transaction.addToBackStack(s);

        transaction.commit();
    }

}
