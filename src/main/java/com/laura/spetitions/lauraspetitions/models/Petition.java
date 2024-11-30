package com.laura.spetitions.lauraspetitions.models;

import java.util.ArrayList;
import java.util.List;

public class Petition {
    private int id;
    private String title;
    private String description;
    private List<Signature> signatures = new ArrayList<>();

    public Petition(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Signature> getSignatures() {
        return signatures;
    }

    public void addSignature(Signature signature) {
        signatures.add(signature);
    }

    public void addSignature(java.security.Signature signature) {
    }
}

