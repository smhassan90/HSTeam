package com.greenstar.hsteam.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.greenstar.hsteam.dao.ApprovalQTVFormDAO;
import com.greenstar.hsteam.dao.AreaDAO;
import com.greenstar.hsteam.dao.DashboardDAO;
import com.greenstar.hsteam.dao.ProvidersDAO;
import com.greenstar.hsteam.dao.QTVFormDAO;
import com.greenstar.hsteam.dao.QuestionsDAO;
import com.greenstar.hsteam.model.ApprovalQTVForm;
import com.greenstar.hsteam.model.Area;
import com.greenstar.hsteam.model.Dashboard;
import com.greenstar.hsteam.model.Providers;
import com.greenstar.hsteam.model.QTVForm;
import com.greenstar.hsteam.model.Question;

@Database(entities = {Providers.class, QTVForm.class, ApprovalQTVForm.class, Dashboard.class, Question.class, Area.class},
        version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "hsteamdb";
    private static AppDatabase INSTANCE;

    public abstract ProvidersDAO getProvidersDAO();
    public abstract DashboardDAO getDashboardDAO();
    public abstract QTVFormDAO getQTVFormDAO();
    public abstract ApprovalQTVFormDAO getApprovalQTVFormDAO();
    public abstract QuestionsDAO getQuestionsDAO();
    public abstract AreaDAO getAreaDAO();

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
