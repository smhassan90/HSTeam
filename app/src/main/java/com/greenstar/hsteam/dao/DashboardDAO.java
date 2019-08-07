package com.greenstar.hsteam.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.hsteam.model.Dashboard;

import java.util.List;

@Dao
public interface DashboardDAO {
    @Insert
    void insert (Dashboard dashboard);

    @Query("SELECT * FROM Dashboard")
    Dashboard getAll();

    @Query("DELETE FROM Dashboard")
    public void nukeTable();
}
