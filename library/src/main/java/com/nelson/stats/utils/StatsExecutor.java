package com.nelson.stats.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * 线程封装类
 *
 * Created by Nelson on 2019-12-13.
 */
public class StatsExecutor {

    private static final String TAG = "nstats.StatsExecutor";

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(2);
    private static final Handler MAIN_THREAD = new Handler(Looper.getMainLooper());


    public static void runWorker(@NonNull Runnable runnable) {
        try {
            EXECUTOR_SERVICE.execute(runnable);
        } catch (Exception e) {
            Log.e(TAG, "runnable stop running unexpected. " + e.getMessage());
        }
    }

    @Nullable
    public FutureTask<Boolean> runWorker(@NonNull Callable<Boolean> callable) {
        FutureTask<Boolean> task = null;
        try {
            task = new FutureTask<>(callable);
            EXECUTOR_SERVICE.submit(task);
            return task;
        } catch (Exception e) {
            Log.e(TAG, "callable stop running unexpected. " + e.getMessage());
        }
        return task;
    }

    public static void runUI(@NonNull Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
            return;
        }
        try {
            MAIN_THREAD.post(runnable);
        } catch (Exception e) {
            Log.d(TAG, "update UI task fail. " + e.getMessage());
        }
    }
}
