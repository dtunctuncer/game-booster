package com.dtunctuncer.booster.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.widget.Toast;

import com.dtunctuncer.booster.R;
import com.dtunctuncer.booster.core.EventCategories;
import com.dtunctuncer.booster.core.events.AppClickEvent;
import com.dtunctuncer.booster.model.AppInfo;
import com.dtunctuncer.booster.utils.RAMBooster;
import com.dtunctuncer.booster.utils.RxBus;
import com.dtunctuncer.booster.utils.analytics.AnalyticsUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;
import rx.internal.schedulers.ExecutorScheduler;

class AppPresenter {
    private IAppView view;
    private Context context;
    private RxBus rxBus;
    private Subscription subscription;
    private RAMBooster ramBooster;

    @Inject
    AppPresenter(IAppView view, Context context, RxBus rxBus) {
        this.ramBooster = new RAMBooster(context.getApplicationContext());
        this.view = view;
        this.context = context;
        this.rxBus = rxBus;
    }

    void getApplicationList() {
        final PackageManager packageManager = context.getPackageManager();
        List<ApplicationInfo> appList = packageManager.getInstalledApplications(0);

        List<AppInfo> appInfoList = new ArrayList<>();

        for (ApplicationInfo info : appList) {
            if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                AppInfo appInfo = new AppInfo();
                String name = packageManager.getApplicationLabel(info).toString();

                appInfo.setName(name);
                appInfo.setPackageName(info.packageName);
                appInfo.setIcon(info.loadIcon(packageManager));
                appInfoList.add(appInfo);
            }
        }

        view.showApplications(appInfoList);
    }

    void subscribe() {
        subscription = rxBus.toObserverable()
                .subscribeOn(new ExecutorScheduler(AsyncTask.THREAD_POOL_EXECUTOR))
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if (o instanceof AppClickEvent) {
                            clickApp(((AppClickEvent) o).getAppInfo());
                        }
                    }


                });
    }

    private void clickApp(AppInfo appInfo) {

        view.startBoostingProgress(appInfo);
    }

    void unsunbscribe() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    void openApp(AppInfo appInfo) {
        ramBooster.clearRAM();
        AnalyticsUtils.trackEvent(EventCategories.CLICK_EVENT, "Click App Booster", "Rootsuz app boostlama tıklandı");
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(appInfo.getPackageName());
        if (intent == null) {
            Toast.makeText(context, R.string.app_boost_error, Toast.LENGTH_SHORT).show();
            return;
        }

        intent.addCategory("android.intent.category.LAUNCHER");
        context.startActivity(intent);
        Toast.makeText(context, R.string.app_boosted, Toast.LENGTH_SHORT).show();
    }
}
