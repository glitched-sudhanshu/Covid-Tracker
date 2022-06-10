package com.example.covidtracker;

import static com.android.volley.VolleyLog.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RegionsRV extends AppCompatActivity {

    private RecyclerView regionsRV;
    private RegionsStatsRVAdapter regionsStatsRVAdapter;
    private final List<Model> stateStats = new ArrayList<>();
    private TextView searchBar;


    //transition on back pressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.from_left, R.anim.to_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regions_rv);

        //transition between activities
        overridePendingTransition(R.anim.from_right, R.anim.to_left);

        //search bar functionality
        searchBar = findViewById(R.id.searchBar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        //fetching indian data to text views
        getIndianInfo();

        //setting up the adapter
        regionsRV = findViewById(R.id.regionsRV);
        regionsStatsRVAdapter = new RegionsStatsRVAdapter(stateStats, this);
        regionsRV.setLayoutManager(new LinearLayoutManager(this));
        regionsRV.setAdapter(regionsStatsRVAdapter);


    }

    //search bar filtered lists
    private void filter(String text) {
        ArrayList<Model> filteredList = new ArrayList<>();
        for (Model filteredItem : stateStats) {
            boolean forState = filteredItem.getState().toLowerCase().contains(text.toLowerCase());
            boolean forDistrict = filteredItem.getDistrict().toLowerCase().contains(text.toLowerCase());
            if (forDistrict || forState) {
                filteredList.add(filteredItem);
            }
        }
        regionsStatsRVAdapter.filterList(filteredList);
    }


    //getting indian info
    private void getIndianInfo() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://data.covid19india.org/state_district_wise.json", null, response -> {
            Log.d(TAG, "onResponse: SUCCESSFUL");
            try {
                Iterator<String> keys = response.keys();
                keys.next();
                while (keys.hasNext()) {
                    String state = keys.next();
                    JSONObject jsonObject = response.getJSONObject(state);
                    JSONObject districtObj = jsonObject.getJSONObject("districtData");
                    Iterator<String> districts = districtObj.keys();
                    while (districts.hasNext()) {
                        String districtName = districts.next();
                        JSONObject district = districtObj.getJSONObject(districtName);
                        long deaths, recovered, cases, active, migratedOthers;

                        deaths = district.getLong("deceased");
                        cases = district.getLong("confirmed");
                        recovered = district.getLong("recovered");
                        active = district.getLong("active");
                        migratedOthers = district.getLong("migratedother");


                        Log.d(TAG, "getIndianInfo: " + deaths);

                        stateStats.add(new Model(state, districtName, cases, recovered, deaths, active, migratedOthers));

                        regionsStatsRVAdapter.setStateStats(stateStats);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        },
                error -> {
                    Log.d(TAG, "onErrorResponse: Something went wrong!");
                    Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                });
        requestQueue.add(jsonObjectRequest);
    }

}