package com.example.nttr.onealarm2;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;

import java.io.IOException;

/**
 * Created by nttr on 2018/01/31.
 */


public class messageService extends Service implements MediaPlayer.OnCompletionListener{

    MediaPlayer mediaPlayer;
    float volume = 0.7f;
    private Vibrator vib;
    private long pattern[] = {1000, 200, 700, 200, 400, 200 };

    boolean isVibe;





    public void onCreate()
    {
//        Toast.makeText(getApplicationContext(), "aaa", Toast.LENGTH_SHORT).show();
//        Thread thread = new Thread(null, task, "sentMessageService");
//        thread.start();

//        Toast.makeText(this, "Received ", Toast.LENGTH_LONG).show();
        super.onCreate();



        SharedPreferences prefer = getSharedPreferences("MainActivity",MODE_PRIVATE);
        isVibe = prefer.getBoolean("vibe",true);
        if (isVibe){
            System.out.println("バイブおん");
        }else{
            System.out.println("バイブオフ");
        }

        volume = prefer.getFloat("volume",0.7f);




    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer = new MediaPlayer();
        play();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop();
    }


    //これがないとエラーになる（おまじない的な感じ）
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    private void play() {
        try {
            mediaPlayer.reset();
            String fileName = "android.resource://" + getPackageName() + "/" + R.raw.sample;
            mediaPlayer.setDataSource(this, Uri.parse(fileName));
            mediaPlayer.setVolume(volume, volume);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.prepare();
            mediaPlayer.start();
            if (isVibe){
                vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                vib.vibrate(1000);
                vib.vibrate(pattern, 0);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 停止
    private void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            vib.cancel();
        }
    }




    @Override
    public void onCompletion(MediaPlayer mp) {
        play();
    }

//    Runnable task = new Runnable()
//    {
//        public void run() {
//            Intent AlarmBroadcastReceiver = new Intent();
//            AlarmBroadcastReceiver.setAction("alarmAction");
//            sendBroadcast(AlarmBroadcastReceiver);
//
//            stopSelf();
//        }
//
//    };








}