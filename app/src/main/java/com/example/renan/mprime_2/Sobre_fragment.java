package com.example.renan.mprime_2;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by Renan on 30/09/2015.
 */
public class Sobre_fragment extends Fragment {
    WebView webView;
    View MyView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.fragment_sobre, container, (false));
        ((MainActivity) getActivity())
                .setActionBarTitle("Sobre");
        webView = (WebView) MyView.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setSaveFormData(true);
        webView.loadUrl("file:///android_res/raw/index.html");
        return MyView;
    }


}