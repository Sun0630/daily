package tech.sx.slidingmenu;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button mFolatingButton;
    private WindowManager.LayoutParams mLayoutParams;
    private WindowManager mWindowManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //演示通过WindowManager添加Window的过程
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mFolatingButton = new Button(this);
        mFolatingButton.setText("button");
        mLayoutParams = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 0, 0, PixelFormat.TRANSPARENT);
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;

        mLayoutParams.gravity = Gravity.LEFT|Gravity.CENTER;
        mLayoutParams.x = 100;
        mLayoutParams.y = 300;

        mWindowManager.addView(mFolatingButton,mLayoutParams);

    }
}
