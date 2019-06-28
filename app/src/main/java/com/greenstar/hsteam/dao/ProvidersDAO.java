package com.greenstar.hsteam.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.hsteam.model.Providers;

import java.util.List;

@Dao
public interface ProvidersDAO {
    @Insert
    void insertMultiple (List<Providers> providers);

    @Query("SELECT * FROM Providers")
    List<Providers> getAll();

    @Query("DELETE FROM Providers")
    public void nukeTable();
}
