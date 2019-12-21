package com.nelson.stats;

import com.nelson.stats.db.NetworkCallDBManager;
import com.nelson.stats.db.NetworkCallEntity;
import com.nelson.stats.utils.StatsExecutor;
import com.nelson.stats.utils.TimeUtil;
import com.nelson.stats.utils.Util;

/**
 * HTTP EVENT MANAGER
 *
 * <p> use the flowing three step to get network stats in bytes. :)
 * <pre>
 *     // 1.set user identity
 *     NetworkStatsManager.setUser(new User(userId,userName));
 *
 *     // 2.get stats bytes
 *     NetworkStatsManager.getTodayBytes(new LoadCallBack() {
 *          @Override
 *          public void onSuccess(long bytes) {
 *             // request bytes
 *          }
 *
 *          @Override
 *          public void onFailure(int errorCode, String errorMsg) {
 *             // handle exception
 *          }
 *     });
 * </pre>
 *
 * Created by Nelson on 2019-11-29.
 */
public class NetworkStatsManager {

    private static final String TAG = "nstats.manager";
    // default user info
    private User user = new User("", "");
    private int saveDays = 2;

    private NetworkStatsManager() {
    }

    public static NetworkStatsManager getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * 设置全局用户信息
     */
    public static void setUser(User user) {
        getInstance().user = user;
    }

    public static User getUser() {
        return getInstance().user;
    }

    /**
     * 设置保存几日数据，默认为 2，内部会保存最近 2 天耗费字节数，包含移动数据流量和 WiFi
     *
     * @param days 保存几日数据
     */
    public static void setSaveDataInDays(int days) {
        if (days <= 0) {
            throw new IllegalArgumentException("set days must > 0");
        }
        getInstance().saveDays = days;
    }

    /**
     * 获取今日已发送和接收的总字节数
     *
     * @return bytes 总字节数
     */
    public static void getTodayBytes(LoadCallBack callBack) {
        long start = TimeUtil.getTodayZeroClockTimeInMillis();
        long end = TimeUtil.getNowOfMills();
        NetworkStatsManager.getBytes(start, end, callBack);
    }

    /**
     * 获取昨日已发送和接收的总字节数
     *
     * @return bytes 总字节数
     */
    public static void getYesterdayBytes(LoadCallBack callBack) {
        long start = TimeUtil.getYesterdayZeroClockTimeInMillis();
        long end = TimeUtil.getTodayZeroClockTimeInMillis();
        NetworkStatsManager.getBytes(start, end, callBack);
    }

    /**
     * 获取已发送和接收的总字节数
     *
     * @param start 开始时间戳，单位：毫秒
     * @param end 结束时间戳，单位：毫秒
     * @return bytes 总字节数
     */
    public static void getBytes(long start, long end, LoadCallBack callback) {
        // NPE CHECK
        Util.checkNotNull(callback, "callback == null");
        StatsExecutor.runWorker(() -> {
            try {
                String userId = NetworkStatsManager.getUser().getUserId();
                long bytes = NetworkCallDBManager.networkCallDao().findBytesByTimeInterval(userId, start, end);
                StatsExecutor.runUI(() -> callback.onSuccess(bytes));
            } catch (Exception e) {
                StatsExecutor.runUI(() -> callback.onFailure(-1, e.getLocalizedMessage()));
            }
        });
    }

    /**
     * internal method-保存一次请求数据
     */
    public static void saveData(NetworkCallEntity entity) {
        StatsExecutor.runWorker(() -> {
            NetworkCallDBManager.networkCallDao().insertEntity(entity);
        });
    }

    /**
     * 删除所有记录数据
     */
    public static void clearAll() {
        StatsExecutor.runWorker(() -> {
            NetworkCallDBManager.networkCallDao().deleteAll();
        });
    }

    /**
     * 删除指定日期之前的数据，默认只保存最近两天数据！
     */
    public static void clearIntervalData() {
        StatsExecutor.runWorker(() -> {
            int saveDays = getInstance().saveDays;
            long timestamp = TimeUtil.getZeroClockTimeInMillis(saveDays);
            String userId = NetworkStatsManager.getUser().getUserId();
            NetworkCallDBManager.networkCallDao().deleteLessThan(userId, timestamp);
        });
    }

    public interface LoadCallBack {

        void onSuccess(long bytes);

        void onFailure(int errorCode, String errorMsg);
    }

    private static class InstanceHolder {

        private static final NetworkStatsManager INSTANCE = new NetworkStatsManager();
    }
}
