package com.lning.melireader.app.event;

/**
 * Created by Ning on 2019/2/14.
 */

public class HistoryEvent {
    private final int curPosition;

    public HistoryEvent(int currentItem) {
        this.curPosition = currentItem;
    }

    public int getCurPosition() {
        return curPosition;
    }
}
