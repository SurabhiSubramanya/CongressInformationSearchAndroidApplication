package com.example.surabhisubramanya.congressapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by surabhisubramanya on 12/1/16.
 */

public class ViewDetailsBills extends AppCompatActivity {

    JSONObject currentObject;
    Intent intent;
    SimpleDateFormat form;

    String sponsor_title,sponsor_first_name,sponsor_last_name,chamber_string,chamber_string_uppercase,introduced_on_initial,checkActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_details_bills);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        intent = getIntent();
        try {
            currentObject = new JSONObject(intent.getStringExtra("currentObject"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            Log.d("view details bills",String.valueOf(currentObject.getString("bill_id")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ImageView favorite = (ImageView) findViewById(R.id.favorite);
        favorite.setImageResource(R.mipmap.favorite_star);

        TextView bill_id = (TextView) findViewById(R.id.billId);
        try {
            if(!Objects.equals(currentObject.getString("bill_id"), "null"))
                bill_id.setText(currentObject.getString("bill_id").toUpperCase());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView title = (TextView) findViewById(R.id.billTitle);
        try {
            if(!Objects.equals(currentObject.getString("official_title"), "null"))
                title.setText(currentObject.getString("official_title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView bill_type = (TextView) findViewById(R.id.billType);
        try {
            if(!Objects.equals(currentObject.getString("bill_type"), "null"))
                bill_type.setText(currentObject.getString("bill_type").toUpperCase());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView sponsor = (TextView) findViewById(R.id.billSponsor);
        try {
            if(!Objects.equals(currentObject.getJSONObject("sponsor").getString("title"), "null")) {
                sponsor_title = currentObject.getJSONObject("sponsor").getString("title");
            }
            if(!Objects.equals(currentObject.getJSONObject("sponsor").getString("first_name"), "null")) {
                sponsor_first_name = currentObject.getJSONObject("sponsor").getString("first_name");
            }
            if(!Objects.equals(currentObject.getJSONObject("sponsor").getString("last_name"), "null")) {
                sponsor_last_name = currentObject.getJSONObject("sponsor").getString("last_name");
            }
            if (!sponsor_title.equals("null") && !sponsor_last_name.equals("null") && !sponsor_first_name.equals("null")) {
                sponsor.setText(sponsor_title + ". " + sponsor_last_name + ", " + sponsor_first_name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView chamber = (TextView) findViewById(R.id.billChamber);
        try {
            if(!Objects.equals(currentObject.getString("chamber"), "null")) {
                chamber_string = currentObject.getString("chamber");
                chamber_string_uppercase = chamber_string.substring(0, 1).toUpperCase() + chamber_string.substring(1);
                chamber.setText(chamber_string_uppercase);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView status = (TextView) findViewById(R.id.billStatus);
        try {
            if(!Objects.equals(currentObject.getJSONObject("history").getString("active"), "null")) {
                checkActive = currentObject.getJSONObject("history").getString("active");

                if(checkActive.equals("true"))
                {
                    status.setText("Active");
                }
                else
                {
                    status.setText("New");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView introduced_on = (TextView) findViewById(R.id.billIntroducedOn);
        try {
            if(!Objects.equals(currentObject.getString("introduced_on"), "null")) {
                introduced_on_initial = currentObject.getString("introduced_on");
                form = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = null;
                try {
                    date = form.parse(introduced_on_initial);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat postFormatter = new SimpleDateFormat("MMM dd,yyyy");
                String newDateStr = postFormatter.format(date);
                introduced_on.setText(newDateStr);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView congress_url = (TextView) findViewById(R.id.billCongressUrl);
        try {
            if(!Objects.equals(currentObject.getJSONObject("urls").getString("congress"), "null"))
                congress_url.setText(currentObject.getJSONObject("urls").getString("congress"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView version_status = (TextView) findViewById(R.id.billVersionStatus);
        try {
            if(!Objects.equals(currentObject.getJSONObject("last_version").getString("version_name"), "null"))
                version_status.setText(currentObject.getJSONObject("last_version").getString("version_name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView bill_url = (TextView) findViewById(R.id.billURL);
        try {
            if(!Objects.equals(currentObject.getJSONObject("last_version").getJSONObject("urls").getString("pdf"), "null"))
                bill_url.setText(currentObject.getJSONObject("last_version").getJSONObject("urls").getString("pdf"));
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
