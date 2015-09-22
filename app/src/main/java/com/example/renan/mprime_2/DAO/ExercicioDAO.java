package com.example.renan.mprime_2.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.renan.mprime_2.Model.Exercicio;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Renan on 21/09/2015.
 */
public class ExercicioDAO {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public ExercicioDAO(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    private SQLiteDatabase getDatabase() {
        if (database == null) {
            database = databaseHelper.getWritableDatabase();
        }
        return database;
    }
    //    _ID, NM_EXERCICIO, DS_SERIES_EXERCICIO, DS_REPETICOES_EXERCICIO,
    //    DS_CARGA_EXERCICIO, DS_TEMPO_EXERCICIO, TREINO_ID_TREINO

    private Exercicio CriarExercicio(Cursor cursor) {
        Exercicio model = new Exercicio(
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Exercicios._ID)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Exercicios.DS_SERIES_EXERCICIO)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Exercicios.DS_REPETICOES_EXERCICIO)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Exercicios.DS_CARGA_EXERCICIO)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Exercicios.DS_TEMPO_EXERCICIO)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Exercicios.TREINO_ID_TREINO)));
        return model;
    }

    public List<Exercicio> listarExercicios() {
        Cursor cursor = getDatabase().query(DatabaseHelper.Exercicios.TABELA,
                DatabaseHelper.Exercicios.COLUNAS, null, null, null, null, null);
        List<Exercicio> Exercicios = new ArrayList<Exercicio>();
        while (cursor.moveToNext()) {
            Exercicio model = CriarExercicio(cursor);
            Exercicios.add(model);
        }
        cursor.close();
        return Exercicios;
    }

    public long SalvarExercicio(Exercicio exercicio) {
        ContentValues valores = new ContentValues();
        valores.put(DatabaseHelper.Exercicios.DS_SERIES_EXERCICIO, exercicio.getSerie_exercicio());
        valores.put(DatabaseHelper.Exercicios.DS_REPETICOES_EXERCICIO, exercicio.getRepeticoes_exercicio());
        valores.put(DatabaseHelper.Exercicios.DS_CARGA_EXERCICIO, exercicio.getCarga_exercicio());
        valores.put(DatabaseHelper.Exercicios.DS_TEMPO_EXERCICIO, exercicio.getTempo_exercicio());
        valores.put(DatabaseHelper.Exercicios.TREINO_ID_TREINO, exercicio.getTreino_ID_treino());

        if ((exercicio.get_id() != null)) {
            return database.update(DatabaseHelper.Exercicios.TABELA, valores, "_id = ?", new String[]{exercicio.get_id().toString()});
        } else {
            return getDatabase().insert(DatabaseHelper.Exercicios.TABELA, null, valores);
        }
    }

    public boolean removerExercicios(int id) {
        return getDatabase().delete(DatabaseHelper.Exercicios.TABELA, "_id = ?", new String[]{Integer.toString(id)}) > 0;

    }

    public Exercicio buscarExercicioPorID(int id) {
        Cursor cursor = getDatabase().query(DatabaseHelper.Exercicios.TABELA,
                DatabaseHelper.Exercicios.COLUNAS, "_id = ?", new String[]{Integer.toString(id)}, null, null, null);
        if (cursor.moveToNext()) {
            Exercicio model = CriarExercicio(cursor);
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
