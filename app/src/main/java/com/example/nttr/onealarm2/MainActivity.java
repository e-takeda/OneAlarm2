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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

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
            public void onClick(View v){

                timerSet();
            }



        });


//        public void onClick(View v) {
//            timeConf(mhours(), mminutes());
//        }




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


    public void timerSet(){
        Intent intent = new Intent(getApplicationContext(), messageService.class);
        Context ct = getApplication();
        PendingIntent pendingIntent = PendingIntent.getService(ct, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);



        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo")); // Calendar取得
        calendar.setTimeInMillis(System.currentTimeMillis()); // 現在時刻を取得


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日hh時mm分ss秒");
        System.out.println(sdf.format(calendar.getTime()));
//        calendar.set(Calendar.HOUR, Integer.parseInt(mhours()));
//        calendar.set(Calendar.MINUTE, Integer.parseInt(mminutes()));
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);

        calendar.add(Calendar.SECOND, 5);

        System.out.println(sdf.format(calendar.getTime()));





        // AlarmManager の設定・開始
        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
    }



}
