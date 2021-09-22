package com.example.searchbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button button;
    ListView listView;
    EditText editText;
    Spinner spinner;
    DBHelper dbHelper;
    ArrayAdapter<Student>arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.button);
        listView=findViewById(R.id.list);
        editText=findViewById(R.id.searchText);
        spinner=findViewById(R.id.spinner);

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

    private void RefreshData() {
        dbHelper=new DBHelper(MainActivity.this);
        ArrayList<Student> allStudents=dbHelper.getAllRecords(
                spinner.getSelectedItem().toString(),editText.getText().toString());
        arrayAdapter=new ArrayAdapter<Student>(MainActivity.this,
                android.R.layout.simple_list_item_1,allStudents);
        listView.setAdapter(arrayAdapter);
    }
    public void showRecords(View view) {
        Toast.makeText(MainActivity.this,"test",Toast.LENGTH_SHORT).show();
    }
}