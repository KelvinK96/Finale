package com.example.user.finale;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.finale.data.DatabaseHelper;

public class Log extends AppCompatActivity {

    TextView dispLog;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        databaseHelper = new DatabaseHelper(this);
        dispLog = findViewById(R.id.tv_disp_log);

        viewLog();
    }

    public void viewLog() {
        try {
            Cursor cursor = databaseHelper.viewLog();
            if (cursor.getCount() == 0) {
                showLog("Warning 🤔", "Empty log...");
                return;
            }

            StringBuffer buffer = new StringBuffer();
            while (cursor.moveToNext()) {
                buffer.append("No. " + cursor.getString(0) + "\t");
                buffer.append("Disease " + cursor.getString(1) + "\n");
            }
            // show all log
            showLog("Log", buffer.toString());
        } catch (Exception e) {
            Toast.makeText(this, "" + e, Toast.LENGTH_LONG).show();
            dispLog.append(String.valueOf(e));
        }
    }

    public void showLog(String title, String disease) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(disease);
        builder.show();
    }

    public void switchToDiagnosis() {
        Intent intent = new Intent(Log.this, Diagnosis.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.log, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_diagnosis_l) {
            switchToDiagnosis();
            return true;
        } else if (id == R.id.action_refresh) {
            viewLog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
