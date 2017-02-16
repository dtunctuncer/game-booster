package com.dtunctuncer.booster.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class RAMBoosterUtil {
    private static final long MEMORY_UNIT = 1048576;
    private static RAMBoosterUtil instance = null;
    private int freeMemory;
    private int percentage;
    private int totalMemory;

    public static RAMBoosterUtil getInstance() {
        if (instance == null) {
            instance = new RAMBoosterUtil();
        }
        return instance;
    }

    public List<Integer> getMemoryStatus(Activity activity) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryInfo(memoryInfo);
        this.totalMemory = (int) (memoryInfo.totalMem / MEMORY_UNIT);
        this.freeMemory = (int) (memoryInfo.availMem / MEMORY_UNIT);
        this.percentage = (int) (((memoryInfo.availMem / MEMORY_UNIT) * 100) / (memoryInfo.totalMem / MEMORY_UNIT));
        List<Integer> memoryInfoList = new ArrayList<>();
        memoryInfoList.add(this.totalMemory);
        memoryInfoList.add(this.freeMemory);
        memoryInfoList.add(100 - this.percentage);
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