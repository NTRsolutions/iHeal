package com.sismatix.iheal.Activity;

import android.animation.ArgbEvaluator;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;


import com.sismatix.iheal.Fragments.Account;
import com.sismatix.iheal.Fragments.Cart;
import com.sismatix.iheal.Fragments.EmailLogin;
import com.sismatix.iheal.Fragments.Favourite;
import com.sismatix.iheal.Fragments.Home;
import com.sismatix.iheal.Fragments.Nature_Category_freg;
import com.sismatix.iheal.Fragments.Search;
import com.sismatix.iheal.Preference.Login_preference;
import com.sismatix.iheal.R;
import com.sismatix.iheal.View.CountDrawable;


public class Navigation_drawer_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;
    MenuItem title_account_tools, title_shop_tools;
    SpannableString shop, account;
    LinearLayout lv_withlogin_header,login_navigation;
    String loginflag;
    LinearLayout lv_title;

    //bottom navigation
    private ViewPager viewPager;
    public static BottomNavigationView bottom_navigation;
    private List<View> viewList;
    MenuItem prevMenuItem;

    /*int navDefaultTextColor = Color.parseColor("#ffe5a8");
    int navDefaultIconColor = Color.parseColor("#ffe5a8");*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        loginflag=Login_preference.getLogin_flag(Navigation_drawer_activity.this);
        AllocateMemory();

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        login_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, new EmailLogin()
                ).addToBackStack("Login").commit();
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        Menu menu = navigationView.getMenu();

        title_account_tools = menu.findItem(R.id.title_account_tools);
        title_shop_tools = menu.findItem(R.id.title_shop_tools);
        account = new SpannableString(title_account_tools.getTitle());
        account.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, account.length(), 0);
        title_account_tools.setTitle(account);

        shop = new SpannableString(title_shop_tools.getTitle());
        shop.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, shop.length(), 0);
        title_shop_tools.setTitle(shop);

        disableNavigationViewScrollbars(navigationView);//remove scrollbar
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        ImageView iv_drawer_close = (ImageView) header.findViewById(R.id.iv_drawer_close);
        iv_drawer_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                }
            }
        });


        Bootom_Navigation_view();
    }
    public DrawerLayout getmDrawerLayout() {
        return drawer;
    }

    private void Bootom_Navigation_view() {
        viewPager = findViewById(R.id.view_pager_bottom_navigation);
        bottom_navigation = findViewById(R.id.bottom_navigation);

        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Menu menu = bottom_navigation.getMenu();
        selectFragment(menu.getItem(0));


       /* viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottom_navigation.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                bottom_navigation.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottom_navigation.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }


        });*/

        //setupViewPager(viewPager);
    }
   /* private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new Home());
        viewPagerAdapter.addFragment(new Search());
        viewPagerAdapter.addFragment(new Favourite());
        viewPagerAdapter.addFragment(new Account());
        viewPagerAdapter.addFragment(new Cart());
        viewPager.setAdapter(viewPagerAdapter);
    }*/
    ////////bottom navigation selected listner
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            selectFragment(item);
            return false;
        }
    };

    private void selectFragment(MenuItem item) {

        item.setChecked(true);

        switch (item.getItemId()) {
            case R.id.bottom_nav_home:
                // Action to perform when Home Menu item is selected.
                pushFragment(new Home());
                viewPager.setCurrentItem(0);
                break;
            case R.id.bottom_nav_search:
                pushFragment(new Search());
                viewPager.setCurrentItem(1);
                break;
            case R.id.bottom_nav_favourite:
                pushFragment(new Favourite());
                viewPager.setCurrentItem(2);
                break;
            case R.id.bottom_nav_cart:
                pushFragment(new Cart());
                viewPager.setCurrentItem(3);
                break;
            case R.id.bottom_nav_account:
                pushFragment(new Account());
                viewPager.setCurrentItem(4);
                break;

        }
    }

    private void pushFragment(Fragment fragment) {
        if (fragment == null)
            return;

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.replace(R.id.rootLayout, fragment);
                ft.commit();
            }
        }
    }

    ///remove scrollbar navigationdrawer
    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }

    private void AllocateMemory() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header=navigationView.getHeaderView(0);
        login_navigation = (LinearLayout) findViewById(R.id.login_navigation);
        lv_withlogin_header=(LinearLayout)header.findViewById(R.id.lv_withlogin_header);

        if(loginflag.equalsIgnoreCase("1") || loginflag == "1"){
            lv_withlogin_header.setVisibility(View.GONE);
            login_navigation.setVisibility(View.VISIBLE);
        }else{
            lv_withlogin_header.setVisibility(View.VISIBLE);
            login_navigation.setVisibility(View.GONE);
        }


    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_product_categories) {
            // Handle the camera action

            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.rootLayout, new Nature_Category_freg());
            fragmentTransaction.commit();

        } else if (id == R.id.nav_health_topic) {

        } else if (id == R.id.nav_offers) {

        } else if (id == R.id.nav_reviews) {

        } else if (id == R.id.nav_recipes) {

        } else if (id == R.id.nav_my_account) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.rootLayout, new Account());
            fragmentTransaction.commit();
        } else if (id == R.id.nav_wishlist) {

        } else if (id == R.id.nav_messages) {

        } else if (id == R.id.nav_notification) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
