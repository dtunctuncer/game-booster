package com.dtunctuncer.booster.utils;

import com.dtunctuncer.booster.core.BoosterModes;
import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.execution.Command;

import timber.log.Timber;

public class BoosterModeManager implements BoosterModes {

    public void setMode(int mode) {
        switch (mode) {
            case BoosterModes.NO_MODE /*0*/:
                disableMode();
                break;
            case BoosterModes.HIGH_MODE /*1*/:
                setHighMode();
                break;
            case BoosterModes.ULTRA_MODE /*2*/:
                setUltraMode();
                break;

        }
    }

    private void setUltraMode() {
        try {
            RootTools.getShell(true).add(new Command(0, BoosterModes.ULTRA_MODE_SCRIPT) {
                @Override
                public void commandOutput(int i, String s) {

                }

                @Override
                public void commandTerminated(int i, String s) {

                }

                @Override
                public void commandCompleted(int i, int i1) {

                }
            });
        } catch (Exception ex) {
            Timber.e(ex);
        }
    }

    private void setHighMode() {
        try {
            RootTools.getShell(true).add(new Command(0, BoosterModes.HIGH_MODE_SCRIPT) {
                @Override
                public void commandOutput(int i, String s) {

                }

                @Override
                public void commandTerminated(int i, String s) {

                }

                @Override
                public void commandCompleted(int i, int i1) {

                }
            });
        } catch (Exception ex) {
            Timber.e(ex);
        }
    }

    private void disableMode() {
        try {
            RootTools.getShell(true).add(new Command(0, "mount -o remount,rw /system", "echo 90 > /sys/devices/system/cpu/cpufreq/ondemand/up_treshold", "echo 90 > /sys/devices/system/cpu/cpufreq/hotplug/up_treshold", "echo 90 > /sys/devices/system/cpu/cpufreq/interactive/up_treshold", "echo 90 > /sys/devices/system/cpu/cpufreq/ondemand/up_treshold_any_load", "echo 90 > /sys/devices/system/cpu/cpufreq/hotplug/up_treshold_any_load", "echo 90 > /sys/devices/system/cpu/cpufreq/interactive/up_treshold_any_load", "echo 90 > /sys/devices/system/cpu/cpufreq/ondemand/up_treshold_multi_core", "echo 90 > /sys/devices/system/cpu/cpufreq/hotplug/up_treshold_multi_core", "echo 90 > /sys/devices/system/cpu/cpufreq/interactive/up_treshold_multi_core") {
                @Override
                public void commandOutput(int i, String s) {

                }

                @Override
                public void commandTerminated(int i, String s) {

                }

                @Override
                public void commandCompleted(int i, int i1) {

                }
            });
        } catch (Exception ex) {
            Timber.e(ex);
        }
    }
}