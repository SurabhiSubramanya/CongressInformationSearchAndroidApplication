package com.example.surabhisubramanya.congressapi;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FavoritesTabBills extends Fragment {

    private static FavoritesTabBills favoritesTabBills = null;

    public FavoritesTabBills() {
        // Required empty public constructor
    }

    public static FavoritesTabBills getInstance() {
        if (favoritesTabBills != null) {
            return favoritesTabBills;
        } else {
            favoritesTabBills = new FavoritesTabBills();
            return favoritesTabBills;
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
        return inflater.inflate(R.layout.fragment_favorites_tab_bills, container, false);
    }
}
