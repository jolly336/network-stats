package com.nelson.stats.db;

import android.arch.persistence.room.Room;
import android.content.Context;
import com.nelson.stats.utils.ContextGetter;

/**
 * Created by Nelson on 2019-12-10.
 */
public class NetworkCallDBManager {

    public static final int DB_VERSION = 1;
    private static final String DB_NAME = "network_stats.db";
    private NetworkCallDatabase mmDataTaskDatabase;
    private static volatile boolean hasInit = false;

    private NetworkCallDBManager() {
        init(ContextGetter.getContext());
    }

    public static NetworkCallDBManager getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public static NetworkCallDao networkCallDao() {
        return NetworkCallDBManager.getInstance().getDatabase().getNetworkCallDao();
    }

    public void init(Context context) {
        if (!hasInit) {
            mmDataTaskDatabase = Room.databaseBuilder(context, NetworkCallDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
            hasInit = true;
        }
    }

    public NetworkCallDatabase getDatabase() {
        checkInit();
        return this.mmDataTaskDatabase;
    }

    private void checkInit() {
        if (!hasInit) {
            throw new RuntimeException("Please init NetworkCallDBManager before use!");
        }
    }

    private static final class InstanceHolder {

        private static final NetworkCallDBManager INSTANCE = new NetworkCallDBManager();
    }
}
