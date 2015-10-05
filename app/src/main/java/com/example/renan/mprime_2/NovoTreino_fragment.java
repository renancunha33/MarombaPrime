package com.example.renan.mprime_2;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
    private TreinoDAO treinoDAO;
    private String treinoNome;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.fragment_novotreino, container, (false));
        Button SalvaTreino = (Button) MyView.findViewById(R.id.button_salvarTreino);
        SalvaTreino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
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
            treino.setTempo_treino(00); //TEMPORário!!!!
//            try {
            resultado = treinoDAO.SalvarTreino(treino);
//            } catch (Exception e) {
//                Toast.makeText(this.getContext(), String.valueOf(resultado), Toast.LENGTH_SHORT).show();
//            }

            if (resultado != -1 && resultado > 0) {
                Toast.makeText(this.getContext(), "Salvo com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this.getContext(), "ERRO ao salvar!", Toast.LENGTH_SHORT).show();
            }
            edtTreino.setText("");
        }
    }
}


