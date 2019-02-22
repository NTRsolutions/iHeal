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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sismatix.iheal.Activity.Navigation_drawer_activity;
import com.sismatix.iheal.Model.Wishlist_Model;
import com.sismatix.iheal.Preference.Login_preference;
import com.sismatix.iheal.Preference.MyAddress_Preference;
import com.sismatix.iheal.R;
import com.sismatix.iheal.Retrofit.ApiClient;
import com.sismatix.iheal.Retrofit.ApiInterface;
import com.sismatix.iheal.View.CountDrawable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sismatix.iheal.Activity.Navigation_drawer_activity.bottom_navigation;
import static com.sismatix.iheal.Fragments.Shipping_fragment.spinner_country_Name;

/**
 * A simple {@link Fragment} subclass.
 */
public class My_Account_With_Login extends Fragment {

    Toolbar toolbar_myaccount;
    TextView tv_manage_address;
    TextView txt_username, tv_email, tv_edit_shipping;

    TextView tv_telephone_no, tv_shipping_country, tv_shipping_postcode, tv_shipping_def_add, tv_shipping_city,
            tv_shipping_addr, tv_shipping_company_nm, tv_shipping_lastname, tv_shipping_firstname,tv_region;

    LinearLayout lv_edit_shipping, lv_my_account, lv_update_add;

    EditText edt_edit_ship_first_name,edt_edit_shipping_lastname,edt__edit_shipping_phone_no,edt_edit_shipping_company,
            edt_edit_street,edt_edit_fax,edt_edit_shipping_zipcode,edt_edit_shipping_city,edt_edit_shipping_region;

    String ship_firsname,ship_lastname,ship_phoneno,ship_company,ship_streetadd,ship_fax,ship_zip,ship_city,ship_region,sp_selected;

    Spinner edit_spinner_country_Name;
    String countryid;
    ArrayList<String> country_name_code = new ArrayList<String>();
    ArrayList<String> country_name = new ArrayList<String>();

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
        Countrylist();

        //set back icon by defult
        getActivity().setTitle("MY ACCOUNT");
        ((Navigation_drawer_activity) getActivity()).setSupportActionBar(toolbar_myaccount);
        ((Navigation_drawer_activity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((Navigation_drawer_activity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white_36dp);

        txt_username.setText(Login_preference.getfullname(getActivity()));
        tv_email.setText(Login_preference.getemail(getActivity()));

        CALL_SHIPPING_ADDRESS_API();

        setValuesToeEditText();

       /* ship_firsname= et_shippingfirstname.getText().toString();
        ship_lastname = et_shippinglastname.getText().toString();
        ship_phoneno = et_shippingphonenumber.getText().toString();
        ship_company = et_shippingcompany.getText().toString();
        ship_streetadd = et_streetadd.getText().toString();
        ship_fax = et_fax.getText().toString();
        ship_zip = et_shippingzipcode.getText().toString();
        ship_city = et_shippingcity.getText().toString();
        ship_region = et_shippingregion.getText().toString();
        sp_selected = spinner_country_Name.getSelectedItem().toString();
        Log.e("spinner_myacc",""+sp_selected);*/

        edt_edit_ship_first_name.setText(tv_shipping_firstname.getText().toString());
        edt_edit_shipping_lastname.setText(tv_shipping_lastname.getText().toString());
        edt_edit_shipping_city.setText(tv_shipping_city.getText().toString());
        edt_edit_shipping_region.setText(tv_region.getText().toString());

        edt_edit_street.setText(tv_shipping_addr.getText().toString());
        edt_edit_shipping_zipcode.setText(tv_shipping_postcode.getText().toString());
        edt__edit_shipping_phone_no.setText(tv_telephone_no.getText().toString());
        edt_edit_shipping_company.setText(tv_shipping_company_nm.getText().toString());

        //edt_edit_fax.setText(tv_);

        //edit_spinner_country_Name

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

/*        spinner_country_Name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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

        });*/

        return v;
    }

    private void setValuesToeEditText() {

        edt_edit_ship_first_name.setText(MyAddress_Preference.getFirstname(getActivity()));
        Log.e("fnameeee", "" + edt_edit_shipping_lastname.getText().toString());
        edt_edit_shipping_lastname.setText(MyAddress_Preference.getLastname(getActivity()));
        edt__edit_shipping_phone_no.setText(MyAddress_Preference.getPhoneNumber(getActivity()));
        edt_edit_shipping_company.setText(MyAddress_Preference.getCompanyName(getActivity()));
        edt_edit_street.setText(MyAddress_Preference.getStreetAddress(getActivity()));
        edt_edit_fax.setText(MyAddress_Preference.getFax(getActivity()));
        edt_edit_shipping_zipcode.setText(MyAddress_Preference.getZipcode(getActivity()));
        edt_edit_shipping_city.setText(MyAddress_Preference.getCity(getActivity()));
        edt_edit_shipping_region.setText(MyAddress_Preference.getRegion(getActivity()));
    }

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

    private void CALL_SHIPPING_ADDRESS_API() {

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        final Call<ResponseBody> shipping_addr = api.GET_SHIPPING_ADDRESS(Login_preference.getcustomer_id(getActivity()));
        //cartList.clear();
        shipping_addr.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
              //  Log.e("res_getaddr", "" + response.body().toString());

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    Log.e("statusadd", "" + status);
                    if (status.equalsIgnoreCase("Success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("address");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {

                                JSONObject addr_object = jsonArray.getJSONObject(i);
                                Log.e("Name_wishlist", "" + addr_object.getString("firstname"));
                                //tv_shipping_def_add.setText("Shipping Address");
                                tv_shipping_firstname.setText(addr_object.getString("firstname"));
                                MyAddress_Preference.setFirstname(getActivity(),addr_object.getString("firstname"));
                                tv_shipping_lastname.setText(addr_object.getString("lastname"));
                                MyAddress_Preference.setLastname(getActivity(),addr_object.getString("lastname"));
                                tv_shipping_city.setText(addr_object.getString("city"));
                                MyAddress_Preference.setCity(getActivity(),addr_object.getString("city"));
                                tv_region.setText(addr_object.getString("region"));
                                MyAddress_Preference.setRegion(getActivity(),addr_object.getString("region"));
                                tv_shipping_country.setText(addr_object.getString("country_id"));

                                tv_shipping_addr.setText(addr_object.getString("street"));
                                MyAddress_Preference.setStreetAddress(getActivity(),addr_object.getString("street"));
                                tv_shipping_postcode.setText(addr_object.getString("postcode"));
                                MyAddress_Preference.setZipcode(getActivity(),addr_object.getString("postcode"));
                                tv_telephone_no.setText(addr_object.getString("telephone"));
                                MyAddress_Preference.setPhoneNumber(getActivity(),addr_object.getString("telephone"));
                                tv_shipping_company_nm.setText(addr_object.getString("company"));
                                MyAddress_Preference.setCompanyName(getActivity(),addr_object.getString("company"));

                            } catch (Exception e) {
                                Log.e("Exception", "" + e);
                            } finally {

                            }
                        }
                    } else if (status.equalsIgnoreCase("error")) {

                    }

                } catch (Exception e) {
                    Log.e("Exception", "" + e);
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
        tv_region = (TextView)v.findViewById(R.id.tv_region);

        edt_edit_ship_first_name = (EditText)v.findViewById(R.id.edt_edit_ship_first_name);
        edt_edit_shipping_lastname = (EditText)v.findViewById(R.id.edt_edit_shipping_lastname);
        edt__edit_shipping_phone_no = (EditText)v.findViewById(R.id.edt__edit_shipping_phone_no);
        edt_edit_shipping_company = (EditText)v.findViewById(R.id.edt_edit_shipping_company);
        edt_edit_street = (EditText)v.findViewById(R.id.edt_edit_street);
        edt_edit_fax = (EditText)v.findViewById(R.id.edt_edit_fax);
        edt_edit_shipping_zipcode = (EditText)v.findViewById(R.id.edt_edit_shipping_zipcode);
        edt_edit_shipping_city = (EditText)v.findViewById(R.id.edt_edit_shipping_city);
        edt_edit_shipping_region = (EditText)v.findViewById(R.id.edt_edit_shipping_region);

        edit_spinner_country_Name = (Spinner)v.findViewById(R.id.edit_spinner_country_Name);

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
