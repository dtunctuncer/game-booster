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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app, container, false);
        ButterKnife.bind(this, view);
        DaggerAppComponent.builder().applicationComponent(App.getApplicationComponent()).appModule(new AppModule(this)).build().inject(this);
        presenter.subscribe();
        presenter.getApplicationList();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        AnalyticsUtils.trackScreenView("AppFragment", getActivity());
    }

    @Override
    public void showApplications(List<AppInfo> appInfoList) {
        AppAdapter appAdapter = new AppAdapter(appInfoList, getActivity(), rxBus);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        appRecyclerView.setLayoutManager(manager);
        appRecyclerView.setAdapter(appAdapter);
    }

    @Override
    public void onDestroy() {
        presenter.unsunbscribe();
        super.onDestroy();
    }
}
