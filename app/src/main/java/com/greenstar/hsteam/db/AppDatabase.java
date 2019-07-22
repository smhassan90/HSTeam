package com.greenstar.hsteam.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.greenstar.hsteam.dao.ProvidersDAO;
import com.greenstar.hsteam.dao.QTVFormDAO;
import com.greenstar.hsteam.model.Providers;
import com.greenstar.hsteam.model.QTVForm;

@Database(entities = {Providers.class, QTVForm.class},
        version = 3)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "hsteamdb";
    private static AppDatabase INSTANCE;

    public abstract ProvidersDAO getProvidersDAO();
    public abstract QTVFormDAO getQTVFormDAO();

    public static AppDatabase getAppDatabase(Context context) {

        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
