package com.sismatix.iheal.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.sismatix.iheal.Adapter.Product_grid_adapter;
import com.sismatix.iheal.Model.Product_Grid_Model;
import com.sismatix.iheal.R;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tablayout_hair_fregment extends Fragment {

    public static ArrayList<Product_Grid_Model> grid_model = new ArrayList<Product_Grid_Model>();
    GridView gridview;
    View v;

    public Tablayout_hair_fregment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_tablayout_hair, container, false);

        gridview=(GridView)v.findViewById(R.id.gridview);

        CALL_GRID_API();

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
               /* Intent ii = new Intent(Package_image_gridview.this, Package_slider.class);
                ii.putExtra("position",position);
                ii.putExtra("Package_id", Package_id);
                activity.startActivity(ii);
                activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);*/

            }
        });
        return v;
    }

    private void CALL_GRID_API() {


        for (int i = 0; i <10; i++) {
            try{
                Product_Grid_Model gmodel = new Product_Grid_Model(
                       "","","");
                grid_model.add(gmodel);

            }catch (Exception e) {
            }finally {
            }
        }

        Product_grid_adapter adapter = new Product_grid_adapter(getActivity(), grid_model);
        gridview.setAdapter(adapter);

    }

}
