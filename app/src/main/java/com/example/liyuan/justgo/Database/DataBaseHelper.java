
package com.example.liyuan.justgo.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.liyuan.justgo.Model.location1;
import com.example.liyuan.justgo.Model.plan;
import com.example.liyuan.justgo.Model.user;

public class DataBaseHelper extends SQLiteOpenHelper {

    public String cur_email;
    public static final String MY_PREF_NAME = "MyPrefFile";

    //Database
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ExpTripDatabase.db";

    //user variables
    public static final String TABLE_USER= "user";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";

    //plan variables
    public static final String TABLE_PLAN = "plan";
    public static final String COLUMN_PLAN_ID = "plan_id";
    public static final String COLUMN_PLAN_NAME = "plan_name";
    public static final String COLUMN_BUDGET = "budget";
    public static final String COLUMN_DESCRIPTION = "description";

    //plan_location variables
    public static final String TABLE_PLAN_LOCATION = "plan_location";
    public static final String COLUMN_KEY_ID = "id";


    //location variables
    public static final String TABLE_LOCATION= "location";
    public static final String COLUMN_LOCATION_ID = "location_id";
    public static final String COLUMN_LOCATION_NAME = "location_name";
    public static final String COLUMN_START_DATE = "start_date";
    public static final String COLUMN_END_DATE = "end_date";


    //Create user table
    public String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER +
            " (" + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            COLUMN_FIRST_NAME + " TEXT," +
            COLUMN_LAST_NAME + " TEXT," +
            COLUMN_COUNTRY + " TEXT," +
            COLUMN_EMAIL + " TEXT," +
            COLUMN_PASSWORD + " TEXT" + ")";



    //create plan table
    public String CREATE_TABLE_PLAN = "CREATE TABLE " + TABLE_PLAN +
            " (" + COLUMN_PLAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            COLUMN_PLAN_NAME + " TEXT," +
            COLUMN_BUDGET + " DOUBLE," +
            COLUMN_DESCRIPTION + " TEXT," +
            COLUMN_USER_ID + " INTEGER, "
            + " FOREIGN KEY ("+COLUMN_USER_ID+") REFERENCES " + TABLE_USER + "("+COLUMN_USER_ID+"));";



    //create location table
    public String CREATE_TABLE_LOCATION = "CREATE TABLE " + TABLE_LOCATION +
            " (" + COLUMN_LOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            COLUMN_LOCATION_NAME + " TEXT," +
            COLUMN_START_DATE + " TEXT," +
            COLUMN_END_DATE + " TEXT" + ")";


    //create plan_location table
     public String CREATE_TABLE_PLAN_LOCATION = "CREATE TABLE " + TABLE_PLAN_LOCATION +
           " (" + COLUMN_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_PLAN_ID + " INTEGER," + COLUMN_LOCATION_ID + " INTEGER" + ")";


    public String DROP_TABLE_USER = "DROP TABLE IF EXISTS " + TABLE_USER;

    public String DROP_TABLE_PLAN = "DROP TABLE IF EXISTS " + TABLE_PLAN;

    public String DROP_TABLE_LOCATION = "DROP TABLE IF EXISTS " + TABLE_LOCATION;

    public String DROP_TABLE_PLAN_LOCATION = "DROP TABLE IF EXISTS " + TABLE_PLAN_LOCATION;

    public DataBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_PLAN);
        db.execSQL(CREATE_TABLE_LOCATION);
        db.execSQL(CREATE_TABLE_PLAN_LOCATION);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(DROP_TABLE_USER);
        db.execSQL(DROP_TABLE_PLAN);
        db.execSQL(DROP_TABLE_LOCATION);
        db.execSQL(DROP_TABLE_PLAN_LOCATION);
        onCreate(db);
    }

    public void addUser(user user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues ();
        values.put(COLUMN_FIRST_NAME, user.getFirst_name());
        values.put(COLUMN_LAST_NAME, user.getLast_name());
        values.put(COLUMN_COUNTRY, user.getCountry());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());

        db.insert(TABLE_USER, null, values);


        db.close();
    }

    public void updateUser(user user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues ();
        contentValues.put(COLUMN_FIRST_NAME, user.getFirst_name());
        contentValues.put(COLUMN_LAST_NAME, user.getLast_name());
        contentValues.put(COLUMN_COUNTRY, user.getCountry());
        contentValues.put(COLUMN_EMAIL, user.getEmail());
        contentValues.put(COLUMN_PASSWORD, user.getPassword());
        String whereClause = COLUMN_EMAIL+"=? ";
        String whereArgs[] = {user.getEmail().toString()};
        db.update(TABLE_USER, contentValues, whereClause,whereArgs);
    }

    public void deleteUser(user user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COLUMN_EMAIL+"=? ";
        String whereArgs[] = {user.getEmail().toString()};
        db.delete(TABLE_USER, whereClause,whereArgs);
    }

    public void addPlan(plan plan)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues ();
        values.put(COLUMN_PLAN_NAME, plan.getPlan_name());
        values.put(COLUMN_BUDGET, plan.getBudget());
        values.put(COLUMN_DESCRIPTION, plan.getDescription());
        values.put(COLUMN_USER_ID, plan.getUserid());

        db.insert(TABLE_PLAN, null, values);
        db.close();
    }

    public void updatePlan(plan plan)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues ();
        contentValues.put(COLUMN_PLAN_NAME, plan.getPlan_name());
        contentValues.put(COLUMN_BUDGET, plan.getBudget());
        contentValues.put(COLUMN_DESCRIPTION, plan.getDescription());

        String whereClause = COLUMN_PLAN_ID+"=? ";
        String whereArgs[] = {String.valueOf(plan.getPlanid())};
        db.update(TABLE_PLAN, contentValues, whereClause,whereArgs);
    }

    public void deletePlan(plan plan)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COLUMN_PLAN_NAME+"=? ";
        String whereArgs[] = {plan.getPlan_name().toString()};
        db.delete(TABLE_PLAN, whereClause,whereArgs);
    }

    public void addLocation(location1 location)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues ();
        values.put(COLUMN_LOCATION_NAME, location.getLocation_name());
        values.put(COLUMN_START_DATE, location.getStart_date());
        values.put(COLUMN_END_DATE, location.getEnd_date());
        db.insert(TABLE_LOCATION, null, values);
        db.close();
    }

    public void updateLocation(location1 location)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues ();
        contentValues.put(COLUMN_LOCATION_NAME, location.getLocation_name());
        contentValues.put(COLUMN_START_DATE, location.getStart_date());
        contentValues.put(COLUMN_END_DATE, location.getEnd_date());
        String whereClause = COLUMN_LOCATION_NAME+"=? ";
        String whereArgs[] = {location.getLocation_name().toString()};
        db.update(TABLE_LOCATION, contentValues, whereClause,whereArgs);
    }

    //check If email already exist
    public boolean checkEmail(String email){
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0){
            return true;
        }
        return false;
    }

    public boolean checkUser(String email, String password)
    {
        String[] columns = {COLUMN_USER_ID};
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_EMAIL + " = ?" + "AND " + COLUMN_PASSWORD + "= ?";
        String[] selectionArgs = { email, password };

        Cursor cursor = db.query(TABLE_USER, columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0)
        {
            return true;
        }
        return false;
    }

    //pass parameter to open specific list for each user
    public Cursor getPlanList(String u_email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
       /* Cursor planData = db.rawQuery("SELECT * FROM "+ TABLE_PLAN + " b" +
                " JOIN " + TABLE_USER + " c" +
                " ON "+ "c."+COLUMN_USER_ID +" = " +"b."+COLUMN_USER_ID
                + " WHERE "+ COLUMN_EMAIL+" ='"+u_email+"'", null);*/
        Cursor planData = db.rawQuery("SELECT * FROM "+ TABLE_PLAN, null);

        return planData;
    }
    public Cursor getLocation()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor planLocation = db.rawQuery("SELECT * FROM "+ TABLE_LOCATION, null);
        return planLocation;
    }

    public Cursor GetProfile(SQLiteDatabase db)
    {
        Cursor cursor;
        String[] profileInfo = {COLUMN_FIRST_NAME, COLUMN_LAST_NAME, COLUMN_COUNTRY, COLUMN_EMAIL};
        cursor = db.query(TABLE_USER,profileInfo,null,null,null,null,null);
        return cursor;
    }

        //Method to get all information from user
    public Cursor getProfile(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor profile = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE "+ COLUMN_EMAIL+"=? ", new String[] {email});
        return profile;
    }

    //Get the first name of user
    public String currentEmail(String curEmail)
    {
        String firstName = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + COLUMN_FIRST_NAME + " FROM " + TABLE_USER + " WHERE TRIM(email) = '"+curEmail.trim()+"'", null);
        if (c.moveToFirst()) {
            do {
                firstName = c.getString(0);
            } while (c.moveToNext());
        }
        c.close();

        return firstName;
    }
}

