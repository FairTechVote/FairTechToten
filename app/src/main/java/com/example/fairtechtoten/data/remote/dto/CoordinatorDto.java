package com.example.fairtechtoten.data.remote.dto;


import com.example.fairtechtoten.domain.model.Status;

public class CoordinatorDto {

    private Long id;
    private String name;
    private String cpf;
    private String email;
    private Status status;

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCpf() { return cpf; }
    public String getEmail() { return email; }
    public Status getStatus() { return status; }

}
