package com.example.renan.mprime_2.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.renan.mprime_2.Model.LogTreino;
import com.example.renan.mprime_2.Model.Treino;
import com.example.renan.mprime_2.R;

import java.util.List;

/**
 * Created by Renan on 22/09/2015.
 */
public class LogTreinoAdapter extends BaseAdapter {

    private Context context;
    private List<LogTreino> lista;

    public LogTreinoAdapter(Context ctx, List<LogTreino> logTreinos) {
        this.context = ctx;
        this.lista = logTreinos;
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
        LogTreino logTreino = lista.get(position);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.fragment_log, null);
        }
        TextView txtDataLog = (TextView) view.findViewById(R.id.txt_data_log);
        TextView txtNomeTreinoLog = (TextView) view.findViewById(R.id.txt_nm_treino_log);
        TextView txtTempoTreinoLog = (TextView) view.findViewById(R.id.txt_tempo_treino_log);
        TextView txtTempoLog = (TextView) view.findViewById(R.id.txt_tempo_log);

        txtDataLog.setText(logTreino.getData_log());
        //ver como pegar os valores pelo ID do treino
        txtTempoLog.setText(logTreino.getTempo_real());
        return view;
    }
}
