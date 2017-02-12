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
import android.widget.Toast;

import com.dtunctuncer.booster.R;
import com.dtunctuncer.booster.app.AppFragment;
import com.dtunctuncer.booster.notification.DisableModeHelperActivity;
import com.dtunctuncer.booster.rootbooster.RootFragment;

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

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
        }

        initFragments();

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
}