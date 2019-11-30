package com.diabin.latte.core.delegates.web.event;

import android.support.annotation.NonNull;

import java.util.HashMap;

public class EventManager {

    private static final HashMap<String, Event> EVENTS = new HashMap<>();

    private EventManager() {
    }

    private static class Holder {
        private static final EventManager INSTANCE = new EventManager();
    }

    public static EventManager getInstance() {
        return Holder.INSTANCE;
    }

    //链式
    public EventManager addEvent(@NonNull String name, @NonNull Event event) {
        EVENTS.put(name, event);
        return this;
    }

    //创建event
    public Event createEvent(String action) {
        Event event = EVENTS.get(action);
        if (event == null) {
            event = new UndefineEvent();
        }
        return event;
    }
}
