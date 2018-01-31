package com.example.nttr.onealarm2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

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
        Thread thread = new Thread(null, task, "sentMessageService");
        thread.start();
    }

    Runnable task = new Runnable()
    {
        public void run() {
            Intent messageBroadcast = new Intent();
            messageBroadcast.setAction("activityAction");
            sendBroadcast(messageBroadcast);
            stopSelf();
        }

    };
}