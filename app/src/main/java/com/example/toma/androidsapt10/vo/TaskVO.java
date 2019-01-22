package com.example.toma.androidsapt10.vo;

public class TaskVO {
    int id;
    String titlu;
    String descriere;


    public String getDescriere() {
        return descriere;
    }
    public void setDescription(String descriere) {
        this.descriere = descriere;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }
    public String getTitlu() {
        return titlu;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) { this.id = id; }

    public TaskVO(int id, String titlu, String description) {
        this.id = id;
        this.descriere = description;
        this.titlu = titlu;
    }
    public TaskVO(){}
}
