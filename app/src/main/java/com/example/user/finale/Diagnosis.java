package com.example.user.finale;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.finale.data.DatabaseHelper;

import java.util.logging.Logger;

public class Diagnosis extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    TextView tvContextMenu;
    CheckBox mFever, mCoughing, mSoreThroat, mRunningNose, mHeadache, mSlowPulse, mUlcer, mDiarrhea,
            mMuscularCramps, mDehydration, mVomiting, mNausea, mMuscularPain, mChilliness, mBodyache,
            mLossOfAppetite, mRedColoredRash, mScabs, mJointPains, mAbdominalPain, mYellowishEyes;
    Button mDiagnose;

    String disease;
    Treatment newTreatment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);

        newTreatment = new Treatment();
        databaseHelper = new DatabaseHelper(this);

        mFever = findViewById(R.id.fever);
        mCoughing = findViewById(R.id.coughing);
        mSoreThroat = findViewById(R.id.sore_throat);
        mRunningNose = findViewById(R.id.running_nose);
        mHeadache = findViewById(R.id.headache);
        mSlowPulse = findViewById(R.id.slow_pulse);
        mUlcer = findViewById(R.id.ulcer);
        mDiarrhea = findViewById(R.id.diarrhea);
        mMuscularCramps = findViewById(R.id.muscular_cramps);
        mDehydration = findViewById(R.id.dehydration);
        mVomiting = findViewById(R.id.vomiting);
        mNausea = findViewById(R.id.nausea);
        mMuscularPain = findViewById(R.id.muscular_pain);
        mChilliness = findViewById(R.id.chilliness);
        mBodyache = findViewById(R.id.body_ache);
        mLossOfAppetite = findViewById(R.id.loss_of_appetite);
        mRedColoredRash = findViewById(R.id.red_colored_rash);
        mScabs = findViewById(R.id.scabs);
        mJointPains = findViewById(R.id.joint_pains);
        mAbdominalPain = findViewById(R.id.abdominal_pain);
        mYellowishEyes = findViewById(R.id.yellowish_eyes);

        mDiagnose = findViewById(R.id.btn_diagnose);

        mDiagnose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForForDisease();
            }
        });

//        tvContextMenu = findViewById(R.id.tv_context_menu);
//        this.registerForContextMenu(tvContextMenu);
    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        if (v.getId() == R.id.tv_context_menu) {
//            this.getMenuInflater().inflate(R.menu.category_menu, menu);
//        }
//        super.onCreateContextMenu(menu, v, menuInfo);
//    }

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

    private void switchToTreatment() {
        Intent intent = new Intent(getApplicationContext(), Treatment.class);
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

    public void successDisplayDisease(final String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("😷 🤒 🤧 🤮 \n\nSeek treatment for disease " + s);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    boolean isInserted = databaseHelper.insertData(s);

                    if (isInserted) {
                        Toast.makeText(newTreatment, "Disease " + s + " is logged", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(newTreatment, "Error saving disease", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Logger.getLogger(String.valueOf(e));
                }
                switchToTreatment();
            }
        })
                .setNegativeButton("Re-" + getString(R.string.diagnose), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetCheckBoxes();
                    }
                });

        //Create AlertDialog object and run it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    // TODO Work on failedDisplayDisease Method
    public void failedDisplayDisease(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Error");
        builder.setPositiveButton(null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        })
                .setNegativeButton(null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        //Create AlertDialog object and run it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //Check for disease matching
    private void checkForForDisease() {
        // Influenza
        if (mFever.isChecked() && mCoughing.isChecked() && mSoreThroat.isChecked() && mRunningNose.isChecked()) {
            Toast.makeText(this, "" + getString(R.string.influenza).toUpperCase(), Toast.LENGTH_SHORT).show();
            successDisplayDisease("" + getString(R.string.influenza).toUpperCase());
            disease = getString(R.string.influenza);
        }
        // Typhoid
        else if (mFever.isChecked() && mHeadache.isChecked() && mSlowPulse.isChecked() && mUlcer.isChecked()) {
            successDisplayDisease("" + getString(R.string.typhoid).toUpperCase());
            disease = getString(R.string.typhoid);
        }
        // Cholera
        else if (mDiarrhea.isChecked() && mMuscularCramps.isChecked() && mDehydration.isChecked() && mVomiting.isChecked()) {
            successDisplayDisease("" + getString(R.string.cholera).toUpperCase());
            disease = getString(R.string.cholera);
        }
        // Malaria
        else if (mHeadache.isChecked() && mNausea.isChecked() && mMuscularPain.isChecked() && mChilliness.isChecked()) {
            successDisplayDisease("" + getString(R.string.malaria).toUpperCase());
            disease = getString(R.string.malaria);
        }
        //Hepatitis
        else if (mBodyache.isChecked() && mLossOfAppetite.isChecked() && mNausea.isChecked() && mYellowishEyes.isChecked()) {
            successDisplayDisease("" + getString(R.string.hepatitis).toUpperCase());
            disease = getString(R.string.hepatitis);
        }
        //Dengue fever
        else if (mFever.isChecked() && mHeadache.isChecked() && mJointPains.isChecked() && mVomiting.isChecked()) {
            successDisplayDisease("" + getString(R.string.dengue).toUpperCase());
            disease = getString(R.string.dengue);
        }
        //Amoeba
        else if (mUlcer.isChecked() && mAbdominalPain.isChecked() && mDiarrhea.isChecked() && mNausea.isChecked()) {
            successDisplayDisease("" + getString(R.string.amoeba).toUpperCase());
            disease = getString(R.string.amoeba);
        }
        //Chicken pox
        else if (mFever.isChecked() && mLossOfAppetite.isChecked() && mRedColoredRash.isChecked() && mScabs.isChecked()) {
            successDisplayDisease("" + getString(R.string.chicken_pox).toUpperCase());
            disease = getString(R.string.chicken_pox);
        } else {
            failedDisplayDisease(getString(R.string.cancel));
        }
    }

    // Reset checkbox after clicking predict button
    private void resetCheckBoxes() {
        if (mFever.isChecked() || mCoughing.isChecked() || mSoreThroat.isChecked() || mRunningNose.isChecked()
                || mHeadache.isChecked() || mSlowPulse.isChecked() || mUlcer.isChecked() || mDiarrhea.isChecked()
                || mMuscularCramps.isChecked() || mDehydration.isChecked() || mVomiting.isChecked()
                || mNausea.isChecked() || mMuscularPain.isChecked() || mChilliness.isChecked() || mBodyache.isChecked()
                || mLossOfAppetite.isChecked() || mRedColoredRash.isChecked() || mScabs.isChecked()
                || mJointPains.isChecked() || mAbdominalPain.isChecked() || mYellowishEyes.isChecked()) {

            mFever.setChecked(false);
            mCoughing.setChecked(false);
            mSoreThroat.setChecked(false);
            mRunningNose.setChecked(false);
            mHeadache.setChecked(false);
            mSlowPulse.setChecked(false);
            mUlcer.setChecked(false);
            mDiarrhea.setChecked(false);
            mMuscularCramps.setChecked(false);
            mDehydration.setChecked(false);
            mVomiting.setChecked(false);
            mNausea.setChecked(false);
            mMuscularPain.setChecked(false);
            mChilliness.setChecked(false);
            mBodyache.setChecked(false);
            mLossOfAppetite.setChecked(false);
            mRedColoredRash.setChecked(false);
            mScabs.setChecked(false);
            mJointPains.setChecked(false);
            mRedColoredRash.setChecked(false);
            mAbdominalPain.setChecked(false);
            mYellowishEyes.setChecked(false);
        }
    }

    public void onCheckboxClicked(View view) {

//        boolean checked = ((CheckBox) view).isChecked();
//
//        int counter = 0;
//        total = counter + 1;
//
//        // Check which checkbox was clicked
//        switch (view.getId()) {
//            case R.id.fever:
//                if (checked) {
//                    Toast.makeText(MainActivity.this, R.string.fever, Toast.LENGTH_SHORT).show();
//                    total = counter + 1;
//                } else {
//
//                }
//                break;
//
//            case R.id.coughing:
//                if (checked) {
//                    Toast.makeText(MainActivity.this, R.string.coughing, Toast.LENGTH_SHORT).show();
//                    total = counter + 1;
//                } else {
//
//                }
//                break;
//            case R.id.sore_throat:
//                if (checked) {
//                    Toast.makeText(MainActivity.this, R.string.sore_throat, Toast.LENGTH_SHORT).show();
//                    total = counter + 1;
//                } else {
//
//                }
//                break;
//            case R.id.running_nose:
//                if (checked) {
//                    Toast.makeText(MainActivity.this, R.string.running_nose, Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//                break;
//            case R.id.headache:
//                if (checked) {
//                    //Toast.makeText(MainActivity.this, R.string.headache, Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//                break;
//            case R.id.slow_pulse:
//                if (checked) {
//                    //Toast.makeText(MainActivity.this, "fever", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//                break;
//            case R.id.ulcer:
//                if (checked) {
//                    //Toast.makeText(MainActivity.this, "fever", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//                break;
//            case R.id.body_ache:
//                if (checked) {
//                    //Toast.makeText(MainActivity.this, "fever", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//                break;
//            case R.id.loss_of_appetite:
//                if (checked) {
//                    //Toast.makeText(MainActivity.this, "fever", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//                break;
//            case R.id.yellowish_eyes:
//                if (checked) {
//                    //Toast.makeText(MainActivity.this, "fever", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//                break;
//            case R.id.red_colored_rash:
//                if (checked) {
//                    //Toast.makeText(MainActivity.this, "fever", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//                break;
//            case R.id.diarrhea:
//                if (checked) {
//                    //Toast.makeText(MainActivity.this, "fever", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//                break;
//            case R.id.muscular_cramps:
//                if (checked) {
//                    //Toast.makeText(MainActivity.this, "fever", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//                break;
//            case R.id.dehydration:
//                if (checked) {
//                    //Toast.makeText(MainActivity.this, "fever", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//                break;
//            case R.id.vomiting:
//                if (checked) {
//                    //Toast.makeText(MainActivity.this, "fever", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//                break;
//            case R.id.nausea:
//                if (checked) {
//                    //Toast.makeText(MainActivity.this, "fever", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//                break;
//            case R.id.muscular_pain:
//                if (checked) {
//                    //Toast.makeText(MainActivity.this, "fever", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//                break;
//            case R.id.chilliness:
//                if (checked) {
//                    //Toast.makeText(MainActivity.this, "fever", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//                break;
//            case R.id.joint_pains:
//                if (checked) {
//                    //Toast.makeText(MainActivity.this, "fever", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//                break;
//            case R.id.abdominal_pain:
//                if (checked) {
//                    // Toast.makeText(MainActivity.this, "fever", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//                break;
//            case R.id.scabs:
//                if (checked) {
//                    // Toast.makeText(MainActivity.this, "fever", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//                break;
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_disease_db) {
            switchToDiseaseInfo();
            return true;
        } else if (id == R.id.action_log) {
            switchToLog();
            return true;
        } else if (id == R.id.action_reset) {
            resetCheckBoxes();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}