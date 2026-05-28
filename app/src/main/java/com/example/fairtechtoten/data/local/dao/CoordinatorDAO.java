package com.example.fairtechtoten.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fairtechtoten.domain.model.Coordinator;

import java.util.List;

@Dao
public interface CoordinatorDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Coordinator coordinator);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Coordinator> coordinators);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsert(Coordinator coordinator);
    @Update
    void update(Coordinator coordinator);

    @Query("SELECT * FROM coordinators WHERE id = :id")
    Coordinator getById(long id);

    @Query("SELECT * FROM coordinators WHERE email = :email")
    Coordinator getByEmail(String email);

    @Query("SELECT * FROM coordinators ORDER BY id DESC LIMIT 1")
    Coordinator getCurrent();

    @Query("DELETE FROM coordinators")
    void deleteAll();

}
