package com.example.renan.mprime_2;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Renan on 30/09/2015.
 */
public class LogTreino_fragment extends Fragment {

    View MyView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState){
        MyView = inflater.inflate(R.layout.fragment_log, container, (false));
        return MyView;
    }


}