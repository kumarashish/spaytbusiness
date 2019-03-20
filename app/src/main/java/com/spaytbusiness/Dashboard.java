package com.spaytbusiness;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import fragments.Locations;
import fragments.Notifications;
import fragments.Offers;
import fragments.Products;
import fragments.Business;
import fragments.ProfileSettings;
import fragments.Transactions;
import fragments.Users;
import utils.Utils;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.transactions)
    View transactions;
    @BindView(R.id.notifications)
    View notifications;
    @BindView(R.id.locations)
    View locations;
    @BindView(R.id.products)
    View products;
    @BindView(R.id.offers)
    View offers;
    @BindView(R.id.business_settings)
    View businessSettings;
    @BindView(R.id.profile_settings)
    View profileSettings;
    @BindView(R.id.logout)
    View logout;
    @BindView(R.id.manage_users)
    View manageUsers;
    @BindView(R.id.app_bar_dashboard)
    View appBardDashboard;
    @BindView(R.id.close)
    Button close;
    View contentDahboard;
    FrameLayout framelayout;
    DrawerLayout drawer;
    Toolbar toolbar;
    AppController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);
        controller=(AppController)getApplicationContext();
       toolbar = (Toolbar) findViewById(R.id.toolbar);
        ButterKnife.bind(this);
        toolbar.setTitle("Transactions");
        setSupportActionBar(toolbar);
        contentDahboard=(View)appBardDashboard.findViewById(R.id.contentDashboar);
        framelayout=(FrameLayout)contentDahboard.findViewById(R.id.fragment);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        transactions.setOnClickListener(this);
        notifications.setOnClickListener(this);
        locations.setOnClickListener(this);
        products.setOnClickListener(this);
        offers.setOnClickListener(this);
        businessSettings.setOnClickListener(this);
        profileSettings.setOnClickListener(this);
        logout.setOnClickListener(this);
        close.setOnClickListener(this);
        manageUsers.setOnClickListener(this);
        showFragment(new Transactions());



    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void showFragment(Fragment fragment) {
        android.support.v4.app.FragmentTransaction mTransactiont = getSupportFragmentManager().beginTransaction();
        mTransactiont.replace(framelayout.getId(), fragment, fragment.getClass().getName());
        mTransactiont.commit();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.transactions:
            showFragment(new Transactions());
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
                getSupportActionBar().setTitle("Transactions");

            break;
            case R.id.profile_settings:

                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                getSupportActionBar().setTitle("Profile Settings");
                showFragment(new ProfileSettings());
                break;
            case R.id.offers:
                showFragment(new Offers());
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                getSupportActionBar().setTitle("Offers");
                break;
            case R.id.products:
                showFragment(new Products());
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                getSupportActionBar().setTitle("Products");
                break;
            case R.id.manage_users:
                showFragment(new Users());
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                getSupportActionBar().setTitle("Manage Users");
                break;
            case R.id.locations:
                showFragment(new Locations());
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                getSupportActionBar().setTitle("Locations");
                break;
            case R.id.business_settings:
                showFragment(new Business());
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                getSupportActionBar().setTitle("Business Settings");
                break;


            case R.id.notifications:
                showFragment(new Notifications());
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                getSupportActionBar().setTitle("Notifications");
                break;

            case R.id.close:
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    }
                    break;
            case R.id.logout:
                controller.logout();
                Utils.logout(Dashboard.this);
                finish();

                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

}
