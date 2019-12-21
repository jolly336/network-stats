package com.nelson.stats.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {NetworkCallEntity.class}, version = NetworkCallDBManager.DB_VERSION, exportSchema = true)
public abstract class NetworkCallDatabase extends RoomDatabase {

    public abstract NetworkCallDao getNetworkCallDao();
}
