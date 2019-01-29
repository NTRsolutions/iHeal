package com.sismatix.iheal.Fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sismatix.iheal.Adapter.Cart_Delivery_Adapter;
import com.sismatix.iheal.Adapter.Payment_Method_Adapter;
import com.sismatix.iheal.Model.Cart_Delivery_Model;
import com.sismatix.iheal.Model.Payment_Method_Model;
import com.sismatix.iheal.R;

import java.util.ArrayList;
import java.util.List;

import static com.sismatix.iheal.Activity.Navigation_drawer_activity.bottom_navigation;

/**
 * A simple {@link Fragment} subclass.
 */
public class Payment_fragment extends Fragment {

    RecyclerView payment_method_recyclerview;
    Payment_Method_Adapter payment_method_adapter;
    private List<Payment_Method_Model> payment_method_models = new ArrayList<Payment_Method_Model>();
    ImageView iv_confirm_order;

    View v;
    public Payment_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_payment, container, false);
        AllocateMEmory(v);
        CALL_CART_DELIVERY();

        Checkout_fragment.iv_shipping_done.setVisibility(View.VISIBLE);
        Checkout_fragment.iv_payment_done.setVisibility(View.INVISIBLE);
        Checkout_fragment.iv_confirmation_done.setVisibility(View.INVISIBLE);

        Checkout_fragment.lv_payment_selected.setVisibility(View.VISIBLE);
        Checkout_fragment.lv_shipping_selected.setVisibility(View.INVISIBLE);
        Checkout_fragment.lv_confirmation_selected.setVisibility(View.INVISIBLE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Checkout_fragment.tv_confirmation.setTextColor(getActivity().getColor(R.color.colorPrimary));
            Checkout_fragment.tv_payment.setTextColor(getActivity().getColor(R.color.white));
            Checkout_fragment.tv_shipping.setTextColor(getActivity().getColor(R.color.colorPrimary));
        }
        iv_confirm_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new Confirmation_fragment());
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
            payment_method_models.add(new Payment_Method_Model("", ""));
        }
        payment_method_adapter.notifyDataSetChanged();
    }

    private void AllocateMEmory(View v) {
        payment_method_recyclerview=(RecyclerView)v.findViewById(R.id.payment_method_recyclerview);
        iv_confirm_order=(ImageView) v.findViewById(R.id.iv_confirm_order);

        payment_method_adapter = new Payment_Method_Adapter(getActivity(), payment_method_models);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
        layoutManager.setReverseLayout(false);
        payment_method_recyclerview.setLayoutManager(layoutManager);
        payment_method_recyclerview.setAdapter(payment_method_adapter);


    }

}
