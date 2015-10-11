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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renan.mprime_2.Adapter.ExercicioAdapter;
import com.example.renan.mprime_2.DAO.DatabaseHelper;
import com.example.renan.mprime_2.DAO.ExercicioDAO;
import com.example.renan.mprime_2.DAO.TreinoDAO;
import com.example.renan.mprime_2.Model.Exercicio;
import com.example.renan.mprime_2.Model.Treino;

import java.util.List;

/**
 * Created by Renan on 30/09/2015.
 */
public class Exercicio_fragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, DialogInterface.OnClickListener {
    int idposicao, menos, idd = 0, idestrangeiro, tempo;
    private ListView lista;
    Spinner spinner;
    View MyView;
    EditText edtID;
    TreinoDAO treinoDAO;
    ExercicioDAO exercicioDAO;
    Exercicio exercicio, model;
    EditText txtNomeExec, txtSerieExec, txtRepetExec, txtCargaExec, txtTempo;
    ExercicioAdapter exercicioAdapter;
    private List<Exercicio> exercicioList;
    TextView txtidExec;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.fragment_exercicio, container, (false));
        ((MainActivity) getActivity())
                .setActionBarTitle("Exercicio");
        model = new Exercicio();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        spinner = (Spinner) MyView.findViewById(R.id.Spinner01);
        spinner.setOnItemSelectedListener(this);
        edtID = (EditText) MyView.findViewById(R.id.edtTreinoRep);
        lista = (ListView) MyView.findViewById(R.id.lvExec);
        spinner = (Spinner) MyView.findViewById(R.id.Spinner01);
        spinner.setOnItemSelectedListener(this);
        //edtID = (EditText) MyView.findViewById(R.id.edtTreinoRep);
        Button btSalvar = (Button) MyView.findViewById(R.id.btSalvarExercicio);
        loadSpinnerData();

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtualizarTreino();
                if (idd == 0) {
                    cadastrar();
                    Log.v(null,"CAFEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
                  //  AtualizarTreino();

                } else {
                    String idi = spinner.getSelectedItem().toString();
                    String strProjectId = treinoDAO.buscarTreinoPorNome(idi);
                    treinoDAO = new TreinoDAO(getContext());
                    Treino treino = treinoDAO.buscarTreinoPorID(Integer.valueOf(strProjectId));
                    treino.set_id(treino.get_id());
                    treino.setNome_treino(treino.getNome_treino());
                    treino.setTempo_treino(Integer.valueOf(treino.getTempo_treino()) - tempo); //TEMPORário!!!!
                    long resultado = treinoDAO.SalvarTreino(treino);

                    if (resultado != -1 && resultado > 0) {
                        // Toast.makeText(this.getContext(), "Atualizado com sucesso", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "ERRO ao Atualizar treino!", Toast.LENGTH_SHORT).show();
                    }
                    atualizarExercicio(idd);
                    idd = 0;
                    tempo = 0;
                }

            }
        });
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                idposicao = position;
                txtidExec = (TextView) view.findViewById(R.id.txt_id_exec);
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                try {
                                    if (String.valueOf(edtID.getText()).equals("ID")) {
                                        Toast.makeText(getContext(), "CRIE UM TREINO!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Toast.makeText(getContext(), txtidExec.getText(), Toast.LENGTH_SHORT).show();
                                        idposicao = position;
                                        model = exercicioDAO.buscarExercicioPorID(Integer.valueOf((String) txtidExec.getText()));
                                        menos = model.getTempo_exercicio();
                                        excluir(menos);
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(getContext(), "ERRO ao salvar!" + txtidExec.getText() + "\n"
                                            , Toast.LENGTH_SHORT).show();
                                }
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clickedvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
                                txtNomeExec = (EditText) MyView.findViewById(R.id.edtExercicio);
                                txtSerieExec = (EditText) MyView.findViewById(R.id.edtSeries);
                                txtRepetExec = (EditText) MyView.findViewById(R.id.edtRepeticoes);
                                txtCargaExec = (EditText) MyView.findViewById(R.id.edtCarga);
                                txtTempo = (EditText) MyView.findViewById(R.id.edtTempo);

                                idd = exercicioList.get(idposicao).get_id();
                                txtNomeExec.setText(exercicioList.get(idposicao).getNome_exercicio());
                                txtSerieExec.setText(String.valueOf(exercicioList.get(idposicao).getSerie_exercicio()));
                                txtRepetExec.setText(String.valueOf(exercicioList.get(idposicao).getRepeticoes_exercicio()));
                                txtCargaExec.setText(String.valueOf(exercicioList.get(idposicao).getCarga_exercicio()));
                                txtTempo.setText(String.valueOf(exercicioList.get(idposicao).getTempo_exercicio()));
                                idestrangeiro = exercicioList.get(idposicao).getTreino_ID_treino();
                                tempo = exercicioList.get(idposicao).getTempo_exercicio();
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

        txtNomeExec = (EditText) MyView.findViewById(R.id.edtExercicio);
        txtSerieExec = (EditText) MyView.findViewById(R.id.edtSeries);
        txtRepetExec = (EditText) MyView.findViewById(R.id.edtRepeticoes);
        txtCargaExec = (EditText) MyView.findViewById(R.id.edtCarga);
        txtTempo = (EditText) MyView.findViewById(R.id.edtTempo);
        String exercicioNome = String.valueOf(txtNomeExec.getText());
        if (exercicioNome == null || exercicioNome.equals("")) {
            validacao = false;
            txtNomeExec.setError("Campo Obrigatório");
        } else {
            validacao = true;
        }
        String exercicioSerie = String.valueOf(txtSerieExec.getText());
        if (exercicioSerie == null || exercicioSerie.equals("")) {
            validacao = false;
            txtSerieExec.setError("Campo Obrigatório");
        } else {
            validacao = true;
        }
        String exercicioRepet = String.valueOf(txtRepetExec.getText());
        if (exercicioRepet == null || exercicioRepet.equals("")) {
            validacao = false;
            txtRepetExec.setError("Campo Obrigatório");
        } else {
            validacao = true;
        }
        String exercicioTempo = String.valueOf(txtTempo.getText());
        if (exercicioTempo == null || exercicioTempo.equals("")) {
            validacao = false;
            txtTempo.setError("Campo Obrigatório");
        } else {
            validacao = true;
        }
        String exercicioCarga = String.valueOf(txtCargaExec.getText());
        if (exercicioCarga == null || exercicioTempo.equals("")) {
            validacao = false;
            txtCargaExec.setError("Campo Obrigatório");
        } else {
            validacao = true;
        }

        if (validacao) {
            exercicioDAO = new ExercicioDAO(this.getContext());
            exercicio = new Exercicio();
            exercicio.setTreino_ID_treino(Integer.valueOf(String.valueOf(edtID.getText())));
            exercicio.setNome_exercicio(String.valueOf(txtNomeExec.getText()));
            exercicio.setSerie_exercicio(Integer.valueOf(String.valueOf(txtSerieExec.getText())));
            exercicio.setRepeticoes_exercicio(Integer.valueOf(String.valueOf(txtRepetExec.getText())));
            exercicio.setCarga_exercicio(Integer.valueOf(String.valueOf(txtCargaExec.getText())));
            exercicio.setTempo_exercicio(Integer.valueOf(String.valueOf(txtTempo.getText())));

//            try {
            resultado = exercicioDAO.SalvarExercicio(exercicio);
//            } catch (Exception e) {
//                Toast.makeText(this.getContext(), String.valueOf(resultado), Toast.LENGTH_SHORT).show();
//            }

            if (resultado != -1 && resultado > 0) {
                Toast.makeText(this.getContext(), "Salvo com sucesso", Toast.LENGTH_SHORT).show();
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();
            } else {
                Toast.makeText(this.getContext(), "ERRO ao salvar!", Toast.LENGTH_SHORT).show();
            }

            txtNomeExec.setText("");
            txtSerieExec.setText("");
            txtRepetExec.setText("");
            txtCargaExec.setText("");
            txtCargaExec.setText("");
            txtTempo.setText("");
        }

    }

    private void loadSpinnerData() {
        // database handler
        DatabaseHelper db = new DatabaseHelper(this.getContext());
        // Spinner Drop down elements
        List<String> lables = db.getAllLabels();
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, lables);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String idi = spinner.getSelectedItem().toString();
        treinoDAO = new TreinoDAO(this.getContext());
        String strProjectId = treinoDAO.buscarTreinoPorNome(idi);
        edtID.setText(String.valueOf(strProjectId));

        exercicioDAO = new ExercicioDAO(this.getActivity());
        exercicioList = exercicioDAO.SelectExerciciosPorID(Integer.valueOf(strProjectId));
        exercicioAdapter = new ExercicioAdapter(this.getContext(), exercicioList);
        lista.setAdapter(exercicioAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onClick(View v) {
        int aba = Integer.valueOf(String.valueOf(edtID.getText()));
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
        edtID.setText(String.valueOf(aba));
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
    }

    public void AtualizarTreino() {
        long resultado = 0;
        boolean validacao;

        txtNomeExec = (EditText) MyView.findViewById(R.id.edtExercicio);
        txtSerieExec = (EditText) MyView.findViewById(R.id.edtSeries);
        txtRepetExec = (EditText) MyView.findViewById(R.id.edtRepeticoes);
        txtCargaExec = (EditText) MyView.findViewById(R.id.edtCarga);
        txtTempo = (EditText) MyView.findViewById(R.id.edtTempo);
        String exercicioNome = String.valueOf(txtNomeExec.getText());
        if (exercicioNome == null || exercicioNome.equals("")) {
            validacao = false;
            txtNomeExec.setError("Campo Obrigatório");
        } else {
            validacao = true;
        }
        String exercicioSerie = String.valueOf(txtSerieExec.getText());
        if (exercicioSerie == null || exercicioSerie.equals("")) {
            validacao = false;
            txtSerieExec.setError("Campo Obrigatório");
        } else {
            validacao = true;
        }
        String exercicioRepet = String.valueOf(txtRepetExec.getText());
        if (exercicioRepet == null || exercicioRepet.equals("")) {
            validacao = false;
            txtRepetExec.setError("Campo Obrigatório");
        } else {
            validacao = true;
        }
        String exercicioTempo = String.valueOf(txtTempo.getText());
        if (exercicioTempo == null || exercicioTempo.equals("")) {
            validacao = false;
            txtTempo.setError("Campo Obrigatório");
        } else {
            validacao = true;
        }
        String exercicioCarga = String.valueOf(txtCargaExec.getText());
        if (exercicioCarga == null || exercicioTempo.equals("")) {
            validacao = false;
            txtCargaExec.setError("Campo Obrigatório");
        } else {
            validacao = true;
        }
        if (validacao) {
            txtTempo = (EditText) MyView.findViewById(R.id.edtTempo);
            String idi = spinner.getSelectedItem().toString();
            treinoDAO = new TreinoDAO(this.getContext());
            String strProjectId = treinoDAO.buscarTreinoPorNome(idi);
            treinoDAO = new TreinoDAO(this.getContext());
            Treino treino = treinoDAO.buscarTreinoPorID(Integer.valueOf(strProjectId));
            treino.set_id(treino.get_id());
            treino.setNome_treino(treino.getNome_treino());
            treino.setTempo_treino(Integer.valueOf(treino.getTempo_treino()) + Integer.valueOf(String.valueOf(txtTempo.getText()))); //TEMPORário!!!!
            resultado = treinoDAO.SalvarTreino(treino);

            if (resultado != -1 && resultado > 0) {
                // Toast.makeText(this.getContext(), "Atualizado com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this.getContext(), "ERRO ao Atualizar treino!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void excluir(int menos) {
        long resultado = 0;
        treinoDAO = new TreinoDAO(this.getContext());
        String idi = spinner.getSelectedItem().toString();
        String strProjectId = treinoDAO.buscarTreinoPorNome(idi);
        Treino treino = treinoDAO.buscarTreinoPorID(Integer.valueOf(strProjectId));
        treino.set_id(treino.get_id());
        treino.setNome_treino(treino.getNome_treino());
        treino.setTempo_treino(treino.getTempo_treino() - menos);
        resultado = treinoDAO.SalvarTreino(treino);

        if (resultado != -1 && resultado > 0) {
            //  Toast.makeText(this.getContext(), "Atualizado com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this.getContext(), "ERRO ao Atualizar treino!", Toast.LENGTH_SHORT).show();
        }
        int id = exercicioList.get(idposicao).get_id();
        exercicioList.remove(idposicao);
        exercicioDAO.removerExercicios(id);
        lista.invalidateViews();
        Toast.makeText(this.getContext(), "Excluido", Toast.LENGTH_SHORT).show();
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    public void atualizarExercicio(int idd) {
        long resultado = 0;
        boolean validacao;

        txtNomeExec = (EditText) MyView.findViewById(R.id.edtExercicio);
        txtSerieExec = (EditText) MyView.findViewById(R.id.edtSeries);
        txtRepetExec = (EditText) MyView.findViewById(R.id.edtRepeticoes);
        txtCargaExec = (EditText) MyView.findViewById(R.id.edtCarga);
        txtTempo = (EditText) MyView.findViewById(R.id.edtTempo);
        String exercicioNome = String.valueOf(txtNomeExec.getText());
        if (exercicioNome == null || exercicioNome.equals("")) {
            validacao = false;
            txtNomeExec.setError("Campo Obrigatório");
        } else {
            validacao = true;
        }
        String exercicioSerie = String.valueOf(txtSerieExec.getText());
        if (exercicioSerie == null || exercicioSerie.equals("")) {
            validacao = false;
            txtSerieExec.setError("Campo Obrigatório");
        } else {
            validacao = true;
        }
        String exercicioRepet = String.valueOf(txtRepetExec.getText());
        if (exercicioRepet == null || exercicioRepet.equals("")) {
            validacao = false;
            txtRepetExec.setError("Campo Obrigatório");
        } else {
            validacao = true;
        }
        String exercicioTempo = String.valueOf(txtTempo.getText());
        if (exercicioTempo == null || exercicioTempo.equals("")) {
            validacao = false;
            txtTempo.setError("Campo Obrigatório");
        } else {
            validacao = true;
        }
        String exercicioCarga = String.valueOf(txtCargaExec.getText());
        if (exercicioCarga == null || exercicioTempo.equals("")) {
            validacao = false;
            txtCargaExec.setError("Campo Obrigatório");
        } else {
            validacao = true;
        }

        if (validacao) {
            exercicioDAO = new ExercicioDAO(this.getContext());
            exercicio = new Exercicio();
            exercicio.set_id(idd);
            exercicio.setTreino_ID_treino(Integer.valueOf(String.valueOf(edtID.getText())));
            exercicio.setNome_exercicio(String.valueOf(txtNomeExec.getText()));
            exercicio.setSerie_exercicio(Integer.valueOf(String.valueOf(txtSerieExec.getText())));
            exercicio.setRepeticoes_exercicio(Integer.valueOf(String.valueOf(txtRepetExec.getText())));
            exercicio.setCarga_exercicio(Integer.valueOf(String.valueOf(txtCargaExec.getText())));
            exercicio.setTempo_exercicio(Integer.valueOf(String.valueOf(txtTempo.getText())));

//            try {
            resultado = exercicioDAO.SalvarExercicio(exercicio);
//            } catch (Exception e) {
//                Toast.makeText(this.getContext(), String.valueOf(resultado), Toast.LENGTH_SHORT).show();
//            }

            if (resultado != -1 && resultado > 0) {
                Toast.makeText(this.getContext(), "Atualizado com sucesso", Toast.LENGTH_SHORT).show();
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();
            } else {
                Toast.makeText(this.getContext(), "ERRO ao salvar!", Toast.LENGTH_SHORT).show();
            }

            txtNomeExec.setText("");
            txtSerieExec.setText("");
            txtRepetExec.setText("");
            txtCargaExec.setText("");
            txtCargaExec.setText("");
            txtTempo.setText("");
        }

    }

}