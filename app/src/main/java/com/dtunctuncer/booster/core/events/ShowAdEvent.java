package com.dtunctuncer.booster.core.events;

public class ShowAdEvent {
    private int adType;

    public ShowAdEvent(int adType) {
        this.adType = adType;
    }

    public int getAdType() {
        return adType;
    }

    public void setAdType(int adType) {
        this.adType = adType;
    }
}
