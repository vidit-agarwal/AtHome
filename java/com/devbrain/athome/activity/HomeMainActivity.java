package com.devbrain.athome.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.devbrain.athome.R;
import com.devbrain.athome.adapter.CategoryGridAdapter;
import com.devbrain.athome.adapter.CategoryPagerAdapter;
import com.devbrain.athome.data.CartConstants;
import com.devbrain.athome.data.CategoryItem;
import com.devbrain.athome.fragment.CategoryFragment;
import com.devbrain.athome.modal.Device;
import com.devbrain.athome.modal.Utility;
import com.devbrain.athome.parser.CategoryJSONParser;
import com.devbrain.athome.rest.NetworkTransaction;
import com.devbrain.athome.rest.NetworkTransactionListener;
import com.devbrain.athome.rest.RestAPIURL;
import com.devbrain.athome.rest.TransactionType;
import com.devbrain.athome.views.CirclePageIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

//    CustomProgressDialog customProgressBar;
//    SharedPreferences sharedPreferences;
//    SharedPreferences.Editor sharedPrefEditor;

    String TAG = "MainActivity";
    static HomeMainActivity mainActivity;
    private boolean _doubleBackToExitPressedOnce = false;
    ArrayList<CategoryItem> categoryItemArrayList;
    ArrayList<CategoryItem> categoryBannerArrayList;
    private Toast toast;
    RelativeLayout cartLayout;
    ViewPager bannerViewPager;
    GridView categoryGridView;

    CategoryFragment categoryFragment;
    private static final long SCREEN_DELAY = 3000L;
    private Handler mHandler;
    private boolean mPageScolling = false;
    CirclePageIndicator pagerIndicator;
    CategoryGridAdapter categoryGridAdapter;
    CategoryPagerAdapter bannerPagerAdapter;
    String name, email, mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mainActivity = this;
        cartLayout = (RelativeLayout) findViewById(R.id.cart_layout);
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        cartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeMainActivity.this, CartConfirmActivity.class);
                startActivityForResult(intent, CartConstants.SHOW_CART_REQUEST_CODE);
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (name == null || email == null || mobile == null)
                    getUserDetails(HomeMainActivity.this, drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        categoryItemArrayList = new ArrayList<>();
        categoryBannerArrayList = new ArrayList<>();
        categoryFragment = new CategoryFragment();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        LinearLayout linearLayout = (LinearLayout) navigationView.findViewById(R.id.profile_layout);
//        linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(HomeMainActivity.this, RegistrationActivity.class));
//            }
//        });

        mHandler = new Handler();
        bannerViewPager = (ViewPager) findViewById(R.id.category_pager);
        pagerIndicator = (CirclePageIndicator) findViewById(R.id.pager_indicator);
        bannerPagerAdapter = new CategoryPagerAdapter(HomeMainActivity.this, categoryBannerArrayList);
        bannerViewPager.setAdapter(bannerPagerAdapter);
        bannerPagerAdapter.setCategoryFragment(categoryFragment);
        pagerIndicator.setViewPager(bannerViewPager);

        categoryGridAdapter = new CategoryGridAdapter(HomeMainActivity.this, categoryItemArrayList);
        categoryGridView = (GridView) findViewById(R.id.category_grid);
        categoryGridView.setAdapter(categoryGridAdapter);

        sendCategoryRequest(HomeMainActivity.this);
        sendBannerRequest(HomeMainActivity.this);

        categoryGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                CategoryFragment categoryFragment=new CategoryFragment();
                CategoryItem categoryItem = categoryGridAdapter.getItem(i);
                Bundle bundle = new Bundle();
                bundle.putInt(CategoryItem.CAT_ID, categoryItem.getCategoryId());
                categoryFragment.setArguments(bundle);
                addFragmentToBackStack(categoryFragment, getResources().getString(R.string.app_name));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        resumeSlideShow();
    }

    @Override
    public void onPause() {
        super.onPause();

        pauseSlideShow();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager manager = mainActivity.getSupportFragmentManager();
            if (manager.getBackStackEntryCount() > 0) {
                manager.popBackStackImmediate();
                if (fragmentArrayList.size() > -1) {
                    fragmentArrayList.remove(fragmentArrayList.size() - 1);
                    fragmentNameList.remove(fragmentNameList.size() - 1);
                }
                if (fragmentNameList.size() > 0) {
                    setTitle(fragmentNameList.get(fragmentNameList.size() - 1));
                } else {
                    setTitle(getString(R.string.app_name));
                }
            } else {
                if (_doubleBackToExitPressedOnce) {
                    super.onBackPressed();
//                    fragmentArrayList.remove(0);

                    return;
                }
                this._doubleBackToExitPressedOnce = true;
                toast.setText("Press Again to Exit");
                toast.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        _doubleBackToExitPressedOnce = false;

                    }
                }, 2000);
//                super.onBackPressed();
            }
        }


    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_current_order) {
            startActivity(new Intent(HomeMainActivity.this, CurrentOrderListActivity.class));
        } else if (id == R.id.nav_order_history) {
            startActivity(new Intent(HomeMainActivity.this, OrderHistoryListActivity.class));
        } else if (id == R.id.nav_share) {
            shareThisApplication(HomeMainActivity.this);
        } else if (id == R.id.nav_term_cond) {
            startActivity(new Intent(HomeMainActivity.this, TNCActivity.class));

        } else if (id == R.id.nav_about) {
            startActivity(new Intent(HomeMainActivity.this, AboutUsActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void shareThisApplication(Context context) {
        Intent sharingIntent = null;
        StringBuilder sbMsg = null;

        try {
            sbMsg = new StringBuilder();
            sbMsg.append("Hi \n I've tried this Grocery Ordering app. You can also try this out and easy your lifestyle.\n");
            sbMsg.append("https://play.google.com/store/apps/details?id=" + context.getPackageName());

            sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, sbMsg.toString());
            context.startActivity(Intent.createChooser(sharingIntent, "Share Using"));
        } catch (Exception e) {
//            App.getInstance().trackException(TAG + " shareThisApplication()", e);
        } finally {
            sharingIntent = null;
            sbMsg = null;
        }
    }

    private void sendCategoryRequest(final Context context) {
//        customProgressBar = new CustomProgressDialog(this);
//        if (!customProgressBar.isShowing()) {
//            customProgressBar.show();
//        }
        JSONObject jObjDeviceInfo = Device.getInstance(context).getDeviceIdentityJSON();
//        try {
//            jObjDeviceInfo.put(APP_ID, "1");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        NetworkTransaction.getInstance(context).ProcessRequest(TransactionType.POST, RestAPIURL.CATEGORIES_URL, jObjDeviceInfo, new NetworkTransactionListener<String>() {
            @Override
            public void onSuccess(String object, Object obj) {
                Utility.printLog("onSuccess: " + object);
//                Toast.makeText(context, object, Toast.LENGTH_LONG).show();
                CategoryJSONParser categoryJSONParser = new CategoryJSONParser(object);
                categoryItemArrayList.addAll(categoryJSONParser.getCategoryArrayList());
                CartConstants.setCategoryItemArrayList(categoryItemArrayList);
                categoryGridAdapter.notifyDataSetChanged();
//                if (customProgressBar != null && customProgressBar.isShowing()) {
//                    customProgressBar.dismiss();
//                }
//                sharedPrefEditor.putString(CategoryJSONParser.JSON_CATEGORIES, object);
//                sharedPrefEditor.commit();
            }

            @Override
            public void onFail(String object, Object obj) {
                Utility.printLog("onFail: " + object);
                Toast.makeText(context, object, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onExist(String object, Object obj) {
                Utility.printLog("onExist: " + object);
                Toast.makeText(context, object, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNetworkError(String object, Object obj) {
                Utility.printLog("onNetworkError: " + object);
                Toast.makeText(context, object, Toast.LENGTH_LONG).show();

            }
        });

    }

    private void sendBannerRequest(final Context context) {
//        customProgressBar = new CustomProgressDialog(this);
//        if (!customProgressBar.isShowing()) {
//            customProgressBar.show();
//        }
        JSONObject jObjDeviceInfo = Device.getInstance(context).getDeviceIdentityJSON();
//        try {
//            jObjDeviceInfo.put(APP_ID, "1");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        NetworkTransaction.getInstance(context).ProcessRequest(TransactionType.POST, RestAPIURL.BANNERS_URI, jObjDeviceInfo, new NetworkTransactionListener<String>() {
            @Override
            public void onSuccess(String object, Object obj) {
                Utility.printLog("onSuccess: " + object);
//                Toast.makeText(context, object, Toast.LENGTH_LONG).show();
                CategoryJSONParser categoryJSONParser = new CategoryJSONParser(object);
                categoryBannerArrayList.addAll(categoryJSONParser.getCategoryArrayList());
                CartConstants.setBannerItemArrayList(categoryBannerArrayList);
                bannerPagerAdapter.notifyDataSetChanged();
//                if (customProgressBar != null && customProgressBar.isShowing()) {
//                    customProgressBar.dismiss();
//                }
//                sharedPrefEditor.putString(CategoryJSONParser.JSON_CATEGORIES, object);
//                sharedPrefEditor.commit();
            }

            @Override
            public void onFail(String object, Object obj) {
                Utility.printLog("onFail: " + object);
                Toast.makeText(context, object, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onExist(String object, Object obj) {
                Utility.printLog("onExist: " + object);
                Toast.makeText(context, object, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNetworkError(String object, Object obj) {
                Utility.printLog("onNetworkError: " + object);
                Toast.makeText(context, object, Toast.LENGTH_LONG).show();

            }
        });

    }


    static ArrayList<String> fragmentArrayList = new ArrayList<String>();
    static ArrayList<String> fragmentNameList = new ArrayList<String>();

    public static void addFragmentToBackStack(Fragment fragment, String title) {
        try {
            String backStateName = fragment.getClass().getName();
            FragmentManager manager = mainActivity.getSupportFragmentManager();

            if (fragmentArrayList.contains(backStateName)) {
                for (int i = fragmentArrayList.size() - 1; i > 0; i--) {
                    if (!fragmentArrayList.get(i).equalsIgnoreCase(backStateName)) {
                        manager.popBackStackImmediate();
                        manager.popBackStack();
                        fragmentArrayList.remove(i);
                    } else if (fragmentArrayList.get(i).equalsIgnoreCase(backStateName)) {
                        break;
                    } else {
                        manager.popBackStackImmediate();
                        break;
                    }
                }
            } else {
                FragmentTransaction ft = manager.beginTransaction();
                ft.add(R.id.container_body, fragment, backStateName);
                /*if(fragmentArrayList.size() > 0)
                {
                    ft.addToBackStack(backStateName);
                }*/
                ft.addToBackStack(backStateName);
                fragmentArrayList.add(backStateName);
                fragmentNameList.add(title);
                ft.commit();
            }
            setTitle(title);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setTitle(String title) {

        TextView titleTextView = (TextView) mainActivity.findViewById(R.id.title_view);
        if (title != null)
            titleTextView.setText(title);
        else
            titleTextView.setText(mainActivity.getString(R.string.app_name));
    }

    public static void setCartItemCount() {
        int count = CartConstants.cartItemsArrayList.size();
        TextView countTextView = (TextView) mainActivity.findViewById(R.id.count_textView);
        countTextView.setText(String.valueOf(count));
    }

    private void getUserDetails(final Context context, final View drawerView) {
        JSONObject jObjDeviceInfo = Device.getInstance(context).getDeviceRegistrationJSON();

        NetworkTransaction.getInstance(context).ProcessRequest(TransactionType.POST, RestAPIURL.USER_DETAILS_URL, jObjDeviceInfo, new NetworkTransactionListener<String>() {
            @Override
            public void onSuccess(String object, Object obj) {
                Utility.printLog("onSuccess: " + object);
                try {
                    JSONObject jsonObject = new JSONObject(object);
//                    setAddressString(jsonObject.getString("address"));
                    name = jsonObject.getString("name");
                    email = jsonObject.getString("emailID");
                    mobile = jsonObject.getString("contactNumber");
                    setUserDetails(drawerView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Toast.makeText(context, object, Toast.LENGTH_LONG).show();
//                sharedPrefEditor.putString(CategoryJSONParser.JSON_CATEGORIES, object);
//                sharedPrefEditor.commit();
            }

            @Override
            public void onFail(String object, Object obj) {
                Utility.printLog("onFail: " + object);
                Toast.makeText(context, object, Toast.LENGTH_LONG).show();
                setResult(RESULT_CANCELED);
            }

            @Override
            public void onExist(String object, Object obj) {
                Utility.printLog("onExist: " + object);
                Toast.makeText(context, object, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNetworkError(String object, Object obj) {
                Utility.printLog("onNetworkError: " + object);
                Toast.makeText(context, object, Toast.LENGTH_LONG).show();

            }
        });

    }

    public void setUserDetails(View view) {

        TextView nameView = (TextView) view.findViewById(R.id.tv_name);
        TextView emailView = (TextView) view.findViewById(R.id.tv_email);
        TextView mobileView = (TextView) view.findViewById(R.id.tv_mobile);

        nameView.setText(name);
        emailView.setText(email);
        mobileView.setText(mobile);
    }

    private void resumeSlideShow() {
        mHandler.postDelayed(runnSlideShow, SCREEN_DELAY);
    }

    private void pauseSlideShow() {
        mHandler.removeCallbacks(runnSlideShow);
    }

    private Runnable runnSlideShow = new Runnable() {

        @Override
        public void run() {
            if (!mPageScolling) {
                int currentItem = bannerViewPager.getCurrentItem();
                currentItem++;
                if (currentItem >= bannerViewPager.getAdapter().getCount()) {
                    currentItem = 0;
                }
                bannerViewPager.setCurrentItem(currentItem, true);
            }

            mHandler.postDelayed(runnSlideShow, SCREEN_DELAY);
        }
    };

}
