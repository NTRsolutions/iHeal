package com.sismatix.iheal.Adapter;

import android.content.Context;
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
import com.sismatix.iheal.Model.Payment_Method_Model;
import com.sismatix.iheal.R;

import java.util.List;


public class Payment_Method_Adapter extends RecyclerView.Adapter<Payment_Method_Adapter.MyViewHolder> {
    private Context context;
    private List<Payment_Method_Model> model;
    int minteger = 1;
    int current_price = 30;
    int product_total = current_price;

    public Payment_Method_Adapter(Context context, List<Payment_Method_Model> cartList) {
        this.context = context;
        this.model = cartList;
    }

    @Override
    public Payment_Method_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payment_method_row, parent, false);

        return new Payment_Method_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Payment_Method_Adapter.MyViewHolder holder, final int position) {
        final Payment_Method_Model payment_model = model.get(position);
        holder.lv_payment_greylayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    holder.lv_paymenet_greenlayout.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.round_boder_green));
                } else {
                    holder.lv_paymenet_greenlayout.setBackground(ContextCompat.getDrawable(context, R.drawable.round_boder_green));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_payment_name, tv_product_delivery_type;
        ImageView iv_payment_icon;
        LinearLayout lv_payment_greylayout, lv_paymenet_greenlayout;

        public MyViewHolder(View view) {
            super(view);
            tv_payment_name = (TextView) view.findViewById(R.id.tv_payment_name);
            iv_payment_icon = (ImageView) view.findViewById(R.id.iv_payment_icon);

            lv_payment_greylayout = (LinearLayout) view.findViewById(R.id.lv_payment_greeylayout);
            lv_paymenet_greenlayout = (LinearLayout) view.findViewById(R.id.lv_paymenet_greenlayout);


        }
    }
}





