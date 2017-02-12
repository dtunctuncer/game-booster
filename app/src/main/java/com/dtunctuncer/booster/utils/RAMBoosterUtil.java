package com.dtunctuncer.booster.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class RAMBoosterUtil {
    private static final long MEMORY_UNIT = 1048576;
    public static RAMBoosterUtil instance = null;
    int freeMemory;
    int percentage;
    int totalMemory;

    public static RAMBoosterUtil getInstance() {
        if (instance == null) {
            instance = new RAMBoosterUtil();
        }
        return instance;
    }

    public ArrayList<Integer> getMemoryStatus(Activity activity) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryInfo(memoryInfo);
        this.totalMemory = (int) (memoryInfo.totalMem / MEMORY_UNIT);
        this.freeMemory = (int) (memoryInfo.availMem / MEMORY_UNIT);
        this.percentage = (int) (((memoryInfo.availMem / MEMORY_UNIT) * 100) / (memoryInfo.totalMem / MEMORY_UNIT));
        ArrayList<Integer> memoryInfoList = new ArrayList();
        memoryInfoList.add(Integer.valueOf(this.totalMemory));
        memoryInfoList.add(Integer.valueOf(this.freeMemory));
        memoryInfoList.add(Integer.valueOf(100 - this.percentage));
        return memoryInfoList;
    }

    public void freeMemory(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = activityManager.getRunningTasks(100);
        for (int i = 1; i < taskInfo.size(); i++) {
            activityManager.killBackgroundProcesses(taskInfo.get(i).topActivity.getPackageName());
        }
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }
}