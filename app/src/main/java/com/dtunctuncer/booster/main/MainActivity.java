package com.dtunctuncer.booster.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.dtunctuncer.booster.R;
import com.dtunctuncer.booster.app.AppFragment;
import com.dtunctuncer.booster.notification.DisableModeHelperActivity;
import com.dtunctuncer.booster.rootbooster.RootFragment;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.activity_main)
    CoordinatorLayout activityMain;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.banner)
    AdView banner;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                if (fragment instanceof RootFragment) {
                    ((RootFragment) fragment).clearModes();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initAd();


        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
        }

        initFragments();


    }

    private void initAd() {
        MobileAds.initialize(this, getString(R.string.ad_app_id));
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("2877C940850B2E89FED5B22AC70F79B3")
                .addTestDevice("17833CDB8A54F87C87757BC82886AD07")
                .build();
        banner.loadAd(adRequest);
    }

    private void initFragments() {
        tabs.addTab(tabs.newTab().setText(R.string.apps).setIcon(R.drawable.ic_apps_white_24dp));
        tabs.addTab(tabs.newTab().setText(R.string.root_modes).setIcon(R.drawable.ic_fingerprint_white_24dp));

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new AppFragment());
        adapter.addFragment(new RootFragment());


        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(receiver, new IntentFilter(DisableModeHelperActivity.CLEAR_ACTION));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    @Override
    public void onPause() {
        if (banner != null) {
            banner.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (banner != null) {
            banner.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (banner != null) {
            banner.destroy();
        }
        super.onDestroy();
    }
}