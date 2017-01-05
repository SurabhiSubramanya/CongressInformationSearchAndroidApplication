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
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BillsTabNew extends Fragment {

    private static BillsTabNew billsTabNew = null;

    private static ArrayList<JSONObject> populateArrayList = new ArrayList<JSONObject>();

    private static JSONArray populateJsonArray = new JSONArray();

    private static ListView newBills = null;

    private static JSONObject data = null;

    private static View view= null;

    private static JSONObject currentObject;

    private static Context context = null;

    public BillsTabNew() {
        // Required empty public constructor
    }

    public static BillsTabNew getInstance() {
        if (billsTabNew != null) {
            return billsTabNew;
        } else {
            billsTabNew = new BillsTabNew();
            return billsTabNew;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bills_tab_new, container, false);
    }

    public static void parseJsonResults(Context ctx, JSONObject bill_results) {

        //CHECK THE JSONObject ARGUMENT RECEIVED FROM PARENT FRAGMENT
        data = bill_results;
        //Log.d("parseJsonResults:",data.toString());
        //Log.d("parseJsonResults:","here");
        //Log.d("Legislator Class", leg_results.toString());

        int i;

        try {
            populateJsonArray = bill_results.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        populateArrayList.clear();
        populateArrayList = new ArrayList<>();
        for (i=0;i<populateJsonArray.length();i++)
        {
            try {
                populateArrayList.add(populateJsonArray.getJSONObject(i));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        //CHECK THE POPULATED ARRAY
        //Log.d("Legislator House Class", populateArrayList.toString() );

        //SET ADAPTER FOR LIST VIEW
        AdapterBills newBillsAdapter = new AdapterBills(ctx, R.id.newBillsListView, populateArrayList);

        if(newBills!=null){
            newBills.setAdapter(newBillsAdapter);
            newBills.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    currentObject = (JSONObject) newBills.getItemAtPosition(position);
                    Log.d("Obtained object:", currentObject.toString());
                    // Launching new Activity on selecting single List Item
                    Intent i = new Intent(context.getApplicationContext(), ViewDetailsBills.class);

                    // sending data to new activity
                    i.putExtra("currentObject",String.valueOf(currentObject));
                    context.startActivity(i);
                }
            });
            //Log.d("parseJsonResults:","adapter set");
        }
        //else Log.d("legByState failed","here");

        //if(view==null) Log.d("view:","null");

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        this.newBills = (ListView) view.findViewById(R.id.newBillsListView);
        if(this.data!=null){
            parseJsonResults(getActivity(),this.data);
            //Log.d("onViewCreated: ","parseJsonResults() called");
        }
        //else Log.d("this.data failed","here");
        //if(this.legByHouse==null) Log.d("legByHouse listView:","null");
        //if(this.view==null) Log.d("view in onViewCreated:","null");

    }
}