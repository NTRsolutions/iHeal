package com.sismatix.iheal.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sismatix.iheal.Fragments.Hair_Cair_fregment;
import com.sismatix.iheal.Fragments.MyOrderDetails;
import com.sismatix.iheal.Model.My_order_model;
import com.sismatix.iheal.Model.Wishlist_Model;
import com.sismatix.iheal.R;

import java.util.List;

public class My_orderlist_Adapter extends RecyclerView.Adapter<My_orderlist_Adapter.MyViewHolder> {
    Context context;
    private List<My_order_model> myorderModels;

    public My_orderlist_Adapter(FragmentActivity context, List<My_order_model> myorderModels) {
        this.context=context;
        this.myorderModels=myorderModels;
    }
    @NonNull
    @Override
    public My_orderlist_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_orderlist_row, viewGroup, false);
        return new My_orderlist_Adapter.MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull My_orderlist_Adapter.MyViewHolder holder, int position) {
        final My_order_model my_order_model = myorderModels.get(position);
        holder.tv_created_date.setText(my_order_model.getCreated_at());
        holder.tv_name.setText(my_order_model.getName());
        holder.tv_order_id.setText(my_order_model.getIncrement_id());
        holder.tv_paymentmethod.setText(my_order_model.getGrand_total());
        holder.tv_wishlist_order_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new MyOrderDetails();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, myFragment).addToBackStack("My Order").commit();
            }
        });
    }
    @Override
    public int getItemCount() {
        return myorderModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_created_date,tv_order_id,tv_name,tv_paymentmethod,grand_total,tv_wishlist_order_now;

        public MyViewHolder(@NonNull View view) {
            super(view);
            tv_created_date = (TextView) view.findViewById(R.id.tv_created_date);
            tv_order_id = (TextView) view.findViewById(R.id.tv_order_id);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_paymentmethod = (TextView) view.findViewById(R.id.tv_paymentmethod);
            grand_total = (TextView) view.findViewById(R.id.grand_total);
            tv_wishlist_order_now=(TextView)view.findViewById(R.id.tv_wishlist_order_now);
        }
    }
}
