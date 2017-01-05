package com.example.surabhisubramanya.congressapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * Created by surabhisubramanya on 12/1/16.
 */

public class ViewDetailsCommittees extends AppCompatActivity {

    Intent intent;
    JSONObject currentObject;

    String chamber_string,chamber_string_camel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_details_committees);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        intent = getIntent();
        try {
            currentObject = new JSONObject(intent.getStringExtra("currentObjectCommittee"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            Log.d("view details committees",String.valueOf(currentObject.getString("committee_id")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ImageView favorite = (ImageView) findViewById(R.id.favorite);
        favorite.setImageResource(R.mipmap.favorite_star);

        TextView committee_id = (TextView) findViewById(R.id.committeeId);
        try {
            if(!Objects.equals(currentObject.getString("committee_id"), "null"))
                committee_id.setText(currentObject.getString("committee_id").toUpperCase());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView committee_name = (TextView) findViewById(R.id.committeeName);
        try {
            if(!Objects.equals(currentObject.getString("name"), "null"))
                committee_name.setText(currentObject.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView committee_chamber = (TextView) findViewById(R.id.committeeChamber);
        try {
            if(!Objects.equals(currentObject.getString("chamber"), "null")) {
                chamber_string = currentObject.getString("chamber");
                chamber_string_camel = chamber_string.substring(0, 1).toUpperCase() + chamber_string.substring(1);
                committee_chamber.setText(chamber_string_camel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView parent_committee = (TextView) findViewById(R.id.committeeParent);
        try {
            if(!Objects.equals(currentObject.getString("parent_committee_id"), "null"))
                parent_committee.setText(currentObject.getString("parent_committee_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView contact = (TextView) findViewById(R.id.committeeContact);
        try {
            if(!Objects.equals(currentObject.getString("phone"), "null"))
                contact.setText(currentObject.getString("phone"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView office = (TextView) findViewById(R.id.committeeOffice);
        try {
            if(!Objects.equals(currentObject.getString("office"), "null"))
                office.setText(currentObject.getString("office"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ImageView chamberImage = (ImageView) findViewById(R.id.chamber_image);
        try {
            if(!Objects.equals(currentObject.getString("chamber"), "null")){
                switch (currentObject.getString("chamber")){
                    case "house": chamberImage.setImageResource(R.mipmap.house);break;
                    case "senate": chamberImage.setImageResource(R.mipmap.senate);break;
                    case "joint": chamberImage.setImageResource(R.mipmap.senate);break;
                    default:chamberImage.setImageResource(R.mipmap.house);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

