package com.example.surabhisubramanya.congressapi;

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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by surabhisubramanya on 11/22/16.
 */

public class AdapterLegislator extends ArrayAdapter<JSONObject> {

    Context context = null;

    //DECLARE VARIABLES TO STORE DETAILS EXTRACTED FROM API
    public String first_name,last_name,title,bioguide_id,party,party_expand,chamber,state_name;
    //public String oc_email,phone,state,term_start,term_end,office,fax,birthday,twitter_id,facebook_id,website;
    public Integer district;
    public String district_output;

    public AdapterLegislator(Context context, int resource, ArrayList<JSONObject> objects) {
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
            row = inflater.inflate(R.layout.fragment_leg_tab_by_state_list_style, parent, false);
        }

        //TRY CATCH BLOCK FETCHING ALL REQUIRED FIELDS
        try {
            first_name = getItem(position).getString("first_name");
            last_name = getItem(position).getString("last_name");
            //title = getItem(position).getString("title");
            bioguide_id = getItem(position).getString("bioguide_id");
            party = getItem(position).getString("party");
            //chamber = getItem(position).getString("chamber");
            state_name = getItem(position).getString("state_name");
//            oc_email = getItem(position).getString("oc_email");
//            phone = getItem(position).getString("phone");
//            state = getItem(position).getString("state");
//            term_start = getItem(position).getString("term_start");
//            term_end = getItem(position).getString("term_end");
//            office = getItem(position).getString("office");
//            fax = getItem(position).getString("fax");
//            facebook_id = getItem(position).getString("facebook_id");
//            twitter_id = getItem(position).getString("twitter_id");
//            website = getItem(position).getString("twitter_id");
//            birthday = getItem(position).getString("birthday");
            district = getItem(position).optInt("district",0);
            if (district == 0) {
                district_output = "N.A.";
            }
            else
            {
                district_output = "District " +  district;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //EXPAND PARTY NAME BASED ON PARTY ATTRIBUTE IN JSON RESPONSE
        /*if (party == "R")
        {
            party_expand = "Republican";
        }

        else if (party == "D")
        {
            party_expand = "Democrat";
        }

        else if (party == "I")
        {
            party_expand = "Independent";
        }*/

        TextView legName =(TextView)row.findViewById(R.id.legByState_legName);
        legName.setText("\n\n" + last_name + ", " + first_name);

        TextView stateDetails =(TextView)row.findViewById(R.id.legByState_stateDistrict);
        stateDetails.setText("(" + party + ")" + state_name + " - " + district_output);

        ImageView legislatorImage = (ImageView)row.findViewById(R.id.legByState_legImage);
        Picasso.with(getContext())
                .load("https://theunitedstates.io/images/congress/225x275/" + bioguide_id+".jpg")
                .into(legislatorImage);

        return row;
    }
}