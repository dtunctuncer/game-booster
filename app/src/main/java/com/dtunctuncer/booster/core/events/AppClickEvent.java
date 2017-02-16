package com.dtunctuncer.booster.core.events;

import com.dtunctuncer.booster.model.AppInfo;

public class AppClickEvent {
    private AppInfo appInfo;

    public AppClickEvent(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

    public AppInfo getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }
}
