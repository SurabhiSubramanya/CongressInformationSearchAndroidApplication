package com.example.surabhisubramanya.congressapi;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * Created by surabhisubramanya on 12/1/16.
 */

public class ViewDetailsLegislators extends AppCompatActivity {
    JSONObject currentObject;
    Intent intent;
    SimpleDateFormat form;
    Context context;


    String leg_title,leg_first_name,leg_last_name,chamber_string_uppercase,chamber_string,start_term_initial,end_term_initial,birthday_initial,bioguide_id,party,party_name,leg_facebook_id,leg_twitter_id,facebook_link,twitter_link,website_link;

    int fb_flag = 0;
    int t_flag = 0;
    int website_flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.view_details_legislators);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        intent = getIntent();

        try {
            currentObject = new JSONObject(intent.getStringExtra("currentObjectLegislator"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            Log.d("view details leg",String.valueOf(currentObject.getString("bioguide_id")));
        } catch (JSONException e) {
            e.printStackTrace();

        }

        try {
            if(!Objects.equals(currentObject.getString("facebook_id"), "null")) {
                leg_facebook_id = currentObject.getString("facebook_id");
                fb_flag = 1;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            /*Toast toast;
            toast = Toast.makeText(getApplicationContext(),"The legislator does not have a facebook page",Toast.LENGTH_SHORT);
            toast.show();*/
        }
        //LINEAR LAYOUT FOR THE IMAGE LOGOS
        ImageView favorite = (ImageView) findViewById(R.id.favorite);
        favorite.setImageResource(R.mipmap.favorite_star);

        ImageView facebook = (ImageView) findViewById(R.id.facebook);
        facebook.setImageResource(R.mipmap.facebook);

        ImageView twitter = (ImageView) findViewById(R.id.twitter);
        twitter.setImageResource(R.mipmap.twitter);

        ImageView website_image = (ImageView) findViewById(R.id.website);
        website_image.setImageResource(R.mipmap.website);

        ImageView facebook_id = (ImageView) findViewById(R.id.facebook);
        facebook_id.setClickable(true);
        facebook_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fb_flag == 1) {
                    facebook_link = "http://facebook.com/" + leg_facebook_id;
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(facebook_link));
                    startActivity(intent);
                } else {
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(),"The legislator does not have a facebook page",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        try {
            if(!Objects.equals(currentObject.getString("twitter_id"), "null")) {
                leg_twitter_id = currentObject.getString("twitter_id");
                t_flag = 1;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            /*Toast toast;
            toast = Toast.makeText(getApplicationContext(),"The legislator does not have a twitter page",Toast.LENGTH_SHORT);
            toast.show();*/
        }

        ImageView twitter_id = (ImageView) findViewById(R.id.twitter);
        twitter_id.setClickable(true);
        twitter_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (t_flag == 1) {
                    twitter_link = "http://twitter.com/" + leg_twitter_id;
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(twitter_link));
                    startActivity(intent);
                } else {
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(),"The legislator does not have a twitter page",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        try {
            if(!Objects.equals(currentObject.getString("website"), "null")) {
                website_link = currentObject.getString("website");
                website_flag = 1;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            /*Toast toast;
            toast = Toast.makeText(getApplicationContext(),"The legislator does not have a website",Toast.LENGTH_SHORT);
            toast.show();*/
        }

        ImageView website = (ImageView) findViewById(R.id.website);
        website.setClickable(true);
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (website_flag == 1) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(website_link));
                    startActivity(intent);
                } else {
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(),"The legislator does not have a website",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });



        //LINEAR LAYOUT FOR LEGISLATOR IMAGE

        try {
            bioguide_id = currentObject.getString("bioguide_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ImageView legislatorImage = (ImageView)findViewById(R.id.legImage);
        Picasso.with(context)
                .load("https://theunitedstates.io/images/congress/225x275/" + bioguide_id + ".jpg")
                .into(legislatorImage);

        //LINEAR LAYOUT FOR PARTY NAME AND PARTY IMAGE
        try {
            party = currentObject.getString("party");

            if(party.equals("R")) {
                party_name = "Republican";
                TextView party_text = (TextView) findViewById(R.id.legPartyName);
                party_text.setText(party_name);
                ImageView party_pic = (ImageView) findViewById(R.id.partyImage);
                party_pic.setImageResource(R.mipmap.republic);
            }
            else if(party.equals("D")) {
                party_name = "Democrat";
                TextView party_text = (TextView) findViewById(R.id.legPartyName);
                party_text.setText(party_name);
                ImageView party_pic = (ImageView) findViewById(R.id.partyImage);
                party_pic.setImageResource(R.mipmap.democrat);
            }
            else if(party.equals("I")) {
                party_name = "Independent";
                TextView party_text = (TextView) findViewById(R.id.legPartyName);
                party_text.setText(party_name);
                ImageView party_pic = (ImageView) findViewById(R.id.partyImage);
                party_pic.setImageResource(R.mipmap.independent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //LINEAR LAYOUT CONTENTS FOR THE TABLE OF LEGISLATOR DETAILS
        TextView leg_name = (TextView) findViewById(R.id.legName);
        try {
            if(!Objects.equals(currentObject.getString("title"), "null")) {
                leg_title = currentObject.getString("title");
            }
            if(!Objects.equals(currentObject.getString("first_name"), "null")) {
                leg_first_name = currentObject.getString("first_name");
            }
            if(!Objects.equals(currentObject.getString("last_name"), "null")) {
                leg_last_name = currentObject.getString("last_name");
            }
            if (!leg_title.equals("null") && !leg_last_name.equals("null") && !leg_first_name.equals("null")) {
                leg_name.setText(leg_title + ". " + leg_last_name + ", " + leg_first_name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView email = (TextView) findViewById(R.id.legEmail);
        try {
            if(!Objects.equals(currentObject.getString("oc_email"), "null"))
                email.setText(currentObject.getString("oc_email"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView chamber = (TextView) findViewById(R.id.legChamber);
        try {
            if(!Objects.equals(currentObject.getString("chamber"), "null")) {
                chamber_string = currentObject.getString("chamber");
                chamber_string_uppercase = chamber_string.substring(0, 1).toUpperCase() + chamber_string.substring(1);
                chamber.setText(chamber_string_uppercase);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView contact = (TextView) findViewById(R.id.legContact);
        try {
            if(!Objects.equals(currentObject.getString("phone"), "null")) {
                contact.setText(currentObject.getString("phone"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView start_term = (TextView) findViewById(R.id.legStartTerm);
        try {
            if(!Objects.equals(currentObject.getString("term_start"), "null")) {
                start_term_initial = currentObject.getString("term_start");
                form = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = null;
                try {
                    date = form.parse(start_term_initial);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat postFormatter = new SimpleDateFormat("MMM dd,yyyy");
                String newDateStr = postFormatter.format(date);
                start_term.setText(newDateStr);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView end_term = (TextView) findViewById(R.id.legEndTerm);
        try {
            if(!Objects.equals(currentObject.getString("term_end"), "null")) {
                end_term_initial = currentObject.getString("term_end");
                form = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = null;
                try {
                    date = form.parse(end_term_initial);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat postFormatter = new SimpleDateFormat("MMM dd,yyyy");
                String newDateStr = postFormatter.format(date);
                end_term.setText(newDateStr);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //STILL NEED TO DO PROGRESS BAR
        TextView Term = (TextView) findViewById(R.id.legTermProgress);
        TextView office = (TextView) findViewById(R.id.legOffice);
        try {
            if(!Objects.equals(currentObject.getString("office"), "null"))
                office.setText(currentObject.getString("office"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView state = (TextView) findViewById(R.id.legState);
        try {
            if(!Objects.equals(currentObject.getString("state"), "null"))
                state.setText(currentObject.getString("state"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView fax = (TextView) findViewById(R.id.legFax);
        try {
            if(!Objects.equals(currentObject.getString("fax"), "null"))
                fax.setText(currentObject.getString("fax"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView birthday = (TextView) findViewById(R.id.legBirthday);
        try {
            if(!Objects.equals(currentObject.getString("birthday"), "null")) {
                birthday_initial = currentObject.getString("birthday");
                form = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = null;
                try {
                    date = form.parse(birthday_initial);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat postFormatter = new SimpleDateFormat("MMM dd,yyyy");
                String newDateStr = postFormatter.format(date);
                birthday.setText(newDateStr);
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
