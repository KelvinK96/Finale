package com.example.user.finale;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
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

public class DiseaseInfo extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    CardView cardView_d;

    private List<ListDiseases> listDiseases;
    DiseaseAdapter diseaseAdapter;

    RequestQueue mQueue;

    final String URL = "http://10.0.2.2/treatment.json";
//    final String URL = "https://api.myjson.com/bins/13fm92";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cardView_d = findViewById(R.id.card_view_d);

        listDiseases = new ArrayList<>();

        mQueue = Volley.newRequestQueue(this);
        loadDiseaseInfo();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.card_view_d) {
            this.getMenuInflater().inflate(R.menu.category_menu, menu);
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int selectedItemId = item.getItemId();
        switch (selectedItemId) {
            case R.id.menu_desc:
                break;
            case R.id.menu_symptoms:
                break;
            case R.id.menu_treatment:
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void loadDiseaseInfo() {
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
                                        disease.getString("desc"),
                                        disease.getString("symptoms"),
                                        disease.getString("treatment"));
                                listDiseases.add(diseaseItem);
                            }

                            adapter = new DiseaseAdapter(listDiseases, getApplicationContext(), DiseaseInfo.this);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DiseaseInfo.this, "Error loading json vvv" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(DiseaseInfo.this, "Error loading json" + error, Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });
        mQueue.add(request);
    }

    private void switchToDiagnosis() {
        Intent intent = new Intent(getApplicationContext(), Diagnosis.class);
        startActivity(intent);
    }

    private void switchToTreatment() {
        Intent intent = new Intent(getApplicationContext(), Treatment.class);
        startActivity(intent);
    }

    // Display disease name and its description on an alert dialog box
    public void showDescriptionOnCard(String diseaseName, String diseaseDesc) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(DiseaseInfo.this);
            builder.setTitle(diseaseName);
            builder.setMessage(diseaseDesc);

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } catch (Exception e) {
            Toast.makeText(DiseaseInfo.this, "" + e, Toast.LENGTH_LONG).show();
        }
    }

     // Popup menu for when card is clicked, will contain a menu for what is to be displayed
     public void categoryPopup() {
         final PopupMenu popupMenu = new PopupMenu(DiseaseInfo.this, cardView_d);
         popupMenu.getMenuInflater().inflate(R.menu.category_menu, popupMenu.getMenu());

         popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
             @Override
             public boolean onMenuItemClick(MenuItem item) {
                 Toast.makeText(DiseaseInfo.this, "" + item.getTitle(), Toast.LENGTH_LONG).show();
                 return true;
             }
         });

         popupMenu.show();
     }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.disease_info_menu, menu);
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
        } else if (id == R.id.action_treatment) {
            switchToTreatment();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

