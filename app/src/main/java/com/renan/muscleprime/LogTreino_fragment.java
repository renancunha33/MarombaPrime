package com.renan.muscleprime;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.renan.muscleprime.Adapter.LogTreinoAdapter;
import com.renan.muscleprime.DAO.LogTreinoDAO;
import com.renan.muscleprime.DAO.TreinoDAO;
import com.renan.muscleprime.Model.LogTreino;

import java.util.List;

/**
 * Created by Renan on 30/09/2015.
 */
public class LogTreino_fragment extends Fragment implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener {

    View MyView;
    private ListView lista;
    private List<LogTreino> logtreinoList;
    private LogTreinoAdapter logTreinoAdapter;
    private LogTreinoDAO logTreinoDAO;
    int idposicao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.fragment_log, container, (false));
        ((MainActivity) getActivity())
                .setActionBarTitle("Log de treinos");
        final AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        lista = (ListView) MyView.findViewById(R.id.lvLog);
        logTreinoDAO = new LogTreinoDAO(getContext());
        final TreinoDAO treinoDAO = new TreinoDAO(getContext());
        logtreinoList = logTreinoDAO.listarLogTreinos();
        logTreinoAdapter = new LogTreinoAdapter(this.getContext(), logtreinoList);
        lista.setAdapter(logTreinoAdapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                idposicao = position;
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                excluir();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };
                builder.setMessage("Deseja realmente excluir ?").setPositiveButton("Sim", dialogClickListener)
                        .setNegativeButton("Não", dialogClickListener).show();
            }
        });
        return MyView;
    }

    public void excluir() {
        int id = logtreinoList.get(idposicao).get_id();
        logtreinoList.remove(idposicao);
        logTreinoDAO.removerLog_treinos(id);
        lista.invalidateViews();
        Toast.makeText(this.getContext(), "Excluido", Toast.LENGTH_SHORT).show();
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }
}

/*
TextView txtIDtreino = (TextView) view.findViewById(R.id.txt_id_log);
            TextView txtNMtreino = (TextView) view.findViewById(R.id.txt_nm_treino_log);
            TextView txtTPtreino = (TextView) view.findViewById(R.id.txt_tempo_treino_log);

            int idTreino = Integer.valueOf(String.valueOf(txtIDtreino));
            Treino model = treinoDAO.buscarTreinoPorID(idTreino);
            txtNMtreino.setText(model.getNome_treino());
            txtTPtreino.setText(model.getTempo_treino() + " mins");
 */