package com.example.searchbox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String STUDENT_ID = "Id";
    public static final String STUDENT_NAME = "Name";
    public static final String STUDENT_AGE = "Age";
    public static final String STUDENT_CNIC = "CNIC";
    public static final String STUDENT_ROLLNO = "RollNo";
    public static final String STUDENT_TABLE = "Student";

    public DBHelper(@Nullable Context context) {
        super(context, "MyDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String createTableSTatementOne = "CREATE TABLE CustTable(CustomerID Integer PRIMARY KEY AUTOINCREMENT, "
        // + CUSTOMER_NAME_FIRST + " Text, CustomerAge Int, ActiveCustomer BOOL) ";
        String createTableSTatement = "CREATE TABLE " + STUDENT_TABLE + "(" + STUDENT_ID +
                " Integer PRIMARY KEY AUTOINCREMENT, " + STUDENT_NAME + " Text, " +
                STUDENT_AGE + " Int, " + STUDENT_CNIC + " Text, "+ STUDENT_ROLLNO + " Text) ";
        db.execSQL(createTableSTatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    public ArrayList<Student> getAllRecords(String colName,String key)
    {
        ArrayList<Student> myList=new ArrayList<>();

        String query = "Select * FROM " + STUDENT_TABLE + " Where LOWER(" + colName + ")" + "=?";
        String[] selectionArg = new String[]{key.toLowerCase()};

        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor=DB.rawQuery(query,selectionArg);
        if(cursor.moveToFirst())
        {
            do{

                String sName=cursor.getString(1);
                int sAge=cursor.getInt(2);
                String sCNIC=cursor.getString(3);
                String rollNo=cursor.getString(4);
                int id=cursor.getInt(0);
                Student stModel=new Student(sName,sAge,sCNIC,rollNo,id);
                myList.add(stModel);
            }while(cursor.moveToNext());
        }
        cursor.close();
        DB.close();
        return myList;
    }

}
