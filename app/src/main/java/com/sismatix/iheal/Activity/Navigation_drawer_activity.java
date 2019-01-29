package com.sismatix.iheal.Activity;

import android.animation.ArgbEvaluator;
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

import java.util.List;

import com.sismatix.iheal.Fragments.Account;
import com.sismatix.iheal.Fragments.Cart;
import com.sismatix.iheal.Fragments.Favourite;
import com.sismatix.iheal.Fragments.Home;
import com.sismatix.iheal.Fragments.Nature_Category_freg;
import com.sismatix.iheal.Fragments.Search;
import com.sismatix.iheal.R;


public class Navigation_drawer_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;
    MenuItem title_account_tools, title_shop_tools;
    SpannableString shop, account;

    //bottom navigation
    private ViewPager viewPager;
    public static BottomNavigationView bottom_navigation;
    private List<View> viewList;

    /*int navDefaultTextColor = Color.parseColor("#ffe5a8");
    int navDefaultIconColor = Color.parseColor("#ffe5a8");*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        AllocateMemory();

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        /*int color = 0;
        ColorStateList navMenuTextList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{android.R.attr.state_enabled},
                        new int[]{android.R.attr.state_pressed},
                        new int[]{android.R.attr.state_focused},
                        new int[]{android.R.attr.state_pressed}
                },
                new int[] {
                        color,
                        navDefaultTextColor,
                        navDefaultTextColor,
                        navDefaultTextColor,
                        navDefaultTextColor
                }
        );

        //Defining ColorStateList for menu item Icon
        ColorStateList navMenuIconList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{android.R.attr.state_enabled},
                        new int[]{android.R.attr.state_pressed},
                        new int[]{android.R.attr.state_focused},
                        new int[]{android.R.attr.state_pressed}
                },
                new int[] {
                        color,
                        navDefaultIconColor,
                        navDefaultIconColor,
                        navDefaultIconColor,
                        navDefaultIconColor
                }
        );

        navigationView.setItemTextColor(navMenuTextList);
        navigationView.setItemIconTintList(navMenuIconList);*/

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
       /* View view1 = getLayoutInflater().inflate(R.layout.item_view_pager_1, null);
        View view2 = getLayoutInflater().inflate(R.layout.item_view_pager_2, null);
        View view3 = getLayoutInflater().inflate(R.layout.item_view_pager_3, null);
        View view4 = getLayoutInflater().inflate(R.layout.item_view_pager_4, null);
*/
       /* viewList = new ArrayList<>();
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);


        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(pageChangeListener);*/

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
                    bottom_navigation.setSelectedItemId(R.id.bottom_nav_favourite);
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
        getMenuInflater().inflate(R.menu.navigation_drawer_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

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
