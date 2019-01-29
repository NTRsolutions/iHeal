package com.sismatix.iheal.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.sismatix.iheal.Adapter.Cart_List_Adapter;
import com.sismatix.iheal.Adapter.Product_grid_adapter;
import com.sismatix.iheal.Adapter.Product_recycler_adapter;
import com.sismatix.iheal.Model.Cart_Model;
import com.sismatix.iheal.Model.Product_Grid_Model;
import com.sismatix.iheal.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tablayout_hair_fregment extends Fragment {

    public static ArrayList<Product_Grid_Model> grid_model = new ArrayList<Product_Grid_Model>();
    GridView gridview;
    RecyclerView recycler_product;
    private List<Product_Grid_Model> product_model = new ArrayList<Product_Grid_Model>();
    private Product_recycler_adapter product_adapter;


    View v;

    public Tablayout_hair_fregment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_tablayout_hair, container, false);

        //gridview=(GridView)v.findViewById(R.id.gridview);
        recycler_product=(RecyclerView) v.findViewById(R.id.recycler_product);


        product_adapter = new Product_recycler_adapter(getActivity(), product_model);
        recycler_product.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recycler_product.setItemAnimator(new DefaultItemAnimator());
        recycler_product.setAdapter(product_adapter);
        CALL_PRODUCT_API();
        return v;
    }
    private void CALL_PRODUCT_API() {

        for (int i = 0; i < 15; i++) {
            product_model.add(new Product_Grid_Model("", "",""));
        }
        product_adapter.notifyDataSetChanged();
    }



}
