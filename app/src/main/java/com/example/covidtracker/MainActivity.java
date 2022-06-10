package com.example.covidtracker;

import static com.android.volley.VolleyLog.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private TextView textWorldTotalCases, textWorldTotalRecovered, textWorldTotalDeaths, textIndiaTotalCases, textIndiaTotalRecovered, textIndiaTotalDeaths;
    private Button trackMore, whoLink;
    private ImageView rotatingLogo, rotatingLogoDark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init views
        initViews();

        //transition between activities
        overridePendingTransition(R.anim.from_right, R.anim.to_left);

        //animation for logo
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        rotatingLogo.startAnimation(rotate);
        rotatingLogoDark.startAnimation(rotate);


        //fetching world data to text views
        getWorldInfo();

        //fetching indian data to text views
        getIndianInfo();

        //track more button event listener
        trackMore.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegionsRV.class);
            startActivity(intent);
        });

        //WHO website
        whoLink.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://covid19.who.int/");
            gotoWeb(uri);
        });
    }


    //initializing views
    private void initViews() {
        rotatingLogo = findViewById(R.id.rotatingLogo);
        rotatingLogoDark = findViewById(R.id.rotatingLogo);
        textWorldTotalCases = findViewById(R.id.textWorldTotalCases);
        textWorldTotalRecovered = findViewById(R.id.textWorldTotalRecovered);
        textWorldTotalDeaths = findViewById(R.id.textWorldTotalDeaths);
        textIndiaTotalCases = findViewById(R.id.textIndiaTotalCases);
        textIndiaTotalRecovered = findViewById(R.id.textIndiaTotalRecovered);
        textIndiaTotalDeaths = findViewById(R.id.textIndiaTotalDeaths);
        whoLink = findViewById(R.id.whoLink);
        trackMore = findViewById(R.id.btnTrackMore);

    }

    //getting world info
//    Volley is an HTTP library that makes networking for Android apps easier and most importantly, faster. 
    private void getWorldInfo() {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://corona.lmao.ninja/v3/covid-19/all", null, response -> {
            Log.d(TAG, "onResponse: SUCCESSFUL");
            try {
                int totalWorldCases = response.getInt("cases");
                textWorldTotalCases.setText(String.valueOf(totalWorldCases));
                int totalWorldRecovery = response.getInt("recovered");
                textWorldTotalRecovered.setText(String.valueOf(totalWorldRecovery));
                int totalWorldDeaths = response.getInt("deaths");
                textWorldTotalDeaths.setText(String.valueOf(totalWorldDeaths));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        },
                error -> {
                    Log.d(TAG, "onErrorResponse: Something went wrong!");
                    Toast.makeText(MainActivity.this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                });
        requestQueue.add(jsonObjectRequest);
        //request
    }

    //getting indian info
    private void getIndianInfo() {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://api.rootnet.in/covid19-in/stats/latest", null, response -> {
            Log.d(TAG, "onResponse: SUCCESSFUL");
            try {
                JSONObject data = response.getJSONObject("data");
                JSONObject summary = data.getJSONObject("summary");
                int totalIndianCases = summary.getInt("total");
                textIndiaTotalCases.setText(String.valueOf(totalIndianCases));
                int totalIndianRecovery = summary.getInt("discharged");
                textIndiaTotalRecovered.setText(String.valueOf(totalIndianRecovery));
                int totalIndianDeaths = summary.getInt("deaths");
                textIndiaTotalDeaths.setText(String.valueOf(totalIndianDeaths));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        },
                error -> {
                    Log.d(TAG, "onErrorResponse: Something went wrong!");
                    Toast.makeText(MainActivity.this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                });
        requestQueue.add(jsonObjectRequest);
    }

    //moving to webpage
    private void gotoWeb(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
