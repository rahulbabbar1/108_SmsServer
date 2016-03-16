package com.apps.my.appointmate_server;


        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import java.util.ArrayList;
        import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "SchedulesInfo";
    // Contacts table name
    private static final String TABLE_ScheduleS = "Schedules";
    // Schedules Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SH_ADDR = "Schedule_timetable";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_ScheduleS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_SH_ADDR + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ScheduleS);
// Creating tables again
        onCreate(db);
    }
    // Adding new Schedule
    public void addSchedule(Schedule Schedule) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, Schedule.getName()); // Schedule Name
        values.put(KEY_SH_ADDR, Schedule.getTimetable()); // Schedule Phone Number

// Inserting Row
        db.insert(TABLE_ScheduleS, null, values);
        db.close(); // Closing database connection
    }
    // Getting one Schedule
    public void clear(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_ScheduleS);
    }
    public Schedule getSchedule(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ScheduleS, new String[]{KEY_ID,
                        KEY_NAME, KEY_SH_ADDR}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Schedule contact = new Schedule(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
// return Schedule
        return contact;
    }


    /*public void editSchedule(int id,String str){
        SQLiteDatabase db = this.getReadableDatabase();
        String sid=id+"";
       String z=("update TABLE_ScheduleS set KEY_SH_ADDR= "+str+" WHERE KEY_ID='"+sid+"'" +
               " ");

        db.execSQL(z);

    }*/




    // Getting All Schedules
    public List<Schedule> getAllSchedules() {
        List<Schedule> ScheduleList = new ArrayList<Schedule>();
// Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_ScheduleS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Schedule Schedule = new Schedule();
                Schedule.setId(Integer.parseInt(cursor.getString(0)));
                Schedule.setName(cursor.getString(1));
                Schedule.setTimetable(cursor.getString(2));
// Adding contact to list
                ScheduleList.add(Schedule);
            } while (cursor.moveToNext());
        }

// return contact list
        return ScheduleList;
    }
    // Getting Schedules Count
    public int getSchedulesCount() {
        String countQuery = "SELECT * FROM " + TABLE_ScheduleS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

// return count
        return cursor.getCount();
    }
    // Updating a Schedule
    public int updateSchedule(Schedule Schedule) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, Schedule.getName());
        values.put(KEY_SH_ADDR, Schedule.getTimetable());

// updating row
        return db.update(TABLE_ScheduleS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(Schedule.getId())});
    }

    // Deleting a Schedule
    public void deleteSchedule(Schedule Schedule) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ScheduleS, KEY_ID + " = ?",
                new String[] { String.valueOf(Schedule.getId()) });
        db.close();
    }
}

