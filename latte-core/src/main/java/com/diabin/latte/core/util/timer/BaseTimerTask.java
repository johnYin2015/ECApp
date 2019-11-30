package com.diabin.latte.core.util.timer;

import java.util.TimerTask;

public class BaseTimerTask extends TimerTask {

    private ITimerListener iTimerListener;

    public BaseTimerTask(ITimerListener iTimerListener) {
        this.iTimerListener = iTimerListener;
    }

    @Override
    public void run() {
        if (iTimerListener != null) {
            iTimerListener.onTimer();
        }
    }
}
