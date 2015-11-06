package com.renan.muscleprime.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.renan.muscleprime.R;
import com.renan.muscleprime.DAO.TreinoDAO;
import com.renan.muscleprime.Model.LogTreino;
import com.renan.muscleprime.Model.Treino;

import java.util.List;

/**
 * Created by Renan on 22/09/2015.
 */
public class LogTreinoAdapter extends BaseAdapter {
    private TreinoDAO treinoDAO;
    private Context context;
    private List<LogTreino> lista;
    Treino model;

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
            view = inflater.inflate(R.layout.lista_log, null);
            TextView txtNMtreino = (TextView) view.findViewById(R.id.txt_nm_treino_log);
            TextView txtTPtreino = (TextView) view.findViewById(R.id.txt_tempo_treino_log);
            Log.v(null, String.valueOf(logTreino.getTreino_ID_treino()));
            try {
                treinoDAO = new TreinoDAO(context);
                model = treinoDAO.buscarTreinoPorID(logTreino.getTreino_ID_treino());
                txtNMtreino.setText("( " + model.getNome_treino());
                txtTPtreino.setText(model.getTempo_treino() + "mins )");
            } catch (Exception e) {

                Log.v(null, e.toString());
            }
        }


        TextView txtDataLog = (TextView) view.findViewById(R.id.txt_data_log);
        TextView txtID = (TextView) view.findViewById(R.id.txt_id_log);
        TextView txtTempoLog = (TextView) view.findViewById(R.id.txt_tempo_log);
        //TextView txtNMtreino = (TextView) view.findViewById(R.id.txt_nm_treino_log);
        //TextView txtTPtreino = (TextView) view.findViewById(R.id.txt_tempo_treino_log);


        txtDataLog.setText(logTreino.getData_log());
        txtTempoLog.setText(String.valueOf("real: " + logTreino.getTempo_real()) + "mins");
        txtID.setText(String.valueOf(logTreino.getTreino_ID_treino()));

        return view;
    }

}
