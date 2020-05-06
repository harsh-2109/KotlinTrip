package com.finlite.admin.kotlintrip.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.finlite.admin.kotlintrip.R;

import java.util.Calendar;

public class AlarmScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_screen);

        Button btnShare=(Button)findViewById(R.id.btnShare);

//        btnShare.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
                share();
//            }
//        });
    }

    private void share(){
        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.MONTH,5);
        cal.set(Calendar.YEAR,2011);
        cal.set(Calendar.DAY_OF_MONTH,29);
        cal.set(Calendar.HOUR_OF_DAY,17);
        cal.set(Calendar.MINUTE,30);


        Intent intent = new Intent(this, AlarmScreenActivity.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 1253,
                intent, PendingIntent.FLAG_UPDATE_CURRENT|  Intent.FILL_IN_DATA);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),pendingIntent );
        Toast.makeText(this, "Alarm Set.", Toast.LENGTH_LONG).show();
    }


}
