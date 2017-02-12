package com.dtunctuncer.booster.rootbooster;

import com.dtunctuncer.booster.model.RootMode;

import java.util.List;

public interface IRootView {
    void closeRootError();

    void showBoosterMode(List<RootMode> rootModes);
}
