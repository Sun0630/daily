package com.sx.expandabletextviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sx.expandabletextview.ExpandableTextView;

/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((ExpandableTextView) findViewById(R.id.tv)).setText(getString(R.string.dummy_text));
    }
}
