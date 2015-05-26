package com.qinyuan15.mall.crawler;

import com.qinyuan15.utils.DateUtils;

/**
 * Class to validate if current time is crawl time
 * Created by qinyuan on 15-5-17.
 */
public class CrawlTimeValidator {
    private int startHour = -1;
    private int endHour = -1;

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public boolean inCrawlTime() {
        if (this.startHour < 0 || this.endHour < 0) {
            return true;
        }

        int currentHour = DateUtils.currentHour();
        if (this.startHour < this.endHour) {
            return currentHour >= this.startHour && currentHour < this.endHour;
        } else {
            return currentHour >= this.startHour || currentHour < this.endHour;
        }
    }
}
