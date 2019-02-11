package com.sismatix.iheal.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.sismatix.iheal.Adapter.SlideingImageAdapter;
import com.sismatix.iheal.Model.sliderimage_model;
import com.sismatix.iheal.Preference.Login_preference;
import com.sismatix.iheal.R;
import com.sismatix.iheal.Retrofit.ApiClient;
import com.sismatix.iheal.Retrofit.ApiInterface;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sismatix.iheal.Activity.Navigation_drawer_activity.bottom_navigation;
import static com.sismatix.iheal.Fragments.Hair_Cair_fregment.product_array;

/**
 * A simple {@link Fragment} subclass.
 */
public class Item_details extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    ViewPager mPager;
    CircleIndicator indicator;
    ImageView iv_wishlist, iv_itemdetail_cart;
    LinearLayout lv_iteamdetails_click;

    TextView tv_product_name, tv_product_price, tv_short_description, tv_long_descriptionn, tv_main_title;
    ImageView iv_item_desc, iv_show_more, iv_back;

    String proddd_id, prodddarr;

    private List<sliderimage_model> sliderimage_models = new ArrayList<sliderimage_model>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_iteam_details, container, false);
        bottom_navigation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        Bundle bundle = this.getArguments();

        proddd_id = bundle.getString("prod_id");

        //prodddarr =bundle.getString("products_array");

        Log.e("products_arrayyyy", "" + prodddarr);

        Log.e("prod_itemdetail_id", "" + proddd_id);

        AllocateMemory(v);
        call_item_detail_api(proddd_id);
        iv_wishlist.setOnClickListener(this);
        iv_itemdetail_cart.setOnClickListener(this);
        lv_iteamdetails_click.setOnClickListener(this);

        mPager.addOnPageChangeListener(this);

        /*iv_item_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_long_descriptionn.setVisibility(View.VISIBLE);
            }
        });*/

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle b = new Bundle();
                b.putString("products_array", Hair_Cair_fregment.product_array);
                Log.e("prarr", "" + product_array);
                Fragment myFragment = new Hair_Cair_fregment();
                myFragment.setArguments(b);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, myFragment).addToBackStack(null).commit();

            }
        });

        return v;
    }

    private void AllocateMemory(View v) {
        mPager = (ViewPager) v.findViewById(R.id.pager);
        indicator = (CircleIndicator) v.findViewById(R.id.indicator);
        iv_wishlist = (ImageView) v.findViewById(R.id.iv_wishlist);
        iv_itemdetail_cart = (ImageView) v.findViewById(R.id.iv_itemdetail_cart);
        lv_iteamdetails_click = (LinearLayout) v.findViewById(R.id.lv_iteamdetails_click);

        tv_product_name = (TextView) v.findViewById(R.id.tv_product_namee);
        tv_product_price = (TextView) v.findViewById(R.id.tv_product_pricee);
        tv_short_description = (TextView) v.findViewById(R.id.tv_short_descriptionn);
        tv_long_descriptionn = (TextView) v.findViewById(R.id.tv_long_descriptionn);

        tv_main_title = (TextView) v.findViewById(R.id.tv_main_title);

        iv_item_desc = (ImageView) v.findViewById(R.id.iv_item_desc);
        iv_show_more = (ImageView) v.findViewById(R.id.iv_show_more);
        iv_back = (ImageView) v.findViewById(R.id.iv_back);

    }

    private void call_item_detail_api(String prodddd_id) {

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> addcategory = api.appprodview(prodddd_id);
        addcategory.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response", "" + response.body().toString());

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    proddd_id=jsonObject.getString("product_id");
                    tv_main_title.setText(jsonObject.getString("product_name"));
                    tv_product_name.setText(jsonObject.getString("product_sku"));
                    tv_product_price.setText(jsonObject.getString("product_price"));
                    final String desc = jsonObject.getString("description");
                    final String shortdesc = jsonObject.getString("short_description");
                    tv_short_description.setText(Html.fromHtml(jsonObject.getString("short_description")));

                    iv_item_desc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            iv_show_more.setVisibility(View.VISIBLE);
                            iv_item_desc.setVisibility(View.GONE);
                            tv_short_description.setText(Html.fromHtml(desc));
                        }
                    });
                    iv_show_more.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            iv_show_more.setVisibility(View.GONE);
                            iv_item_desc.setVisibility(View.VISIBLE);
                            tv_short_description.setText(Html.fromHtml(shortdesc));
                        }
                    });
                    JSONArray jsonArray = jsonObject.getJSONArray("mediaGallary");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        sliderimage_models.add(new sliderimage_model(jsonArray.getString(i)));
                        Log.e("json", i + "=" + jsonArray.getString(i));
                    }
                    mPager.setAdapter(new SlideingImageAdapter(getActivity(), sliderimage_models));
                    indicator.setViewPager(mPager);
                } catch (Exception e) {
                    Log.e("", "" + e);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadFragment(Fragment fragment) {
        Log.e("clickone", "");
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.rootLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public void onClick(View view) {
        if (view == iv_wishlist) {
            loadFragment(new Wishlist_fragment());
        } else if (view == iv_itemdetail_cart) {
            loadFragment(new Cart());
        } else if (view == lv_iteamdetails_click) {
            AddTocart();
        }
    }
    private void AddTocart() {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> addtocart=api.addtocart(proddd_id,Login_preference.getcustomer_id(getActivity()));
        Log.e("proddd_idddd",""+proddd_id);
        addtocart.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("",""+response.body().toString());
                JSONObject jsonObject=null;
                try{
                    jsonObject=new JSONObject(response.body().string());
                    String status =jsonObject.getString("status");
                    String meassg=jsonObject.getString("message");
                    Log.e("message",""+meassg);
                    if (status.equalsIgnoreCase("success")){
                        Toast.makeText(getContext(), ""+meassg, Toast.LENGTH_SHORT).show();
                        Login_preference.setiteamqty(getActivity(),jsonObject.getString("items_qty"));
                        loadFragment(new Cart());

                    }else if (status.equalsIgnoreCase("error")){
                        Toast.makeText(getContext(), ""+meassg, Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Log.e("exception",""+e);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }
    @Override
    public void onPageSelected(int i) {

    }
    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
