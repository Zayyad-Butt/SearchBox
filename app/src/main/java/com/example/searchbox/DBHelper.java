package com.example.searchbox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.ThemedSpinnerAdapter;
import androidx.constraintlayout.solver.widgets.Helper;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DBHelper extends SQLiteOpenHelper {
    public static final String STUDENT_ID = "Id";
    public static final String STUDENT_NAME = "Name";
    public static final String STUDENT_AGE = "Age";
    public static final String STUDENT_CNIC = "CNIC";
    public static final String STUDENT_ROLLNO = "RollNo";
    public static final String STUDENT_TABLE = "Book1";
    public static final String IMAGE = "img";



    public static final String DATABASE_NAME = "MyDB.db";
    public static final String DATABASE_LOCATION = "/data/data/com.example.searchbox/databases/";
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

        this.mContext=context;

    }

    public void openDatabase(){
        String dbPath=mContext.getDatabasePath(DATABASE_NAME).getPath();
        if(mDatabase!=null && mDatabase.isOpen()){
            return;
        }
        mDatabase=SQLiteDatabase.openDatabase(dbPath,null,SQLiteDatabase.OPEN_READONLY);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        //String createTableSTatementOne = "CREATE TABLE CustTable(CustomerID Integer PRIMARY KEY AUTOINCREMENT, "
        // + CUSTOMER_NAME_FIRST + " Text, CustomerAge Int, ActiveCustomer BOOL) ";
        String createTableSTatement = "CREATE TABLE " + STUDENT_TABLE + "(" + STUDENT_ID +
                " Integer PRIMARY KEY AUTOINCREMENT, " + STUDENT_NAME + " Text, " +
                STUDENT_AGE + " Int, " + STUDENT_CNIC + " Text, "+ STUDENT_ROLLNO + " Text,"+
                IMAGE+"Text) ";
        db.execSQL(createTableSTatement);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    public List<Student> getAllRecords(String colName,String key)
    {

        List<Student> myList=new ArrayList<Student>();
        if(colName.equals("CNIC")&& key.length()>13){
            Character dash=key.charAt(5);
            if(dash.equals('-')){
                key=charRemoveAt(key,5);
                key=charRemoveAt(key,12);
            }

        }

        String query = "Select * FROM " + STUDENT_TABLE + " Where LOWER(" + colName + ")" + "=?";
        String[] selectionArg = new String[]{key.toLowerCase()};

        openDatabase();
        Cursor cursor=mDatabase.rawQuery(query,selectionArg);
        if(cursor.moveToFirst())
        {
            do{

                String sName=cursor.getString(0);
                int sAge=cursor.getInt(2);
                String sCNIC=cursor.getString(3);
                String rollNo=cursor.getString(1);
                String img=cursor.getString(4);
                //int id=cursor.getInt(0);
                Student stModel=new Student(sName,sAge,sCNIC,rollNo,img);
                myList.add(stModel);
            }while(cursor.moveToNext());
        }
        cursor.close();
        if(mDatabase!=null)
        {
            mDatabase.close();
        }
        return myList;
    }
    public static String charRemoveAt(String str, int p) {
        return str.substring(0, p) + str.substring(p + 1);
    }

}
