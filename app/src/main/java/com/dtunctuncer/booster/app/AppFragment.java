package com.dtunctuncer.booster.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dtunctuncer.booster.App;
import com.dtunctuncer.booster.R;
import com.dtunctuncer.booster.model.AppInfo;
import com.dtunctuncer.booster.utils.RxBus;
import com.dtunctuncer.booster.utils.analytics.AnalyticsUtils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppFragment extends Fragment implements IAppView {

    @Inject
    AppPresenter presenter;
    @Inject
    RxBus rxBus;

    @BindView(R.id.appRecyclerView)
    RecyclerView appRecyclerView;

    private InterstitialAd interstitialAd;
    private AppInfo appInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app, container, false);
        ButterKnife.bind(this, view);
        DaggerAppComponent.builder().applicationComponent(App.getApplicationComponent()).appModule(new AppModule(this)).build().inject(this);
        presenter.subscribe();
        presenter.getApplicationList();
        initAd();
        return view;
    }

    private void initAd() {
        interstitialAd = new InterstitialAd(getContext());
        interstitialAd.setAdUnitId(getString(R.string.interstial_ad_unit_id));

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                if (appInfo != null) {
                    presenter.openApp(appInfo);
                    getActivity().finish();
                }

            }
        });

        requestNewInterstitial();
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("2877C940850B2E89FED5B22AC70F79B3")
                .addTestDevice("17833CDB8A54F87C87757BC82886AD07")
                .build();

        interstitialAd.loadAd(adRequest);
    }


    @Override
    public void onResume() {
        super.onResume();
        AnalyticsUtils.trackScreenView("AppFragment", getActivity());
    }

    @Override
    public void showApplications(List<AppInfo> appInfoList) {
        AppAdapter appAdapter = new AppAdapter(appInfoList, getActivity(), rxBus);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        appRecyclerView.setLayoutManager(manager);
        appRecyclerView.setAdapter(appAdapter);
    }

    @Override
    public void startBoostingProgress(AppInfo appInfo) {
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            presenter.openApp(appInfo);
            getActivity().finish();
        }
        this.appInfo = appInfo;
    }

    @Override
    public void onDestroy() {
        presenter.unsunbscribe();
        super.onDestroy();
    }
}