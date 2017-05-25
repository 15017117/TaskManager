package com.myapplicationdev.android.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ListView lvTask;
Button btnAdd;
    ArrayList<Task>al;
    ArrayAdapter<Task>aa;
    int requestCodeForthisActivity = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        lvTask = (ListView) findViewById(R.id.lvTask);

        al = new ArrayList<Task>();

        DBHelper dbh = new DBHelper(this);
        ArrayList<String> data = dbh.getTasks();
        dbh.close();
        ArrayList<Task> tasks = dbh.getAllTask();

        aa = new taskAdapter(this, R.layout.row, tasks);
        lvTask.setAdapter(aa);

        for (int i = 0; i < data.size(); i++) {
            Log.d("Database content", i + ". " + tasks.get(i));
            aa = new taskAdapter(MainActivity.this, R.layout.row, al);

        }


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddActivity.class);


                startActivityForResult(i, requestCodeForthisActivity);
            }
        });
    }
        @Override
        protected void onActivityResult(int requestCode, int 	resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            // Only handle when 2nd activity closed normally
            //  and data contains something
            if(resultCode == RESULT_OK){
                if (data != null) {
                    String statement = "";
                    // Get data passed back from a 2nd activity
                    // If it is activity started by clicking
                    if(requestCode == requestCodeForthisActivity){
                        statement = "Move to Main Activity";
                    }
                    DBHelper dbh = new DBHelper(MainActivity.this);
                    al.clear();
                    al.addAll(dbh.getAllTask());
                    dbh.close();
                    lvTask.setAdapter(aa);
                    aa.notifyDataSetChanged();


                    Toast.makeText(MainActivity.this, statement,
                            Toast.LENGTH_LONG).show();
                }
            }
        }

    }

