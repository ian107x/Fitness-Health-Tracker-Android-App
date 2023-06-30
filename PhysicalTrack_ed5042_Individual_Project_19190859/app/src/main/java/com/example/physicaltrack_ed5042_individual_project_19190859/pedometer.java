package com.example.physicaltrack_ed5042_individual_project_19190859;
//Name: Ian Rowland
//ID:19190859
//This activity and get_fitness_health_info.java to be graded for activities and layout section.
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



import java.util.Calendar;

public class pedometer extends AppCompatActivity implements SensorEventListener {

    boolean isPedometerStarted;
    int stepCount;
    SensorManager sensorManager;
    TextView instructionText;
    TextView stepCounterField;
    Button returnToMain;
    Button startPedometerButton;
    Button startReceiverButton;
    Button stopReceiverButton;
    Button getSteps;
    TextView getStepsField;
    userDB userDB;
    private double previousAccel = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer);
        //stop phone from auto-sleeping in this activity so steps can constantly be counted
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        instructionText = (TextView) findViewById(R.id.instructiontext);
        returnToMain = (Button) findViewById(R.id.returntomainbutton);
        startPedometerButton = (Button) findViewById(R.id.startpedometer);
        stepCounterField = (TextView) findViewById(R.id.stepcounter);
        startReceiverButton = (Button) findViewById(R.id.startreceiver);
        stopReceiverButton = (Button)findViewById(R.id.stopreceiver);
        getSteps = (Button)findViewById(R.id.getsteps);
        getStepsField = (TextView)findViewById(R.id.stepmessage);

        userDB = new userDB(getApplicationContext());
        returnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnToMainIntent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(returnToMainIntent);
            }
        });

        startPedometerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isPedometerStarted){
                    startPedometerButton.setText(getResources().getString(R.string.stoppedometertext));
                    startPedometer();

                }else{
                    stopPedometer();
                    startPedometerButton.setText(getResources().getString(R.string.startpedometertext));
                }
            }
        });
        getSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalStepCount = getStepCountFromDB();
                double caloriesBurned = totalStepCount * 0.04;
                getStepsField.setText(getResources().getString(R.string.stepmessage) + totalStepCount +  " " + getResources().getString(R.string.caloriemessage) + caloriesBurned);
            }
        });
        startReceiverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDailyAlarm();
            }
        });
        stopReceiverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopDailyAlarm();
            }
        });
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

    @Override
    public void onSensorChanged(SensorEvent event){
        startPedometerSensor(event);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void startPedometerSensor(SensorEvent event) {
        if(isPedometerStarted){

        float values[] = event.values;

        float accel_x = values[0];
        float accel_y = values[1];
        float accel_z = values[2];

        double acceleration = Math.sqrt((accel_x * accel_x) + (accel_y * accel_y) + (accel_z * accel_z));
        double accelDelta = acceleration - previousAccel;
        previousAccel = acceleration;

        if (accelDelta > 7) {
            stepCount++;
            stepCounterField.setText(String.valueOf(stepCount));
        }
    }
    }
        @Override
        protected void onResume() {
            super.onResume();
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        }

        @Override
        protected void onPause(){
            super.onPause();
            sensorManager.unregisterListener(this);
        }

        public void startPedometer(){
            isPedometerStarted = true;
        }

        public void stopPedometer(){
            isPedometerStarted = false;

            int oldStepCount = getStepCountFromDB();
            int newStepCount = stepCount + oldStepCount;
            updateDBStepCount(newStepCount);

            stepCount = 0;
            stepCounterField.setText(String.valueOf(stepCount));
        }

        public void startDailyAlarm(){

            //schedule broadcastReceiver to be called at 9am every day.
            Calendar startOfDayCalender = Calendar.getInstance();

            //define time for receiver using Calendar
            startOfDayCalender.set(Calendar.HOUR_OF_DAY, 9);
            startOfDayCalender.set(Calendar.MINUTE, 0);
            startOfDayCalender.set(Calendar.SECOND, 0);

            if (startOfDayCalender.getTimeInMillis() < System.currentTimeMillis()) {
                //ensures that the receiver is executed at the next instance of 9am, if the start receiver button is clicked after 9am.
                startOfDayCalender.add(Calendar.DAY_OF_YEAR, 1);
            }

            AlarmManager receiverAlarm = (AlarmManager) getSystemService(ALARM_SERVICE);

            Intent dailyReceiverIntent = new Intent(getApplicationContext(), dailyBroadcastReceiver.class);

            PendingIntent dailyPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, dailyReceiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            //set broadcastReceiver to repeat every day.
            receiverAlarm.setRepeating(AlarmManager.RTC_WAKEUP, startOfDayCalender.getTimeInMillis(), AlarmManager.INTERVAL_DAY, dailyPendingIntent);

            Toast startReceiverToast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.startreceivernote) , Toast.LENGTH_LONG);
            startReceiverToast.show();


        }

        public void stopDailyAlarm(){
        //define alarm and pendingIntent using same information used to define it in startDailyAlarm().
            AlarmManager receiverAlarm = (AlarmManager) getSystemService(ALARM_SERVICE);

            Intent dailyReceiverIntent = new Intent(getApplicationContext(), dailyBroadcastReceiver.class);

            PendingIntent dailyPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, dailyReceiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            //cancel pendingIntent and alarm to cancel the daily step reset
            dailyPendingIntent.cancel();
            receiverAlarm.cancel(dailyPendingIntent);

            Toast stopReceiverToast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.stopreceivernote) , Toast.LENGTH_LONG);
            stopReceiverToast.show();
        }

    public int getStepCountFromDB(){


        return  userDB.getStepCount(1);
    }

    public void updateDBStepCount(int newStepCount){
        userDB.updateStepCount(1, newStepCount);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putString("pedbuttontext", startPedometerButton.getText().toString());
        outState.putString("getSteps", getStepsField.getText().toString());
        outState.putString("stepCountField", stepCounterField.getText().toString());

        outState.putDouble("prevAccel", previousAccel);
        outState.putInt("number", stepCount);
        outState.putBoolean("isStarted", isPedometerStarted);





    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        stepCount = savedInstanceState.getInt("number");
        isPedometerStarted = savedInstanceState.getBoolean("isStarted");
        previousAccel = savedInstanceState.getDouble("prevAccel");

        stepCounterField.setText(savedInstanceState.getString("stepCountField"));
        getStepsField.setText(savedInstanceState.getString("getSteps"));
        startPedometerButton.setText(savedInstanceState.getString("pedbuttontext"));

    }
    public void onDestroy(){
        super.onDestroy();
        userDB.closeDatabase();
    }



}
