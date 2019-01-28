package com.sismatix.iheal.Fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.sismatix.iheal.Activity.Navigation_drawer_activity;
import com.sismatix.iheal.Adapter.TabPageAdapter;
import com.sismatix.iheal.R;

import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class Hair_Cair_fregment extends Fragment {
    int mutedColor = R.attr.colorPrimary;

    private CollapsingToolbarLayout collapsingToolbar;

    private ViewPager viewPager;

    private TabLayout tabLayout;
    LinearLayout fragment_container;
    View view;

    public Hair_Cair_fregment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_hair_cair, container, false);
        //option manu
        setHasOptionsMenu(true);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
       // fragment_container = (LinearLayout) view.findViewById(R.id.fragment_container);
        collapsingToolbar = (CollapsingToolbarLayout) view
                .findViewById(R.id.collapsing_toolbar);
        ImageView header = (ImageView) view.findViewById(R.id.header);

        final Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        ((Navigation_drawer_activity) getActivity()).setSupportActionBar(toolbar);
        ((Navigation_drawer_activity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);

        collapsingToolbar.setTitle("Hair Care");

        SetTablayout();

        return view;
    }

    //tablayout
    private void SetTablayout() {

        //   setupViewPager(viewPager);
        // tabLayout.setupWithViewPager(viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("VIEW-ALL"));
        tabLayout.addTab(tabLayout.newTab().setText("ANTI-SULFAT"));
         tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
         final TabPageAdapter adapter = new TabPageAdapter(getFragmentManager(), tabLayout.getTabCount());
         viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));




        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                   viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        //  header.setImageResource(R.drawable.header_1);
                        break;
                    case 1:
                        //  header.setImageResource(R.drawable.header2);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
       // transaction.replace(R.id.fragment_container, fragment);

        transaction.commit();
    }

    // cart menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.cart, menu);
        MenuItem item = menu.findItem(R.id.action_settings);
        item.setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.cart:
                Toast.makeText(getActivity(), "cart Icon Click", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
