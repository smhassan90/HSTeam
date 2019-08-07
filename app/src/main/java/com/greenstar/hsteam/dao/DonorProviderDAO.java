package com.greenstar.hsteam.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.hsteam.model.DonorProvider;
import com.greenstar.hsteam.model.Providers;

import java.util.List;

@Dao
public interface DonorProviderDAO {
    @Insert
    void insertMultiple (List<DonorProvider> donorProviders);

    @Query("SELECT * FROM DonorProvider")
    List<DonorProvider> getAll();

    @Query("DELETE FROM DonorProvider")
    public void nukeTable();
}
