package com.sx.popupwndowdemo;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hds on 2016/4/23.
 */
public class Util {
    private static final String LOCAL_CACHE_SUF = ".dat";
    private static final String TAG = Util.class.getSimpleName();

    private Util() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     *  判断设备是否是平板？
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        return context.getResources().getConfiguration().smallestScreenWidthDp >= 600;
    }
    /**
     * @Title stringToInt
     * @Description 字符串转整型
     * @return int
     * @param str
     */
    public static int stringToInt(String str) {
        if (isNumeric(str)) {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException e) {
                return -1;
            }
        }
        return -1;
    }
    /**
     * @Title isNumeric
     * @Description 是否为数字
     * @return boolean
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * @Title stringToDouble
     * @Description 字符串转双精度
     */
    public static double stringToDouble(String str) {
        if (!"".equals(str)) {
            try {
                return Double.parseDouble(str);
            } catch (NumberFormatException e) {
                return -1;
            }
        }

        return 0;
    }
    /**
     * @Title stringToDouble
     * @Description 字符串转单精度
     */
    public static float stringToFloat(String str) {
        if (!"".equals(str)) {
            try {
                return Float.parseFloat(str);
            } catch (NumberFormatException e) {
                return -1;
            }
        }

        return 0;
    }
    /**
     * @Title saveJsonCacheData
     * @Description 缓存json数据到本地
     * @return void
     * @param context
     * @param name
     */
//    public static void deleteJsonCacheData(Context context, String name) {
//        String saveDir = context.getCacheDir().getPath() + File.separator;
//        File file = new File(saveDir + name + LOCAL_CACHE_SUF); // 要输出文件的路径
//        if (file.exists()) { // 文件不存在
//            file.delete();//删除文件
//        }
//    }

    /**
     * @Title saveJsonCacheData
     * @Description 缓存json数据到本地
     * @return void
     * @param context
     * @param name
     * @param data
     */
//    public static void saveJsonCacheData(Context context, String name, String data) {
//        String saveDir = context.getCacheDir().getPath() + File.separator;
//        File file = new File(saveDir + name + LOCAL_CACHE_SUF); // 要输出文件的路径
//        if (!file.getParentFile().exists()) { // 文件不存在
//            file.getParentFile().mkdirs(); // 创建文件夹
//        }
//        PrintStream out = null;
//        try {
//            out = new PrintStream(new FileOutputStream(file));
//            out.print(data); // 将数据变为字符串后保存
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            if (out != null) {
//                out.close(); // 关闭输出
//            }
//        }
//    }
    /**
     * @Title clearWebViewCache
     * @Description 清除WebView缓存
     * @return void
     * @param context
     */
    public static void clearWebViewCache(Context context) {
        // 清理Webview缓存数据库
        try {
            context.deleteDatabase("webview.db");
            context.deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // WebView 缓存文件
        File appCacheDir = new File(context.getFilesDir().getAbsolutePath() + "/webcache");
        Log.i(TAG, "appCacheDir path = " + appCacheDir.getAbsolutePath());
        File webviewCacheDir = new File(context.getCacheDir().getAbsolutePath() + "/webviewCache");
        Log.i(TAG, "webviewCacheDir path = " + webviewCacheDir.getAbsolutePath());
        // 删除webview 缓存目录
        if (webviewCacheDir.exists()) {
            deleteFile(webviewCacheDir);
        } else {
            Log.e(TAG, "delete file no exists " + webviewCacheDir);
        }
        // 删除webview 缓存 缓存目录
        if (appCacheDir.exists()) {
            deleteFile(appCacheDir);
        } else {
            Log.e(TAG, "delete file no exists " + appCacheDir);
        }
    }

    /**
     * @Title deleteFile
     * @Description 递归删除 文件/文件夹
     * @return void
     * @param file
     */
    public static void deleteFile(File file) {
        Log.i(TAG, "delete file path = " + file.getAbsolutePath());
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            Log.e(TAG, "delete file no exists " + file.getAbsolutePath());
        }
    }


    /**
     *     @Title: editTextDate  @Description: 输入框保留几位小数验证   @return void  @throws
     *
     * @param editText  
     */

    public static void editTextDate(final EditText editText, final int isTwo) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                String temp = arg0.toString();
                int posDot = temp.indexOf(".");
                int posZero = temp.indexOf("0");
                int posZeroDot = temp.indexOf("0.");
                if (posZero == 0 && temp.length() >= 2 && posZeroDot != 0) {
                    arg0.delete(0, 1);
                }
                if (posDot == 0) {
                    arg0.delete(0, 1);
                    // return;
                } else if (posDot > 0) {
                    if (isTwo == 2) {
                        // 保留两位小数
                        if (temp.length() - posDot - 1 > 2) {
                            arg0.delete(posDot + 3, posDot + 4);
                        }
                    } else {
                        // 保留一位小数
                        if (temp.length() - posDot - 1 > 1) {
                            arg0.delete(posDot + 2, posDot + 3);
                        }
                    }
                } else {
                    return;
                }
            }
        });
    }

    public static int getScreenHeight(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        int width = wm.getDefaultDisplay().getWidth();//屏幕宽度
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }
}
