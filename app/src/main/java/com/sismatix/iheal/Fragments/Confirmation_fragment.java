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
import android.widget.TextView;
import android.widget.Toast;

import com.sismatix.iheal.Adapter.Cart_List_Adapter;
import com.sismatix.iheal.Adapter.Confirmation_cart_Adapter;
import com.sismatix.iheal.Model.Cart_Model;
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
import static com.sismatix.iheal.Adapter.Payment_Method_Adapter.paymentcode_ada;
import static com.sismatix.iheal.Fragments.Cart.cart_adapter;
import static com.sismatix.iheal.Fragments.Cart.cart_items_count;
import static com.sismatix.iheal.Fragments.Cart.cartlistt;
import static com.sismatix.iheal.Fragments.Cart.context;
import static com.sismatix.iheal.Fragments.Cart.qoute_id_cart;
import static com.sismatix.iheal.Fragments.Cart.qt;
import static com.sismatix.iheal.Fragments.Cart.tv_maintotal;

/**
 * A simple {@link Fragment} subclass.
 */
public class Confirmation_fragment extends Fragment {
    ImageView iv_confirm_pay;
    TextView confirm_add;
    RecyclerView recyclerview_confirmation;
    private List<Cart_Model> cartList = new ArrayList<Cart_Model>();
    private Confirmation_cart_Adapter confirmation_cart_adapter;
    String fname_confirm, lname_confirm, zipcode_confirm, city_confirm, phone_confirm, fax_confirm, company_confirm, streetadd_confirm,
            countryid_confirm, customerid_confirm, saveaddress_confirm, shipping_confirm, email_confirm, quote_confirm;
    String paycode;

    LinearLayout lv_confirm_pay;
    View v;

    public Confirmation_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_confirmation, container, false);
        Allocatememory(v);
        prepareConfirmCart();

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            paycode = bundle.getString("paymentcode_payment");
            fname_confirm = bundle.getString("Firstname_payment");
            lname_confirm = bundle.getString("Lastname_payment");
            zipcode_confirm = bundle.getString("Zipcode_payment");
            city_confirm = bundle.getString("City_payment");
            phone_confirm = bundle.getString("Phonenumber_payment");
            fax_confirm = bundle.getString("Fax_payment");
            company_confirm = bundle.getString("Company_payment");
            streetadd_confirm = bundle.getString("streetadd_payment");
            countryid_confirm = bundle.getString("Countryid_payment");
            customerid_confirm = bundle.getString("customer_id_payment");
            saveaddress_confirm = bundle.getString("saveadd_payment");
            shipping_confirm = bundle.getString("shippingmethod_payment");
            email_confirm = bundle.getString("email_id_payment");
            quote_confirm = bundle.getString("quote_id_payment");

            Log.e("confirm_fname", "" + fname_confirm);
            Log.e("confirm_lname", "" + lname_confirm);
            Log.e("confirm_zip", "" + zipcode_confirm);
            Log.e("confirm_city", "" + city_confirm);
            Log.e("confirm_phone", "" + phone_confirm);
            Log.e("confirm_fax", "" + fax_confirm);
            Log.e("confirm_comp", "" + company_confirm);
            Log.e("confirm_streetadd", "" + streetadd_confirm);
            Log.e("confirm_countrtyid", "" + countryid_confirm);
            Log.e("confirm_customerid", "" + customerid_confirm);
            Log.e("confirm_saveadd", "" + saveaddress_confirm);
            Log.e("confirm_shipmethod", "" + shipping_confirm);
            Log.e("confirm_emailid", "" + email_confirm);
            Log.e("confirm_qid", "" + quote_confirm);
            Log.e("confirm_code", "" + paymentcode_ada);
            Log.e("confirm_paycode_final", "" + paycode);

        }

        confirm_add.setText(streetadd_confirm + " " + zipcode_confirm + " " + city_confirm + " " + countryid_confirm);

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

                        CONFIRMATION_CART();

                        /*loadFragment(new Fianl_Order_Checkout_freg());*/

                    }
                }, 1000);

            }
        });
        return v;
    }

    private void prepareConfirmCart() {
        cartList.clear();
        String email = Login_preference.getemail(context);

        String loginflag = Login_preference.getLogin_flag(context);
        Log.e("customeriddd", "" + Login_preference.getcustomer_id(context));
        if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {
            Log.e("with_login", "");
            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            cartlistt = api.Cartlist(email);
        } else {
            Log.e("without_login", "");
            String quote_id = Login_preference.getquote_id(context);//359
            Log.e("quoteidd", "" + quote_id);
            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            cartlistt = api.getlistcart(quote_id);
        }
        cartlistt.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("responseeeeee_confirm", "" + response.body().toString());

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    Log.e("status_confirm_cart", "" + status);
                    if (status.equalsIgnoreCase("success")) {
                        String grand_total = jsonObject.getString("grand_total");
                        tv_maintotal.setText(grand_total);
                        Login_preference.setquote_id(context, jsonObject.getString("quote_id"));
                        qoute_id_cart = jsonObject.getString("quote_id");
                        Log.e("qoute_id_confirm_cart", "" + qoute_id_cart);

                        cart_items_count = jsonObject.getString("items_count");

                        Login_preference.setCart_item_count(context, cart_items_count);
                        JSONArray jsonArray = jsonObject.getJSONArray("products");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject vac_object = jsonArray.getJSONObject(i);
                                Log.e("Name", "" + vac_object.getString("product_name"));
                                cartList.add(new Cart_Model(vac_object.getString("product_name"),
                                        vac_object.getString("product_price"),
                                        vac_object.getString("product_image"),
                                        vac_object.getString("product_sku"),
                                        vac_object.getString("product_id"),
                                        vac_object.getString("row_total"),
                                        vac_object.getString("product_qty"),
                                        vac_object.getString("itemid")));
                                qt = vac_object.getString("product_qty");
                                Log.e("qtttttttt", "" + qt);
                            } catch (Exception e) {
                                Log.e("Exception", "" + e);
                            } finally {
                                confirmation_cart_adapter.notifyItemChanged(i);
                            }
                        }
                    } else if (status.equalsIgnoreCase("error")) {

                    }

                } catch (Exception e) {
                    Log.e("", "" + e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
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

        ApiInterface apii = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> confirm = apii.AppCreateOrder(customerid_confirm, email_confirm, quote_confirm, fname_confirm,
                lname_confirm, countryid_confirm, zipcode_confirm, city_confirm, phone_confirm, company_confirm,
                streetadd_confirm, shipping_confirm, paycode, saveaddress_confirm);

        confirm.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response", "" + response.body().toString());
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    String code = jsonObject.getString("code");
                    Log.e("code_confirmation", "" + code);
                    String meassg = jsonObject.getString("message");
                    Log.e("message_confirmation", "" + meassg);
                    if (code.equalsIgnoreCase("200")) {
                        Toast.makeText(getContext(), "" + meassg, Toast.LENGTH_SHORT).show();
                        loadFragment(new Fianl_Order_Checkout_freg());
                    } else if (code.equalsIgnoreCase("error")) {
                        Toast.makeText(getContext(), "" + meassg, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("Exception", "" + e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

       /* for (int i = 0; i < 4; i++) {
            cartList.add(new Cart_Model("", "",
                    "", "", "", "", "", ""));
        }
        confirmation_cart_adapter.notifyDataSetChanged();*/

    }

    private void Allocatememory(View v) {
        recyclerview_confirmation = (RecyclerView) v.findViewById(R.id.recyclerview_confirmation);
        iv_confirm_pay = (ImageView) v.findViewById(R.id.iv_confirm_pay);
        lv_confirm_pay = (LinearLayout) v.findViewById(R.id.lv_confirm_pay);
        confirm_add = (TextView) v.findViewById(R.id.confirm_add);

        confirmation_cart_adapter = new Confirmation_cart_Adapter(getActivity(), cartList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview_confirmation.setLayoutManager(mLayoutManager);
        recyclerview_confirmation.setItemAnimator(new DefaultItemAnimator());
        recyclerview_confirmation.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerview_confirmation.setAdapter(confirmation_cart_adapter);
    }

}
