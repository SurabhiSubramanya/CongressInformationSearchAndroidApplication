package com.example.surabhisubramanya.congressapi;

/**
 * Created by surabhisubramanya on 11/24/16.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;


/**
 * Created by surabhisubramanya on 11/22/16.
 */

public class AdapterCommittees extends ArrayAdapter<JSONObject> {

    Context context = null;

    //DECLARE VARIABLES TO STORE DETAILS EXTRACTED FROM API
    public String committee_id,committee_name,chamber;
    //public String oc_email,phone,state,term_start,term_end,office,fax,birthday,twitter_id,facebook_id,website;

    public AdapterCommittees(Context context, int resource, ArrayList<JSONObject> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        View row = convertView;

        if(row==null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.fragment_committees_tab_house_list_style, parent, false);
        }

        //TRY CATCH BLOCK FETCHING ALL REQUIRED FIELDS
        try {
            committee_id = getItem(position).getString("committee_id");
            committee_name = getItem(position).getString("name");
            chamber = getItem(position).getString("chamber");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView commID =(TextView)row.findViewById(R.id.commHouse_CommId);
        commID.setText("\n" + committee_id + "\n");

        TextView commName =(TextView)row.findViewById(R.id.commHouse_CommName);
        commName.setText(committee_name + "\n");

        TextView chamberName =(TextView)row.findViewById(R.id.commHouse_CommChamber);
        chamberName.setText(chamber.substring(0,1).toUpperCase() + chamber.substring(1).toLowerCase());

        return row;
    }
}
