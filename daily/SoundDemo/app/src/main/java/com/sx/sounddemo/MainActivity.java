package com.sx.sounddemo;

import android.app.Service;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private SoundPoolHelper soundPoolHelper;
    private Vibrator mVibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //震动
        mVibrator = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
        soundPoolHelper = new SoundPoolHelper(4, SoundPoolHelper.TYPE_ALARM)
                .setRingtoneType(SoundPoolHelper.RING_TYPE_ALARM)
                .loadDefault(this)
                .load(this, "duan", R.raw.duan);


    }

    public void play1(View view) {
        //播放默认
        soundPoolHelper.playDefault();
        long[] pattern = new long[] { 0, 180, 80, 120 };
        mVibrator.vibrate(pattern, 1);
    }

    public void play2(View view) {
        soundPoolHelper.play("duan", true);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPoolHelper.release();
        mVibrator.cancel();
    }
}
