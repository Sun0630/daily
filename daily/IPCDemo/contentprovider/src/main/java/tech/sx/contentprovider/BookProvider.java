package tech.sx.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @Author Administrator
 * @Date 2018/2/2 0002 下午 4:05
 * @Description
 */

public class BookProvider extends ContentProvider {
    private static final String TAG = "BookProvider";

    public static final String AUTHORITY = "tech.sx.contentprovider.BookProvider";

    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user");

    public static final int BOOK_URI_CODE = 0;
    public static final int USER_INT_CODE = 1;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    private Context mContext;

    private DbOpenHelper mDbOpenHelper;


    static {
        URI_MATCHER.addURI(AUTHORITY, "book", BOOK_URI_CODE);
        URI_MATCHER.addURI(AUTHORITY, "user", USER_INT_CODE);
    }

    private SQLiteDatabase mDb;


    /**
     * 通过Uri来得到表名
     * @param uri
     * @return
     */
    private String getTableName(Uri uri) {
        String tableName = null;
        switch (URI_MATCHER.match(uri)) {
            case BOOK_URI_CODE:
                tableName = DbOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_INT_CODE:
                tableName = DbOpenHelper.USER_TABLE_NAME;
                break;
            default:
                break;
        }
        return tableName;
    }


    private void initProviderData() {
        mDb = new DbOpenHelper(mContext).getReadableDatabase();
        mDb.execSQL("delete from " + DbOpenHelper.BOOK_TABLE_NAME);
        mDb.execSQL("delete from " + DbOpenHelper.USER_TABLE_NAME);
        mDb.execSQL("insert into book values (3,'Android');");
        mDb.execSQL("insert into book values (4,'iOS');");
        mDb.execSQL("insert into book values (5,'HTML');");
        mDb.execSQL("insert into user values (1,'lip',1);");
        mDb.execSQL("insert into user values (2,'Ian',0);");
    }


    /**
     * 创建，做一些初始化工作 ，由系统回调运行在主线程
     *
     * @return
     */
    @Override
    public boolean onCreate() {
        Log.e(TAG, "onCreate: current thread " + Thread.currentThread());
        mContext = getContext();
        initProviderData();
        return false;
    }

    /**
     * 运行在Binder线程池中
     *
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.e(TAG, "query:current thread " + Thread.currentThread());

        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        return mDb.query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.e(TAG, "getType: ");
        return null;
    }

    /**
     * 插入数据库
     * @param uri
     * @param values
     * @return
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.e(TAG, "insert: ");
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("Unsupport URI:" + uri);
        }
        mDb.insert(tableName, null, values);
        //提醒
        mContext.getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.e(TAG, "delete: ");
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("Unsupport URI:" + uri);
        }
        int count = mDb.delete(tableName, selection, selectionArgs);
        if (count > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.e(TAG, "update: ");
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("Unsupport URI:" + uri);
        }
        int row = mDb.update(tableName, values, selection, selectionArgs);
        if (row > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return row;
    }
}
