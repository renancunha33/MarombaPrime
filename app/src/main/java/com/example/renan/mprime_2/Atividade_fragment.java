package com.example.renan.mprime_2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renan.mprime_2.DAO.DatabaseHelper;
import com.example.renan.mprime_2.DAO.LogTreinoDAO;
import com.example.renan.mprime_2.DAO.TreinoDAO;
import com.example.renan.mprime_2.Model.LogTreino;
import com.example.renan.mprime_2.Model.Treino;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Renan on 30/09/2015.
 */
public class Atividade_fragment extends Fragment implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    View MyView;
    TextView txtNome, txtTempo, txtReal, txtData, txtID;
    int teste;
    LogTreinoDAO logTreinoDAO;
    LogTreino logTreino;
    //  MediaPlayer som, som2;
    int x;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.fragment_atividade, container, (false));
        x = 0;
        final Button btDescartar = (Button) MyView.findViewById(R.id.btDescartarAtividade);
        final Button btSalvar = (Button) MyView.findViewById(R.id.btSalvarAtividade);
        final MainActivity main = new MainActivity();
        final Vibrator v = (Vibrator) this.getContext().getSystemService(this.getActivity().VIBRATOR_SERVICE);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final Ringtone r = RingtoneManager.getRingtone(getContext(), notification);
        final Calendar c = Calendar.getInstance();
        final Chronometer ch = (Chronometer) MyView.findViewById(R.id.chronometer);
        final ImageButton imgButton = (ImageButton) MyView.findViewById(R.id.imageButton);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        ch.setText(MainActivity.tempo);
        spinner = (Spinner) MyView.findViewById(R.id.Spinner02);
        txtNome = (TextView) MyView.findViewById(R.id.txt_nm_treino_);
        txtTempo = (TextView) MyView.findViewById(R.id.txt_tempo_treino_);
        txtReal = (TextView) MyView.findViewById(R.id.txt_tempoReal_treino_);
        txtData = (TextView) MyView.findViewById(R.id.txt_data_treino_);
        txtID = (TextView) MyView.findViewById(R.id.id_txt);

        if (!ch.getText().equals("00:00")) {
            spinner.setEnabled(false);
            Toast.makeText(this.getContext(), "Aperte o botão play para continuar a contagem do tempo!", Toast.LENGTH_LONG).show();
            x = 1;
        }
        spinner.setOnItemSelectedListener(this);
        btDescartar.setEnabled(false);
        btSalvar.setEnabled(false);
        loadSpinnerData();

        imgButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(final View button) {
                v.vibrate(100);
                r.play();

                button.setSelected(!button.isSelected());

                if (button.isSelected()) {
                    if (!ch.getText().equals("00:00") && x == 0) {
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        btDescartar.setEnabled(true);
                                        btSalvar.setEnabled(true);
                                        imgButton.setBackgroundResource(R.drawable.pause);
                                        main.chstart(false, ch, r, v);
                                        txtReal.setText(ch.getText());
                                        ch.setText("00:00");
                                        teste = 0;
                                        int day = c.get(Calendar.DAY_OF_MONTH);
                                        int month = c.get(Calendar.MONTH);
                                        int year = c.get(Calendar.YEAR);
                                        txtData.setText(day + "/" + (month + 1) + "/" + year);
                                        imgButton.setEnabled(false);
                                        Toast.makeText(getContext(), "Salve ou descarte a Atividade para treinar novamente! ", Toast.LENGTH_LONG).show();
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        break;
                                }
                            }
                        };
                        builder.setMessage("Deseja realmente parar o treino?").setPositiveButton("Sim", dialogClickListener)
                                .setNegativeButton("Não", dialogClickListener).show();
                    }
                    btDescartar.setEnabled(false);
                    btSalvar.setEnabled(false);
                    spinner.setEnabled(false);
                    imgButton.setBackgroundResource(R.drawable.stop);
                    main.chstart(true, ch, r, v);
                } else {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    btDescartar.setEnabled(true);
                                    btSalvar.setEnabled(true);
                                    imgButton.setBackgroundResource(R.drawable.pause);
                                    main.chstart(false, ch, r, v);
                                    txtReal.setText(ch.getText());
                                    ch.setText("00:00");
                                    teste = 0;
                                    int day = c.get(Calendar.DAY_OF_MONTH);
                                    int month = c.get(Calendar.MONTH);
                                    int year = c.get(Calendar.YEAR);
                                    txtData.setText(day + "/" + (month + 1) + "/" + year);
                                    imgButton.setEnabled(false);
                                    Toast.makeText(getContext(), "Salve ou descarte a Atividade para treinar novamente! ", Toast.LENGTH_LONG).show();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }

                        }

                    };
                    builder.setMessage("Deseja realmente parar o treino?").setPositiveButton("Sim", dialogClickListener)
                            .setNegativeButton("Não", dialogClickListener).show();
                }
                x = 0;
            }
        });

        btDescartar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgButton.setEnabled(true);
                ch.setText("00:00");
                txtReal.setText("--------------");
                txtData.setText("--------------");
                spinner.setEnabled(true);
                btDescartar.setEnabled(false);
                btSalvar.setEnabled(false);
                imgButton.setBackgroundResource(R.drawable.play);
                MainActivity.tempo = "00:00";

            }
        });
        btSalvar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cadastrar();
                imgButton.setEnabled(true);
                ch.setText("00:00");
                txtReal.setText("--------------");
                txtData.setText("--------------");
                spinner.setEnabled(true);
                btDescartar.setEnabled(false);
                btSalvar.setEnabled(false);
                imgButton.setBackgroundResource(R.drawable.play);
                MainActivity.tempo = "00:00";

            }
        });
        setRetainInstance(true);
        return MyView;
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
        txtID = (TextView) MyView.findViewById(R.id.id_txt);
        String idi = spinner.getSelectedItem().toString();
        TreinoDAO treinoDAO = new TreinoDAO(this.getContext());
        Treino model = treinoDAO.buscarTreinoPorNome_e(idi);
        txtNome.setText(model.getNome_treino());
        txtTempo.setText(String.valueOf(model.getTempo_treino()));
        String strProjectId = treinoDAO.buscarTreinoPorNome(idi);
        txtID.setText(String.valueOf(strProjectId));

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void cadastrar() {
        int hora;
        String minS = String.valueOf(txtReal.getText());
        String data = txtData.getText().toString();
        int id = Integer.valueOf(txtID.getText().toString());
        if ((minS.charAt(1)) == ':') {
            hora = (Character.digit(minS.charAt(0), 10) * 60) + (Character.digit(minS.charAt(2), 10) * 10) + Character.digit(minS.charAt(3), 10);
        } else {
            hora = (Character.digit(minS.charAt(0), 10) * 10) + Character.digit(minS.charAt(1), 10);
        }

        logTreinoDAO = new LogTreinoDAO(this.getContext());
        logTreino = new LogTreino();

        logTreino.setData_log(data);
        logTreino.setTempo_real(hora);
        logTreino.setTreino_ID_treino(id);

        long resultado = logTreinoDAO.SalvarLogTreino(logTreino);

        if (resultado != -1 && resultado > 0) {
            Toast.makeText(this.getContext(), "Salvo com sucesso", Toast.LENGTH_SHORT).show();
            // final FragmentTransaction ft = getFragmentManager().beginTransaction();
            // ft.detach(this).attach(this).commit();
        } else {
            Toast.makeText(this.getContext(), "ERRO ao salvar! ID =" + id + "\n mins = " + hora + "\n data = " + data, Toast.LENGTH_LONG).show();
        }

    }

    public void onSaveInstanceState(Bundle outState) {
        setRetainInstance(true);
        super.onSaveInstanceState(outState);
    }

}