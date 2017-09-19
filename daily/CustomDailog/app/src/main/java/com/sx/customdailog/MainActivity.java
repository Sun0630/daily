package com.sx.customdailog;

import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.sx.customdailog.dialog.AlertDialog;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }

    private void initData() {
        for (int i = 0; i < 30; i++) {
            datas.add(i + "s");
        }
    }

    public void dialog(View view) {
        //背景变暗
        dimBackground(1.0f, 0.5f);
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.remark_dialog)
                .show();


        final EditText editText = dialog.getView(R.id.et_remark);
        String text = editText.getText().toString();
        System.out.println("Text-->" + text);
        dialog.setOnClickListener(R.id.btn_sure, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "内容是：" + editText.getText().toString(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface anInterface) {
                //背景变亮
                dimBackground(0.5f, 1.0f);
            }
        });


    }

    /**
     * 调整窗口的透明度
     *
     * @param from>=0&&from<=1.0f
     * @param to>=0&&to<=1.0f
     */
    private void dimBackground(final float from, final float to) {
        final Window window = getWindow();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                WindowManager.LayoutParams params = window.getAttributes();
                params.alpha = (Float) animation.getAnimatedValue();
                window.setAttributes(params);
            }
        });

        valueAnimator.start();
    }

    /**
     * 设置 hint字体大小
     *
     * @param editText 输入控件
     * @param hintText hint的文本内容
     * @param textSize hint的文本的文字大小（以dp为单位设置即可）
     */
    public static void setHintTextSize(EditText editText, String hintText, int textSize) {
        // 新建一个可以添加属性的文本对象
        SpannableString ss = new SpannableString(hintText);
        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(textSize, true);
        // 附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 设置hint
        editText.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
    }


    public void dialog2(View view) {
        //背景变暗
        dimBackground(1.0f, 0.5f);
        final AlertDialog timeDialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.time_dialog)
                .show();

        TagFlowLayout flowLayout = timeDialog.getView(R.id.flow_layout);
        System.out.println("TagFlowLayout-->" + flowLayout);
        flowLayout.setAdapter(new TagAdapter(datas) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                return null;
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
                view.setBackgroundResource(R.mipmap.home_home_reservation_pic_03);
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
                view.setBackgroundResource(R.mipmap.home_home_reservation_pic_01);
            }

        });
        timeDialog.setOnClickListener(R.id.btn_sure, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "内容是：", Toast.LENGTH_SHORT).show();
                timeDialog.dismiss();
            }
        });


    }


}
