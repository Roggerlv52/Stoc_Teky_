package com.rogger.myapplication.database;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ProductEntity.class}, version = 1, exportSchema = false)
public abstract  class StockDatabaseProvider extends RoomDatabase {

    public abstract ProductDao productDao();
    private static volatile StockDatabaseProvider INSTANCE;

    public static StockDatabaseProvider getDatabase(final Application application) {
        if (INSTANCE == null) {
            synchronized (StockDatabaseProvider.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(application, StockDatabaseProvider.class, "estoque_db").build();
                }
            }
        }
        return INSTANCE;
    }
}
