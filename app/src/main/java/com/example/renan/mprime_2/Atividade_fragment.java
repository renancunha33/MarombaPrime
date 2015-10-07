package com.example.renan.mprime_2;

import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.SystemClock;
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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renan.mprime_2.DAO.DatabaseHelper;
import com.example.renan.mprime_2.DAO.TreinoDAO;
import com.example.renan.mprime_2.Model.Treino;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Renan on 30/09/2015.
 */
public class Atividade_fragment extends Fragment implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    View MyView;
    TextView txtNome, txtTempo, txtReal, txtData;
    int teste;
    MediaPlayer som;
    //  MediaPlayer som, som2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.fragment_atividade, container, (false));
        final Button btDescartar = (Button) MyView.findViewById(R.id.btDescartarAtividade);
        final Button btSalvar = (Button) MyView.findViewById(R.id.btSalvarAtividade);
        spinner = (Spinner) MyView.findViewById(R.id.Spinner02);
        txtNome = (TextView) MyView.findViewById(R.id.txt_nm_treino_);
        txtTempo = (TextView) MyView.findViewById(R.id.txt_tempo_treino_);
        txtReal = (TextView) MyView.findViewById(R.id.txt_tempoReal_treino_);
        txtData = (TextView) MyView.findViewById(R.id.txt_data_treino_);
        spinner.setOnItemSelectedListener(this);
        btDescartar.setEnabled(false);
        btSalvar.setEnabled(false);
        loadSpinnerData();
        final Calendar c = Calendar.getInstance();
        final Chronometer ch = (Chronometer) MyView.findViewById(R.id.chronometer);
        final ImageButton imgButton = (ImageButton) MyView.findViewById(R.id.imageButton);
        imgButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View button) {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                final Ringtone r = RingtoneManager.getRingtone(getContext(), notification);
                r.play();
                button.setSelected(!button.isSelected());

                if (button.isSelected()) {
                    btDescartar.setEnabled(false);
                    btSalvar.setEnabled(false);
                    spinner.setEnabled(false);
                    imgButton.setBackgroundResource(R.drawable.stop);
                    ch.setBase(SystemClock.elapsedRealtime());
                    ch.start();

                    ch.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                        @Override
                        public void onChronometerTick(Chronometer chronometer) {
                            if (ch.getBase() % 1 == 0) {
                                teste++;
                                if (teste == 900) {
                                    r.play();
                                    //   som2.start();  som.start(); ===================================RESOLVER MAIS TARDE
                                    teste = 0;
                                }
                            }

                        }
                    });
                } else {

                    btDescartar.setEnabled(true);
                    btSalvar.setEnabled(true);
                    imgButton.setBackgroundResource(R.drawable.start);
                    ch.stop();
                    txtReal.setText(ch.getText());
                    ch.setText("00:00");
                    teste = 0;
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    int month = c.get(Calendar.MONTH);
                    int year = c.get(Calendar.YEAR);
                    txtData.setText(day + "/" + (month + 1) + "/" + year);
                    imgButton.setEnabled(false);
                    Toast.makeText(getContext(), "Salve ou descarte a Atividade para treinar novamente! ", Toast.LENGTH_LONG).show();
                }

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
            }
        });
        btSalvar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Em fase de implementação", Toast.LENGTH_SHORT).show();
                imgButton.setEnabled(true);
                ch.setText("00:00");
                txtReal.setText("--------------");
                txtData.setText("--------------");
                spinner.setEnabled(true);
                btDescartar.setEnabled(false);
                btSalvar.setEnabled(false);
            }
        });
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
        String idi = spinner.getSelectedItem().toString();
        TreinoDAO treinoDAO = new TreinoDAO(this.getContext());
        Treino model = treinoDAO.buscarTreinoPorNome_e(idi);
        txtNome.setText(model.getNome_treino());
        txtTempo.setText(String.valueOf(model.getTempo_treino()));

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}