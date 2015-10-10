package com.example.renan.mprime_2.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

    /*,*/
    public List<LogTreino> listarLogTreinos() {
        Cursor cursor = getDatabase().query(DatabaseHelper.LogTreinos.TABELA, DatabaseHelper.LogTreinos.COLUNAS, null, null, null, null, null);
        List<LogTreino> logtreinos = new ArrayList<LogTreino>();
        Log.v(null, String.valueOf(cursor.getColumnIndex(DatabaseHelper.LogTreinos._ID)));
        Log.v(null, String.valueOf(cursor.getColumnIndex(DatabaseHelper.LogTreinos.DS_TEMPO_REAL_LOG)));
        Log.v(null, String.valueOf(cursor.getColumnIndex(DatabaseHelper.LogTreinos.DT_TREINO_LOG)));
        Log.v(null, String.valueOf(cursor.getColumnIndex(DatabaseHelper.LogTreinos.TREINO_ID_TREINO)));
        while (cursor.moveToNext()) {
            LogTreino model = CriarLogTreino(cursor);
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
            Log.v(null, "AQUIIIIII");
            return getDatabase().insert(DatabaseHelper.LogTreinos.TABELA, null, valores);
        }
    }

    public boolean removerLog_treinos(int id) {
        return getDatabase().delete(DatabaseHelper.LogTreinos.TABELA, "_id = ?", new String[]{Integer.toString(id)}) > 0;

    }

    public List<String[]> inerjoin(int id) {
        Cursor cursor = getDatabase().rawQuery(null, new String[]{"SELECT nm_treino, ds_tempo_treino FROM treinos JOIN log_treinos ON '" + id + "' = treinos._id"});
        List<String[]> loop = new ArrayList<String[]>();
        while (cursor.moveToNext()) {
            String[] model = {String.valueOf(cursor.getString(cursor.getColumnIndex("nm_treino"))), String.valueOf(cursor.getString(cursor.getColumnIndex("ds_tempo_treino")))};
            loop.add(model);
        }
        cursor.close();
        return loop;
    }


    public void fechar() {
        databaseHelper.close();
        database = null;
    }
}
