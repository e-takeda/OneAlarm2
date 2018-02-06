package com.example.nttr.onealarm2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by nttr on 2018/01/31.
 */


public class messageService extends Service{
    //これがないとエラーになる（おまじない的な感じ）
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    public void onCreate()
    {
        Log.d("service","onCreate");
        Toast.makeText(getApplicationContext(), "aaa", Toast.LENGTH_SHORT).show();
        Thread thread = new Thread(null, task, "sentMessageService");
        thread.start();
    }

    Runnable task = new Runnable()
    {
        public void run() {
            Intent AlarmBroadcastReceiver = new Intent();
            AlarmBroadcastReceiver.setAction("alarmAction");
            sendBroadcast(AlarmBroadcastReceiver);

            stopSelf();
        }

    };
}