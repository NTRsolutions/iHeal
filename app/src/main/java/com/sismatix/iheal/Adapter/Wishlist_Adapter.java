package com.sismatix.iheal.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sismatix.iheal.Model.Cart_Delivery_Model;
import com.sismatix.iheal.Model.Wishlist_Model;
import com.sismatix.iheal.R;

import java.util.List;

    public class Wishlist_Adapter extends RecyclerView.Adapter<Wishlist_Adapter.MyViewHolder> {
        private Context context;
        private List<Wishlist_Model> model;

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


        }

        @Override
        public int getItemCount() {
            return model.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView tv_wishlist_product_price, tv_wishlist_product_name,tv_wishlist_haircare,tv_wishlist_order_now;
            LinearLayout lv_wishlist_click;
            ImageView iv_wishlist_image,iv_wishlist_image_remove;

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



