package com.sismatix.iheal.Fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sismatix.iheal.Preference.Login_preference;
import com.sismatix.iheal.R;

import static com.sismatix.iheal.Activity.Navigation_drawer_activity.bottom_navigation;

/**
 * A simple {@link Fragment} subclass.
 */
public class Checkout_fragment extends Fragment implements View.OnClickListener {
    LinearLayout lv_shipping,lv_confirmation,lv_payment;
    public static LinearLayout lv_shipping_selected,lv_payment_selected,lv_confirmation_selected;
    public static ImageView iv_confirmation_done,iv_payment_done,iv_shipping_done;
    public static TextView tv_shipping,tv_payment,tv_confirmation,tv_checkout_total;
    String loginflag,grand_total;
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

        loginflag = Login_preference.getLogin_flag(getActivity());

        AllocateMemory(v);
        Bundle bundle = this.getArguments();

        grand_total = bundle.getString("cart_grand_total");
        Log.e("grand_to_checkout", "" + grand_total);


        lv_shipping.setOnClickListener(this);
        lv_confirmation.setOnClickListener(this);
        lv_payment.setOnClickListener(this);

        lv_payment_selected.setVisibility(View.INVISIBLE);
        lv_confirmation_selected.setVisibility(View.INVISIBLE);
        lv_shipping_selected.setVisibility(View.VISIBLE);

        tv_checkout_total.setText(grand_total);


        if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {

            loadFragment(new Shipping_fragment());
        } else {
            loadFragment(new EmailLogin());
        }
        return v;
    }

    private void AllocateMemory(View v) {
        lv_shipping=(LinearLayout)v.findViewById(R.id.lv_shipping);
        lv_confirmation=(LinearLayout)v.findViewById(R.id.lv_confirmation);
        lv_payment=(LinearLayout)v.findViewById(R.id.lv_payment);
        lv_shipping_selected=(LinearLayout)v.findViewById(R.id.lv_shipping_selected);
        lv_payment_selected=(LinearLayout)v.findViewById(R.id.lv_payment_selected);
        lv_confirmation_selected=(LinearLayout)v.findViewById(R.id.lv_confirmation_selected);

        iv_confirmation_done=(ImageView)v.findViewById(R.id.iv_confirmation_done);
        iv_payment_done=(ImageView)v.findViewById(R.id.iv_payment_done);
        iv_shipping_done=(ImageView)v.findViewById(R.id.iv_shipping_done);
        tv_shipping=(TextView)v.findViewById(R.id.tv_shipping);
        tv_payment=(TextView)v.findViewById(R.id.tv_payment);
        tv_confirmation=(TextView)v.findViewById(R.id.tv_confirmation);
        tv_checkout_total=(TextView)v.findViewById(R.id.tv_checkout_total);

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

            if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {

                loadFragment(new Shipping_fragment());
            } else {
                loadFragment(new EmailLogin());
            }

        }else if(view==lv_confirmation)
        {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tv_confirmation.setTextColor(getActivity().getColor(R.color.white));
            }
            lv_payment_selected.setVisibility(View.INVISIBLE);
            lv_confirmation_selected.setVisibility(View.VISIBLE);
            lv_shipping_selected.setVisibility(View.INVISIBLE);

            if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {

                loadFragment(new Confirmation_fragment());
            } else {
                loadFragment(new EmailLogin());
            }


        }
        else if(view==lv_payment) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tv_payment.setTextColor(getActivity().getColor(R.color.white));
            }
            lv_payment_selected.setVisibility(View.VISIBLE);
            lv_confirmation_selected.setVisibility(View.INVISIBLE);
            lv_shipping_selected.setVisibility(View.INVISIBLE);


            if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {

                loadFragment(new Payment_fragment());
            } else {
                loadFragment(new EmailLogin());
            }

        }
    }
}
