package com.sismatix.iheal.Fragments;


import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sismatix.iheal.Activity.Navigation_drawer_activity;
import com.sismatix.iheal.Adapter.Cart_List_Adapter;
import com.sismatix.iheal.Adapter.Wishlist_Adapter;
import com.sismatix.iheal.Model.Cart_Model;
import com.sismatix.iheal.Model.Wishlist_Model;
import com.sismatix.iheal.R;
import com.sismatix.iheal.View.CountDrawable;

import java.util.ArrayList;
import java.util.List;

import static com.sismatix.iheal.Activity.Navigation_drawer_activity.bottom_navigation;

/**
 * A simple {@link Fragment} subclass.
 */
public class Wishlist_fragment extends Fragment {
    RecyclerView recycler_wishlist;
    private List<Wishlist_Model> wishlist_models = new ArrayList<Wishlist_Model>();
    private Wishlist_Adapter wishlist_adapter;
    View v;
    Toolbar toolbar_mywishlist;

    public static LayerDrawable icon;
    public String count = "1";
    public static CountDrawable badge;


    public Wishlist_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_wishlist_fragment, container, false);
        bottom_navigation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        AllocateMemory(v);

        //option manu
        setHasOptionsMenu(true);
        toolbar_mywishlist = (Toolbar) v.findViewById(R.id.toolbar_mywishlist);

        ((Navigation_drawer_activity) getActivity()).setSupportActionBar(toolbar_mywishlist);
        ((Navigation_drawer_activity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((Navigation_drawer_activity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white_36dp);


        wishlist_adapter = new Wishlist_Adapter(getContext(), wishlist_models);
        recycler_wishlist.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycler_wishlist.setAdapter(wishlist_adapter);
        // snapHelper.attachToRecyclerView(recycler_wishlist);

        CALL_WISHLIST_API();

        return v;
    }

    private void CALL_WISHLIST_API() {
        wishlist_models.clear();
        for (int i = 0; i < 4; i++) ;
        {
            wishlist_models.add(new Wishlist_Model("", "abc",
                    "", ""));
            //wishlist_adapter.notifyItemChanged(i);
        }
        //wishlist_adapter.notifyDataSetChanged();

    }

    private void AllocateMemory(View v) {
        recycler_wishlist = (RecyclerView) v.findViewById(R.id.recycler_wishlist);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.cart, menu);
        MenuItem item = menu.findItem(R.id.cart);
        MenuItem item_search = menu.findItem(R.id.search);
        item_search.setVisible(false);

//        icon = (LayerDrawable) item.getIcon();
        icon = (LayerDrawable) item.getIcon();

        CountDrawable badge;
        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_group_count);
        if (reuse != null && reuse instanceof CountDrawable) {
            badge = (CountDrawable) reuse;
        } else {
            badge = new CountDrawable(getActivity());
        }
        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_group_count, badge);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.cart:
                Toast.makeText(getActivity(), "cart Icon Click", Toast.LENGTH_SHORT).show();
                return true;

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
