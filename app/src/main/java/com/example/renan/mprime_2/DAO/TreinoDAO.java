package com.example.renan.mprime_2.DAO;

/**
 * Created by Renan on 21/09/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.TextView;

import com.example.renan.mprime_2.Model.Treino;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renancunha on 30/08/15.
 */
public class TreinoDAO {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public TreinoDAO(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    private SQLiteDatabase getDatabase() {
        if (database == null) {
            database = databaseHelper.getWritableDatabase();
        }
        return database;
    }

    //  _ID, NM_TREINO, DS_TEMPO_TREINO
    private Treino CriarTreino(Cursor cursor) {
        Treino model = new Treino(
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Treinos._ID)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Treinos.NM_TREINO)),
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Treinos.DS_TEMPO_TREINO)));
        return model;
    }

    public List<Treino> listarTreinos() {
        Cursor cursor = getDatabase().query(DatabaseHelper.Treinos.TABELA, DatabaseHelper.Treinos.COLUNAS, null, null, null, null, null);
        List<Treino> treinos = new ArrayList<Treino>();
        while (cursor.moveToNext()) {
            Treino model = CriarTreino(cursor);
            treinos.add(model);
        }
        cursor.close();
        return treinos;
    }

    public long SalvarTreino(Treino treino) {
        ContentValues valores = new ContentValues();
        valores.put(DatabaseHelper.Treinos.NM_TREINO, treino.getNome_treino());
        valores.put(DatabaseHelper.Treinos.DS_TEMPO_TREINO, treino.getTempo_treino());
        if ((treino.get_id() != null)) {
            return database.update(DatabaseHelper.Treinos.TABELA, valores, "_id = ?", new String[]{treino.get_id().toString()});
        } else {
            return getDatabase().insert(DatabaseHelper.Treinos.TABELA, null, valores);
        }
    }

    public boolean removerTreinos(int id) {
        return getDatabase().delete(DatabaseHelper.Treinos.TABELA, "_id = ?", new String[]{Integer.toString(id)}) > 0;

    }

    public Treino buscarTreinoPorID(int id) {
        Cursor cursor = getDatabase().query(DatabaseHelper.Treinos.TABELA,
                DatabaseHelper.Treinos.COLUNAS, "_id = ?", new String[]{Integer.toString(id)}, null, null, null);
        if (cursor.moveToNext()) {
            Treino model = CriarTreino(cursor);
            cursor.close();
            return model;
        }
        return null;
    }

    public String buscarTreinoPorNome(String nome) {
        Cursor mCursor = getDatabase().rawQuery("SELECT _id  FROM treinos WHERE nm_treino = '" + nome + "'", null);
        if (mCursor != null) {
            mCursor.moveToFirst();
            int projectId = mCursor.getInt(0);
            String strProjectId = String.valueOf(projectId);
            return strProjectId;
        }
        return null;
    }

    public Treino buscarTreinoPorNome_e(String nome) {
        Cursor mCursor = getDatabase().rawQuery("SELECT *  FROM treinos WHERE nm_treino = '" + nome + "'", null);
        if (mCursor.moveToNext()) {
            Treino model = CriarTreino(mCursor);
            mCursor.close();
            return model;
        }
        return null;
    }

    public void fechar() {
        databaseHelper.close();
        database = null;
    }
}
