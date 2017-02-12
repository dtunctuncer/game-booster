package com.dtunctuncer.booster.rootbooster;

import android.content.Context;

import com.dtunctuncer.booster.R;
import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.Command;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class BoosterModeManager implements BoosterModes {
    Context context;

    public BoosterModeManager(Context context) {
        this.context = context;
    }

    public void setMode(int mode) {
        switch (mode) {
            case R.styleable.View_android_theme /*0*/:
                disableMode();
                return;
            case R.styleable.View_android_focusable /*1*/:
                setHighMode();
                return;
            case R.styleable.View_paddingStart /*2*/:
                setUltraMode();
                return;
            default:
                return;
        }
    }

    private void setUltraMode() {
        Exception e;
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
            return;
        } catch (IOException e2) {
            e = e2;
        } catch (TimeoutException e3) {
            e = e3;
        } catch (RootDeniedException e4) {
            e = e4;
        }
    }

    private void setHighMode() {
        Exception e;
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
            return;
        } catch (IOException e2) {
            e = e2;
        } catch (TimeoutException e3) {
            e = e3;
        } catch (RootDeniedException e4) {
            e = e4;
        }
        e.printStackTrace();
    }

    private void disableMode() {
        Exception e;
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
            return;
        } catch (IOException e2) {
            e = e2;
        } catch (TimeoutException e3) {
            e = e3;
        } catch (RootDeniedException e4) {
            e = e4;
        }
        e.printStackTrace();
    }
}