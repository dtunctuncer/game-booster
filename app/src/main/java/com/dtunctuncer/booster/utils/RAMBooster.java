package com.dtunctuncer.booster.utils;

import android.content.Context;

import timber.log.Timber;

public class RAMBooster {
    Context context;
    int free_memory;

    public RAMBooster(Context context) {
        this.context = context;
    }

    public int getUnusedRAM() {
        try {
            return CPURAMUtil.getUsedRamPercent(this.context.getApplicationContext());
        } catch (Throwable throwable) {
            Timber.e(throwable);
            return 0;
        }
    }

    public int clearRAM() {
        RAMBoosterUtil.getInstance().freeMemory(this.context);
        try {
            this.free_memory = (int) CPURAMUtil.getFreeMemorySize(this.context.getApplicationContext());
        } catch (Throwable throwable) {
            this.free_memory = 0;
            Timber.e(throwable);
        }
        return this.free_memory;
    }
}