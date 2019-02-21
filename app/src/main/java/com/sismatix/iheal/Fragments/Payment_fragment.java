package com.sismatix.iheal.Fragments;


import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sismatix.iheal.Adapter.Cart_Delivery_Adapter;
import com.sismatix.iheal.Adapter.Payment_Method_Adapter;
import com.sismatix.iheal.Model.Cart_Delivery_Model;
import com.sismatix.iheal.Model.Payment_Method_Model;
import com.sismatix.iheal.Model.Product_Category_model;
import com.sismatix.iheal.Model.Product_Grid_Model;
import com.sismatix.iheal.Preference.Login_preference;
import com.sismatix.iheal.R;
import com.sismatix.iheal.Retrofit.ApiClient;
import com.sismatix.iheal.Retrofit.ApiInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sismatix.iheal.Activity.Navigation_drawer_activity.bottom_navigation;

/**
 * A simple {@link Fragment} subclass.
 */
public class Payment_fragment extends Fragment {

    RecyclerView payment_method_recyclerview;
    Payment_Method_Adapter payment_method_adapter;
    private List<Payment_Method_Model> payment_method_models = new ArrayList<Payment_Method_Model>();
    ImageView iv_confirm_order;
    LinearLayout lv_confirm_order;
    String loginflag;
    View v;
    public Payment_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_payment, container, false);
        AllocateMEmory(v);
        CALL_PAYMENT_API();
        loginflag = Login_preference.getLogin_flag(getActivity());

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

        lv_confirm_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {

                            loadFragment(new Confirmation_fragment());
                        } else {
                            loadFragment(new EmailLogin());
                        }
                    }
                }, 1000);
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
    private void CALL_PAYMENT_API() {

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> categorylist = api.getPaymentMethods();

        categorylist.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response_payment", "" + response.body().toString());
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    if (status.equalsIgnoreCase("success")){
                        JSONArray jsonArray=jsonObject.getJSONArray("payment_method");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            try {
                                JSONObject vac_object = jsonArray.getJSONObject(i);
                                payment_method_models.add(new Payment_Method_Model(vac_object.getString("label"),
                                        vac_object.getString("value")));

                            } catch (Exception e) {
                                Log.e("Exception", "" + e);
                            } finally {
                                payment_method_adapter.notifyItemChanged(i);
                            }

                        }

                    }else if (status.equalsIgnoreCase("error")){
                    }

                }catch (Exception e){
                    Log.e("Exc",""+e);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

        /*for (int i=0;i<6;i++) {
            payment_method_models.add(new Payment_Method_Model("", ""));
        }
        payment_method_adapter.notifyDataSetChanged();*/
    }

    private void AllocateMEmory(View v) {
        payment_method_recyclerview=(RecyclerView)v.findViewById(R.id.payment_method_recyclerview);
        iv_confirm_order=(ImageView) v.findViewById(R.id.iv_confirm_order);
        lv_confirm_order=(LinearLayout) v.findViewById(R.id.lv_confirm_order);

        payment_method_adapter = new Payment_Method_Adapter(getActivity(), payment_method_models);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setReverseLayout(false);
        payment_method_recyclerview.setLayoutManager(layoutManager);
        payment_method_recyclerview.setAdapter(payment_method_adapter);

    }

}
