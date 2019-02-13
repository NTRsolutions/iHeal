package com.sismatix.iheal.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sismatix.iheal.Fragments.Cart;
import com.sismatix.iheal.Fragments.Wishlist_fragment;
import com.sismatix.iheal.Model.Cart_Delivery_Model;
import com.sismatix.iheal.Model.Wishlist_Model;
import com.sismatix.iheal.Preference.Login_preference;
import com.sismatix.iheal.R;
import com.sismatix.iheal.Retrofit.ApiClient;
import com.sismatix.iheal.Retrofit.ApiInterface;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Wishlist_Adapter extends RecyclerView.Adapter<Wishlist_Adapter.MyViewHolder> {
    private Context context;
    private List<Wishlist_Model> model;
    final AppCompatActivity activity = (AppCompatActivity) context;
    String action = "remove";

    public Wishlist_Adapter(Context context, List<Wishlist_Model> wishlist_model) {
        this.context = context;
        this.model = wishlist_model;
    }

    @Override
    public Wishlist_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wishlist_row, parent, false);

        return new Wishlist_Adapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final Wishlist_Adapter.MyViewHolder holder, final int position) {
        final Wishlist_Model wishlist_model = model.get(position);

        holder.tv_wishlist_haircare.setText(wishlist_model.getProduct_category());
        holder.tv_wishlist_product_name.setText(wishlist_model.getProduct_name());
        holder.tv_wishlist_product_price.setText(wishlist_model.getProduct_price());
        Glide.with(context).load(wishlist_model.getProduct_image()).into(holder.iv_wishlist_image);

        final String wishliist_product_id = wishlist_model.getProduct_id();
        final String wishlist_customer_id = Login_preference.getcustomer_id(context);

        Intent intent = new Intent("wishlist-custom-message");
        intent.putExtra("wishlist_pidddd", wishliist_product_id);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        Log.e("wishliist_product_id", "" + wishliist_product_id);
        Log.e("wishlist_customer_id", "" + wishlist_customer_id);
        Log.e("wishlist_action", "" + action);

        holder.iv_wishlist_image_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();
                remove_wishlist(action, wishliist_product_id, wishlist_customer_id);
            }
        });

        holder.lv_wishlist_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, wishlist_model.getProduct_id(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void remove_wishlist(String action, String wishliist_product_id, String wishlist_customer_id) {

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> removeWishlist = api.Wishlistactions(action, wishliist_product_id, wishlist_customer_id);
        removeWishlist.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
// Log.e("remove_wishlist_res", "" + response.body().toString());

//progressBar_item.setVisibility(View.GONE);
                JSONObject jsonObject = null;

                try {

                    jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    Log.e("status_wishlist_remove", "" + status);

                    if (status.equalsIgnoreCase("Success")) {
                        /* Cart.prepare_Cart();*/
                        String msg = jsonObject.getString("message");
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

//loadFragment(new Wishlist_fragment());

                        Wishlist_fragment.CALL_WISHLIST_API();

                    } else {
                        Toast.makeText(context, "there are some problem(s).", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Log.e("", "" + e);
                } finally {
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void loadFragment(Wishlist_fragment wishlist_fragment) {
        Log.e("clickone", "");
        android.support.v4.app.FragmentManager manager = activity.getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.rootLayout, wishlist_fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_wishlist_product_price, tv_wishlist_product_name, tv_wishlist_haircare, tv_wishlist_order_now;
        LinearLayout lv_wishlist_click;
        ImageView iv_wishlist_image, iv_wishlist_image_remove;

        public MyViewHolder(View view) {
            super(view);
            tv_wishlist_product_price = (TextView) view.findViewById(R.id.tv_wishlist_product_price);
            tv_wishlist_product_name = (TextView) view.findViewById(R.id.tv_wishlist_product_name);
            tv_wishlist_haircare = (TextView) view.findViewById(R.id.tv_wishlist_haircare);
            tv_wishlist_order_now = (TextView) view.findViewById(R.id.tv_wishlist_order_now);
            lv_wishlist_click = (LinearLayout) view.findViewById(R.id.lv_wishlist_click);
            iv_wishlist_image = (ImageView) view.findViewById(R.id.iv_wishlist_image);
            iv_wishlist_image_remove = (ImageView) view.findViewById(R.id.iv_wishlist_image_remove);
        }
    }
}


