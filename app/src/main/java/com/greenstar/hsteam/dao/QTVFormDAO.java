package com.greenstar.hsteam.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.hsteam.model.Providers;
import com.greenstar.hsteam.model.QTVForm;

import java.util.List;

@Dao
public interface QTVFormDAO {
    @Insert
    void insertMultiple (List<QTVForm> qtvForms);

    @Insert
    void insert (QTVForm qtvForms);

    @Query("SELECT * FROM QTVForm")
    List<QTVForm> getAll();

    @Query("SELECT * FROM QTVForm WHERE status = 1")
    List<QTVForm> getAllSuccessfulForms();

    @Query("SELECT * FROM QTVForm WHERE status = 2")
    List<QTVForm> getAllRejectedForms();

    @Query("SELECT * FROM QTVForm WHERE status = 0")
    List<QTVForm> getAllPendingForms();

    @Query("DELETE FROM QTVForm")
    public void nukeTable();

    @Query("DELETE FROM QTVForm WHERE id=:id")
    public void deleteQtvFormById(long id);

    @Query("UPDATE QTVForm SET status=1 WHERE id=:id")
    public void markQTVSuccessful(int id);

    @Query("UPDATE QTVForm SET status=2 WHERE id=:id")
    public void markQTVRejected(int id);

}
