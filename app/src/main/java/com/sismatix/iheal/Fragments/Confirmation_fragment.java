package com.sismatix.iheal.Fragments;


import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.LinearLayout;

import com.sismatix.iheal.Adapter.Cart_List_Adapter;
import com.sismatix.iheal.Adapter.Confirmation_cart_Adapter;
import com.sismatix.iheal.Model.Cart_Model;
import com.sismatix.iheal.R;

import java.util.ArrayList;
import java.util.List;

import static com.sismatix.iheal.Activity.Navigation_drawer_activity.bottom_navigation;

/**
 * A simple {@link Fragment} subclass.
 */
public class Confirmation_fragment extends Fragment {
    ImageView iv_confirm_pay;
    RecyclerView recyclerview_confirmation;
    private List<Cart_Model> cartList = new ArrayList<Cart_Model>();
    private Confirmation_cart_Adapter confirmation_cart_adapter;

    LinearLayout lv_confirm_pay;
    View v;
    public Confirmation_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_confirmation, container, false);
        Allocatememory(v);
        CONFIRMATION_CART();

        Checkout_fragment.lv_payment_selected.setVisibility(View.INVISIBLE);
        Checkout_fragment.lv_shipping_selected.setVisibility(View.INVISIBLE);

        Checkout_fragment.lv_confirmation_selected.setVisibility(View.VISIBLE);
        Checkout_fragment.iv_payment_done.setVisibility(View.VISIBLE);
        Checkout_fragment.iv_shipping_done.setVisibility(View.VISIBLE);
        Checkout_fragment.iv_confirmation_done.setVisibility(View.INVISIBLE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Checkout_fragment.tv_confirmation.setTextColor(getActivity().getColor(R.color.white));
            Checkout_fragment.tv_payment.setTextColor(getActivity().getColor(R.color.colorPrimary));
            Checkout_fragment.tv_shipping.setTextColor(getActivity().getColor(R.color.colorPrimary));
        }

        lv_confirm_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        loadFragment(new Fianl_Order_Checkout_freg());
                    }
                }, 1000);

                }
        });
        return  v;
    }
    private void loadFragment(Fragment fragment) {
        Log.e("clickone", "");
        android.support.v4.app.FragmentManager manager = getFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.rootLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void CONFIRMATION_CART() {

        for (int i=0;i<4;i++) {
            cartList.add(new Cart_Model("", "",
                    "", "","","","",""));
        }
        confirmation_cart_adapter.notifyDataSetChanged();
    }

    private void Allocatememory(View v) {
        recyclerview_confirmation=(RecyclerView)v.findViewById(R.id.recyclerview_confirmation);
        iv_confirm_pay=(ImageView) v.findViewById(R.id.iv_confirm_pay);
        lv_confirm_pay=(LinearLayout) v.findViewById(R.id.lv_confirm_pay);

        confirmation_cart_adapter = new Confirmation_cart_Adapter(getActivity(), cartList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview_confirmation.setLayoutManager(mLayoutManager);
        recyclerview_confirmation.setItemAnimator(new DefaultItemAnimator());
        recyclerview_confirmation.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerview_confirmation.setAdapter(confirmation_cart_adapter);
    }

}
