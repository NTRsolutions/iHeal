package com.sismatix.iheal.Fragments;


import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sismatix.iheal.Activity.Navigation_drawer_activity;
import com.sismatix.iheal.Model.Wishlist_Model;
import com.sismatix.iheal.Preference.Login_preference;
import com.sismatix.iheal.R;
import com.sismatix.iheal.Retrofit.ApiClient;
import com.sismatix.iheal.Retrofit.ApiInterface;
import com.sismatix.iheal.View.CountDrawable;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sismatix.iheal.Activity.Navigation_drawer_activity.bottom_navigation;

/**
 * A simple {@link Fragment} subclass.
 */
public class My_Account_With_Login extends Fragment {

    Toolbar toolbar_myaccount;
    TextView tv_manage_address;
    TextView txt_username, tv_email, tv_edit_shipping;

    TextView tv_telephone_no, tv_shipping_country, tv_shipping_postcode, tv_shipping_def_add,
            tv_shipping_city, tv_shipping_addr, tv_shipping_company_nm, tv_shipping_lastname, tv_shipping_firstname;

    LinearLayout lv_edit_shipping, lv_my_account, lv_update_add;
    View v;

    public My_Account_With_Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_my__account__with__login, container, false);
        bottom_navigation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        setHasOptionsMenu(true);
        AllocateMemory(v);

        //set back icon by defult
        getActivity().setTitle("MY ACCOUNT");
        ((Navigation_drawer_activity) getActivity()).setSupportActionBar(toolbar_myaccount);
        ((Navigation_drawer_activity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((Navigation_drawer_activity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white_36dp);

        txt_username.setText(Login_preference.getfullname(getActivity()));
        tv_email.setText(Login_preference.getemail(getActivity()));


        CALL_SHIPPING_ADDRESS_API();


        tv_edit_shipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lv_my_account.setVisibility(View.GONE);
                lv_edit_shipping.setVisibility(View.VISIBLE);
            }
        });


        lv_update_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                        lv_my_account.setVisibility(View.VISIBLE);
                        lv_edit_shipping.setVisibility(View.GONE);
                    }
                }, 1000);
            }
        });


        return v;
    }

    private void CALL_SHIPPING_ADDRESS_API() {

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        final Call<ResponseBody> shipping_addr = api.GET_SHIPPING_ADDRESS(Login_preference.getcustomer_id(getActivity()));
//cartList.clear();
        shipping_addr.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("res_getaddr", "" + response.body().toString());
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    Log.e("statusadd", "" + status);
                    if (status.equalsIgnoreCase("Success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("address");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject addr_object = jsonArray.getJSONObject(0);
                                Log.e("Name_wishlist", "" + addr_object.getString("firstname"));
                                tv_shipping_def_add.setText("Shipping Address");
                                tv_telephone_no.setText(addr_object.getString("telephone"));
                                tv_shipping_firstname.setText(addr_object.getString("firstname"));
                                tv_shipping_lastname.setText(addr_object.getString("middlename"));
                                tv_shipping_company_nm.setText(addr_object.getString("company"));
                                tv_shipping_addr.setText(addr_object.getString("street"));
                                tv_shipping_city.setText(addr_object.getString("city"));
                                tv_shipping_postcode.setText(addr_object.getString("postcode"));
                                tv_shipping_country.setText(addr_object.getString("region"));


                            } catch (Exception e) {
                                Log.e("Exception", "" + e);
                            } finally {

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
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void AllocateMemory(View v) {
        toolbar_myaccount = (Toolbar) v.findViewById(R.id.toolbar_myaccount);
        tv_manage_address = (TextView) v.findViewById(R.id.tv_manage_address);
        txt_username = (TextView) v.findViewById(R.id.txt_username);
        tv_email = (TextView) v.findViewById(R.id.tv_email);
        lv_edit_shipping = (LinearLayout) v.findViewById(R.id.lv_edit_shipping);
        lv_my_account = (LinearLayout) v.findViewById(R.id.lv_my_account);
        lv_update_add = (LinearLayout) v.findViewById(R.id.lv_update_add);


        tv_edit_shipping = (TextView) v.findViewById(R.id.tv_edit_shipping);
        tv_email = (TextView) v.findViewById(R.id.tv_email);
        tv_telephone_no = (TextView) v.findViewById(R.id.tv_telephone_no);
        tv_shipping_country = (TextView) v.findViewById(R.id.tv_shipping_country);
        tv_shipping_postcode = (TextView) v.findViewById(R.id.tv_shipping_postcode);
        tv_shipping_city = (TextView) v.findViewById(R.id.tv_shipping_city);
        tv_shipping_addr = (TextView) v.findViewById(R.id.tv_shipping_addr);
        tv_shipping_company_nm = (TextView) v.findViewById(R.id.tv_shipping_company_nm);
        tv_shipping_lastname = (TextView) v.findViewById(R.id.tv_shipping_lastname);
        tv_shipping_firstname = (TextView) v.findViewById(R.id.tv_shipping_firstname);
        tv_shipping_def_add = (TextView) v.findViewById(R.id.tv_shipping_def_add);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        MenuItem item_search = menu.findItem(R.id.search);
        item_search.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
