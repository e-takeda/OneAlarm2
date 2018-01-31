package com.example.nttr.onealarm2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button enter;
    EditText Ehour;
    EditText Eminute;
    TextView confview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enter = (Button) findViewById(R.id.enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeConf(mhours(), mminutes());
            }
            timerSet();


        });







    }
    //時を取得
    private String mhours(){
        Ehour=(EditText)this.findViewById(R.id.hour);
        String hour = Ehour.getText().toString();
        return hour;
    }
    //分を取得
    private String mminutes(){
        Eminute=(EditText)this.findViewById(R.id.minute);
        String minute = Eminute.getText().toString();
        return minute;
    }
    //設定した時刻を表示
    private void timeConf(String h, String m){
        confview=(TextView)this.findViewById(R.id.confview);
        confview.setText("設定した時間は　" + h + "：" + m);
    }


    public void timerSet(Calendar calendar){
        Intent intent = new Intent(getApplicationContext(), messageService.class);
        Context ct = getApplication();
        PendingIntent pendingIntent = PendingIntent.getService(ct, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance(); // Calendar取得
        calendar.setTimeInMillis(System.currentTimeMillis()); // 現在時刻を取得
        calendar.add(Calendar.SECOND, 15);



        // AlarmManager の設定・開始
        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
    }



}
