package com.finlite.admin.kotlintrip.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show();
        context.startActivity(new Intent(context, AlarmScreenActivity.class));
    }

}
