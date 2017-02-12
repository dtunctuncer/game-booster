package com.dtunctuncer.booster.rootbooster;

import android.content.Context;

import com.dtunctuncer.booster.model.RootMode;
import com.dtunctuncer.booster.utils.SpUtils;
import com.stericson.RootTools.RootTools;

import java.util.ArrayList;
import java.util.List;

class RootPresenter {

    private IRootView view;
    private Context context;
    private SpUtils spUtils;

    RootPresenter(IRootView view, Context context) {
        this.view = view;
        this.context = context;
        spUtils = new SpUtils(context);
    }

    void checkRoot() {
        if (RootTools.isRootAvailable()) {
            view.closeRootError();
        }
    }

    public void getBoosterModes() {
        List<RootMode> rootModes = new ArrayList<>();

        int mode = spUtils.getCurrentMode();

        RootMode ultraMode = new RootMode();
        ultraMode.setBootMode(BoosterModes.ULTRA_MODE);
        rootModes.add(ultraMode);

        RootMode highMode = new RootMode();
        highMode.setBootMode(BoosterModes.HIGH_MODE);
        rootModes.add(highMode);

        for (RootMode rootMode : rootModes) {
            if (rootMode.getBootMode() == mode) {
                rootMode.setIsActive(true);
            }
        }

        view.showBoosterMode(rootModes);
    }
}
