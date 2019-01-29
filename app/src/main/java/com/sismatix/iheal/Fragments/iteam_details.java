package com.sismatix.iheal.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sismatix.iheal.R;

import me.relex.circleindicator.CircleIndicator;

import static com.sismatix.iheal.Activity.Navigation_drawer_activity.bottom_navigation;

/**
 * A simple {@link Fragment} subclass.
 */
public class iteam_details extends Fragment implements View.OnClickListener {
    ViewPager mPager;
    CircleIndicator indicator;
    ImageView iv_wishlist,iv_itemdetail_cart;
    LinearLayout lv_iteamdetails_click;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_iteam_details, container, false);
        bottom_navigation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        AllocateMemory(v);
        iv_wishlist.setOnClickListener(this);
        iv_itemdetail_cart.setOnClickListener(this);
        lv_iteamdetails_click.setOnClickListener(this);

        return v;
    }

    private void AllocateMemory(View v) {
        mPager = (ViewPager) v.findViewById(R.id.pager);
        indicator = (CircleIndicator) v.findViewById(R.id.indicator);
        iv_wishlist = (ImageView) v.findViewById(R.id.iv_wishlist);
        iv_itemdetail_cart = (ImageView) v.findViewById(R.id.iv_itemdetail_cart);
        lv_iteamdetails_click = (LinearLayout) v.findViewById(R.id.lv_iteamdetails_click);

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
    public void onClick(View view) {
        if(view==iv_wishlist)
        {
            loadFragment(new Wishlist_fragment());
        }else if(view==iv_itemdetail_cart)
        {
            loadFragment(new Cart());
        }else if (view== lv_iteamdetails_click)
        {
            loadFragment(new Cart());
        }
    }
}
