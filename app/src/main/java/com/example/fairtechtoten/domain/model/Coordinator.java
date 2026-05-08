package com.example.fairtechtoten.domain.model;

public class Coordinator {

    private String token;
    private long CoordinatorId;
    private String email;
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
