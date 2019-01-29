package com.sismatix.iheal.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sismatix.iheal.Model.Cart_Model;
import com.sismatix.iheal.Model.Product_Grid_Model;
import com.sismatix.iheal.R;

import java.util.List;


public class Product_recycler_adapter extends RecyclerView.Adapter<Product_recycler_adapter.MyViewHolder> {
    private Context context;
    private List<Product_Grid_Model> models;


    public Product_recycler_adapter(Context context, List<Product_Grid_Model> models) {
        this.context = context;
        this.models = models;
    }

    @Override
    public Product_recycler_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_grid_row, parent, false);

        return new Product_recycler_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Product_recycler_adapter.MyViewHolder holder, final int position) {
        final Product_Grid_Model product_model = models.get(position);

    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_product_image;
        TextView tv_product_name, tv_product_price;
        LinearLayout lv_img_click;
        ProgressBar progress_grid;

        public MyViewHolder(View view) {
            super(view);
            tv_product_name = (TextView) view.findViewById(R.id.tv_product_name);
            tv_product_price = (TextView) view.findViewById(R.id.tv_product_price);


            iv_product_image = (ImageView) view.findViewById(R.id.iv_product_image);


        }
    }
}



