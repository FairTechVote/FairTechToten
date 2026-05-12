package com.example.fairtechtoten.domain.model;

public class Institute {

    private Long id;
    private String name;
    private String cnpj;

    public Institute(long id, String name, String cnpj) {
        this.id = id;
        this.name = name;
        this.cnpj = cnpj;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCnpj() {
        return cnpj;
    }

}
