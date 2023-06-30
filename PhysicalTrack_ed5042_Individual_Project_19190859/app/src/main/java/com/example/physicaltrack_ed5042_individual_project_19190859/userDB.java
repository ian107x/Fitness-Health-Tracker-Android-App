package com.example.physicaltrack_ed5042_individual_project_19190859;
//Name: Ian Rowland
//ID:19190859
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class userDB {

    public static final String KEY_ID = "_id";
    public static final String KEY_WEIGHT = "user_weight";
    public static final String KEY_HEIGHT = "user_height";
    public static final String KEY_AGE = "user_age";
    public static final String KEY_GENDER = "user_gender";
    public static final String KEY_STEP_COUNT = "pedometer_step_count";

    private Context context;
    private userDBOpenHelper userDBOpenHelper;

    public userDB(Context context){
        this.context = context;
        userDBOpenHelper = new userDBOpenHelper(context, userDBOpenHelper.DATABASE_NAME, null,
                userDBOpenHelper.DATABASE_VERSION);

        if(getAll().length == 0){
            this.generateDefaultUser(80, 200, 20,"Male", 0);
        }


    }

    public void generateDefaultUser(float weight, float height, int age, String gender, int stepCount){
        ContentValues newValues = new ContentValues();

        newValues.put(KEY_WEIGHT, weight);
        newValues.put(KEY_HEIGHT, height);
        newValues.put(KEY_AGE, age);
        newValues.put(KEY_GENDER, gender);
        newValues.put(KEY_STEP_COUNT, stepCount);

        SQLiteDatabase db = userDBOpenHelper.getWritableDatabase();

        db.insert(userDBOpenHelper.DATABASE_TABLE, null, newValues);

    }

    public void closeDatabase() {
        userDBOpenHelper.close();
    }

    public String[] getAll() {

        ArrayList<String> outputArray = new ArrayList<String>();
        String[] result_columns = new String[]{
                KEY_WEIGHT, KEY_HEIGHT, KEY_AGE, KEY_GENDER, KEY_STEP_COUNT};


        float weight;
        float height;
        int age;
        String gender;
        int stepCount;

        String where = null;
        String whereArgs[] = null;
        String groupBy = null;
        String having = null;
        String order = null;

        SQLiteDatabase db = userDBOpenHelper.getWritableDatabase();
        Cursor cursor = db.query(userDBOpenHelper.DATABASE_TABLE,
                result_columns, where,
                whereArgs, groupBy, having, order);
        //
        boolean result = cursor.moveToFirst();
        while (result) {

            weight = cursor.getFloat(cursor.getColumnIndex(KEY_WEIGHT));
            height = cursor.getFloat(cursor.getColumnIndex(KEY_HEIGHT));
            age = cursor.getInt(cursor.getColumnIndex(KEY_AGE));
            gender = cursor.getString(cursor.getColumnIndex(KEY_GENDER));
            stepCount = cursor.getInt(cursor.getColumnIndex(KEY_STEP_COUNT));

            outputArray.add(weight + " " + height + " " + age + " " + gender + " " +stepCount);
            result = cursor.moveToNext();

        }return outputArray.toArray(new String[outputArray.size()]);
    }

    public float getWeight(int id){
        String[] result_columns = new String[]{
                KEY_WEIGHT};

        String where = KEY_ID + "=" + id;
        String whereArgs[] = null;
        String groupBy = null;
        String having = null;
        String order = null;

        SQLiteDatabase db = userDBOpenHelper.getWritableDatabase();
        Cursor cursor = db.query(userDBOpenHelper.DATABASE_TABLE,
                result_columns, where,
                whereArgs, groupBy, having, order);
        if (cursor.moveToFirst()) {
            int columnWeight = cursor.getColumnIndex(KEY_WEIGHT);
            return cursor.getFloat(columnWeight);
        } else return 0;
    }
    public float getHeight(int id){
        String[] result_columns = new String[]{
               KEY_HEIGHT};

        String where = KEY_ID + "=" + id;
        String whereArgs[] = null;
        String groupBy = null;
        String having = null;
        String order = null;

        SQLiteDatabase db = userDBOpenHelper.getWritableDatabase();
        Cursor cursor = db.query(userDBOpenHelper.DATABASE_TABLE,
                result_columns, where,
                whereArgs, groupBy, having, order);
        if (cursor.moveToFirst()) {
            int columnHeight = cursor.getColumnIndex(KEY_HEIGHT);
            return cursor.getFloat(columnHeight);
        } else return 0;
    }

    public int getAge(int id){
        String[] result_columns = new String[]{
               KEY_AGE};

        String where = KEY_ID + "=" + id;
        String whereArgs[] = null;
        String groupBy = null;
        String having = null;
        String order = null;

        SQLiteDatabase db = userDBOpenHelper.getWritableDatabase();
        Cursor cursor = db.query(userDBOpenHelper.DATABASE_TABLE,
                result_columns, where,
                whereArgs, groupBy, having, order);
        if (cursor.moveToFirst()) {
            int columnAge = cursor.getColumnIndex(KEY_AGE);
            return cursor.getInt(columnAge);
        } else return 0;
    }
    public String getGender(int id){
        String[] result_columns = new String[]{
                KEY_GENDER};

        String where = KEY_ID + "=" + id;
        String whereArgs[] = null;
        String groupBy = null;
        String having = null;
        String order = null;

        SQLiteDatabase db = userDBOpenHelper.getWritableDatabase();
        Cursor cursor = db.query(userDBOpenHelper.DATABASE_TABLE,
                result_columns, where,
                whereArgs, groupBy, having, order);
        if (cursor.moveToFirst()) {
            int columnGender = cursor.getColumnIndex(KEY_GENDER);
            return cursor.getString(columnGender);
        } else return "";
    }
    public int getStepCount(int id){
        String[] result_columns = new String[]{
                KEY_STEP_COUNT};

        String where = KEY_ID + "=" + id;
        String whereArgs[] = null;
        String groupBy = null;
        String having = null;
        String order = null;

        SQLiteDatabase db = userDBOpenHelper.getWritableDatabase();
        Cursor cursor = db.query(userDBOpenHelper.DATABASE_TABLE,
                result_columns, where,
                whereArgs, groupBy, having, order);
        if (cursor.moveToFirst()) {
            int columnStepCount = cursor.getColumnIndex(KEY_STEP_COUNT);
            return cursor.getInt(columnStepCount);
        } else return 0;
    }

    public void updateWeight(int id, float weight){
        ContentValues updatedValues = new ContentValues();

        updatedValues.put(KEY_WEIGHT, weight);
        String where = KEY_ID + "=" + id;
        String whereArgs[] = null;

        SQLiteDatabase db = userDBOpenHelper.getWritableDatabase();
        db.update(userDBOpenHelper.DATABASE_TABLE, updatedValues,
                where, whereArgs);
    }

    public void updateHeight(int id, float height){
        ContentValues updatedValues = new ContentValues();

        updatedValues.put(KEY_HEIGHT, height);
        String where = KEY_ID + "=" + id;
        String whereArgs[] = null;

        SQLiteDatabase db = userDBOpenHelper.getWritableDatabase();
        db.update(userDBOpenHelper.DATABASE_TABLE, updatedValues,
                where, whereArgs);

    }
    public void updateAge(int id, int age){
        ContentValues updatedValues = new ContentValues();

        updatedValues.put(KEY_AGE, age);
        String where = KEY_ID + "=" + id;
        String[] whereArgs = null;

        SQLiteDatabase db = userDBOpenHelper.getWritableDatabase();
        db.update(userDBOpenHelper.DATABASE_TABLE, updatedValues,
                where, whereArgs);

    }
    public void updateGender(int id, String gender){
        ContentValues updatedValues = new ContentValues();

        updatedValues.put(KEY_GENDER, gender);
        String where = KEY_ID + "=" + id;
        String[] whereArgs = null;

        SQLiteDatabase db = userDBOpenHelper.getWritableDatabase();
        db.update(userDBOpenHelper.DATABASE_TABLE, updatedValues,
                where, whereArgs);

    }

    public void updateStepCount(int id, int stepCount){
        ContentValues updatedValues = new ContentValues();

        updatedValues.put(KEY_STEP_COUNT, stepCount);
        String where = KEY_ID + "=" + id;
        String whereArgs[] = null;

        SQLiteDatabase db = userDBOpenHelper.getWritableDatabase();
        db.update(userDBOpenHelper.DATABASE_TABLE, updatedValues,
                where, whereArgs);
    }

    private static class userDBOpenHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "userDatabase.db";
        private static final String DATABASE_TABLE = "USER";
        private static final int DATABASE_VERSION = 1;

        // SQL Statement to create a new database.
        private static final String DATABASE_CREATE = "create table " +
                DATABASE_TABLE + " (" +KEY_ID + " integer primary key autoincrement, " +
                KEY_WEIGHT + " float, " + KEY_HEIGHT + " float, " +KEY_AGE + " int, " + KEY_GENDER + " string, " + KEY_STEP_COUNT + " int) ";


        public userDBOpenHelper(Context context, String name,
                                SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
            // Log the version upgrade.
            Log.w("TaskDBAdapter", "Upgrading from version " +
                    oldVersion + " to " +
                    newVersion + ", which will destroy all old data");

            // Upgrade the existing database to conform to the new
            // version. Multiple previous versions can be handled by
            // comparing oldVersion and newVersion values.

            // The simplest case is to drop the old table and create a new one.
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            // Create a new one.
            onCreate(db);
        }
    }
}