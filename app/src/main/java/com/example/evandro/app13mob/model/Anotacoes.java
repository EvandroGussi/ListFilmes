package com.example.evandro.app13mob.model;

import com.orm.SugarRecord;

/**
 * Created by Evandro on 09/09/2017.
 */

public class Anotacoes extends SugarRecord<Anotacoes> {

    private String desc;
    private String nome;
    private Double lat;
    private Double lon;

    public Anotacoes(){

    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Anotacoes(String nome, String desc, Double lat, Double lon){
        this.desc = desc;
        this.nome = nome;
        this.lat = lat;
        this.lon = lon;
    }

    public String getDescricao() {
        return desc;
    }

    public void setDescricao(String descricao) {
        this.desc = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
