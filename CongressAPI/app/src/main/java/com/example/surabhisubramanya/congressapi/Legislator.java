package com.example.surabhisubramanya.congressapi;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;

import static com.android.volley.VolleyLog.TAG;
import static com.example.surabhisubramanya.congressapi.LegTabHouse.*;

public class Legislator extends Fragment {

    private FragmentTabHost tabHostLegislator;

    private LegTabByState legTabByState = null;
    private LegTabHouse legTabHouse = null;
    private LegTabSenate legTabSenate = null;

    private static Legislator legislator = null;

    private static final String LEG_URL_STATE = "http://hw9surabhisubramany-env.us-west-2.elasticbeanstalk.com/index.php?legislatorsstate=true";

    private static final String LEG_URL_HOUSE_SENATE = "http://hw9surabhisubramany-env.us-west-2.elasticbeanstalk.com/index.php?legislatorshousesenate=true";

    private JSONObject leg_results_state;
    private JSONObject leg_results_housesenate;

    public Legislator() {
        // Required empty public constructor
    }

    public static Legislator getInstance() {
        if (legislator != null) {
            return legislator;
        } else {
            legislator = new Legislator();
            return legislator;
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
        View baseView = inflater.inflate(R.layout.fragment_legislator, container, false);

        tabHostLegislator = (FragmentTabHost) baseView.findViewById(R.id.legTabHost);

        tabHostLegislator.setup(getActivity(),getChildFragmentManager(),R.id.legTabContent);

        //legTabByState = LegTabByState.getInstance();

        //legTabHouse = LegTabHouse.getInstance();

        //legTabSenate = LegTabSenate.getInstance();

        tabHostLegislator.addTab(tabHostLegislator.newTabSpec("LegTab1").setIndicator("By State"),LegTabByState.class,null);

        tabHostLegislator.addTab(tabHostLegislator.newTabSpec("LegTab2").setIndicator("House"),LegTabHouse.class,null);

        tabHostLegislator.addTab(tabHostLegislator.newTabSpec("LegTab3").setIndicator("Senate"),LegTabSenate.class,null);

        Log.d("Legislator Class", "Fragment Newly Created. Existing instance not used.");

        //REQUEST DATA FROM URL BEFORE GETTING THE INSTANCE
        volleyJsonObjectRequestState(LEG_URL_STATE);


        volleyJsonObjectRequestHouseSenate(LEG_URL_HOUSE_SENATE);

        return baseView;

    }

    public void volleyJsonObjectRequestState(String url){

        String  REQUEST_TAG = "http://hw9surabhisubramany-env.us-west-2.elasticbeanstalk.com/index.php?legislatorsstate=true";

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        leg_results_state = response;
                        Log.d(TAG, response.toString());

                        LegTabByState.parseJsonResults(getActivity(), response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d(TAG, "Error: " + error.getMessage());
            }
        });

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectReq,REQUEST_TAG);
    }

    public void volleyJsonObjectRequestHouseSenate(String url){

        String  REQUEST_TAG = "http://hw9surabhisubramany-env.us-west-2.elasticbeanstalk.com/index.php?legislatorshousesenate=true";

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        leg_results_housesenate = response;
                        Log.d(TAG, response.toString());

                        LegTabHouse.parseJsonResults(getActivity(), response);
                        LegTabSenate.parseJsonResults(getActivity(), response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d(TAG, "Error: " + error.getMessage());
            }
        });

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectReq,REQUEST_TAG);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();;
        tabHostLegislator=null;
    }

}