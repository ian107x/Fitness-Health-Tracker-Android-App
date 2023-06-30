package com.example.physicaltrack_ed5042_individual_project_19190859;
//Name: Ian Rowland
//ID:19190859
//This activity and pedometer.java to be graded for activities and layout section.
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



public class get_fitness_health_info extends AppCompatActivity {

    SharedPreferences.OnSharedPreferenceChangeListener prefListener;

    Button getWeightButton;
    Button getHeightButton;
    Button getBMIButton;
    Button getBMRButton;
    Button getGenderButton;
    Button getAgeButton;

    Button returnToMainButton;
    Button setUserInfoButton;
    TextView weightDisplay;
    TextView heightDisplay;
    TextView genderDisplay;
    TextView BMIDisplay;
    TextView BMRDisplay;
    TextView ageDisplay;

    userDB userDB;
    float weight;
    float height;
    float BMI ;
    int age ;

    float BMR;
    float caloriesToMaintainWeight;
    String gender;


    //SharedPreferences.OnSharedPreferenceChangeListener prefListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info);


        userDB = new userDB(getApplicationContext());
        getWeightButton = (Button) findViewById(R.id.getweight);
        getHeightButton = (Button) findViewById(R.id.getheight);
        getBMIButton = (Button) findViewById(R.id.getbmi);
        getBMRButton = (Button) findViewById(R.id.getbmr);
        getGenderButton = (Button)findViewById(R.id.getgender);
        getAgeButton = (Button)findViewById(R.id.getage);


        weightDisplay = (TextView)findViewById(R.id.weightdisplay);
        heightDisplay = (TextView)findViewById(R.id.heightdisplay);
        genderDisplay = (TextView)findViewById(R.id.genderdisplay);
        ageDisplay = (TextView)findViewById(R.id.agedisplayfield);



        returnToMainButton = (Button)findViewById(R.id.returntomainbutton);
        setUserInfoButton = (Button)findViewById(R.id.setinfobutton);

        BMIDisplay = (TextView)findViewById(R.id.bmidisplay);
        BMRDisplay = (TextView)findViewById(R.id.bmrdisplay);

        weight = getWeight();
        height = getHeight();
        age = getAge();
        gender = getGender();


//


        



        getWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                weightDisplay.setText(getResources().getString(R.string.weightdisplay) + weight + getResources().getString(R.string.kgtext) );
            }
        });

        getHeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               heightDisplay.setText(getResources().getString(R.string.heightdisplay) + height + " " + getResources().getString(R.string.cmtext));
            }
        });


        getBMRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BMR = getBMR(height, weight, age, gender);

                caloriesToMaintainWeight = BMR * (float) 1.375;
                BMRDisplay.setText(getResources().getString(R.string.bmrdisplay) + BMR + ". " + getResources().getString(R.string.caloriestext)+ caloriesToMaintainWeight);
            }
        });

        getGenderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                genderDisplay.setText(getResources().getString(R.string.genderdisplay) + " " + gender);
            }
        });
        getAgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ageDisplay.setText(getResources().getString(R.string.agedisplay) + " " + age + " " + getResources().getString(R.string.yearstext));
            }
        });


        getBMIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BMI = getBMI(height, weight);
                if(BMI > 30){
                    BMIDisplay.setText(getResources().getString(R.string.bmidisplay) + " " + BMI + ". " + getResources().getString(R.string.bmiobese));
                }else if(BMI > 25){
                    BMIDisplay.setText(getResources().getString(R.string.bmidisplay) + " " + BMI + ". " + getResources().getString(R.string.bmioverweight));
                }else if(BMI > 18.5){
                    BMIDisplay.setText(getResources().getString(R.string.bmidisplay) + " " + BMI + ". " + getResources().getString(R.string.bminormal));
                }else{
                    BMIDisplay.setText(getResources().getString(R.string.bmidisplay) + " " + BMI + ". " + getResources().getString(R.string.bmiunderweight));
                }

            }
        });


        returnToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnToMainIntent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(returnToMainIntent);
            }
        });

        setUserInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setUserInfoIntent = new Intent(getBaseContext(), set_user_info.class);
                startActivity(setUserInfoIntent);
            }
        });

        SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String name = myPrefs.getString(AppPreferences.KEY_USERNAME,"");
        if (!name.contentEquals("")) {

            setUserInfoButton.setText(getResources().getString(R.string.set) + " " + name + getResources().getString(R.string.sharedprefphysinfo));

        }


        prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                updateUIFromPreferences(prefs);
            }
        };

        myPrefs.registerOnSharedPreferenceChangeListener(prefListener);


    }



    public float getWeight(){

        return userDB.getWeight(1);
    }

    public float getHeight(){
        return userDB.getHeight(1);
    }

    public String getGender(){
        return userDB.getGender(1);
    }
    public int getAge(){
       return userDB.getAge(1);
    }

    public float getBMI(float height, float weight){
        float heightInMetres = height/100;
        return weight/(heightInMetres*heightInMetres);
    }
    public float getBMR(float height, float weight, int age, String gender){
        float BMR;
            if(gender.equals("Male")){
                BMR = (float) ((10*weight)+(6.25*height)-(5*age) + 5);
            }else{
                BMR = (float) ((10*weight) + (6.25*height) - (5*age) - 161);
            }
        return BMR;
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

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putString("savedweight", weightDisplay.getText().toString());
        outState.putString("savedheight", heightDisplay.getText().toString());
        outState.putString("savedage", ageDisplay.getText().toString());
        outState.putString("savedbmi", BMIDisplay.getText().toString());
        outState.putString("savedbmr", BMRDisplay.getText().toString());
        outState.putString("savedgender", genderDisplay.getText().toString());



    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        weightDisplay.setText(savedInstanceState.getString("savedweight"));
        heightDisplay.setText(savedInstanceState.getString("savedheight"));
        ageDisplay.setText(savedInstanceState.getString("savedage"));
        BMIDisplay.setText(savedInstanceState.getString("savedbmi"));
        BMRDisplay.setText(savedInstanceState.getString("savedbmr"));
        genderDisplay.setText(savedInstanceState.getString("savedgender"));

    }

    private void updateUIFromPreferences(SharedPreferences prefs) {
        String name = prefs.getString(AppPreferences.KEY_USERNAME,"");
        if (!name.contentEquals("")) {

            setUserInfoButton.setText(getResources().getString(R.string.set) + " " + name + getResources().getString(R.string.sharedprefphysinfo));

        }
    }

    public void onDestroy(){
        super.onDestroy();
        userDB.closeDatabase();
    }

}