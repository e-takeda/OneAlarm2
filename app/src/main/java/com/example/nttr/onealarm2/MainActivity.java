package com.example.nttr.onealarm2;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
    Calendar Dcalendar;
    ImageButton timeSet;
    ImageButton alarmSet;
    ConstraintLayout Constraint;
    ImageButton vibe;
    ImageButton time;
    ImageButton music;
    Button preview;
    Button preStop;
    TextClock TextClock;
    TextView startTextView;


    boolean isVibe = true;
    boolean isime = true;
    boolean isMusic = true;


    private TimePickerDialog t_dlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //イヤホン検知
        registerReceiver(broadcastReceiver, new IntentFilter(Intent.ACTION_HEADSET_PLUG));
        registerReceiver(broadcastReceiver, new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY));

        setContentView(R.layout.activity_main);

        timeSet = (ImageButton) findViewById(R.id.timeSet);

        vibe = findViewById(R.id.vibe);
        time = findViewById(R.id.time);
        music = findViewById(R.id.music);


        final SharedPreferences prefer = getSharedPreferences("MainActivity",MODE_PRIVATE);
        isVibe = prefer.getBoolean("vibe",true);

        if (isVibe){
            vibe.setImageResource(R.mipmap.vibe1);
        }else {
            vibe.setImageResource(R.mipmap.vibe2);
        }


        vibe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibe();

            }
        });

        preStop = (Button) findViewById(R.id.preStop);
        preStop.setVisibility(View.GONE);


        preview = (Button) findViewById(R.id.preview);

        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, messageService.class);
                startService(intent);
                preview.setVisibility(View.GONE);
                preStop.setVisibility(View.VISIBLE);
            }
        });

        preStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, messageService.class);
                stopService(intent);
                preview.setVisibility(View.VISIBLE);
                preStop.setVisibility(View.GONE);
            }
        });


        alarmSet = (ImageButton) findViewById(R.id.alarmSet);
        startTextView = (TextView) findViewById(R.id.startTextView);
        alarmSet.setVisibility(View.INVISIBLE);
        startTextView.setVisibility(View.INVISIBLE);




        //Intentを取得
        Intent intent = getIntent();
        //intentから指定キーの文字列を取得する

        boolean isStop = intent.getBooleanExtra("stop",false);

        if(isStop){
            timeSet.setImageResource(R.mipmap.timebutton1);

        }

        boolean isSnooze = intent.getBooleanExtra("snooze",false);

        if (isSnooze){
            System.out.println("スヌーズ");
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日hh時mm分ss秒");
//            System.out.println(sdf.format(calendar.getTime()));

            snooze();

//            isSnooze = false;
        }else{


            timeSet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timeSet();
                }

            });

            alarmSet = (ImageButton) findViewById(R.id.alarmSet);
            alarmSet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timeSet.setImageResource(R.mipmap.timebutton2);
                    Constraint = findViewById(R.id.Constraint);
                    Constraint.setBackgroundColor(Color.rgb(239,161,143));

                    alarm();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日hh時mm分ss秒");
                    System.out.println(sdf.format(calendar.getTime()));
                }

            });



        }




        }

//    }

//    //時を取得
//    private String mhours() {
//        Ehour = (EditText) this.findViewById(R.id.hour);
//        String hour = Ehour.getText().toString();
//        return hour;
//    }
//
//    //分を取得
//    private String mminutes() {
//        Eminute = (EditText) this.findViewById(R.id.minute);
//        String minute = Eminute.getText().toString();
//        return minute;
//    }

//    //設定した時刻を表示
//    private void timeConf(String h, String m) {
//        confview = (TextView) this.findViewById(R.id.confview);
//        confview.setText("設定した時間は　" + h + "：" + m);
//    }

//    public void timerSet() {
////        Intent intent = new Intent(getApplicationContext(), messageService.class);
////        Context ct = getApplication();
////        PendingIntent pendingIntent = PendingIntent.getService(ct, 0,
////                intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//
//
//        Calendar Dcalendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"));
//        calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"));// Calendar取得
//        calendar.setTimeInMillis(System.currentTimeMillis()); // 現在時刻を取得
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日hh時mm分ss秒");
//        System.out.println(sdf.format(calendar.getTime()));
//
//
//
//        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(mhours()));
//        calendar.set(Calendar.MINUTE, Integer.parseInt(mminutes()));
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//
////        calendar.add(Calendar.SECOND, 5);
//
//        System.out.println(sdf.format(calendar.getTime()));
//
//        int diff = Dcalendar.compareTo(calendar);
//
//        if (diff >= 0){
//
//            System.out.println("正しい時間に設定してください。");
//        } else {
//
//            alarm();
//        }
//    }

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






    public void snooze(){

        SharedPreferences prefer = getSharedPreferences("MainActivity", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefer.edit();
        calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"));
        calendar.setTimeInMillis(prefer.getLong("calendar",0));
        editor.clear();

        calendar.add(Calendar.MINUTE, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日hh時mm分ss秒");
        System.out.println(sdf.format(calendar.getTime()));
        long millis = calendar.getTimeInMillis();
        editor.putLong("calendar",millis);
        editor.commit();
        alarm();



    }



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
                        SharedPreferences prefer = getSharedPreferences("MainActivity",MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefer.edit();
                        float volume = 0.7f;
                        editor.putFloat("volume",0.7f);
                        editor.commit();

                    }
                    break;
                case AudioManager.ACTION_AUDIO_BECOMING_NOISY:

                    // 音声経路の変更！大きな音が鳴りますよ！！
                    System.out.println("ヘッドセットなし２");
                    System.out.println("ヘッドセット装着");
                    SharedPreferences prefer = getSharedPreferences("MainActivity",MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefer.edit();
                    float volume = 0.0f;
                    editor.putFloat("volume",0.0f);
                    editor.commit();


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


                Dcalendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"));
                calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"));// Calendar取得
                Dcalendar.setTimeInMillis(System.currentTimeMillis()); // 現在時刻を取得
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
                        DateFormat df = new SimpleDateFormat("HH:mm");
//                        timeSet.setText(df.format(calendar.getTime()));
                        TextClock = (android.widget.TextClock) findViewById(R.id.textClock);
                        TextClock.setText(df.format(calendar.getTime()));
                        alarmSet.setVisibility(View.VISIBLE);
                        startTextView.setVisibility(View.VISIBLE);

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日hh時mm分ss秒");
                        System.out.println(sdf.format(calendar.getTime()));

                        SharedPreferences prefer = getSharedPreferences("MainActivity",MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefer.edit();
                        long millis = calendar.getTimeInMillis();
                        editor.putLong("calendar",millis);
                        editor.commit();

                        int diff = Dcalendar.compareTo(calendar);

                        if (diff >= 0){
                            System.out.println("正しい時間に設定してください。");
                            Toast.makeText(getApplicationContext(), "正しい時間に設定してください。", Toast.LENGTH_SHORT).show();
                            } else {

                            }
                    }

                }, hour, minute, true);
                timePickerDialog.show();








//               editor.putInt("shour", calendar.HOUR_OF_DAY);
//               editor.putInt("sminute", calendar.MINUTE);
//               editor.commit();

            }




            public void vibe(){

                if (isVibe){
                    vibe.setImageResource(R.mipmap.vibe2);
                    isVibe = false;
                    SharedPreferences prefer = getSharedPreferences("MainActivity",MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefer.edit();
                    boolean isVibe = false;
                    editor.putBoolean("vibe",isVibe);
                    editor.commit();

                }else{
                    vibe.setImageResource(R.mipmap.vibe1);
                    isVibe = true;
                    SharedPreferences prefer = getSharedPreferences("MainActivity",MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefer.edit();
                    boolean isVibe = true;
                    editor.putBoolean("vibe",isVibe);
                    editor.commit();
                }


            }








}
