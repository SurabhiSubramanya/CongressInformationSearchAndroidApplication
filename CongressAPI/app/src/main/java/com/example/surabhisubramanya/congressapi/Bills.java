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

public class Bills extends Fragment {

    private FragmentTabHost tabHostBills;

    private static Bills bills = null;

    private BillsTabActive billsTabActive = null;
    private BillsTabNew billsTabNew = null;

    private static final String ACTIVE_BILL_URL = "http://hw9surabhisubramany-env.us-west-2.elasticbeanstalk.com/index.php?activebills=true";
    private static final String NEW_BILL_URL = "http://hw9surabhisubramany-env.us-west-2.elasticbeanstalk.com/index.php?newbills=true";

    private JSONObject active_bill_results;
    private JSONObject new_bill_results;

    public Bills() {
        // Required empty public constructor
    }

    public static Bills getInstance() {
        if (bills != null) {
            return bills;
        } else {
            bills = new Bills();
            return bills;
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
        View baseView = inflater.inflate(R.layout.fragment_bills, container, false);

        tabHostBills = (FragmentTabHost) baseView.findViewById(R.id.billTabHost);

        tabHostBills.setup(getActivity(),getChildFragmentManager(),R.id.billTabContent);

        //billsTabActive = BillsTabActive.getInstance();

        //billsTabNew = BillsTabNew.getInstance();

        tabHostBills.addTab(tabHostBills.newTabSpec("BillTab1").setIndicator("Active Bills"),BillsTabActive.class,null);

        tabHostBills.addTab(tabHostBills.newTabSpec("BillTab2").setIndicator("New Bills"),BillsTabNew.class,null);

        Log.d("Bills Class", "Fragment Newly Created. Existing instance not used.");

        //FETCH THE DATA FROM THE URL
        //ACTIVE BILLS
        volleyJsonObjectRequestActive(ACTIVE_BILL_URL);

        //NEW BILLS
        volleyJsonObjectRequestNew(NEW_BILL_URL);

        return baseView;

    }

    public void volleyJsonObjectRequestActive(String url){

        String  ACTIVE_REQUEST_TAG = "http://hw9surabhisubramany-env.us-west-2.elasticbeanstalk.com/index.php?activebills=trueindex.php?activebills=true";
        //String  NEW_REQUEST_TAG = "http://104.198.0.197:8080/bills?history.active=false&last_version.urls.pdf__exists=true&order=introduced_on__desc&per_page=50";

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        active_bill_results = response;
                        Log.d(TAG, response.toString());

                        BillsTabActive.parseJsonResults(getActivity(), response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d(TAG, "Error: " + error.getMessage());
            }
        });

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectReq,ACTIVE_REQUEST_TAG);
    }

    public void volleyJsonObjectRequestNew(String url){

        //String  ACTIVE_REQUEST_TAG = "http://104.198.0.197:8080/bills?history.active=true&last_version.urls.pdf__exists=true&order=introduced_on__desc&per_page=50";
        String  NEW_REQUEST_TAG = "http://hw9surabhisubramany-env.us-west-2.elasticbeanstalk.com/index.php?newbills=true";

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        new_bill_results = response;
                        Log.d(TAG, response.toString());

                        BillsTabNew.parseJsonResults(getActivity(), response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d(TAG, "Error: " + error.getMessage());
            }
        });

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectReq,NEW_REQUEST_TAG);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();;
        tabHostBills = null;
    }
}