package com.greenstar.hsteam.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.hsteam.model.Providers;
import com.greenstar.hsteam.model.Question;

import java.util.List;

@Dao
public interface QuestionsDAO {
    @Insert
    void insertMultiple(List<Question> questionBankList);

    @Query("SELECT * FROM Question")
    List<Question> getAll();

    @Query("SELECT * FROM Question where status=1")
    List<Question> getActiveQuestions();

    @Query("DELETE FROM Question")
    public void nukeTable();
}
