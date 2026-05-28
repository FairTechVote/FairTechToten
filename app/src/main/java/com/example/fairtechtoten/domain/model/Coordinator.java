package com.example.fairtechtoten.domain.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "coordinators")
public class Coordinator {

    @Ignore
    private String token;
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name="cpf")
    private String cpf;

    @ColumnInfo(name = "status")
    private Status status;

    public Coordinator() {}

    @Ignore
    public Coordinator(String token, long id, String email, String name) {

        this.token = token;
        this.id = id;
        this.email = email;
        this.name = name;

    }

    public Coordinator(long id, String name, String email, String cpf, Status status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.status = status;
    }

    /*
    * SETTERS
    * */
    public void setId(long id) { this.id = id; }
    public void setToken(String token) { this.token = token; }
    public void setEmail(String email) { this.email = email; }
    public void setName(String name) { this.name = name; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setStatus(Status status) { this.status = status; }

    /*
    * GETTERS
    * */
    public long getId() { return this.id; }
    public String getToken() { return this.token; }
    public String getEmail() { return this.email; }
    public String getName() { return this.name; }
    public String getCpf() { return this.cpf; }
    public Status getStatus() { return this.status; }

}
