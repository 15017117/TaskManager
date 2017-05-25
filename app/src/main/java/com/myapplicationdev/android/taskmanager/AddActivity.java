package com.myapplicationdev.android.taskmanager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    Button btnAdd,btnCancel;
    int reqCode = 12345;
    EditText etName,etDesc,etRemind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Intent i = getIntent();

        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnCancel =(Button)findViewById(R.id.btnCancel);
        etName = (EditText)findViewById(R.id.etName);
        etDesc = (EditText)findViewById(R.id.etDesc);
        etRemind = (EditText)findViewById(R.id.etRemind);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String desc = etDesc.getText().toString().trim();
                int remind = Integer.parseInt(etRemind.getText().toString().trim());

                if(name.length() == 0 && desc.length() == 0)
                    return;

                long row_affected = 0;
                DBHelper dbh = new DBHelper(AddActivity.this);
                row_affected = dbh.insertTask(name,desc);

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND,remind);

                Intent intent = new Intent(AddActivity.this,NotificationReceiver.class);
                intent.putExtra("name",name);
                intent.putExtra("desc",desc);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(AddActivity.this,reqCode,intent,PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager am = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);

                Intent i = new Intent();
                dbh.close();
                setResult(RESULT_OK,i);
                finish();

                if(row_affected !=-1){
                    Toast.makeText(AddActivity.this, "Insert successful",
                            Toast.LENGTH_SHORT).show();
                }


            }

        });
    }
}
