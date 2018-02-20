package com.example.nttr.onealarm2;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

/**
 * Created by nttr on 2018/02/06.
 */

public class messageActivity extends AppCompatActivity{
    ImageButton stop;
    ImageButton snooze;


//    public static boolean isSnooze=false;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_message);











        startService(new Intent(this, messageService.class));


        stop = findViewById(R.id.stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(messageActivity.this, messageService.class));
//                PreferenceUtil pref = new PreferenceUtil(PlaySoundActivity.this);
//                pref.delete(MainActivity.ALARM_TIME);

//                finish();
                Intent intent = new Intent(messageActivity.this, MainActivity.class);
                intent.putExtra("stop", true);
                System.out.println("stop");

                startActivity(intent);


            }
        });


        snooze = findViewById(R.id.snooze);
        snooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                isSnooze = true;
                stopService(new Intent(messageActivity.this, messageService.class));

                Intent intent = new Intent(messageActivity.this, MainActivity.class);
                intent.putExtra("snooze", true);
                System.out.println("スヌーズ");

                startActivity(intent);
            }
        });


    }




}
