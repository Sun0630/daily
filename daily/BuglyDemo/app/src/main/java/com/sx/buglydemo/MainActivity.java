package com.sx.buglydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void say(View view) {
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
    }

    public void sayAnother(View view) {
        Toast.makeText(this, "Hello World", Toast.LENGTH_SHORT).show();
    }

    public void loadPatch(View view) {

    }
}
