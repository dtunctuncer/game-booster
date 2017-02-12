package com.dtunctuncer.booster.app;

import com.dtunctuncer.booster.model.AppInfo;

import java.util.List;

interface IAppView {
    void showApplications(List<AppInfo> appInfoList);
}
