package com.sx.sounddemo;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * @author Administrator
 * @Date 2017/12/13 0013 上午 9:50
 * @Description
 */

class ConvertUtils {
    /**
     * 把 Uri 转变 为 真实的 String 路径
     *
     * @param context 上下文
     * @param uri     URI
     * @return 转换结果
     */
    public static String uri2Path(Context context, Uri uri) {
        if (null == uri) {
            return null;
        }
        String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
