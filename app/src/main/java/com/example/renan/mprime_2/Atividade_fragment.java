package com.example.renan.mprime_2;

import android.media.MediaPlayer;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.renan.mprime_2.Adapter.ExercicioAdapter;
import com.example.renan.mprime_2.DAO.DatabaseHelper;
import com.example.renan.mprime_2.DAO.ExercicioDAO;
import com.example.renan.mprime_2.DAO.TreinoDAO;
import com.example.renan.mprime_2.Model.Treino;

import java.util.Calendar;
import java.util.List;

import static com.example.renan.mprime_2.R.raw.johncena;

/**
 * Created by Renan on 30/09/2015.
 */
public class Atividade_fragment extends Fragment implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    View MyView;
    TextView txtNome, txtTempo, txtReal, txtData;
    int teste;
    //  MediaPlayer som, som2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.fragment_atividade, container, (false));
        spinner = (Spinner) MyView.findViewById(R.id.Spinner02);
        txtNome = (TextView) MyView.findViewById(R.id.txt_nm_treino_);
        txtTempo = (TextView) MyView.findViewById(R.id.txt_tempo_treino_);
        txtReal = (TextView) MyView.findViewById(R.id.txt_tempoReal_treino_);
        txtData = (TextView) MyView.findViewById(R.id.txt_data_treino_);
        spinner.setOnItemSelectedListener(this);
        spinner.setOnItemSelectedListener(this);
        loadSpinnerData();
        final ImageButton imgButton = (ImageButton) MyView.findViewById(R.id.imageButton);


        imgButton.setOnClickListener(new View.OnClickListener() {
            final Calendar c = Calendar.getInstance();
            final Chronometer ch = (Chronometer) MyView.findViewById(R.id.chronometer);
            final MediaPlayer som = MediaPlayer.create(Atividade_fragment.super.getContext(), R.raw.pim);
            //===================================RESOLVER MAIS TARDE
            final MediaPlayer som2 = MediaPlayer.create(Atividade_fragment.super.getContext(), R.raw.johncena);
            //===================================RESOLVER MAIS TARDE

            public void onClick(View button) {
                //Set the button's appearance
                //  som.start(); ===================================RESOLVER MAIS TARDE
                button.setSelected(!button.isSelected());

                if (button.isSelected()) {
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
                                    //   som2.start();  som.start(); ===================================RESOLVER MAIS TARDE
                                    teste = 0;
                                }
                            }

                        }
                    });
                } else {

                    spinner.setEnabled(true);
                    imgButton.setBackgroundResource(R.drawable.start);
                    ch.stop();
                    txtReal.setText(ch.getText());
                    ch.setText("00:00");
                    teste = 0;
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    int month = c.get(Calendar.MONTH);
                    int year = c.get(Calendar.YEAR);
                    txtData.setText(day + "/" + (month + 1) + "/" + year);


                }

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