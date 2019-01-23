package com.sismatix.iheal.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sismatix.iheal.Model.Cart_Model;
import com.sismatix.iheal.R;

import java.util.List;


public class Confirmation_cart_Adapter extends RecyclerView.Adapter<Confirmation_cart_Adapter.MyViewHolder> {
    private Context context;
    private List<Cart_Model> cartList;
    int minteger = 1;
    int current_price = 30;
    int product_total = current_price;

    public Confirmation_cart_Adapter(Context context, List<Cart_Model> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @Override
    public Confirmation_cart_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_list_item_row, parent, false);

        return new Confirmation_cart_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Confirmation_cart_Adapter.MyViewHolder holder, final int position) {
        final Cart_Model cart_model = cartList.get(position);

        holder.tv_cart_quantity_total.setText("1" );
        //increse and decrese quantity
        Log.e("minteger_5", "" + minteger);
        Log.e("minteger_5", "" + product_total);

       holder.iv_cart_quantity_increase.setVisibility(View.GONE);
       holder.iv_cart_quantity_decrease.setVisibility(View.GONE);

    }


    private void display(int number, Confirmation_cart_Adapter.MyViewHolder holder, int total_price) {

        Log.e("minteger_83", "" + number);
        Log.e("total_price_83", "" + total_price);
        holder.tv_cart_quantity_total.setText("" + number);

        holder.tv_product_price_total.setText("" + total_price);
    }


    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public void removeItem(int position) {
        cartList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Cart_Model model_item, int position) {
        cartList.add(position, model_item);
        // notify item added by position
        notifyItemInserted(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_cart_product_title, tv_cart_product_description, tv_product_price_total, tv_cart_quantity_total;
        public ImageView iv_cart_product_image, iv_cart_quantity_decrease, iv_cart_quantity_increase;
        public RelativeLayout viewForeground;

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



