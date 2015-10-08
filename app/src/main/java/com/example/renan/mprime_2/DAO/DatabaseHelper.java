package com.example.renan.mprime_2.DAO;
/**
 * Created by Renan on 21/09/2015.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String BANCO_DADOS = "Mprime_db";
    private static final int VERSAO = 13;

    public DatabaseHelper(Context context) {
        super(context, BANCO_DADOS, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //TREINO
        db.execSQL("create table treinos" +
                "(_id integer primary key autoincrement, " +
                "nm_treino text not null," +
                " ds_tempo_treino integer)");

        //EXERCICIOS
        db.execSQL("create table exercicios" +
                "(_id integer primary key autoincrement," +
                "nm_exercicio text not null," +
                "ds_series_exercicio integer not null," +
                "ds_repeticoes_exercicio integer not null," +
                "ds_carga_exercicio integer not null," +
                "Treino_ID_treino integer not null, " +
                "ds_tempo_exercicio integer not null)");

        //LOG_TREINOS
        db.execSQL("create table log_treinos" +
                "(_id integer primary key autoincrement, " +
                "dt_treino_log text not null," +
                "ds_tempo_real_log integer not null," +
                "Treino_id_treino integer not null)");

    }

    //tabela tarefas
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static class Treinos {
        public static final String TABELA = "treinos";
        public static final String _ID = "_id";
        public static final String NM_TREINO = "nm_treino";
        public static final String DS_TEMPO_TREINO = "ds_tempo_treino";

        public static final String[] COLUNAS = new String[]{
                _ID, NM_TREINO, DS_TEMPO_TREINO
        };

    }

    public static class Exercicios {
        public static final String TABELA = "exercicios";
        public static final String _ID = "_id";
        public static final String NM_EXERCICIO = "nm_exercicio";
        public static final String DS_SERIES_EXERCICIO = "ds_series_exercicio";
        public static final String DS_REPETICOES_EXERCICIO = "ds_repeticoes_exercicio";
        public static final String DS_CARGA_EXERCICIO = "ds_carga_exercicio";
        public static final String DS_TEMPO_EXERCICIO = "ds_tempo_exercicio";
        public static final String TREINO_ID_TREINO = "Treino_ID_treino";

        public static final String[] COLUNAS = new String[]{
                _ID, NM_EXERCICIO, DS_SERIES_EXERCICIO, DS_REPETICOES_EXERCICIO, DS_CARGA_EXERCICIO, DS_TEMPO_EXERCICIO, TREINO_ID_TREINO
        };
    }

    public static class LogTreinos {
        public static final String TABELA = "log_treinos";
        public static final String _ID = "_id";
        public static final String DT_TREINO_LOG = "dt_treino_log";
        public static final String DS_TEMPO_REAL_LOG = "ds_tempo_real_log";
        public static final String TREINO_ID_TREINO = "Treino_ID_treino";

        public static final String[] COLUNAS = new String[]{
                _ID, DT_TREINO_LOG, DS_TEMPO_REAL_LOG, TREINO_ID_TREINO
        };
    }

    public List<String> getAllLabels() {
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT nm_treino FROM  treinos";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }

}
