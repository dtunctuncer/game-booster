package com.dtunctuncer.booster.rootbooster;

public interface BoosterModes {
    public static final int HIGH_MODE = 1;
    public static final String[] HIGH_MODE_SCRIPT = new String[]{"mount -o remount,rw /system", "echo 25 > /sys/devices/system/cpu/cpufreq/ondemand/up_treshold", "echo 25 > /sys/devices/system/cpu/cpufreq/hotplug/up_treshold", "echo 25 > /sys/devices/system/cpu/cpufreq/interactive/up_treshold", "echo 25 > /sys/devices/system/cpu/cpufreq/ondemand/up_treshold_any_load", "echo 25 > /sys/devices/system/cpu/cpufreq/hotplug/up_treshold_any_load", "echo 25 > /sys/devices/system/cpu/cpufreq/interactive/up_treshold_any_load", "echo 25 > /sys/devices/system/cpu/cpufreq/ondemand/up_treshold_multi_core", "echo 25 > /sys/devices/system/cpu/cpufreq/hotplug/up_treshold_multi_core", "echo 25 > /sys/devices/system/cpu/cpufreq/interactive/up_treshold_multi_core"};
    public static final int NO_MODE = 0;
    public static final int ULTRA_MODE = 2;
    public static final String[] ULTRA_MODE_SCRIPT = new String[]{"mount -o remount,rw /system", "echo 6 > /sys/devices/system/cpu/cpufreq/ondemand/up_treshold", "echo 6 > /sys/devices/system/cpu/cpufreq/hotplug/up_treshold", "echo 6 > /sys/devices/system/cpu/cpufreq/interactive/up_treshold", "echo 6 > /sys/devices/system/cpu/cpufreq/ondemand/up_treshold_any_load", "echo 6 > /sys/devices/system/cpu/cpufreq/hotplug/up_treshold_any_load", "echo 6 > /sys/devices/system/cpu/cpufreq/interactive/up_treshold_any_load", "echo 6 > /sys/devices/system/cpu/cpufreq/ondemand/up_treshold_multi_core", "echo 6 > /sys/devices/system/cpu/cpufreq/hotplug/up_treshold_multi_core", "echo 6 > /sys/devices/system/cpu/cpufreq/interactive/up_treshold_multi_core"};
}
