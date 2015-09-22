package com.example.renan.mprime_2.Model;

/**
 * Created by Renan on 21/09/2015.
 *
 * create table EXERCICIO (_id, nm_exercicio, ds_series_exercicio,
 * ds_repeticoes_exercicio, ds_carga_exercicio, Treino_ID_treino, ds_tempo_exercicio)
 */
public class Exercicio {
    private Integer _id;
    private String nome_exercicio;
    private int serie_exercicio;
    private int repeticoes_exercicio;
    private int carga_exercicio;
    private int tempo_exercicio;
    private Integer Treino_ID_treino;

    public Exercicio(int anInt, String string, String cursorString, String s, String string1, String cursorString1) {
    }

    public Exercicio(Integer _id, String nome_exercicio, int serie_exercicio, int repeticoes_exercicio, int carga_exercicio, int tempo_exercicio, Integer treino_ID_treino) {
        this._id = _id;
        this.nome_exercicio = nome_exercicio;
        this.serie_exercicio = serie_exercicio;
        this.repeticoes_exercicio = repeticoes_exercicio;
        this.carga_exercicio = carga_exercicio;
        this.tempo_exercicio = tempo_exercicio;
        Treino_ID_treino = treino_ID_treino;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getNome_exercicio() {
        return nome_exercicio;
    }

    public void setNome_exercicio(String nome_exercicio) {
        this.nome_exercicio = nome_exercicio;
    }

    public int getSerie_exercicio() {
        return serie_exercicio;
    }

    public void setSerie_exercicio(int serie_exercicio) {
        this.serie_exercicio = serie_exercicio;
    }

    public int getRepeticoes_exercicio() {
        return repeticoes_exercicio;
    }

    public void setRepeticoes_exercicio(int repeticoes_exercicio) {
        this.repeticoes_exercicio = repeticoes_exercicio;
    }

    public int getCarga_exercicio() {
        return carga_exercicio;
    }

    public void setCarga_exercicio(int carga_exercicio) {
        this.carga_exercicio = carga_exercicio;
    }

    public int getTempo_exercicio() {
        return tempo_exercicio;
    }

    public void setTempo_exercicio(int tempo_exercicio) {
        this.tempo_exercicio = tempo_exercicio;
    }

    public Integer getTreino_ID_treino() {
        return Treino_ID_treino;
    }

    public void setTreino_ID_treino(Integer treino_ID_treino) {
        Treino_ID_treino = treino_ID_treino;
    }
}
