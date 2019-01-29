package com.sismatix.iheal.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sismatix.iheal.Fragments.TransParant_Hair_care_freg;
import com.sismatix.iheal.Model.Product_Category_model;
import com.sismatix.iheal.Model.Product_Grid_Model;
import com.sismatix.iheal.R;

import java.util.List;


public class Product_Category_adapter extends RecyclerView.Adapter<Product_Category_adapter.MyViewHolder> {
    private Context context;
    private List<Product_Category_model> models;


    public Product_Category_adapter(Context context, List<Product_Category_model> models) {
        this.context = context;
        this.models = models;
    }

    @Override
    public Product_Category_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.natures_category_row, parent, false);

        return new Product_Category_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Product_Category_adapter.MyViewHolder holder, final int position) {
        final Product_Category_model product_model = models.get(position);
        holder.lv_nature_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new TransParant_Hair_care_freg();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, myFragment).addToBackStack(null).commit();

            }
        });

    }


    @Override
    public int getItemCount() {
        return models.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_category_name ;
        LinearLayout lv_nature_click;

        public MyViewHolder(View view) {
            super(view);
            tv_category_name = (TextView) view.findViewById(R.id.tv_category_name);
            lv_nature_click = (LinearLayout) view.findViewById(R.id.lv_nature_click);





        }
    }
}




