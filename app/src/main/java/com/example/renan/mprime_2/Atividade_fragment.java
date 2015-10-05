package com.example.renan.mprime_2;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Spinner;

import com.example.renan.mprime_2.DAO.DatabaseHelper;

import java.util.List;

/**
 * Created by Renan on 30/09/2015.
 */
public class Atividade_fragment extends Fragment implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    View MyView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.fragment_atividade, container, (false));
        spinner = (Spinner) MyView.findViewById(R.id.Spinner02);
        spinner.setOnItemSelectedListener(this);
        spinner.setOnItemSelectedListener(this);
        loadSpinnerData();

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

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}