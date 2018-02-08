package com.example.nttr.onealarm2;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    Button enter;
    EditText Ehour;
    EditText Eminute;
    TextView confview;
    Calendar calendar;
    Button set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //イヤホン検知
        registerReceiver(broadcastReceiver, new IntentFilter(Intent.ACTION_HEADSET_PLUG));
        registerReceiver(broadcastReceiver, new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY));




        setContentView(R.layout.activity_main);

//        //Intentを取得
//        Intent intent = getIntent();
//        //intentから指定キーの文字列を取得する
//        boolean isSnooze = intent.getBooleanExtra("snooze",false);
//        if (isSnooze){
//            snooze();
//            isSnooze = false;
//            System.out.println("snooze");
//        }else{
//            System.out.println("normal");


            enter = (Button) findViewById(R.id.enter);
            enter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    timerSet();
                }


            });

        set = (Button) findViewById(R.id.set);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timeSet();
            }


        });



        }

//    }

    //時を取得
    private String mhours() {
        Ehour = (EditText) this.findViewById(R.id.hour);
        String hour = Ehour.getText().toString();
        return hour;
    }

    //分を取得
    private String mminutes() {
        Eminute = (EditText) this.findViewById(R.id.minute);
        String minute = Eminute.getText().toString();
        return minute;
    }

    //設定した時刻を表示
    private void timeConf(String h, String m) {
        confview = (TextView) this.findViewById(R.id.confview);
        confview.setText("設定した時間は　" + h + "：" + m);
    }





    public void timerSet() {
//        Intent intent = new Intent(getApplicationContext(), messageService.class);
//        Context ct = getApplication();
//        PendingIntent pendingIntent = PendingIntent.getService(ct, 0,
//                intent, PendingIntent.FLAG_UPDATE_CURRENT);



        Calendar Dcalendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"));
        calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"));// Calendar取得
        calendar.setTimeInMillis(System.currentTimeMillis()); // 現在時刻を取得

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日hh時mm分ss秒");
        System.out.println(sdf.format(calendar.getTime()));



        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(mhours()));
        calendar.set(Calendar.MINUTE, Integer.parseInt(mminutes()));
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

//        calendar.add(Calendar.SECOND, 5);

        System.out.println(sdf.format(calendar.getTime()));

        int diff = Dcalendar.compareTo(calendar);

        if (diff >= 0){

            System.out.println("正しい時間に設定してください。");
        } else {

            alarm();
        }
    }

    @SuppressLint("NewApi")
    public void alarm(){
        PendingIntent pendingIntent = getPendingIntent();
        // AlarmManager の設定・開始
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        API Level 19未満は、 set
//        API Level 19以上 23未満は、 setExact
//        API Level 23以上は、 setExactAndAllowWhileIdle
        if (BuildConfig.VERSION_CODE < 19) {
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else if (BuildConfig.VERSION_CODE < 23) {
            am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

//
//    public void snooze(){
//        calendar.add(Calendar.MINUTE, 5);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日hh時mm分ss秒");
//        System.out.println(sdf.format(calendar.getTime()));
//        alarm();
//    }







    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(this, AlarmBroadcastReceiver.class);
        intent.setClass(this, AlarmBroadcastReceiver.class);
        // 複数のアラームを登録する場合はPendingIntent.getBroadcastの第二引数を変更する
        // 第二引数が同じで第四引数にFLAG_CANCEL_CURRENTがセットされている場合、2回以上呼び出されたときは
        // あとからのものが上書きされる
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }



    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action == null) {
                return;
            }

            switch (action) {
                case Intent.ACTION_HEADSET_PLUG:

                    int state = intent.getIntExtra("state", -1);
                    if (state == 0) {
                        // ヘッドセットが装着されていない・外された
                        System.out.println("ヘッドセットなし");
                    } else if (state > 0) {
                        // イヤホン・ヘッドセット(マイク付き)が装着された
                        System.out.println("ヘッドセット装着");
                    }
                    break;
                case AudioManager.ACTION_AUDIO_BECOMING_NOISY:

                    // 音声経路の変更！大きな音が鳴りますよ！！
                    System.out.println("ヘッドセットなし２");
                    break;
                default:
                    break;
            }
        }
    };



    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(broadcastReceiver);
    }








           public void timeSet(){


                final Calendar Dcalendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"));
                final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"));// Calendar取得
                calendar.setTimeInMillis(System.currentTimeMillis()); // 現在時刻を取得

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日hh時mm分ss秒");
                System.out.println(sdf.format(calendar.getTime()));



                final int hour = calendar.get(Calendar.HOUR_OF_DAY);
                final int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        set.setText(df.format(calendar.getTime()));

                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }





}
