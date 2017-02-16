package com.dtunctuncer.booster.core.events;

public class ChangeRootModeEvent {
    private int mode;

    public ChangeRootModeEvent(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
