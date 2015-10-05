package com.example.renan.mprime_2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.renan.mprime_2.Adapter.TreinoAdapter;
import com.example.renan.mprime_2.DAO.TreinoDAO;
import com.example.renan.mprime_2.Model.Treino;
import com.example.renan.mprime_2.Util.Mensagem;

import java.util.List;

/**
 * Created by Renan on 30/09/2015.
 */
public class Treinos_fragment extends Fragment implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener {

    View MyView;
    private ListView lista;
    private List<Treino> treinoList;
    private TreinoAdapter treinoAdapter;
    private TreinoDAO treinoDAO;
    int idposicao;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.fragment_treinos, container, (false));
        final AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        lista = (ListView) MyView.findViewById(R.id.lvTreino);
        treinoDAO = new TreinoDAO(this.getContext());
        treinoList = treinoDAO.listarTreinos();
        treinoAdapter = new TreinoAdapter(this.getContext(), treinoList);
        lista.setAdapter(treinoAdapter);

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
                builder.setMessage("Deseja realmente excluir ?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
        return MyView;
    }

    public void excluir() {
        int id = treinoList.get(idposicao).get_id();
        treinoList.remove(idposicao);
        treinoDAO.removerTreinos(id);
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
