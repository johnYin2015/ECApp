package com.diabin.latte.core.ui.loader;

import android.content.Context;
import android.util.Log;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

public class LoaderCreator {
    private static final WeakHashMap<String, Indicator> LOADER_MAP = new WeakHashMap<>();

    static AVLoadingIndicatorView create(String type, Context context) {
        AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);

        if (LOADER_MAP.get(type) == null) {
            Indicator indicator = getIndicator(type);
            LOADER_MAP.put(type, indicator);
        }

        avLoadingIndicatorView.setIndicator(LOADER_MAP.get(type));

        return avLoadingIndicatorView;
    }

    //BallClipRotatePulseIndicator
    private static Indicator getIndicator(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        final StringBuilder drawableClassName = new StringBuilder();
        final String defaultPackageName = AVLoadingIndicatorView.class.getPackage().getName();
        if (!name.contains(".")) {
            drawableClassName.append(defaultPackageName)
                    .append(".")
                    .append("indicators")
                    .append(".");
        }
        drawableClassName.append(name);

        try {
            Class<?> drawableClass = Class.forName(drawableClassName.toString());
            Log.d("LoaderCreator",drawableClass.getName());
            Indicator indicator = (Indicator) drawableClass.newInstance();
            Log.d("LoaderCreator",String.valueOf(indicator.getHeight()));
            return indicator;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
