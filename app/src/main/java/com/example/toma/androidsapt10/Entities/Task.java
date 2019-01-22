package com.example.toma.androidsapt10.Entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "Tasks")
public class Task {
    @PrimaryKey
    @NonNull
    int id;
    String titlu;
    String descriere;
    Date dataCreare;

    public Date getDataCreare() {
        return dataCreare;
    }

    public void setDataCreare(Date dataCreare) {
        this.dataCreare = dataCreare;
    }

    public String getTitlu() {
        return titlu;
    }
    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }
    public String getDescriere() {
        return descriere;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) { this.id = id; }

    public Task(@NonNull int id, String titlu, String description, Date dataCreare) {
        this.id = id;
        this.dataCreare = dataCreare;
        this.descriere = description;
        this.titlu = titlu;
    }

    public Task(){}
}
