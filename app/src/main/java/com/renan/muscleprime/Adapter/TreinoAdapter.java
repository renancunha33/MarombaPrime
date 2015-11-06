package com.renan.muscleprime.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.renan.muscleprime.R;
import com.renan.muscleprime.Model.Treino;

import java.util.List;

/**
 * Created by Renan on 22/09/2015.
 */
public class TreinoAdapter extends BaseAdapter {

    private Context context;
    private List<Treino> lista;

    public TreinoAdapter(Context ctx, List<Treino> treinos) {
        this.context = ctx;
        this.lista = treinos;
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
        Treino treino = lista.get(position);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lista_treinos, null);
        }
        TextView txtIDtreino = (TextView) view.findViewById(R.id.txt_id_treino);
        TextView txtNomeTreino = (TextView) view.findViewById(R.id.txt_nome_treino);
        TextView txtTempoTreino = (TextView) view.findViewById(R.id.txt_tempo_treino);

        txtIDtreino.setText(treino.get_id()+" - ");
        txtNomeTreino.setText(treino.getNome_treino()+ " - ");
        txtTempoTreino.setText(String.valueOf(treino.getTempo_treino()) + " minutos");

        return view;
    }
}

