package com.laura.spetitions.lauraspetitions.models;

import java.util.ArrayList;
import java.util.List;

public class Petition {
    private Integer id; // Changed from int to Integer
    private String title;
    private String description;
    private List<Signature> signatures = new ArrayList<>();

    // Default constructor (required for Spring to bind form data)
    public Petition() {}

    public Petition(Integer id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Signature> getSignatures() {
        return signatures;
    }

    public void setSignatures(List<Signature> signatures) {
        this.signatures = signatures;
    }

    public void addSignature(Signature signature) {
        this.signatures.add(signature);
    }
}
