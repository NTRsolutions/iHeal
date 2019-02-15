package com.sismatix.iheal.Fragments;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sismatix.iheal.Activity.Navigation_drawer_activity;
import com.sismatix.iheal.R;

import static com.sismatix.iheal.Activity.Navigation_drawer_activity.bottom_navigation;

/**
 * A simple {@link Fragment} subclass.
 */
public class Checkout_fragment extends Fragment implements View.OnClickListener {
    LinearLayout lv_shipping,lv_confirmation,lv_payment;
    public static LinearLayout lv_shipping_selected,lv_payment_selected,lv_confirmation_selected;
    public static ImageView iv_confirmation_done,iv_payment_done,iv_shipping_done;
    public static TextView tv_shipping,tv_payment,tv_confirmation;
    ImageView iv_close_checkout;

    View v;
    public Checkout_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_checkout_fragment, container, false);
        bottom_navigation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        AllocateMemory(v);

       /* getActivity().getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        Toast.makeText(getContext(), "hmm barabar", Toast.LENGTH_SHORT).show();   // Update your UI here.
                    }
                });
*/
        lv_shipping.setOnClickListener(this);
        lv_confirmation.setOnClickListener(this);
        lv_payment.setOnClickListener(this);

        lv_payment_selected.setVisibility(View.INVISIBLE);
        lv_confirmation_selected.setVisibility(View.INVISIBLE);
        lv_shipping_selected.setVisibility(View.VISIBLE);

        iv_close_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Navigation_drawer_activity.class);
                startActivity(i);
            }
        });

        loadFragment(new Shipping_fragment());

        return v;
    }

    private void AllocateMemory(View v) {
        lv_shipping=(LinearLayout)v.findViewById(R.id.lv_shipping);
        lv_confirmation=(LinearLayout)v.findViewById(R.id.lv_confirmation);
        lv_payment=(LinearLayout)v.findViewById(R.id.lv_payment);
        lv_shipping_selected=(LinearLayout)v.findViewById(R.id.lv_shipping_selected);
        lv_payment_selected=(LinearLayout)v.findViewById(R.id.lv_payment_selected);
        lv_confirmation_selected=(LinearLayout)v.findViewById(R.id.lv_confirmation_selected);
        iv_close_checkout = (ImageView)v.findViewById(R.id.iv_close_checkout);

        iv_confirmation_done=(ImageView)v.findViewById(R.id.iv_confirmation_done);
        iv_payment_done=(ImageView)v.findViewById(R.id.iv_payment_done);
        iv_shipping_done=(ImageView)v.findViewById(R.id.iv_shipping_done);
        tv_shipping=(TextView)v.findViewById(R.id.tv_shipping);
        tv_payment=(TextView)v.findViewById(R.id.tv_payment);
        tv_confirmation=(TextView)v.findViewById(R.id.tv_confirmation);

    }

    private void loadFragment(Fragment fragment) {
        Log.e("clickone", "");
        android.support.v4.app.FragmentManager manager = getFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout_checkout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        if(view==lv_shipping)
        {
            lv_payment_selected.setVisibility(View.INVISIBLE);
            lv_confirmation_selected.setVisibility(View.INVISIBLE);
            lv_shipping_selected.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tv_shipping.setTextColor(getActivity().getColor(R.color.white));
            }

            loadFragment(new Shipping_fragment());

        }else if(view==lv_confirmation)
        {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tv_confirmation.setTextColor(getActivity().getColor(R.color.white));
            }
            lv_payment_selected.setVisibility(View.INVISIBLE);
            lv_confirmation_selected.setVisibility(View.VISIBLE);
            lv_shipping_selected.setVisibility(View.INVISIBLE);
            loadFragment(new Confirmation_fragment());

        }
        else if(view==lv_payment) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tv_payment.setTextColor(getActivity().getColor(R.color.white));
            }
            lv_payment_selected.setVisibility(View.VISIBLE);
            lv_confirmation_selected.setVisibility(View.INVISIBLE);
            lv_shipping_selected.setVisibility(View.INVISIBLE);
            loadFragment(new Payment_fragment());
        }
    }
}
