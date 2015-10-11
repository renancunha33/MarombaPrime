package com.example.renan.mprime_2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renan.mprime_2.Adapter.TreinoAdapter;
import com.example.renan.mprime_2.DAO.TreinoDAO;
import com.example.renan.mprime_2.Model.Treino;

import java.util.List;

/**
 * Created by Renan on 30/09/2015.
 */
public class NovoTreino_fragment extends Fragment {
    private View MyView;
    private EditText edtTreino;
    private Treino treino;
    private String treinoNome;
    private ListView lista;
    private List<Treino> treinoList;
    private TreinoAdapter treinoAdapter;
    private TreinoDAO treinoDAO;
    int idposicao, idd = 0, treinoTempo;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.fragment_novotreino, container, (false));
        Button SalvaTreino = (Button) MyView.findViewById(R.id.button_salvarTreino);
        ((MainActivity) getActivity())
                .setActionBarTitle("Treinos");


        SalvaTreino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(null, String.valueOf(idd));
                if (idd == 0) {
                    cadastrar();
                } else {
                    atualizar(idd, treinoTempo);
                    idd = 0;
                    treinoTempo = 0;
                }
            }
        });
        final AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        lista = (ListView) MyView.findViewById(R.id.lvTreino2);
        treinoDAO = new TreinoDAO(this.getContext());
        treinoList = treinoDAO.listarTreinos();
        treinoAdapter = new TreinoAdapter(this.getContext(), treinoList);
        lista.setAdapter(treinoAdapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                idposicao = position;
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                excluir();

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                EditText edtTreino = (EditText) MyView.findViewById(R.id.edtNomeTreino);
                                Log.v(null, String.valueOf(treinoList.get(idposicao).get_id()));
                                idd = treinoList.get(idposicao).get_id();
                                treinoTempo = treinoList.get(idposicao).getTempo_treino();
                                edtTreino.setText(treinoList.get(idposicao).getNome_treino());

                                break;
                        }
                    }
                };
                builder.setMessage("O que deseja fazer?").setPositiveButton("Excluir", dialogClickListener)
                        .setNegativeButton("Alterar", dialogClickListener).show();
            }
        });
        return MyView;
    }

    public void cadastrar() {
        long resultado = 0;
        boolean validacao;
        edtTreino = (EditText) MyView.findViewById(R.id.edtNomeTreino);
        treinoNome = String.valueOf(edtTreino.getText());

        if (treinoNome == null || treinoNome.equals("")) {
            validacao = false;
            edtTreino.setError("Campo obrigatório");
        } else {
            validacao = true;
        }

        if (validacao) {
            treinoDAO = new TreinoDAO(this.getContext());
            treino = new Treino();
            treino.setNome_treino(treinoNome);
            treino.setTempo_treino(00);
//
            resultado = treinoDAO.SalvarTreino(treino);
//
            if (resultado != -1 && resultado > 0) {
                Toast.makeText(this.getContext(), "Salvo com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this.getContext(), "ERRO ao salvar!", Toast.LENGTH_SHORT).show();
            }
            final FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
            edtTreino.setText("");
        }
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

    public void atualizar(int id, int treinoTempo) {
        long resultado = 0;
        boolean validacao;
        edtTreino = (EditText) MyView.findViewById(R.id.edtNomeTreino);
        treinoNome = String.valueOf(edtTreino.getText());

        if (treinoNome == null || treinoNome.equals("")) {
            validacao = false;
            edtTreino.setError("Campo obrigatório");
        } else {
            validacao = true;
        }

        if (validacao) {
            treinoDAO = new TreinoDAO(this.getContext());
            treino = new Treino();
            treino.set_id(id);
            treino.setNome_treino(treinoNome);
            treino.setTempo_treino(treinoTempo); //TEMPORário!!!!
//
            resultado = treinoDAO.SalvarTreino(treino);
//
            if (resultado != -1 && resultado > 0) {
                Toast.makeText(this.getContext(), "Atualizado com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this.getContext(), "ERRO ao salvar!", Toast.LENGTH_SHORT).show();
            }
            final FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
            edtTreino.setText("");
        }
    }
}


