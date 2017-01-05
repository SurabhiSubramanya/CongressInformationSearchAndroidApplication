package com.example.surabhisubramanya.congressapi;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Aboutme extends Fragment {

    private static Aboutme aboutme = null;

    public Aboutme() {
        // Required empty public constructor
    }

    public static Aboutme getInstance() {
        if (aboutme != null) {
            return aboutme;
        } else {
            aboutme = new Aboutme();
            return aboutme;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_aboutme, container, false);
    }
}
