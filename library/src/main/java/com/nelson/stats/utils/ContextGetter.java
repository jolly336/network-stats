package com.nelson.stats.utils;

import android.app.Application;
import android.content.Context;

/**
 * Context 获取
 *
 * Created by Nelson on 2019/5/9.
 */
public final class ContextGetter {

    private static Context CONTEXT_INSTANCE;

    public static Context getContext() {
        if (CONTEXT_INSTANCE == null) {
            synchronized (ContextGetter.class) {
                if (CONTEXT_INSTANCE == null) {
                    try {
                        Application app = getApplicationUsingReflection();
                        CONTEXT_INSTANCE = app.getApplicationContext();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return CONTEXT_INSTANCE;
    }

    private static Application getApplicationUsingReflection() throws Exception {
        return (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null, (Object[]) null);
    }
}
