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

public class Committees extends Fragment {

    private FragmentTabHost tabHostCommittees;

    private CommitteesTabHouse committeesTabHouse = null;
    private CommitteesTabSenate committeesTabSenate = null;
    private CommitteesTabJoint committeesTabJoint = null;

    private static Committees committees = null;

    private static final String COMM_URL = "http://hw9surabhisubramany-env.us-west-2.elasticbeanstalk.com/index.php?committees=true";

    private JSONObject comm_results;

    public Committees() {
        // Required empty public constructor
    }

    public static Committees getInstance() {
        if (committees != null) {
            return committees;
        } else {
            committees = new Committees();
            return committees;
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
        View baseView = inflater.inflate(R.layout.fragment_committees, container, false);

        tabHostCommittees = (FragmentTabHost) baseView.findViewById(R.id.commTabHost);

        tabHostCommittees.setup(getActivity(),getChildFragmentManager(),R.id.commTabContent);

        //committeesTabHouse = CommitteesTabHouse.getInstance();

        //committeesTabSenate = CommitteesTabSenate.getInstance();

        //committeesTabJoint = CommitteesTabJoint.getInstance();

        tabHostCommittees.addTab(tabHostCommittees.newTabSpec("CommTab1").setIndicator("House"),CommitteesTabHouse.class,null);

        tabHostCommittees.addTab(tabHostCommittees.newTabSpec("CommTab2").setIndicator("Senate"),CommitteesTabSenate.class,null);

        tabHostCommittees.addTab(tabHostCommittees.newTabSpec("CommTab3").setIndicator("Joint"),CommitteesTabJoint.class,null);

        Log.d("Committees Class", "Fragment Newly Created. Existing instance not used.");

        //FETCH THE DATA FROM THE URL
        volleyJsonObjectRequest(COMM_URL);

        return baseView;

    }

    public void volleyJsonObjectRequest(String url){

        String  REQUEST_TAG = "http://hw9surabhisubramany-env.us-west-2.elasticbeanstalk.com/index.php?committees=true";

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        comm_results = response;
                        Log.d(TAG, response.toString());

                        CommitteesTabHouse.parseJsonResults(getActivity(), response);
                        CommitteesTabSenate.parseJsonResults(getActivity(), response);
                        CommitteesTabJoint.parseJsonResults(getActivity(), response);
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
        tabHostCommittees = null;
    }
}