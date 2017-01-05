package com.example.surabhisubramanya.congressapi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Favorites extends Fragment {

    private FragmentTabHost tabHostFavorites;

    private static Favorites favorites = null;

    private FavoritesTabLegislators favoritesTabLegislators = null;
    private FavoritesTabBills favoritesTabBills = null;
    private FavoritesTabCommittees favoritesTabCommittees = null;

    public Favorites() {
        // Required empty public constructor
    }

    public static Favorites getInstance() {
        if (favorites != null) {
            return favorites;
        } else {
            favorites = new Favorites();
            return favorites;
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
        View baseView = inflater.inflate(R.layout.fragment_favorites, container, false);

        tabHostFavorites = (FragmentTabHost) baseView.findViewById(R.id.favTabHost);

        tabHostFavorites.setup(getActivity(),getChildFragmentManager(),R.id.favTabContent);

        favoritesTabLegislators = FavoritesTabLegislators.getInstance();

        favoritesTabBills = FavoritesTabBills.getInstance();

        favoritesTabCommittees = FavoritesTabCommittees.getInstance();

        tabHostFavorites.addTab(tabHostFavorites.newTabSpec("FavTab1").setIndicator("Legislators"),favoritesTabLegislators.getClass(),null);

        tabHostFavorites.addTab(tabHostFavorites.newTabSpec("FavTab2").setIndicator("Bills"),favoritesTabBills.getClass(),null);

        tabHostFavorites.addTab(tabHostFavorites.newTabSpec("FavTab3").setIndicator("Committees"),favoritesTabCommittees.getClass(),null);

        Log.d("Favorites Class", "Fragment Newly Created. Existing instance not used.");

        return baseView;

    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();;
        tabHostFavorites = null;
    }
}