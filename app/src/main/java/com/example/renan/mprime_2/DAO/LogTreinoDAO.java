package com.example.renan.mprime_2.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Log_treinos._ID)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Log_treinos.DS_TEMPO_REAL_LOG)),
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Log_treinos.DT_TEMPO_TREINO)),
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Log_treinos.TREINO_ID_TREINO))
        );
        return model;
    }

    public List<LogTreino> listarLog_treinos() {
        Cursor cursor = getDatabase().query(DatabaseHelper.Log_treinos.TABELA,
                DatabaseHelper.Log_treinos.COLUNAS, null, null, null, null, null);
        List<LogTreino> Log_treinos = new ArrayList<LogTreino>();
        while (cursor.moveToNext()) {
            LogTreino model = CriarLogTreino(cursor);
            Log_treinos.add(model);
        }
        cursor.close();
        return Log_treinos;
    }

    public long SalvarLogTreino(LogTreino Logtreino) {
        ContentValues valores = new ContentValues();
        valores.put(DatabaseHelper.Log_treinos.DS_TEMPO_REAL_LOG, Logtreino.getTempo_real());
        valores.put(DatabaseHelper.Log_treinos.DT_TEMPO_TREINO, Logtreino.getData_log());
        valores.put(DatabaseHelper.Log_treinos.TREINO_ID_TREINO, Logtreino.getTreino_ID_treino());

        if ((Logtreino.get_id() != null)) {
            return database.update(DatabaseHelper.Log_treinos.TABELA, valores, "_id = ?", new String[]{Logtreino.get_id().toString()});
        } else {
            return getDatabase().insert(DatabaseHelper.Log_treinos.TABELA, null, valores);
        }
    }

    public boolean removerLog_treinos(int id) {
        return getDatabase().delete(DatabaseHelper.Log_treinos.TABELA, "_id = ?", new String[]{Integer.toString(id)}) > 0;

    }

    public LogTreino buscarLogTreinoPorID(int id) {
        Cursor cursor = getDatabase().query(DatabaseHelper.Log_treinos.TABELA,
                DatabaseHelper.Log_treinos.COLUNAS, "_id = ?", new String[]{Integer.toString(id)}, null, null, null);
        if (cursor.moveToNext()) {
            LogTreino model = CriarLogTreino(cursor);
            cursor.close();
            return model;
        }
        return null;
    }

    public void fechar() {
        databaseHelper.close();
        database = null;
    }
}
