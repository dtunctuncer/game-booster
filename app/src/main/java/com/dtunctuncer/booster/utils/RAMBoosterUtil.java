package com.dtunctuncer.booster.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

public class RAMBoosterUtil {
    private static final long MEMORY_UNIT = 1048576;
    private static RAMBoosterUtil instance = null;

    public static RAMBoosterUtil getInstance() {
        if (instance == null) {
            instance = new RAMBoosterUtil();
        }
        return instance;
    }

    public List<Integer> getMemoryStatus(Activity activity) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryInfo(memoryInfo);
        int totalMemory = (int) (memoryInfo.totalMem / MEMORY_UNIT);
        int freeMemory = (int) (memoryInfo.availMem / MEMORY_UNIT);
        int percentage = (int) (((memoryInfo.availMem / MEMORY_UNIT) * 100) / (memoryInfo.totalMem / MEMORY_UNIT));
        List<Integer> memoryInfoList = new ArrayList<>();
        memoryInfoList.add(totalMemory);
        memoryInfoList.add(freeMemory);
        memoryInfoList.add(100 - percentage);
        return memoryInfoList;
    }

    public void freeMemory(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = activityManager.getRunningTasks(100);

        for (ActivityManager.RunningTaskInfo runningTaskInfo : taskInfo) {
            activityManager.killBackgroundProcesses(runningTaskInfo.topActivity.getPackageName());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            List<ActivityManager.AppTask> tasks = activityManager.getAppTasks();
            for (ActivityManager.AppTask task : tasks) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    activityManager.killBackgroundProcesses(task.getTaskInfo().topActivity.getPackageName());
                }
            }
        }

        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : activityManager.getRunningAppProcesses()) {
            if (runningAppProcessInfo.importanceReasonComponent != null && runningAppProcessInfo.importanceReasonComponent.getPackageName() != null) {
                activityManager.killBackgroundProcesses(runningAppProcessInfo.importanceReasonComponent.getPackageName());
            }
        }


        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }
}