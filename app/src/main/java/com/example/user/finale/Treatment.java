package com.example.user.finale;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Treatment extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<ListDiseases> listDiseases;

    RequestQueue mQueue;

    final String URL = "http://10.0.2.2/treatment.json";
//    final String URL = "https://api.myjson.com/bins/13fm92";

    Diagnosis activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Treatment.this, Diagnosis.class);
                startActivity(intent);
            }
        });

        activity = new Diagnosis();

        recyclerView = findViewById(R.id.recyclerView_t);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listDiseases = new ArrayList<>();

        mQueue = Volley.newRequestQueue(this);
        loadDiseaseInfo();
    }

    private void switchToDiagnosis() {
        Intent intent = new Intent(getApplicationContext(), Diagnosis.class);
        startActivity(intent);
    }

    private void switchToDiseaseInfo() {
        Intent intent = new Intent(getApplicationContext(), DiseaseInfo.class);
        startActivity(intent);
    }

    private void switchToLog() {
        Intent intent = new Intent(getApplicationContext(), Log.class);
        startActivity(intent);
    }

    private void loadDiseaseInfo() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading data...");
        dialog.show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dialog.dismiss();
                        try {
                            JSONArray jsonArray = response.getJSONArray("disease");


                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject disease = jsonArray.getJSONObject(i);
                                ListDiseases diseaseItem = new ListDiseases(
                                        disease.getString("name"),
                                        "", "",
                                        disease.getString("treatment")
                                );
                                listDiseases.add(diseaseItem);
                            }

                            adapter = new TreatmentAdapter(listDiseases, getApplicationContext(), Treatment.this);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(Treatment.this, "Error loading json" + error, Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                });
        mQueue.add(request);
    }

    // Display disease name and its treatmen on an alert dialog box
    public void showTreatmentOnCard(String diseaseName, String diseaseTreatment) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(Treatment.this);
            builder.setTitle(diseaseName);
            builder.setMessage(diseaseTreatment);

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } catch (Exception e) {
            Toast.makeText(Treatment.this, "" + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.treatment_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_diagnosis) {
            switchToDiagnosis();
            return true;
        } else if (id == R.id.action_log) {
            switchToLog();
            return true;
        } else if (id == R.id.action_disease_db) {
            switchToDiseaseInfo();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
