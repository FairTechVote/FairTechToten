package com.example.fairtechtoten.data.remote.dto;

import com.example.fairtechtoten.domain.model.Status;

public class CoordinatorUpdateRequestDto {

    private String name;
    private String cpf;
    private String email;
    private Status status;
    public CoordinatorUpdateRequestDto(String name, String cpf, String email, Status status) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.status = status;
    }
    public String getName() { return name; }
    public String getCpf() { return cpf; }
    public String getEmail() { return email; }
    public Status getStatus() { return status; }

}
