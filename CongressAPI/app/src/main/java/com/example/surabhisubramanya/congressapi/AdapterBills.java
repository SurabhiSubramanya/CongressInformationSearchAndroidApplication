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

public class AdapterBills extends ArrayAdapter<JSONObject> {

    Context context = null;

    //DECLARE VARIABLES TO STORE DETAILS EXTRACTED FROM API
    public String bill_id,official_title,introduced_on,short_title;
    //public String oc_email,phone,state,term_start,term_end,office,fax,birthday,twitter_id,facebook_id,website;

    public AdapterBills(Context context, int resource, ArrayList<JSONObject> objects) {
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
            row = inflater.inflate(R.layout.fragment_bills_tab_active_list_style, parent, false);
        }

        //TRY CATCH BLOCK FETCHING ALL REQUIRED FIELDS
        try {
            bill_id = getItem(position).getString("bill_id");
            short_title = getItem(position).getString("short_title");
            official_title = getItem(position).getString("official_title");
            //title = getItem(position).getString("title");
            introduced_on = getItem(position).getString("introduced_on");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView billID =(TextView)row.findViewById(R.id.activeBills_BillId);
        billID.setText(bill_id.toUpperCase() + "\n");

        TextView officialTitle =(TextView)row.findViewById(R.id.activeBills_BillTitle);
        if (short_title.equals("null")) {
            officialTitle.setText(official_title + "\n");
        }
        else {
            officialTitle.setText(short_title + "\n");
        }

        TextView introducedOn =(TextView)row.findViewById(R.id.activeBills_BillIntroduced);
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
        try
        {
            date = form.parse(introduced_on);
        }
        catch (ParseException e)
        {

            e.printStackTrace();
        }
        SimpleDateFormat postFormatter = new SimpleDateFormat("MMM dd,yyyy");
        String newDateStr = postFormatter.format(date);
        introducedOn.setText(newDateStr +"\n");

        return row;
    }
}
