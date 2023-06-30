package com.example.physicaltrack_ed5042_individual_project_19190859;
//Name: Ian Rowland
//ID:19190859
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class dailyBroadcastReceiver extends BroadcastReceiver {
    userDB userDB;
    @Override
    public void onReceive(final Context context, Intent intent){

        userDB = new userDB(context);



        updateDBStepCount();
        Toast alarmNote = Toast.makeText(context, context.getString(R.string.receivermessage), Toast.LENGTH_LONG);
        alarmNote.show();

        userDB.closeDatabase();


    }



    public void updateDBStepCount(){
        userDB.updateStepCount(1, 0);

    }
}
