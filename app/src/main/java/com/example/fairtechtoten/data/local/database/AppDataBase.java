package com.example.fairtechtoten.data.local.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.fairtechtoten.data.local.dao.CoordinatorDAO;
import com.example.fairtechtoten.domain.model.Coordinator;

@Database(entities = {
        Coordinator.class
},version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    private static AppDataBase instance;

    public abstract CoordinatorDAO coordinatorDAO();

    public static synchronized AppDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDataBase.class,
                            "fairtech_database"
                    )
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
