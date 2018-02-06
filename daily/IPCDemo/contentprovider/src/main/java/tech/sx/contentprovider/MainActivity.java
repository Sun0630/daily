package tech.sx.contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "BookProvider";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Uri bookUri = Uri.parse("content://tech.sx.contentprovider.BookProvider/book");
        ContentValues values = new ContentValues();
        values.put("_id",6);
        values.put("name","程序设计艺术");
        getContentResolver().insert(bookUri,values);

        Cursor bookCursor = getContentResolver().query(bookUri, new String[]{"_id", "name"}, null, null, null);
        while (bookCursor.moveToNext()){
            Book book = new Book();
            book.bookId = bookCursor.getInt(0);
            book.bookName = bookCursor.getString(1);
            Log.e(TAG, "query book : "+book.toString() );
        }

        bookCursor.close();


        Uri userUri = Uri.parse("content://tech.sx.contentprovider.BookProvider/user");
        Cursor userCursor = getContentResolver().query(userUri, new String[]{"_id", "name", "sex"}, null, null, null);

        while (userCursor.moveToNext()){
            User user = new User();
            user.userId = userCursor.getInt(0);
            user.userName = userCursor.getString(1);
            user.isMale = userCursor.getInt(2)==1;
            Log.e(TAG, "query user: "+user.toString() );
        }

        userCursor.close();


    }
}
