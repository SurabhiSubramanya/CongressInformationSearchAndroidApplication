package com.example.surabhisubramanya.congressapi;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FavoritesTabLegislators extends Fragment {

    private static FavoritesTabLegislators favoritesTabLegislators = null;

    public FavoritesTabLegislators() {
        // Required empty public constructor
    }

    public static FavoritesTabLegislators getInstance() {
        if (favoritesTabLegislators != null) {
            return favoritesTabLegislators;
        } else {
            favoritesTabLegislators = new FavoritesTabLegislators();
            return favoritesTabLegislators;
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
        return inflater.inflate(R.layout.fragment_favorites_tab_legislators, container, false);
    }
}
