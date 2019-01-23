package com.sismatix.iheal.Fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sismatix.iheal.Adapter.Cart_Delivery_Adapter;
import com.sismatix.iheal.Adapter.Cart_List_Adapter;
import com.sismatix.iheal.Model.Cart_Delivery_Model;
import com.sismatix.iheal.Model.Cart_Model;
import com.sismatix.iheal.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Shipping_fragment extends Fragment {
    View v;
    RecyclerView recyclerview_item_delivery;
    Cart_Delivery_Adapter cart_delivery_adapter;
    private List<Cart_Delivery_Model>cart_delivery_models = new ArrayList<Cart_Delivery_Model>();
    ImageView iv_continue_payment;
    public Shipping_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_shipping, container, false);
        AllocateMEmory(v);
        CALL_CART_DELIVERY();

        Checkout_fragment.lv_payment_selected.setVisibility(View.INVISIBLE);
        Checkout_fragment.iv_shipping_done.setVisibility(View.INVISIBLE);
        Checkout_fragment.lv_shipping_selected.setVisibility(View.VISIBLE);
        Checkout_fragment.lv_payment_selected.setVisibility(View.INVISIBLE);
        Checkout_fragment.lv_confirmation_selected.setVisibility(View.INVISIBLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
           Checkout_fragment.tv_confirmation.setTextColor(getActivity().getColor(R.color.colorPrimary));
           Checkout_fragment.tv_payment.setTextColor(getActivity().getColor(R.color.colorPrimary));
           Checkout_fragment.tv_shipping.setTextColor(getActivity().getColor(R.color.white));
        }

        iv_continue_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new Payment_fragment());
            }
        });


        return v;
    }
    private void loadFragment(Fragment fragment) {
        Log.e("clickone", "");
        android.support.v4.app.FragmentManager manager = getFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout_checkout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void CALL_CART_DELIVERY() {
        for (int i=0;i<6;i++) {
            cart_delivery_models.add(new Cart_Delivery_Model("", ""));
        }
        cart_delivery_adapter.notifyDataSetChanged();
    }

    private void AllocateMEmory(View v) {
        recyclerview_item_delivery=(RecyclerView)v.findViewById(R.id.recyclerview_item_delivery);
        iv_continue_payment=(ImageView) v.findViewById(R.id.iv_continue_payment);

        cart_delivery_adapter = new Cart_Delivery_Adapter(getActivity(), cart_delivery_models);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
        layoutManager.setReverseLayout(false);
        recyclerview_item_delivery.setLayoutManager(layoutManager);
        recyclerview_item_delivery.setAdapter(cart_delivery_adapter);


    }

}
