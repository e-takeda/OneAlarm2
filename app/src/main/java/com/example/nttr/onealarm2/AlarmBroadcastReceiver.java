package com.example.nttr.onealarm2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by nttr on 2018/01/31.
 */

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Received ", Toast.LENGTH_LONG).show();


//        String message = intent.getAction();
//        Toast.makeText(context, message, Toast.LENGTH_LONG).show();





    }





}
