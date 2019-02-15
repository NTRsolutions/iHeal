package com.sismatix.iheal.Activity;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.sismatix.iheal.Fragments.Account;
import com.sismatix.iheal.Fragments.Cart;
import com.sismatix.iheal.Fragments.EmailLogin;
import com.sismatix.iheal.Fragments.Favourite;
import com.sismatix.iheal.Fragments.Home;
import com.sismatix.iheal.Fragments.MyOrder;
import com.sismatix.iheal.Fragments.MyOrderDetails;
import com.sismatix.iheal.Fragments.Nature_Category_freg;
import com.sismatix.iheal.Fragments.Search;
import com.sismatix.iheal.Fragments.Wishlist_fragment;
import com.sismatix.iheal.Preference.Login_preference;
import com.sismatix.iheal.R;
import com.sismatix.iheal.Retrofit.ApiClient;
import com.sismatix.iheal.Retrofit.ApiInterface;
import com.sismatix.iheal.View.CountDrawable;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Navigation_drawer_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

  public static DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;
    MenuItem title_account_tools, title_shop_tools;
    SpannableString shop, account;
    LinearLayout lv_withlogin_header, login_navigation,lv_logout;
    String loginflag;
    public  static TextView tv_navidrawer,item_count;

    //bottom navigation
    private ViewPager viewPager;
    public static BottomNavigationView bottom_navigation;
    private List<View> viewList;
    boolean doubleBackToExitPressedOnce = false;
    /*int navDefaultTextColor = Color.parseColor("#ffe5a8");
    int navDefaultIconColor = Color.parseColor("#ffe5a8");*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        loginflag = Login_preference.getLogin_flag(Navigation_drawer_activity.this);
        AllocateMemory();

        login_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, new EmailLogin()
                ).addToBackStack("Login").commit();
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        //  toggle.syncState();

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


        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                } else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }
            }
        });


        lv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login_preference.setLogin_flag(Navigation_drawer_activity.this,"0");
                Intent intent=new Intent(Navigation_drawer_activity.this,Navigation_drawer_activity.class);
                startActivity(intent);


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
    }

    ////////bottom navigation selected listner
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            selectFragment(item);


           /* switch (item.getItemId()) {
                case R.id.bottom_nav_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.bottom_nav_search:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.bottom_nav_favourite:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.bottom_nav_cart:
                    viewPager.setCurrentItem(3);
                    return true;
                case R.id.bottom_nav_account:
                    viewPager.setCurrentItem(4);
                    return true;
            }*/
            return false;
        }
    };

    private void selectFragment(MenuItem item) {

        item.setChecked(true);

        switch (item.getItemId()) {
            case R.id.bottom_nav_home:
                // Action to perform when Home Menu item is selected.
                pushFragment(new Home(),"Home_fragment");
                viewPager.setCurrentItem(0);
                break;
            case R.id.bottom_nav_search:
                pushFragment(new MyOrder(),"Search_fragment");//Search
                viewPager.setCurrentItem(1);
                break;
            case R.id.bottom_nav_Wishlist:
                pushFragment(new Wishlist_fragment(),"Wishlist_fragment");
                viewPager.setCurrentItem(2);
                break;
            case R.id.bottom_nav_cart:
                pushFragment(new Cart(),null);
                viewPager.setCurrentItem(3);
                break;
            case R.id.bottom_nav_account:
                pushFragment(new Account(),"Account_fragment");
                viewPager.setCurrentItem(4);
                break;
        }
    }
    private void pushFragment(Fragment fragment,String add_to_backstack) {
        if (fragment == null)
            return;
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.replace(R.id.rootLayout, fragment);
                ft.addToBackStack(add_to_backstack);
                ft.commit();
            }
        }
    }

    private PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }
    };
    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            ArgbEvaluator evaluator = new ArgbEvaluator();
          /*  int evaluate = getResources().getColor(R.color.app_blue);
            if (position == 0) {
                evaluate = (Integer) evaluator.evaluate(positionOffset, getResources().getColor(R.color.app_blue), getResources().getColor(R.color.app_green));
            } else if (position == 1) {
                evaluate = (Integer) evaluator.evaluate(positionOffset, getResources().getColor(R.color.app_green), getResources().getColor(R.color.app_yellow));
            } else if (position == 2) {
                evaluate = (Integer) evaluator.evaluate(positionOffset, getResources().getColor(R.color.app_yellow), getResources().getColor(R.color.app_red));
            } else {
                evaluate = getResources().getColor(R.color.app_red);
            }*/
            // ((View) viewPager.getParent()).setBackgroundColor(evaluate);
        }
        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    bottom_navigation.setSelectedItemId(R.id.bottom_nav_home);
                    break;
                case 1:
                    bottom_navigation.setSelectedItemId(R.id.bottom_nav_search);
                    break;
                case 2:
                    bottom_navigation.setSelectedItemId(R.id.bottom_nav_Wishlist);

                    break;
                case 3:
                    bottom_navigation.setSelectedItemId(R.id.bottom_nav_cart);
                    break;
                case 4:
                    bottom_navigation.setSelectedItemId(R.id.bottom_nav_account);
                    break;
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }

    private void AllocateMemory() {
        //set bydefault itemcount
        Login_preference.setCart_item_count(Navigation_drawer_activity.this,"0");

       toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        login_navigation = (LinearLayout) findViewById(R.id.login_navigation);
        lv_logout = (LinearLayout) findViewById(R.id.lv_logout);
        tv_navidrawer=(TextView)header.findViewById(R.id.tv_navidraweremail);
        item_count=(TextView)header.findViewById(R.id.item_count);
        lv_withlogin_header = (LinearLayout) header.findViewById(R.id.lv_withlogin_header);

        ///menu in login&logout opetionshow
        if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {
            lv_withlogin_header.setVisibility(View.VISIBLE);
            login_navigation.setVisibility(View.GONE);
            lv_logout.setVisibility(View.VISIBLE);
        } else {
            tv_navidrawer.setText(Login_preference.getemail(Navigation_drawer_activity.this));
            lv_withlogin_header.setVisibility(View.GONE);
            login_navigation.setVisibility(View.VISIBLE);
            lv_logout.setVisibility(View.GONE);
        }
        ///menu icon change
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_dehaze_white_36dp);


    }
/*
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
     /*   MenuItem item = menu.findItem(R.id.cart);

        LayerDrawable icon = (LayerDrawable) item.getIcon();

        CountDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_group_count);
        if (reuse != null && reuse instanceof CountDrawable) {
            badge = (CountDrawable) reuse;
        } else {
            badge = new CountDrawable(Navigation_drawer_activity.this);
        }

        badge.setCount("1");
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_group_count, badge);
*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

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

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.rootLayout, new Nature_Category_freg());
            fragmentTransaction.commit();

        }/* else if (id == R.id.nav_health_topic) {

        } else if (id == R.id.nav_offers) {

        } else if (id == R.id.nav_reviews) {

        }*/ else if (id == R.id.nav_recipes) {

        }
        else if (id == R.id.nav_my_order) {

        }
        else if (id == R.id.nav_my_account) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.rootLayout, new Account());
            fragmentTransaction.commit();
        } else if (id == R.id.nav_wishlist) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.rootLayout, new Wishlist_fragment());
            fragmentTransaction.commit();

        } else if (id == R.id.nav_messages) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.rootLayout, new MyOrderDetails());
            fragmentTransaction.commit();
        } else if (id == R.id.nav_notification) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    ///////////
    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int count = fragmentManager.getBackStackEntryCount();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

         if (count == 1) {
             if (doubleBackToExitPressedOnce) {
                 super.onBackPressed();
                 super.finish();
                 return;
             }
             this.doubleBackToExitPressedOnce = true;
             Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
             new Handler().postDelayed(new Runnable() {
                 @Override
                 public void run() {
                     doubleBackToExitPressedOnce = false;
                 }
             }, 2000);
        } else {
            String title = fragmentManager.getBackStackEntryAt(count - 2).getName();
            super.onBackPressed();
            Log.e("onBackPressetitle", "" + title);
           // tv_title.setText(title);
        }
        //doExitApp();
    }
    private long exitTime = 0;
    public void doExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(Navigation_drawer_activity.this,
                    "Please click BACK again to exit",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

}
