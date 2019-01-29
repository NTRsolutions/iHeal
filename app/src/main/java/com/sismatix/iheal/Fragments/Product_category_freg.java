package com.sismatix.iheal.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.sismatix.iheal.Adapter.Product_Category_adapter;
import com.sismatix.iheal.Adapter.Product_recycler_adapter;
import com.sismatix.iheal.Model.Product_Category_model;
import com.sismatix.iheal.Model.Product_Grid_Model;
import com.sismatix.iheal.R;

import java.util.ArrayList;
import java.util.List;

import static com.sismatix.iheal.Activity.Navigation_drawer_activity.bottom_navigation;

/**
 * A simple {@link Fragment} subclass.
 */
public class Product_category_freg extends Fragment {
    View view;
    public static ArrayList<Product_Grid_Model> grid_model = new ArrayList<Product_Grid_Model>();
    GridView gridview;
    RecyclerView recycler_product_category;
    private List<Product_Category_model> product_model = new ArrayList<Product_Category_model>();
    private Product_Category_adapter product_category_adapter;

    public Product_category_freg() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_product_category_freg, container, false);

        recycler_product_category = (RecyclerView) view.findViewById(R.id.recycler_product_category);

        product_category_adapter = new Product_Category_adapter(getActivity(), product_model);
        recycler_product_category.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recycler_product_category.setItemAnimator(new DefaultItemAnimator());
        // recycler_product.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recycler_product_category.setAdapter(product_category_adapter);
        CALL_PRODUCT_CATEGORY_API();

        return view;
    }
    private void CALL_PRODUCT_CATEGORY_API() {

        for (int i = 0; i < 15; i++) {
            product_model.add(new Product_Category_model(""));
        }
        product_category_adapter.notifyDataSetChanged();
    }

}
