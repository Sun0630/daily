package com.sx.cityindex;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * @author sunxin
 */
public class MainActivity extends AppCompatActivity {

    private TextView tv_letter;
    private LetterSideBar sideBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_letter = findViewById(R.id.tv_letter);
        sideBar = findViewById(R.id.letter_side_bar);

        sideBar.setOnTouchLetterListener(new LetterSideBar.OnTouchLetterListener() {
            @Override
            public void onTouchLetter(CharSequence letter, boolean isTouch) {
                if (isTouch) {

                    tv_letter.setVisibility(View.VISIBLE);
                    tv_letter.setText(letter);
                } else {
                    tv_letter.setVisibility(View.GONE);
                }
            }

        });

    }
}
