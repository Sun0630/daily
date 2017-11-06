package com.sx.datepicker;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity {

    private TextView tv_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_time = findViewById(R.id.tv_time);
    }

    public void selectDate(View view) {
        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tv_time.setText(getTime(date));
            }
        })
                .setTitleText("请选择预约时间")
                .setCancelText("取消")
                .setSubmitText("确认")
                .setCancelColor(Color.parseColor("#58CBBE"))
                .setSubmitColor(Color.parseColor("#58CBBE"))
                .setOutSideCancelable(true)
                .setType(new boolean[]{true,true,true,false,false,false})
                .build();

        //注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，
        // 避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.setDate(Calendar.getInstance());
        pvTime.show();
    }

    private String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
