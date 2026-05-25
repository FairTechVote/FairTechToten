package com.example.fairtechtoten.domain.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "coordinators")
public class Coordinator {

    private String token;
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    private long CoordinatorId;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "name")
    private String name;

    public Coordinator(String token, long CoordinatorId, String email, String name) {

        this.token = token;
        this.CoordinatorId = CoordinatorId;
        this.email = email;
        this.name = name;

    }
    public String getToken() { return this.token; }
    public long getCoordinatorId() { return this.CoordinatorId; }
    public String getEmail() { return this.email; }
    public String getName() { return this.name; }

}
