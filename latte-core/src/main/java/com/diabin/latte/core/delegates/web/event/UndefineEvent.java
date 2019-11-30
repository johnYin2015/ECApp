package com.diabin.latte.core.delegates.web.event;

import com.diabin.latte.core.util.log.LatteLogger;

public class UndefineEvent extends Event {

    public String execute(String params){
        LatteLogger.d("UndefineEvent",params);
        return null;
    }

}
