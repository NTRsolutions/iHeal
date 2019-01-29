package com.sismatix.iheal.Fragments;


import android.content.ClipData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import com.sismatix.iheal.Adapter.Cart_List_Adapter;
import com.sismatix.iheal.Model.Cart_Model;
import com.sismatix.iheal.R;

import java.util.ArrayList;
import java.util.List;

import static com.sismatix.iheal.Activity.Navigation_drawer_activity.bottom_navigation;

/**
 * A simple {@link Fragment} subclass.
 */
public class Cart extends Fragment  {
    private Paint p = new Paint();
    private View view;
    private RecyclerView cart_recyclerview;
    private List<Cart_Model> cartList = new ArrayList<Cart_Model>();
    private Cart_List_Adapter cart_adapter;
    Toolbar toolbar;
    ImageView iv_place_order;

    private static final String URL = "https://api.androidhive.info/json/menu.json";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_cart, container, false);
        bottom_navigation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        AllocateMemory(view);

        prepare_Cart();
        init_Swipe_recyclerview();//swiper recyclerview
        iv_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               loadFragment(new Checkout_fragment());
            }
        });


        return view;
    }


    private void loadFragment(Fragment fragment) {
        Log.e("clickone", "");
        android.support.v4.app.FragmentManager manager = getFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.rootLayout, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }
    private void init_Swipe_recyclerview(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT){
                    cart_adapter.removeItem(position);
                }else if(direction == ItemTouchHelper.RIGHT)
                {
                    Toast.makeText(getActivity(), "edit data", Toast.LENGTH_SHORT).show();

                }   else {
                    removeView();
                    /*edit_position = position;
                    alertDialog.setTitle("Edit Country");
                    et_country.setText(countries.get(position));
                    alertDialog.show();*/
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX > 0){
                        p.setColor(Color.parseColor("#3f7a5c"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_create_black_18dp);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);

                    } else {
                        p.setColor(Color.parseColor("#f45d64"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_close_black_18dp);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(cart_recyclerview);
    }
    private void removeView(){
        if(view.getParent()!=null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }
    private void AllocateMemory(View v) {
        cart_recyclerview = v.findViewById(R.id.cart_recyclerview);
        toolbar=(Toolbar)v.findViewById(R.id.toolbar_cart);
        iv_place_order=(ImageView) v.findViewById(R.id.iv_place_order);

        cart_adapter = new Cart_List_Adapter(getActivity(), cartList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        cart_recyclerview.setLayoutManager(mLayoutManager);
        cart_recyclerview.setItemAnimator(new DefaultItemAnimator());
        cart_recyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        cart_recyclerview.setAdapter(cart_adapter);
        }

    /**
     * method make volley network call and parses json
     */
    private void prepare_Cart() {

        for (int i=0;i<4;i++) {
            cartList.add(new Cart_Model("", "",
                    "", ""));
        }
        cart_adapter.notifyDataSetChanged();

      /*  final StringRequest request = new StringRequest(StringRequest.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("res_getlocationwish", "" + response);

                    if (response == null) {
                        Toast.makeText(getActivity(), "Couldn't fetch the menu! Pleas try again.", Toast.LENGTH_LONG).show();
                        return;
                    }

                for (int i=0;i<10;i++) {
                    cartList.add(new Cart_Model("", "",
                            "", ""));
                }
                cart_adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("", "" + error);
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);*/
    }



}
