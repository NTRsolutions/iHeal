package com.sismatix.iheal.Fragments;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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

import com.sismatix.iheal.Activity.Navigation_drawer_activity;
import com.sismatix.iheal.Adapter.Nature_TabPager_Adapter;
import com.sismatix.iheal.Adapter.TabPageAdapter;
import com.sismatix.iheal.R;

import static com.sismatix.iheal.Activity.Navigation_drawer_activity.bottom_navigation;

/**
 * A simple {@link Fragment} subclass.
 */
public class Nature_Category_freg extends Fragment {
    View view;
    private CollapsingToolbarLayout collapsing_toolbar_nature;

    private ViewPager viewpager_nature;

    private TabLayout tablayout_nature;
    LinearLayout fragment_container;
    public Nature_Category_freg() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_nature__category_freg, container, false);
        bottom_navigation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
       setHasOptionsMenu(true);

        viewpager_nature = (ViewPager) view.findViewById(R.id.viewpager_nature);
        tablayout_nature = (TabLayout) view.findViewById(R.id.tablayout_nature);
        // fragment_container = (LinearLayout) view.findViewById(R.id.fragment_container);
        collapsing_toolbar_nature = (CollapsingToolbarLayout) view
                .findViewById(R.id.collapsing_toolbar_nature);
        ImageView iv_nature = (ImageView) view.findViewById(R.id.iv_nature);

        final Toolbar toolbar_nature = (Toolbar) view.findViewById(R.id.toolbar_nature);

        ((Navigation_drawer_activity) getActivity()).setSupportActionBar(toolbar_nature);
        ((Navigation_drawer_activity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);

        collapsing_toolbar_nature.setTitle("Nature's Categories");

        SetTablayout();
        return view;
    }

    //tablayout
    private void SetTablayout() {

        //   setupViewPager(viewPager);
        // tabLayout.setupWithViewPager(viewPager);

        tablayout_nature.addTab(tablayout_nature.newTab().setText("VIEW-ALL"));
        tablayout_nature.addTab(tablayout_nature.newTab().setText("ANTI-SULFAT"));
        tablayout_nature.setTabGravity(TabLayout.GRAVITY_FILL);
        final Nature_TabPager_Adapter adapter = new Nature_TabPager_Adapter(getChildFragmentManager(), tablayout_nature.getTabCount());
        viewpager_nature.setAdapter(adapter);
        viewpager_nature.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout_nature));

        tablayout_nature.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewpager_nature.setCurrentItem(tab.getPosition());
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

   // cart menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.cart, menu);
        MenuItem item = menu.findItem(R.id.search);
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
