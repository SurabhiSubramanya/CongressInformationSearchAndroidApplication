package com.example.surabhisubramanya.congressapi;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FavoritesTabCommittees extends Fragment {

    private static FavoritesTabCommittees favoritesTabCommittees = null;

    public FavoritesTabCommittees() {
        // Required empty public constructor
    }

    public static FavoritesTabCommittees getInstance() {
        if (favoritesTabCommittees != null) {
            return favoritesTabCommittees;
        } else {
            favoritesTabCommittees = new FavoritesTabCommittees();
            return favoritesTabCommittees;
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
        return inflater.inflate(R.layout.fragment_favorites_tab_committees, container, false);
    }
}