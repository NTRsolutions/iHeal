package com.sismatix.iheal.Adapter;



import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.sismatix.iheal.Fragments.Cart;
import com.sismatix.iheal.Fragments.Item_details;
import com.sismatix.iheal.Model.Cart_Model;
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

public class Cart_List_Adapter extends RecyclerView.Adapter<Cart_List_Adapter.MyViewHolder> {
    private Context context;
    private List<Cart_Model> cartList;
    int minteger = 1;
    int current_price=30;
    int  product_total = current_price;
    Call<ResponseBody> remove_from_cart=null;

    public static String cart_item_grand_total;

    public Cart_List_Adapter(Context context, List<Cart_Model> cartList) {
        this.context = context;
        this.cartList = cartList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_list_item_row, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Cart_Model cart_model = cartList.get(position);
        holder.tv_cart_product_title.setText(cart_model.getProduct_name());
        Glide.with(context).load(cart_model.getProduct_image()).into(holder.iv_cart_product_image);
        holder.tv_product_price_total.setText(cart_model.getProduct_price());
        holder.tv_cart_product_description.setText(cart_model.getProduct_description());

        display(minteger,holder,product_total);

        holder.iv_cart_quantity_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minteger = minteger + 1;
                product_total = product_total + current_price;
                display(minteger, holder,product_total);
                Log.e("minteger_50", "" + minteger);

            }
        });


        holder.viewForeground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                    }
                }, 1000);

            }
        });

        holder.iv_cart_quantity_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (minteger != 0) {
                    minteger = minteger - 1;
                        if(minteger==0)
                        {
                            Log.e("minteger_zero", "" + minteger);
                            minteger=1;
                          // product_total = product_total - current_price;
                        }else {
                        product_total = product_total - current_price;
                            Log.e("minteger_69", "" + product_total);
                        display(minteger, holder, product_total);
                        }

                } else {
                    //display(minteger, holder,product_total);
                }
            }
        });

    }


    private void display(int number, MyViewHolder holder,int total_price) {

        Log.e("minteger_83", "" + number);
        Log.e("total_price_83", "" + total_price);
        holder.tv_cart_quantity_total.setText("" + number);

        //holder.tv_product_price_total.setText("" + total_price);
    }


    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public void removeItem(int position) {
        String product_id=cartList.get(position).getProduct_id();

        Log.e("remove_product_id_113",""+product_id);
        CALL_REMOVE_FROM_CART_API(product_id);
        cartList.remove(position);
        notifyItemRemoved(position);
    }

    public  void CALL_REMOVE_FROM_CART_API(String proddd_id)
    {
        String email=Login_preference.getemail(context);
        Log.e("product_id_remove",""+proddd_id);
        String loginflag=Login_preference.getLogin_flag(context);
        if (loginflag.equalsIgnoreCase("1") || loginflag == "1"){
            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            remove_from_cart=api.remove_from_cartlist(proddd_id,email);
            Log.e("proddd_idddd",""+proddd_id);
        }else{
            String quote_id=Login_preference.getquote_id(context);
            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            remove_from_cart=api.withoutlogin_remove_from_cartlist(proddd_id,quote_id);
            Log.e("proddd_idddd",""+proddd_id);
        }
        remove_from_cart.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("responjse_remove_cart",""+response.body().toString());
                JSONObject jsonObject=null;
                try{
                    jsonObject=new JSONObject(response.body().string());
                    String status =jsonObject.getString("status");
                     Log.e("status_remove",""+status);
                    if (status.equalsIgnoreCase("Success")){
                        cart_item_grand_total=jsonObject.getString("grand_total");
                        Log.e("cart_item_count",""+cart_item_grand_total);
                        Cart.tv_maintotal.setText(cart_item_grand_total);

                        Cart.cart_items_count=jsonObject.getString("items_count");
                        Log.e("cart_item_count",""+Cart.cart_items_count);

                        Login_preference.setCart_item_count(context,Cart.cart_items_count);


                        Toast.makeText(context, ""+status, Toast.LENGTH_SHORT).show();
                          //  Cart.prepare_Cart();
                    }else if (status.equalsIgnoreCase("error")){
                       // Toast.makeText(context, ""+meassg, Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Log.e("exception",""+e);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void restoreItem(Cart_Model model_item, int position) {
        cartList.add(position, model_item);
        // notify item added by position
        notifyItemInserted(position);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_cart_product_title, tv_cart_product_description, tv_product_price_total, tv_cart_quantity_total;
        public ImageView iv_cart_product_image, iv_cart_quantity_decrease, iv_cart_quantity_increase;
        public RelativeLayout  viewForeground;

        public MyViewHolder(View view) {
            super(view);
            tv_cart_product_description = (TextView) view.findViewById(R.id.tv_cart_product_description);
            tv_cart_product_title = (TextView) view.findViewById(R.id.tv_cart_product_title);
            tv_product_price_total = (TextView) view.findViewById(R.id.tv_product_price_total);
            tv_cart_quantity_total = (TextView) view.findViewById(R.id.tv_cart_quantity_total);
            iv_cart_product_image = (ImageView) view.findViewById(R.id.iv_cart_product_image);
            iv_cart_quantity_decrease = (ImageView) view.findViewById(R.id.iv_cart_quantity_decrease);
            iv_cart_quantity_increase = (ImageView) view.findViewById(R.id.iv_cart_quantity_increase);

            viewForeground = (RelativeLayout) view.findViewById(R.id.view_foreground);

        }
    }
}

