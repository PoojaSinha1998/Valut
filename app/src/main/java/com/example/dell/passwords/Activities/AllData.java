package com.example.dell.passwords.Activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dell.passwords.AdapterClass.AllRecordeAdapter;
import com.example.dell.passwords.ApplicationClass;
import com.example.dell.passwords.Common.Constants;
import com.example.dell.passwords.Common.ExportIntoExcel;
import com.example.dell.passwords.Common.myCommonVariables;
import com.example.dell.passwords.Dailogs.customDailog;
import com.example.dell.passwords.DatabaseHandler.DBHelper;
import com.example.dell.passwords.Fragments.All_Data_fragment;
import com.example.dell.passwords.Fragments.ChangeMyPin;
import com.example.dell.passwords.Fragments.ChangePassword;
import com.example.dell.passwords.Fragments.ChangePin;
import com.example.dell.passwords.Fragments.ExportToExcel;
import com.example.dell.passwords.Listeners.ConfirmationListener;
import com.example.dell.passwords.Listeners.HomePageListener;
import com.example.dell.passwords.Listeners.itemClickListener;
import com.example.dell.passwords.Pojo.RecordsPojo;
import com.example.dell.passwords.R;

import java.util.ArrayList;

public class AllData extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener,HomePageListener {

    boolean homeFragment = true;
    DrawerLayout drawer;
    ListView mDrawerList;
    AlertDialog dialog;
    private ActionBar actionBar;
    Bundle pBundle = new Bundle();
    private ActionBarDrawerToggle toggle;
    FloatingActionButton fab;
    RecyclerView mrecyclerView;
    DBHelper dbHelper;
    itemClickListener itemClick;
    AllRecordeAdapter allRecordeAdapter;
    boolean yes=false;
    String keyVal = "EMPTY";
    String tag = "HOME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_data);
        dbHelper = new DBHelper(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.setDrawerListener(toggle);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        actionBar = getSupportActionBar();
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);



        setUpFragments();
    }

    private void setUpFragments() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment homeFragment = fm.findFragmentByTag(tag);

        if (homeFragment == null) {
            FragmentTransaction ft = fm.beginTransaction();
            homeFragment = new All_Data_fragment();
            pBundle.putString("pooja", keyVal);
            homeFragment.setArguments(pBundle);
            ft.add(R.id.am_full_container, homeFragment, tag);
            ft.commit();
        }
    }


    @Override
    public void onBackPressed() {

        toggle.setDrawerIndicatorEnabled(true);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            if(homeFragment)
                callAlert();
            else {
                actionBar.setDisplayHomeAsUpEnabled(false);
                switchFragment(Constants.HOME_FRAGMENT,null);
                toggle.syncState();
            }
        }
    }

    public void callAlert() {
        Log.d("db", "logout");

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
        View mView = getLayoutInflater().inflate(R.layout.alert_dialog, null);
        TextView i_yes = mView.findViewById(R.id.dai_yes);
        TextView i_no = mView.findViewById(R.id.dai_no);


        i_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                finish();

            }
        });
        i_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

            }
        });

        mBuilder.setView(mView);
        dialog = mBuilder.create();

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        int frag_id = 0;
        switch (id) {
            case R.id.c_password:
                //   Toast.makeText(MainActivity.this, "Alphabates", Toast.LENGTH_SHORT).show();
                frag_id = Constants.CHANGE_PASSWORD;
                break;
            case R.id.c_pin:
                //  Toast.makeText(MainActivity.this, "Table", Toast.LENGTH_SHORT).show();
                frag_id = Constants.CHANGE_PIN;
                break;
//            case R.id.c_export:
//                //  Toast.makeText(MainActivity.this, "Table", Toast.LENGTH_SHORT).show();
//                frag_id = Constants.EXPORT_EXCEL;
//                break;
        }
        if (frag_id != 0)
            switchFragment(frag_id, null);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void switchFragment(int fragment_id, Bundle pBundle) {
        boolean addFragment = false;
        Fragment normFragment = null;
        FragmentManager fm = getSupportFragmentManager();

        String tag = "";
        String title = getString(R.string.app_name);

        // Default value for all fragments except home fragment
        homeFragment = false;

        switch (fragment_id) {
            case Constants.HOME_FRAGMENT:            // Home Fragment
                tag = "HOME";
                normFragment = fm.findFragmentByTag(tag);
                if (normFragment == null) {
                    normFragment = new All_Data_fragment();
                    addFragment = true;
                    Log.d("TAG","HOME FRAGEMNT");
                }
                homeFragment = true;
                break;
            case Constants.CHANGE_PASSWORD:
                tag="NUMBERS";
                normFragment=fm.findFragmentByTag(tag);
                if(normFragment==null)
                {
                    normFragment=new ChangePassword();
                    addFragment=true;
                }
                homeFragment=true;
                break;

            case Constants.CHANGE_PIN:
                tag="NUMBERS";
                normFragment=fm.findFragmentByTag(tag);
                if(normFragment==null)
                {
                    normFragment=new ChangeMyPin();
                    addFragment=true;
                }
                homeFragment=true;
                break;
//            case Constants.EXPORT_EXCEL:
////                tag="EXPORT";
////                normFragment=fm.findFragmentByTag(tag);
////                if(normFragment==null)
////                {
////                    normFragment=new ExportToExcel();
////                    addFragment=true;
////                }
////                homeFragment=true;
//                new ExportIntoExcel(getApplicationContext());
//                break;
            default:
                homeFragment=true;
        }
        FragmentTransaction ft = fm.beginTransaction();
        if (normFragment != null) {
            if (addFragment) {
                if (pBundle != null) {
                    normFragment.setArguments(pBundle);
                    Log.d("VT", "Adding bundle");
                }
                ft.add(R.id.am_full_container, normFragment, tag);
                Log.d("VT", "Adding Fragment: " + tag);
            } else {
                ft.replace(R.id.am_full_container, normFragment, tag);
                Log.d("VT", "Replacing Fragment: " + tag);
            }
            ft.commit();
        }
        if (!homeFragment)
            resetHomePage();

        // set the toolbar title to the fragment title
//        actionBar.setTitle(title);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void resetHomePage() {
        homeFragment = false;
        toggle.setDrawerIndicatorEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }


}
