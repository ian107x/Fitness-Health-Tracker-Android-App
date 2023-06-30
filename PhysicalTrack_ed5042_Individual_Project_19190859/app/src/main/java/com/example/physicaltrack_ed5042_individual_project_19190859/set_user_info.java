package com.example.physicaltrack_ed5042_individual_project_19190859;
//Name: Ian Rowland
//ID:19190859
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class set_user_info extends AppCompatActivity {
    userDB userDB;
    Button setHeightButton;
    Button setWeightButton;
    Button setAgeButton;
    Button setGenderButton;
    Button returnToMainButton;
    Button viewUserInfoButton;
    EditText heightField;
    EditText weightField;
    EditText ageField;
    Spinner genderSet;
    SharedPreferences.OnSharedPreferenceChangeListener prefListener;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_info);

        setHeightButton = (Button) findViewById(R.id.setheight);
        setWeightButton = (Button) findViewById(R.id.setweight);
        setAgeButton = (Button) findViewById(R.id.agebutton);
        setGenderButton = (Button)findViewById(R.id.setgender);
        returnToMainButton = (Button) findViewById(R.id.returntomain);
        viewUserInfoButton = (Button) findViewById(R.id.viewinfo);
        genderSet = (Spinner)findViewById(R.id.gender);
        heightField = (EditText)findViewById(R.id.height);
        weightField = (EditText)findViewById(R.id.weight);
        ageField = (EditText) findViewById(R.id.age);

        userDB = new userDB(getApplicationContext());

        setHeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isHeightSet()){
                    float height = Float.parseFloat(heightField.getText().toString());
                    setHeight(height);
                    heightField.setText("");
                    Toast confirmHeightToast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.confirmheighttext) , Toast.LENGTH_LONG);
                    confirmHeightToast.show();
                }
            }
        });

        setWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isWeightSet()){
                    float weight = Float.parseFloat(weightField.getText().toString());
                    setWeight(weight);
                    weightField.setText("");
                    Toast confirmWeightToast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.confirmweighttext) , Toast.LENGTH_LONG);
                    confirmWeightToast.show();
                }
            }
        });
        setAgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAgeSet()){
                    int age = Integer.parseInt(ageField.getText().toString());
                    setAge(age);
                    ageField.setText("");
                    Toast confirmAgeToast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.confirmagetext) , Toast.LENGTH_LONG);
                    confirmAgeToast.show();
                }
            }
        });
        setGenderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gender = genderSet.getSelectedItem().toString();
                setGender(gender);
                Toast confirmGenderToast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.confirmgendertext) , Toast.LENGTH_LONG);
                confirmGenderToast.show();
            }
        });

        returnToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnToMainIntent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(returnToMainIntent);
            }
        });
        viewUserInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewInfoIntent = new Intent(getBaseContext(), get_fitness_health_info.class);
                startActivity(viewInfoIntent);
            }
        });

        SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String name = myPrefs.getString(AppPreferences.KEY_USERNAME,"");
        if (!name.contentEquals("")) {

            viewUserInfoButton.setText(getResources().getString(R.string.view) + " " + name + getResources().getString(R.string.sharedprefphysinfo));

        }


        prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                updateUIFromPreferences(prefs);
            }
        };

        myPrefs.registerOnSharedPreferenceChangeListener(prefListener);



    }


    private boolean isHeightSet(){
        //returns true if heightfield contains text
        return !heightField.getText().toString().contentEquals("");
    }

    private boolean isWeightSet(){
        //returns true if weightfield contains text
        return !weightField.getText().toString().contentEquals("");
    }
    private boolean isAgeSet(){
        //returns true if agefield contains text
        return !ageField.getText().toString().contentEquals("");
    }

    private void setWeight(float weight){
        userDB.updateWeight(1, weight);
    }

    private void setHeight(float height){
        userDB.updateHeight(1, height);
    }
    private void setAge(int age){
        userDB.updateAge(1, age);
    }
    private void setGender(String gender){
        userDB.updateGender(1, gender);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.preferences: {
                Intent AppPreferencesIntent = new Intent(getBaseContext(), AppPreferences.class);
                startActivity(AppPreferencesIntent);
                //
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateUIFromPreferences(SharedPreferences prefs) {
        String name = prefs.getString(AppPreferences.KEY_USERNAME,"");
        if (!name.contentEquals("")) {

            viewUserInfoButton.setText(getResources().getString(R.string.view) + " " + name + getResources().getString(R.string.sharedprefphysinfo));

        }
    }
    
    public void onDestroy(){
        super.onDestroy();
        userDB.closeDatabase();
    }
}
