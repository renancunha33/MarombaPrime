package com.example.renan.mprime_2.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.renan.mprime_2.Model.Exercicio;
import com.example.renan.mprime_2.Model.LogTreino;
import com.example.renan.mprime_2.R;

import java.util.List;

/**
 * Created by Renan on 22/09/2015.
 */
public class ExercicioAdapter extends BaseAdapter {

    private Context context;
    private List<Exercicio> lista;

    public ExercicioAdapter(Context ctx, List<Exercicio> exercicios) {
        this.context = ctx;
        this.lista = exercicios;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Exercicio exercicio = lista.get(position);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lista_exercicios, null);
        }
        TextView txtidExec = (TextView) view.findViewById(R.id.txt_id_exec);
        TextView txtNomeExec = (TextView) view.findViewById(R.id.txt_nome_exec);
        TextView txtRepetExec = (TextView) view.findViewById(R.id.txt_repet_exec);
        TextView txtSerieExec = (TextView) view.findViewById(R.id.txt_serie_exec);
        TextView txtCargaExec = (TextView) view.findViewById(R.id.txt_carga_exec);
        TextView txtTempoExec = (TextView) view.findViewById(R.id.txt_tempo_exec);

        txtidExec.setText(String.valueOf(exercicio.get_id()));
        txtNomeExec.setText(exercicio.getNome_exercicio());
        txtRepetExec.setText(String.valueOf(exercicio.getRepeticoes_exercicio()));
        txtSerieExec.setText(String.valueOf(exercicio.getSerie_exercicio()));
        txtCargaExec.setText(String.valueOf(exercicio.getCarga_exercicio())+" Kg ");
        txtTempoExec.setText(String.valueOf(exercicio.getTempo_exercicio())+" mins ");

        return view;
    }
}
