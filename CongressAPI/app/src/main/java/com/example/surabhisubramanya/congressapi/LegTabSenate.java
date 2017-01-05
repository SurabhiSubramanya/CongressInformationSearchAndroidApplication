package com.example.surabhisubramanya.congressapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LegTabSenate extends Fragment {

    private static LegTabSenate legTabSenate = null;

    private static ArrayList<JSONObject> populateArrayList = new ArrayList<JSONObject>();

    private static JSONArray populateJsonArray = new JSONArray();

    private static ListView legBySenate = null;

    private static JSONObject data = null;

    private static View view= null;

    private static JSONObject currentObject;

    private static Context context = null;

    static Map<String, Integer> mapIndex = null;
    static LinearLayout indexLayout = null;

    static LayoutInflater LI=null;

    public LegTabSenate() {
        // Required empty public constructor
    }

    public static LegTabSenate getInstance() {
        if (legTabSenate != null) {
            return legTabSenate;
        } else {
            legTabSenate = new LegTabSenate();
            return legTabSenate;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        LI = getActivity().getLayoutInflater();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leg_tab_senate, container, false);
    }

    public static void parseJsonResults(Context ctx, JSONObject leg_results) {

        //CHECK THE JSONObject ARGUMENT RECEIVED FROM PARENT FRAGMENT
        data = leg_results;
        Log.d("parseJsonResults:",data.toString());
        Log.d("parseJsonResults:","here");
        //Log.d("Legislator Class", leg_results.toString());

        int i;

        try {
            populateJsonArray = leg_results.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        populateArrayList.clear();
        populateArrayList = new ArrayList<>();
        for (i=0;i<populateJsonArray.length();i++)
        {
            try {
                if(populateJsonArray.getJSONObject(i).getString("chamber").equals("senate")) {
                    populateArrayList.add(populateJsonArray.getJSONObject(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        //CHECK THE POPULATED ARRAY
        Log.d("Legislator Senate Class", populateArrayList.toString() );

        //SET ADAPTER FOR LIST VIEW
        AdapterLegislator legBySenateAdapter = new AdapterLegislator(ctx, R.id.legBySenateListView, populateArrayList);

        if(legBySenate!=null){
            legBySenate.setAdapter(legBySenateAdapter);
            legBySenate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    currentObject = (JSONObject) legBySenate.getItemAtPosition(position);
                    Log.d("Obtained object:", currentObject.toString());
                    // Launching new Activity on selecting single List Item
                    Intent i = new Intent(context.getApplicationContext(), ViewDetailsLegislators.class);

                    // sending data to new activity
                    i.putExtra("currentObjectLegislator",String.valueOf(currentObject));
                    context.startActivity(i);
                }
            });
            getIndexList(populateArrayList);
            displayIndex();
            Log.d("parseJsonResults:","adapter set");
        }
        else Log.d("legByState failed","here");

        if(view==null) Log.d("view:","null");

    }

    private static void getIndexList(ArrayList<JSONObject> legislators) {
        mapIndex = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < legislators.size(); i++) {
            JSONObject legislator = legislators.get(i);
            String index = null;
            try {
                index = (String.valueOf(legislator.getString("last_name")).substring(0, 1)).toUpperCase();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (mapIndex.get(index) == null)
                mapIndex.put(index, i);
        }
    }

    private static void displayIndex() {
        TextView textView;
        List<String> indexList = new ArrayList<String>(mapIndex.keySet());
        for (String index : indexList) {
            textView = (TextView) LI.inflate(
                    R.layout.alphabetical_index_single_item, null);
            textView.setText(index.toUpperCase());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView selectedIndex = (TextView) v;
                    legBySenate.setSelection(mapIndex.get(selectedIndex.getText()));
                }
            });
            indexLayout.addView(textView);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.indexLayout = (LinearLayout) view.findViewById(R.id.alphabetical_index);
        this.view = view;
        this.legBySenate = (ListView) view.findViewById(R.id.legBySenateListView);
        if(this.data!=null){
            parseJsonResults(getActivity(),this.data);
            Log.d("onViewCreated: ","parseJsonResults() called");
        }
        else Log.d("this.data failed","here");
        if(this.legBySenate==null) Log.d("legByHouse listView:","null");
        if(this.view==null) Log.d("view in onViewCreated:","null");

    }
}
