package com.dtunctuncer.booster.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import com.dtunctuncer.booster.BuildConfig;
import com.dtunctuncer.booster.model.OneCpuInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.regex.Pattern;

import timber.log.Timber;

public class CPURAMUtil {
    private static int sLastCpuCoreCount = -1;

    public static int calcCpuCoreCount() {
        if (sLastCpuCoreCount >= 1) {
            return sLastCpuCoreCount;
        }
        try {
            sLastCpuCoreCount = new File("/sys/devices/system/cpu/").listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                        return true;
                    }
                    return false;
                }
            }).length;
        } catch (Exception e) {
            sLastCpuCoreCount = Runtime.getRuntime().availableProcessors();
        }
        return sLastCpuCoreCount;
    }

    public static int takeCurrentCpuFreq() {
        return readIntegerFile("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
    }

    public static int takeMinCpuFreq() {
        return readIntegerFile("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq");
    }

    public static int takeMaxCpuFreq() {
        return readIntegerFile("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq") + 100000;
    }

    private static int readIntegerFile(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)), 1000);
            String line = reader.readLine();
            reader.close();
            return Integer.parseInt(line);
        } catch (Exception e) {
            return 0;
        }
    }

    public static ArrayList<OneCpuInfo> takeCpuUsageSnapshot() {
        ArrayList<OneCpuInfo> result = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/stat")), 1000);
            while (reader.read() != -1) {
                String line = reader.readLine();
                if (line == null || !line.startsWith("cpu")) {
                    reader.close();
                } else {
                    String[] tokens = line.split(" +");
                    OneCpuInfo oci = new OneCpuInfo();
                    oci.setIdle(Long.parseLong(tokens[4]));
                    long a = ((((Long.parseLong(tokens[1]) + Long.parseLong(tokens[2])) + Long.parseLong(tokens[3])) + oci.getIdle()) + Long.parseLong(tokens[5])) + Long.parseLong(tokens[6]) + Long.parseLong(tokens[7]);
                    oci.setTotal(a);
                    result.add(oci);
                }
            }
        } catch (Exception e) {
            Timber.e(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Timber.e(e);
                }
            }
        }
        return result;
    }

    public static int[] calcCpuUsages(ArrayList<OneCpuInfo> currentInfo, ArrayList<OneCpuInfo> lastInfo) {
        int[] iArr = null;
        if (!(currentInfo == null || lastInfo == null)) {
            int nLast = lastInfo.size();
            int nCurr = currentInfo.size();
            if (nLast == 0 || nCurr == 0) {
                Log.d("ERROR", " no info: [" + nLast + "][" + nCurr + "]");
            } else {
                int n;
                if (nLast < nCurr) {
                    n = nLast;
                } else {
                    n = nCurr;
                }
                iArr = new int[n];
                for (int i = 0; i < n; i++) {
                    OneCpuInfo last = (OneCpuInfo) lastInfo.get(i);
                    OneCpuInfo curr = (OneCpuInfo) currentInfo.get(i);
                    int totalDiff = (int) (curr.getTotal() - last.getTotal());
                    if (totalDiff > 0) {
                        iArr[i] = 100 - ((((int) (curr.getIdle() - last.getIdle())) * 100) / totalDiff);
                    } else {
                        iArr[i] = 0;
                    }
                }
            }
        }
        return iArr;
    }

    public static String formatFreq(int clockHz) {
        if (clockHz < 1000000) {
            return (clockHz / 1000) + " MHz";
        }
        return ((clockHz / 1000) / 1000) + "." + (((clockHz / 1000) / 100) % 10) + " GHz";
    }

    public static long getFreeMemorySize(Context context) throws Throwable {
        long total_ram = 0;
        try {
            RandomAccessFile reader = new RandomAccessFile("/proc/meminfo", "r");
            try {
                total_ram = Long.parseLong(reader.readLine().replaceAll("\\D+", BuildConfig.FLAVOR)) / 1024;
            } catch (IOException e) {
                return total_ram - getUsedMemorySize(context);
            }
        } catch (IOException e2) {
            return total_ram - getUsedMemorySize(context);
        }
        return total_ram - getUsedMemorySize(context);
    }

    public static long getUsedMemorySize(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem / 1048576;
    }

    public static int getUsedRamPercent(Context context) throws Throwable {
        return (int) ((getUsedMemorySize(context) * 100) / (getUsedMemorySize(context) + getFreeMemorySize(context)));
    }
}
