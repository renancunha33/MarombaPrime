package com.example.renan.mprime_2.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.renan.mprime_2.Model.Exercicio;
import com.example.renan.mprime_2.Model.LogTreino;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Renan on 21/09/2015.
 */
public class LogTreinoDAO {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public LogTreinoDAO(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    private SQLiteDatabase getDatabase() {
        if (database == null) {
            database = databaseHelper.getWritableDatabase();
        }
        return database;
    }

    //  _ID, NM_LogTREINO, DS_TEMPO_LogTREINO
    private LogTreino CriarLogTreino(Cursor cursor) {
        LogTreino model = new LogTreino(
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.LogTreinos._ID)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.LogTreinos.DT_TREINO_LOG)),
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.LogTreinos.DS_TEMPO_REAL_LOG)),
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.LogTreinos.TREINO_ID_TREINO)));
        return model;
    }

    public List<LogTreino> listarLogTreinos() {
        Cursor cursor = getDatabase().query(DatabaseHelper.LogTreinos.TABELA, DatabaseHelper.LogTreinos.COLUNAS, null, null, null, null, null);
        List<LogTreino> logtreinos = new ArrayList<LogTreino>();
        while (cursor.moveToNext()) {

           LogTreino model = CriarLogTreino(cursor);// RESOLVER LISTAGEM MAIS TARDE =====================================!!!!!!!!!!!!!!!!!!!!!
            logtreinos.add(model);
        }
        cursor.close();
        return logtreinos;
    }

    public long SalvarLogTreino(LogTreino Logtreino) {
        ContentValues valores = new ContentValues();
        valores.put(DatabaseHelper.LogTreinos.DT_TREINO_LOG, Logtreino.getData_log());
        valores.put(DatabaseHelper.LogTreinos.DS_TEMPO_REAL_LOG, Logtreino.getTempo_real());
        valores.put(DatabaseHelper.LogTreinos.TREINO_ID_TREINO, Logtreino.getTreino_ID_treino());

        if ((Logtreino.get_id() != null)) {
            return database.update(DatabaseHelper.LogTreinos.TABELA, valores, "_id = ?", new String[]{Logtreino.get_id().toString()});
        } else {
            return getDatabase().insert(DatabaseHelper.LogTreinos.TABELA, null, valores);
        }
    }

    public boolean removerLog_treinos(int id) {
        return getDatabase().delete(DatabaseHelper.LogTreinos.TABELA, "_id = ?", new String[]{Integer.toString(id)}) > 0;

    }

    public LogTreino buscarLogTreinoPorID(int id) {
        Cursor cursor = getDatabase().query(DatabaseHelper.LogTreinos.TABELA,
                DatabaseHelper.LogTreinos.COLUNAS, "_id = ?", new String[]{Integer.toString(id)}, null, null, null);
        if (cursor.moveToNext()) {
            LogTreino model = CriarLogTreino(cursor);
            cursor.close();
            return model;
        }
        return null;
    }
    public List<LogTreino> SelectLogPorID() {
        List<LogTreino> logtreino = new ArrayList<LogTreino>();
        Cursor cursor = getDatabase().rawQuery("SELECT * FROM log_treinos", null);
        // looping through all rows and adding to list
        while (cursor.moveToNext()) {
           // LogTreino model = CriarLogTreino(cursor); ==========================RESOLVER!!!!!!!!!!!!
           // logtreino.add(model);
        }
        cursor.close();
        return  logtreino;
    }

    public void fechar() {
        databaseHelper.close();
        database = null;
    }
}
