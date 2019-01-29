package com.sismatix.iheal.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sismatix.iheal.Adapter.Cart_List_Adapter;
import com.sismatix.iheal.Adapter.Wishlist_Adapter;
import com.sismatix.iheal.Model.Cart_Model;
import com.sismatix.iheal.Model.Wishlist_Model;
import com.sismatix.iheal.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Wishlist_fragment extends Fragment {
    RecyclerView recycler_wishlist;
    private List<Wishlist_Model> wishlist_models = new ArrayList<Wishlist_Model>();
    private Wishlist_Adapter wishlist_adapter;
    View v;
    public Wishlist_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         v=inflater.inflate(R.layout.fragment_wishlist_fragment, container, false);

         AllocateMemory(v);

        wishlist_adapter = new Wishlist_Adapter(getContext(), wishlist_models);
        recycler_wishlist.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycler_wishlist.setAdapter(wishlist_adapter);
       // snapHelper.attachToRecyclerView(recycler_wishlist);

        CALL_WISHLIST_API();

        return v;
    }

    private void CALL_WISHLIST_API() {
        wishlist_models.clear();
        for(int i=0;i<4;i++);
        {
            wishlist_models.add(new Wishlist_Model("", "abc",
                    "", ""));
            //wishlist_adapter.notifyItemChanged(i);
        }
        //wishlist_adapter.notifyDataSetChanged();

    }

    private void AllocateMemory(View v) {
        recycler_wishlist=(RecyclerView)v.findViewById(R.id.recycler_wishlist);
    }

}
