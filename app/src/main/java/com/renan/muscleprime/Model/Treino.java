package com.renan.muscleprime.Model;

/**
 * Created by Renan on 21/09/2015.
 */
public class Treino {
    private Integer _id;
    private String nome_treino;
    private int tempo_treino;

   public Treino() {
    }

    public Treino(Integer _id, String nome_treino, int tempo_treino) {
        this._id = _id;
        this.nome_treino = nome_treino;
        this.tempo_treino = tempo_treino;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getNome_treino() {
        return nome_treino;
    }

    public void setNome_treino(String nome_treino) {
        this.nome_treino = nome_treino;
    }

    public int getTempo_treino() {
        return tempo_treino;
    }

    public void setTempo_treino(int tempo_treino) {
        this.tempo_treino = tempo_treino;
    }


}
