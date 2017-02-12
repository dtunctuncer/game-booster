package com.dtunctuncer.booster.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.dtunctuncer.booster.model.AppInfo;

import java.util.ArrayList;
import java.util.List;

class AppPresenter {
    private IAppView view;
    private Context context;

    AppPresenter(IAppView view, Context context) {
        this.view = view;
        this.context = context;
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
}
