package com.sismatix.iheal.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class Iteam_details extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    ViewPager mPager;
    CircleIndicator indicator;
    ImageView iv_wishlist, iv_itemdetail_cart;
    LinearLayout lv_iteamdetails_click;

    TextView tv_product_name, tv_product_price, tv_short_description,tv_long_descriptionn,tv_main_title;
    ImageView iv_item_desc,iv_show_more;

    String proddd_id;

    private List<sliderimage_model> sliderimage_models = new ArrayList<sliderimage_model>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_iteam_details, container, false);
        bottom_navigation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        Bundle bundle = this.getArguments();

        proddd_id = bundle.getString("prod_id");

        Log.e("prod_itemdetail_id", "" + proddd_id);

        AllocateMemory(v);
        call_item_detail_api(proddd_id);
        iv_wishlist.setOnClickListener(this);
        iv_itemdetail_cart.setOnClickListener(this);
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
        mPager = (ViewPager) v.findViewById(R.id.pager);
        indicator = (CircleIndicator) v.findViewById(R.id.indicator);
        iv_wishlist = (ImageView) v.findViewById(R.id.iv_wishlist);
        iv_itemdetail_cart = (ImageView) v.findViewById(R.id.iv_itemdetail_cart);
        lv_iteamdetails_click = (LinearLayout) v.findViewById(R.id.lv_iteamdetails_click);

        tv_product_name = (TextView) v.findViewById(R.id.tv_product_namee);
        tv_product_price = (TextView) v.findViewById(R.id.tv_product_pricee);
        tv_short_description = (TextView) v.findViewById(R.id.tv_short_descriptionn);
        tv_long_descriptionn = (TextView)v.findViewById(R.id.tv_long_descriptionn);

        tv_main_title = (TextView)v.findViewById(R.id.tv_main_title);

        iv_item_desc = (ImageView)v.findViewById(R.id.iv_item_desc);
        iv_show_more = (ImageView)v.findViewById(R.id.iv_show_more);

    }

    private void call_item_detail_api(String proddd_id) {

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> addcategory = api.appprodview(proddd_id);

        addcategory.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response", "" + response.body().toString());

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

    @Override
    public void onClick(View view) {
        if (view == iv_wishlist) {
            loadFragment(new Wishlist_fragment());
        } else if (view == iv_itemdetail_cart) {
            loadFragment(new Cart());
        } else if (view == lv_iteamdetails_click) {
            loadFragment(new Cart());
        }
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
