package com.example.fairtechtoten.data.remote.dto;

public class LoginResponseDto {

    private String token;
    private long coordinatorId;
    private String email;
    private String name;


    public String getToken(){
        return this.token;
    }

    public long getCoordinatorId(){
        return this.coordinatorId;
    }

    public String getEmail(){
        return this.email;
    }

    public String getName(){
        return this.name;
    }
}
