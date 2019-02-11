package com.sismatix.iheal.Fragments;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.sismatix.iheal.Activity.Navigation_drawer_activity;
import com.sismatix.iheal.Adapter.SlideingImageAdapter;
import com.sismatix.iheal.Model.sliderimage_model;
import com.sismatix.iheal.Preference.Login_preference;
import com.sismatix.iheal.R;
import com.sismatix.iheal.Retrofit.ApiClient;
import com.sismatix.iheal.Retrofit.ApiInterface;
import com.sismatix.iheal.View.CountDrawable;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class Item_details extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    ViewPager mPager;
    CircleIndicator indicator;
    ImageView iv_wishlist, iv_itemdetail_cart, iv_back;
    LinearLayout lv_iteamdetails_click;

    TextView tv_product_name, tv_product_price, tv_short_description, tv_long_descriptionn, tv_main_title;
    ImageView iv_item_desc, iv_show_more;

    String proddd_id;
    public static LayerDrawable icon;
    public String count = "1";
    public static CountDrawable badge;
    Toolbar toolbar;
    ProgressBar progressBar_item;

    private List<sliderimage_model> sliderimage_models = new ArrayList<sliderimage_model>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_iteam_details, container, false);
        bottom_navigation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        setHasOptionsMenu(true);
        AllocateMemory(v);


        //set back icon by defult
        ((Navigation_drawer_activity) getActivity()).setSupportActionBar(toolbar);

        ((Navigation_drawer_activity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((Navigation_drawer_activity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white_36dp);
        getActivity().setTitle("Shopping Cart");

        //----------//
        Bundle bundle = this.getArguments();
        proddd_id = bundle.getString("prod_id");
        Log.e("prod_itemdetail_id", "" + proddd_id);

        call_item_detail_api(proddd_id);

        lv_iteamdetails_click.setOnClickListener(this);
        mPager.addOnPageChangeListener(this);

        iv_item_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_long_descriptionn.setVisibility(View.VISIBLE);
            }
        });


        return v;
    }

    private void AllocateMemory(View v) {
        toolbar = (Toolbar) v.findViewById(R.id.toolbar_item_detail);


        mPager = (ViewPager) v.findViewById(R.id.pager);
        progressBar_item = (ProgressBar) v.findViewById(R.id.progressBar_item);
        indicator = (CircleIndicator) v.findViewById(R.id.indicator);
        lv_iteamdetails_click = (LinearLayout) v.findViewById(R.id.lv_iteamdetails_click);

        tv_product_name = (TextView) v.findViewById(R.id.tv_product_namee);
        tv_product_price = (TextView) v.findViewById(R.id.tv_product_pricee);
        tv_short_description = (TextView) v.findViewById(R.id.tv_short_descriptionn);
        tv_long_descriptionn = (TextView) v.findViewById(R.id.tv_long_descriptionn);

          tv_main_title = (TextView)v.findViewById(R.id.tv_main_title);

        iv_item_desc = (ImageView) v.findViewById(R.id.iv_item_desc);
        iv_show_more = (ImageView) v.findViewById(R.id.iv_show_more);

    }

    private void call_item_detail_api(String proddd_id) {
        progressBar_item.setVisibility(View.VISIBLE);
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> addcategory = api.appprodview(proddd_id);

        addcategory.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response", "" + response.body().toString());

                progressBar_item.setVisibility(View.GONE);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    String typeee = jsonObject.getString("type");
                    Log.e("type_item_details", "" + typeee);

                    String main_title = jsonObject.getString("product_name");
                    tv_main_title.setText(main_title);

                    String proname = jsonObject.getString("product_sku");
                    tv_product_name.setText(proname);

                    String proprice = jsonObject.getString("product_price");
                    tv_product_price.setText(proprice);

                    final String desc = jsonObject.getString("description");
                    //tv_long_descriptionn.setText(Html.fromHtml(desc));

                    final String shortdesc = jsonObject.getString("short_description");
                    tv_short_description.setText(Html.fromHtml(shortdesc));

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

                    Log.e("jsonarrraay", "" + jsonArray);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        String value = jsonArray.getString(i);
                        sliderimage_models.add(new sliderimage_model(jsonArray.getString(i)));
                        Log.e("json", i + "=" + value);

                    }

                    mPager.setAdapter(new SlideingImageAdapter(getActivity(), sliderimage_models));
                    indicator.setViewPager(mPager);

                   /* String title = jsonObject.getString("title");
                    String count = jsonObject.getString("count");
                    String categoryimage = jsonObject.getString("categoryimage");*/

                    // products = jsonObject.getString("products");
                    //Log.e("prods",""+products);

                  /*  Log.e("title",""+title);
                    Log.e("count",""+count);
                    Log.e("categoryimage",""+categoryimage);*/

                  /*  if (status.equalsIgnoreCase("success")){
                        // Toast.makeText(getContext(),""+meassg, Toast.LENGTH_SHORT).show();

                     *//*   tv_hair.setText(title);
                        tv_item_count.setText(count+" "+getString(R.string.item));*//*


                    }else if (status.equalsIgnoreCase("error")){
                        // Toast.makeText(getContext(), ""+meassg, Toast.LENGTH_SHORT).show();
                    }*/

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
        android.support.v4.app.FragmentManager manager = getFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.rootLayout, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }


    // cart menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.cart_wishlist, menu);
        MenuItem item = menu.findItem(R.id.cartt);
        MenuItem item_search = menu.findItem(R.id.search);
        item_search.setVisible(false);

//        icon = (LayerDrawable) item.getIcon();
        icon = (LayerDrawable) item.getIcon();

        CountDrawable badge;
        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_group_count);
        if (reuse != null && reuse instanceof CountDrawable) {
            badge = (CountDrawable) reuse;
        } else {
            badge = new CountDrawable(getActivity());
        }
        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_group_count, badge);

        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.cartt:
                loadFragment(new Cart());
                return true;

            case R.id.wish:
                loadFragment(new Wishlist_fragment());
                return true;

            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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

    @Override
    public void onClick(View view) {
        if (view == lv_iteamdetails_click) {
            AddTocart();
        }
    }
}
