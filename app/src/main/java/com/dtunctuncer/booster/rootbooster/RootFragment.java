package com.dtunctuncer.booster.rootbooster;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dtunctuncer.booster.R;
import com.dtunctuncer.booster.core.BoosterModes;
import com.dtunctuncer.booster.model.RootMode;
import com.dtunctuncer.booster.utils.SpUtils;
import com.dtunctuncer.booster.utils.analytics.AnalyticsUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RootFragment extends Fragment implements IRootView, RootAdapterListener {


    @BindView(R.id.rootErrorText)
    TextView rootErrorText;
    @BindView(R.id.rootModeRecyclerView)
    RecyclerView rootModeRecyclerView;

    private RootPresenter presenter;
    private SpUtils dataManager;
    private ProgressDialog dialog;
    private RootAdapter rootAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_root, container, false);
        ButterKnife.bind(this, view);
        this.dataManager = new SpUtils(getActivity().getApplicationContext());
        this.presenter = new RootPresenter(this, getContext());

        presenter.checkRoot();
        presenter.getBoosterModes();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        AnalyticsUtils.trackScreenView("RootFragment", getActivity());
    }

    @Override
    public void closeRootError() {
        rootErrorText.setVisibility(View.GONE);
    }

    @Override
    public void showBoosterMode(List<RootMode> rootModes) {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rootAdapter = new RootAdapter(rootModes, getActivity());
        rootAdapter.setListener(this);
        rootModeRecyclerView.setLayoutManager(manager);
        rootModeRecyclerView.setAdapter(rootAdapter);
    }

    @Override
    public void startBoost() {
        if (dialog != null && !dialog.isShowing()) {
            showProgres();
        } else if (dialog == null && dataManager.getCurrentMode() == BoosterModes.NO_MODE) {
            showProgres();
        }
    }

    public void clearModes() {
        rootAdapter.clearModes();
    }

    private void showProgres() {
        dialog = new ProgressDialog(getContext());
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle(getString(R.string.dialog_title));
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setProgress(0);
        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i <= 9; i++) {
                    try {
                        Thread.sleep(300);
                        dialog.incrementProgressBy(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                dialog.dismiss();
            }
        }).start();
        dialog.show();
    }
}