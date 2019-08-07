package com.greenstar.hsteam.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.hsteam.model.DonorProvider;
import com.greenstar.hsteam.model.Donors;

import java.util.List;

@Dao
public interface DonorsDAO {
    @Insert
    void insertMultiple (List<Donors> donors);

    @Query("SELECT * FROM Donors")
    List<Donors> getAll();

    @Query("DELETE FROM Donors")
    public void nukeTable();
}
