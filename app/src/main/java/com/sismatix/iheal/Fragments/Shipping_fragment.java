package com.sismatix.iheal.Fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.sismatix.iheal.Adapter.Cart_Delivery_Adapter;
import com.sismatix.iheal.Adapter.Cart_List_Adapter;
import com.sismatix.iheal.Model.Cart_Delivery_Model;
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
import static com.sismatix.iheal.Adapter.Cart_Delivery_Adapter.shippingmethod;

/**
 * A simple {@link Fragment} subclass.
 */
public class Shipping_fragment extends Fragment {
    View v;
    RecyclerView recyclerview_item_delivery;
    Cart_Delivery_Adapter cart_delivery_adapter;
    private List<Cart_Delivery_Model> cart_delivery_models = new ArrayList<Cart_Delivery_Model>();
    ImageView iv_continue_payment;
    EditText et_shippingfirstname, et_shippinglastname, et_shippingphonenumber, et_shippingcompany,  et_streetadd, et_fax,
            et_shippingzipcode, et_shippingcity, et_shippingregion;
    LinearLayout lv_continue_payment;
    String loginflag, setdefault;
    Spinner spinner_country_Name;
    CheckBox ck_default;
    ArrayList<String> country_name_code = new ArrayList<String>();
    ArrayList<String> country_name = new ArrayList<String>();
    String qidd,email_id,customer_id, firstName, lastName, countryid, postcode, city, region, telephone, fax, company, street;

    public Shipping_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_shipping, container, false);
        loginflag = Login_preference.getLogin_flag(getActivity());

        AllocateMEmory(v);
        CALL_CART_DELIVERY();
        Countrylist();

        qidd = Login_preference.getquote_id(getActivity());
        Log.e("quoteidd_shippingfrag",""+qidd);

        et_shippingfirstname = (EditText) v.findViewById(R.id.et_shippingfirstname);
        Checkout_fragment.lv_payment_selected.setVisibility(View.INVISIBLE);
        Checkout_fragment.iv_shipping_done.setVisibility(View.INVISIBLE);
        Checkout_fragment.iv_payment_done.setVisibility(View.INVISIBLE);
        Checkout_fragment.iv_confirmation_done.setVisibility(View.INVISIBLE);
        Checkout_fragment.lv_shipping_selected.setVisibility(View.VISIBLE);
        Checkout_fragment.lv_payment_selected.setVisibility(View.INVISIBLE);
        Checkout_fragment.lv_confirmation_selected.setVisibility(View.INVISIBLE);

        spinner_country_Name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                spinner_country_Name.setSelection(116);

                int selected_item_position = spinner_country_Name.getSelectedItemPosition();
                countryid = country_name_code.get(selected_item_position);
                Log.e("countryid", "" + countryid);
                String selected_country = String.valueOf(spinner_country_Name.getSelectedItem());

                Toast.makeText(getActivity(), selected_item_position + " " + selected_country + " => " + countryid, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {

            }

        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Checkout_fragment.tv_confirmation.setTextColor(getActivity().getColor(R.color.colorPrimary));
            Checkout_fragment.tv_payment.setTextColor(getActivity().getColor(R.color.colorPrimary));
            Checkout_fragment.tv_shipping.setTextColor(getActivity().getColor(R.color.white));
        }

        lv_continue_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {
                            if (ck_default.isChecked()) {
                                setdefault = "1";
                            } else {
                                setdefault = "0";
                            }
                            validateShippingData();
                            /*loadFragment(new Payment_fragment());*/
                        } else {
                            loadFragment(new EmailLogin());
                        }
                    }
                }, 1000);

            }
        });


        return v;
    }

    private void validateShippingData() {

        customer_id = Login_preference.getcustomer_id(getActivity());
        email_id = Login_preference.getemail(getActivity());

        firstName = et_shippingfirstname.getText().toString();
        lastName = et_shippinglastname.getText().toString();
        postcode = et_shippingzipcode.getText().toString();
        city = et_shippingcity.getText().toString();
        telephone = et_shippingphonenumber.getText().toString();
        fax = et_fax.getText().toString();
        company = et_shippingcompany.getText().toString();
        street = et_streetadd.getText().toString();
        region = et_shippingregion.getText().toString();

        if (et_shippingfirstname.getText().length() == 0) {
            et_shippingfirstname.setError("Please enter your Firstname");
        } else if (et_shippinglastname.getText().length() == 0) {
            et_shippinglastname.setError("Please enter your Lastname");
        } else if (et_shippingzipcode.getText().length() == 0) {
            et_shippingzipcode.setError("Please enter your Zipcode");
        } else if (et_shippingcity.getText().length() == 0) {
            et_shippingcity.setError("Please enter your Cityname");
        } else if (et_shippingphonenumber.getText().length() == 0) {
            et_shippingphonenumber.setError("Please enter your Mobile no.");
        } else if (et_fax.getText().length() == 0) {
            et_fax.setError("Please enter your Fax");
        } else if (et_shippingcompany.getText().length() == 0) {
            et_shippingcompany.setError("Please enter your Company Name");
        } else if (et_streetadd.getText().length() == 0) {
            et_streetadd.setError("Please enter your Street");
        } else if (et_shippingregion.getText().length() == 0) {
            et_shippingregion.setError("Please enter your Region");
        }

        Log.e("firstname_shipping",""+et_shippingfirstname.getText().toString());
        Log.e("lastname_shipping",""+et_shippinglastname.getText().toString());
        Log.e("zipcode_shipping",""+et_shippingzipcode.getText().toString());
        Log.e("city_shipping",""+et_shippingcity.getText().toString());
        Log.e("phonenumber_shipping",""+et_shippingphonenumber.getText().toString());
        Log.e("fax_shipping",""+et_fax.getText().toString());
        Log.e("company_shipping",""+et_shippingcompany.getText().toString());
        Log.e("streetadd_shipping",""+et_streetadd.getText().toString());
        Log.e("countryid_shipping",""+countryid);
        Log.e("Shipping_cust_id", "" + customer_id);
        Log.e("saveadd", "" + setdefault);
        Log.e("shippingmethod",""+shippingmethod);
        Log.e("email_id_shipping",""+email_id);
        Log.e("quoteidshipping",""+qidd);
        /*Log.e("shippingcode", "" + setdefault);*/
        /*Log.e("address_shipping",""+et_shippingaddress.getText().toString());*/

        loadfrag();

        //callAppCreateAddressApi(customer_id, firstName, lastName, countryid, postcode, city, telephone, fax, company, street);

    }

    private void loadfrag() {
        Bundle bundle = new Bundle();
        bundle.putString("Firstname_shipping", "" + et_shippingfirstname.getText().toString());
        bundle.putString("Lastname_shipping", "" + et_shippinglastname.getText().toString());
        bundle.putString("Zipcode_shipping", "" + et_shippingzipcode.getText().toString());
        bundle.putString("City_shipping", "" + et_shippingcity.getText().toString());
        bundle.putString("Phonenumber_shipping", "" + et_shippingphonenumber.getText().toString());
        bundle.putString("Fax_shipping", "" + et_fax.getText().toString());
        bundle.putString("Company_shipping", "" + et_shippingcompany.getText().toString());
        bundle.putString("streetadd_shipping", "" + et_streetadd.getText().toString());
        bundle.putString("Countryid_shipping", "" + countryid);
        bundle.putString("customer_id_shipping", "" + customer_id);
        bundle.putString("saveadd_shipping",""+setdefault);
        bundle.putString("shippingmethod",""+shippingmethod);
        bundle.putString("email_id_shipping",""+email_id);
        bundle.putString("quote_id_shipping",""+qidd);
        Fragment myFragment = new Payment_fragment();
        myFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_checkout, myFragment).addToBackStack(null).commit();

    }

    /*private void callAppCreateAddressApi(String customer_id, String firstName, String lastName, String countryid, String postcode,
                                         String city, String telephone, String fax, String company, String street) {

        ApiInterface apii = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> shipping = apii.AppCreateAddress(customer_id, firstName, lastName, countryid, postcode, city,
                region, telephone, fax, company, street);

        Log.e("firstname", "" + firstName);
        Log.e("lastName", "" + lastName);
        Log.e("customer_id", "" + customer_id);
        Log.e("countryid", "" + countryid);
        Log.e("postcode", "" + postcode);
        Log.e("city", "" + city);
        Log.e("region", "" + region);
        Log.e("telephone", "" + telephone);
        Log.e("fax", "" + fax);
        Log.e("company", "" + company);
        Log.e("street", "" + street);

        shipping.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response", "" + response.body().toString());
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    Log.e("status", "" + status);
                    String meassg = jsonObject.getString("message");
                    Log.e("message", "" + meassg);
                    if (status.equalsIgnoreCase("success")) {
                        Toast.makeText(getContext(), "" + meassg, Toast.LENGTH_SHORT).show();
                        loadFragment(new Payment_fragment());
                    } else if (status.equalsIgnoreCase("error")) {
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

    }*/

    private void Countrylist() {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        final Call<ResponseBody> countrylist = api.get_country_list();
        countrylist.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response", "" + response.body().toString());
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    Log.e("status_wishlist", "" + status);
                    if (status.equalsIgnoreCase("Success")) {
                        String countries = jsonObject.getString("countries");
                        JSONArray jsonArray = jsonObject.getJSONArray("countries");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject wish_object = jsonArray.getJSONObject(i);
                                Log.e("valuessss", "" + wish_object.getString("values"));
                                country_name.add(wish_object.getString("label"));
                                country_name_code.add(wish_object.getString("values"));


                            } catch (Exception e) {
                                Log.e("Exception", "" + e);
                            } finally {
                            }
                        }
                        spinner_country_Name.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, country_name));


                    }
                } catch (Exception e) {
                    Log.e("", "" + e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
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

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> shippingmethods = api.getShippingMethods();
        shippingmethods.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response_shipping_methods", "" + response.body().toString());
//progressBar.setVisibility(View.GONE);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    String msg = jsonObject.getString("message");
                    if (status.equalsIgnoreCase("success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("shipping_method");
                        for (int j = 0; j < jsonArray.length(); j++) {
                            JSONObject obj = jsonArray.getJSONObject(j);
                            JSONArray val_array = obj.getJSONArray("value");
                            for (int k = 0; k < val_array.length(); k++) {
                                try {
                                    JSONObject val_object = val_array.getJSONObject(k);
                                    cart_delivery_models.add(new Cart_Delivery_Model(val_object.getString("code"),
                                            val_object.getString("method"), val_object.getString("title"),
                                            val_object.getString("price")));

                                } catch (Exception e) {
                                    Log.e("Exception", "" + e);
                                } finally {
                                    cart_delivery_adapter.notifyDataSetChanged();
                                }

                            }

                        }

                    } else if (status.equalsIgnoreCase("error")) {
                        Toast.makeText(getContext(), "" + msg, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Log.e("Exception", "" + e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AllocateMEmory(View v) {
        recyclerview_item_delivery = (RecyclerView) v.findViewById(R.id.recyclerview_item_delivery);
        iv_continue_payment = (ImageView) v.findViewById(R.id.iv_continue_payment);
        lv_continue_payment = (LinearLayout) v.findViewById(R.id.lv_continue_payment);
        spinner_country_Name = (Spinner) v.findViewById(R.id.spinner_country_Name);

        et_shippingfirstname = (EditText) v.findViewById(R.id.et_shippingfirstname);
        et_shippinglastname = (EditText) v.findViewById(R.id.et_shippinglastname);
        et_shippingphonenumber = (EditText) v.findViewById(R.id.et_shippingphonenumber);
        et_shippingcompany = (EditText) v.findViewById(R.id.et_shippingcompany);
        /*et_shippingaddress = (EditText) v.findViewById(R.id.et_shippingaddress);*/
        et_streetadd = (EditText) v.findViewById(R.id.et_street);
        et_fax = (EditText) v.findViewById(R.id.et_fax);
        et_shippingzipcode = (EditText) v.findViewById(R.id.et_shippingzipcode);
        et_shippingcity = (EditText) v.findViewById(R.id.et_shippingcity);
        et_shippingregion = (EditText) v.findViewById(R.id.et_shippingregion);
        ck_default = (CheckBox)v.findViewById(R.id.ck_default);

        cart_delivery_adapter = new Cart_Delivery_Adapter(getActivity(), cart_delivery_models);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
        layoutManager.setReverseLayout(false);
        recyclerview_item_delivery.setLayoutManager(layoutManager);
        recyclerview_item_delivery.setAdapter(cart_delivery_adapter);
        recyclerview_item_delivery.setHasFixedSize(true);

    }

}
