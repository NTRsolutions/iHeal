package com.sismatix.iheal.Fragments;


import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sismatix.iheal.Activity.Navigation_drawer_activity;
import com.sismatix.iheal.Preference.Login_preference;
import com.sismatix.iheal.R;
import com.sismatix.iheal.View.CountDrawable;

import static com.sismatix.iheal.Activity.Navigation_drawer_activity.bottom_navigation;

/**
 * A simple {@link Fragment} subclass.
 */
public class My_Account_With_Login extends Fragment {

    Toolbar toolbar_myaccount;
    TextView tv_manage_address;
    View v;
    public My_Account_With_Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_my__account__with__login, container, false);
        bottom_navigation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        setHasOptionsMenu(true);
        AllocateMemory(v);

        //set back icon by defult
           getActivity().setTitle("MY Account");
        ((Navigation_drawer_activity) getActivity()).setSupportActionBar(toolbar_myaccount);
        ((Navigation_drawer_activity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((Navigation_drawer_activity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white_36dp);




        return v;
    }

    private void AllocateMemory(View v) {
        toolbar_myaccount = (Toolbar) v.findViewById(R.id.toolbar_myaccount);
        tv_manage_address = (TextView) v.findViewById(R.id.tv_manage_address);

    }





    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.cart, menu);
        MenuItem item = menu.findItem(R.id.cart);
        MenuItem item_search = menu.findItem(R.id.search);
        item_search.setVisible(false);
        item.setVisible(false);



        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
