package com.example.searchbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    List<Student> allStudents;

    Button button;
    RecyclerView recycleView;
    EditText editText;
    Spinner spinner;
    DBHelper dbHelper;
    ArrayAdapter<Student>arrayAdapter;

    //Variables

    int currentRecord=0;

    //Record Elements


    ImageView imageViewFriend;
    TextView name;
    TextView age;
    TextView rollNo;
    TextView cnic;

    //Control Buttons
    LinearLayout recordLayout;
    Button prev,next,goTo;
    TextView recordNumberTextView,recordNumByUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        button=findViewById(R.id.search);
      // recycleView=findViewById(R.id.recyclerView);
        editText=findViewById(R.id.editText);
        spinner=findViewById(R.id.spinner);
        dbHelper=new DBHelper(this);

        //getting display of records

        imageViewFriend = findViewById(R.id.imageView);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        cnic = findViewById(R.id.cnic);
        rollNo = findViewById(R.id.rollNo);
        recordLayout=findViewById(R.id.recordLayout);

        //getting control Buttons

        prev=findViewById(R.id.previous);
        next=findViewById(R.id.next);
        goTo=findViewById(R.id.goTo);
        recordNumberTextView=findViewById(R.id.recordNumber);
        recordNumByUser=findViewById(R.id.recordNumberByUser);

        // first=findViewById(R.id.firstPage);
       // second=findViewById(R.id.secondPage);
       // third=findViewById(R.id.thirdPage);
        //firstRecord=findViewById(R.id.firstRecord);
        //lastRecord=findViewById(R.id.lastRecord);

        //Check Exists Database

        File database=getApplicationContext().getDatabasePath(DBHelper.DATABASE_NAME);
        if(false==database.exists())
        {
            dbHelper.getReadableDatabase();
            //Copy db
            if(copyDatabase(this))
            {
                Toast.makeText(this, "Copy Databse success", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Copy Database error", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        ArrayAdapter<String> allCategories=new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.names));
        allCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(allCategories);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefreshData();
                //Toast.makeText(MainActivity.this, allCustomers.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }
    private boolean copyDatabase(Context context)
    {
        try{
            InputStream inputStream=context.getAssets().open(DBHelper.DATABASE_NAME);
            String outFilename=DBHelper.DATABASE_LOCATION+DBHelper.DATABASE_NAME;
            OutputStream outputStream=new FileOutputStream(outFilename);
            byte [] buff=new byte[1024];
            int length=0;
            while((length=inputStream.read(buff))>0)
            {
                outputStream.write(buff,0,length);

            }
            outputStream.flush();
            outputStream.close();
            Log.v("Main AActivity","DB Copied");
            return true;


        }catch( Exception e)
        {
            e.printStackTrace();
            return false;
        }

    }

    public void setRecordOnScreen(int index)
    {
        Student s=allStudents.get(index);
        name.setText(s.getName());
        age.setText(String.valueOf(s.getAge()));
        cnic.setText(String.valueOf(s.getCNIC()));
        rollNo.setText(String.valueOf(s.getRollNo()));

        if(s.getImg()!=null)
        {
            String uri = "@drawable/"+s.getImg();  // where myresource (without the extension) is the file
            int imageResource = getResources().getIdentifier(uri, null, getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            imageViewFriend.setImageDrawable(res);
        }

    }

    public void showButtons(int size)
    {
        if(size>3)
        {
            size=3;
        }
        for(int i=1;i<=size;i++)
        {
            Button btn = new Button(this);
            btn.setId(i);
            btn.setText(i);
        }
    }

    private void RefreshData() {

        dbHelper=new DBHelper(MainActivity.this);
        allStudents=dbHelper.getAllRecords(
                spinner.getSelectedItem().toString(),editText.getText().toString());

        if(allStudents.isEmpty())
        {
            Toast.makeText(MainActivity.this,"No Record Found",Toast.LENGTH_SHORT).show();
            recordLayout.setVisibility(View.INVISIBLE);

        }
        else{
            currentRecord=1;
            setRecordOnScreen(currentRecord);
            recordLayout.setVisibility(View.VISIBLE);
            SetRecordNumber(currentRecord);
            //showButtons(allStudents.size());
            //setRecordOnScreen();
        }

    }


    public void resetData(View view) {
        editText.setText("");
        recordLayout.setVisibility(View.INVISIBLE);
    }

    public void GoToPreviousRecord(View view) {
        if(allStudents.isEmpty())
        {
            Toast.makeText(MainActivity.this,"No Record Exists",Toast.LENGTH_SHORT).show();
            return;
        }
        if(currentRecord==1)
        {
            currentRecord=allStudents.size();
        }
        else{
            currentRecord--;
        }
        SetRecordNumber(currentRecord);
    }

    public void GoToNextRecord(View view) {
        if(allStudents.isEmpty())
        {
            Toast.makeText(MainActivity.this,"No Record Exists",Toast.LENGTH_SHORT).show();
            return;
        }
        if(currentRecord==allStudents.size())
        {
            currentRecord=1;

        }
        else{
            currentRecord++;
        }
        SetRecordNumber(currentRecord);

    }

    public void SetRecordNumber(int r)
    {
        String s=currentRecord+ "/" + allStudents.size();
        recordNumberTextView.setText(s);
    }
}