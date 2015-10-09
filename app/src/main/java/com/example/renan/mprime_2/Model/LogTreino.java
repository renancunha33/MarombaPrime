package com.example.renan.mprime_2.Model;

/**
 * Created by Renan on 21/09/2015.
 */
public class LogTreino {

    private Integer _id;
    private String data_log;
    private int tempo_real;
    private int treino_ID_treino;

    public LogTreino() {
    }

    public LogTreino(Integer _id, String data_log, int tempo_real, int treino_ID_treino ) {
        this._id = _id;
        this.data_log = data_log;
        this.tempo_real = tempo_real;
        this.treino_ID_treino = treino_ID_treino;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getData_log() {
        return data_log;
    }

    public void setData_log(String data_log) {
        this.data_log = data_log;
    }

    public int getTempo_real() {
        return tempo_real;
    }

    public void setTempo_real(int tempo_real) {
        this.tempo_real = tempo_real;
    }

    public int getTreino_ID_treino() {
        return treino_ID_treino;
    }

    public void setTreino_ID_treino(Integer treino_ID_treino) {
        this.treino_ID_treino = treino_ID_treino;
    }
}
