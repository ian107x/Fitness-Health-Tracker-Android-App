package com.example.physicaltrack_ed5042_individual_project_19190859;
//Name: Ian Rowland
//ID:19190859
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {


    Button setUserInfoButton;
    Button getUserInfoButton;
    Button usePedometerButton;
    SharedPreferences.OnSharedPreferenceChangeListener prefListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUserInfoButton = (Button) findViewById(R.id.setuserinfo);
        getUserInfoButton = (Button)findViewById(R.id.getuserinfo);
        usePedometerButton =(Button)findViewById(R.id.usepedometer);

        setUserInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setUserInfoIntent = new Intent(getBaseContext(),set_user_info.class);
                startActivity(setUserInfoIntent);
            }
        });

        getUserInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getUserInfoIntent = new Intent(getBaseContext(), get_fitness_health_info.class);
                startActivity(getUserInfoIntent);
            }
        });

        usePedometerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent usePedometerIntent = new Intent(getBaseContext(), pedometer.class);
                startActivity(usePedometerIntent);
            }
        });

        SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String name = myPrefs.getString(AppPreferences.KEY_USERNAME,"");
        if (!name.contentEquals("")) {
            setUserInfoButton.setText(getResources().getString(R.string.set) + " " +  name + getResources().getString(R.string.sharedprefphysinfo));
            getUserInfoButton.setText(getResources().getString(R.string.view) + " " + name + getResources().getString(R.string.sharedprefphysinfo));
            usePedometerButton.setText(getResources().getString(R.string.use) + " " +name + getResources().getString(R.string.sharedprefpedometertext));
        }


       prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                updateUIFromPreferences(prefs);
            }
        };

        myPrefs.registerOnSharedPreferenceChangeListener(prefListener);


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

            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateUIFromPreferences(SharedPreferences prefs) {
        String name = prefs.getString(AppPreferences.KEY_USERNAME,"");
        if (!name.contentEquals("")) {
            setUserInfoButton.setText(getResources().getString(R.string.set) + " " + name + getResources().getString(R.string.sharedprefphysinfo));
            getUserInfoButton.setText(getResources().getString(R.string.view) + " " + name + getResources().getString(R.string.sharedprefphysinfo));
            usePedometerButton.setText(getResources().getString(R.string.use) + " " + name + getResources().getString(R.string.sharedprefpedometertext));
        }
    }
}