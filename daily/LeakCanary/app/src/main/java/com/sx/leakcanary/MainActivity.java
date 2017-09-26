package com.sx.leakcanary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = (TextView) findViewById(R.id.textView);
        LHelper.getInstance(this).setRetainedTextView(tv);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LApplication.getRefWatcher().watch(this);
    }
}
