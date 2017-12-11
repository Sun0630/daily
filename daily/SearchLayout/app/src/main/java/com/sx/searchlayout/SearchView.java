package com.sx.searchlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

/**
 * @author Administrator
 * @Date 2017/12/11 0011 上午 11:05
 * @Description 搜索框
 */

public class SearchView extends LinearLayout {


    private float searchTextSize;
    private String textHint;
    private int textColor;
    private int searchHeight;
    private int searchBolokColor;
    private Context context;
    private EditText et_search;
    private LinearLayout searchBlock;
    private SearchListView listView;
    private TextView tv_clear;
    private ImageView searchBack;

    //回调接口
    private ICallBack iCallBack;
    private BackCallBack bCallBack;
    private RecordSQLiteOpenHelper helper;
    private BaseAdapter adapter;
    private SQLiteDatabase db;

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttrs(context, attrs);
        init();
    }


    /**
     * 初始化自定义属性
     *
     * @param context
     * @param attrs
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Search_View);
        searchTextSize = typedArray.getDimension(R.styleable.Search_View_textSizeSearch, 20);
        textHint = typedArray.getString(R.styleable.Search_View_textHintSearch);
        textColor = typedArray.getColor(R.styleable.Search_View_textColorSearch, getResources().getColor(R.color.colorText));
        //搜索框高度
        searchHeight = typedArray.getInteger(R.styleable.Search_View_searchBlockHeight, 150);
        //搜索框颜色
        searchBolokColor = typedArray.getColor(R.styleable.Search_View_searchBlockColor, getResources().getColor(R.color.colorDefault));

        typedArray.recycle();
    }

    /**
     * 初始化搜索框
     */
    private void init() {
        //初始化视图
        initView();

        //获取数据库助手
        helper = new RecordSQLiteOpenHelper(context);

        //第一次进入时查询所有历史搜索记录数据
        queryData("");

        //清空搜索历史
        tv_clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //清空数据库
                deleteData();
                queryData("");
            }
        });

        /**
         * 点击键盘上的搜索按钮的时候调用
         */
        et_search.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (iCallBack != null) {
                        iCallBack.ActionSearch(et_search.getText().toString());
                    }
                    Toast.makeText(context, "搜索的是" + et_search.getText().toString(), Toast.LENGTH_SHORT).show();

                    //点击搜索之后，将搜索的字段在数据库中进行查询，如果没有就插入数据库，病作为历史记录
                    boolean hasData = hasData(et_search.getText().toString());
                    if (!hasData) {
                        //没有这条记录，插入数据库
                        insertData(et_search.getText().toString().trim());
                        queryData("");
                    }


                }
                return false;
            }
        });

        /**
         * 监听文本框实时变化
         */
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //显示历史记录
                String name = et_search.getText().toString().trim();
                queryData(name);
            }
        });


        /**
         * 点击搜索的字段后直接进行搜索
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_text = view.findViewById(android.R.id.text1);
                et_search.setText(tv_text.getText().toString());
                Toast.makeText(context, tv_text.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 点击返回按钮
         */
        searchBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bCallBack != null) {
                    bCallBack.backAction();
                }
                Toast.makeText(context, "i will be back", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 清空数据库
     */
    private void deleteData() {
         db = helper.getWritableDatabase();
         db.execSQL("delete from records");
         db.close();
         tv_clear.setVisibility(INVISIBLE);
    }

    /**
     * 插入数据库
     *
     * @param name
     */
    private void insertData(String name) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + name + "')");
        Log.e(TAG, "insertData success " );
        db.close();
    }

    /**
     * 判断数据库中是否有该条记录
     *
     * @param name
     * @return
     */
    private boolean hasData(String name) {
        Cursor cursor = helper.getReadableDatabase()
                .rawQuery("select id as _id,name from records where name = ?", new String[]{name});
        //判断是否有下一个
        return cursor.moveToNext();
    }

    /**
     * 模糊查询 显示到ListView上
     *
     * @param name
     */
    private void queryData(String name) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + name + "%' order by id desc ", null);

//        adapter = new SimpleCursorAdapter(
//                context,
//                android.R.layout.simple_list_item_1,
//                cursor,
//                new String[]{"name"},
//                new int[]{android.R.id.text1},
//                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
//        );
        adapter = new SimpleCursorAdapter(
                context,
                android.R.layout.simple_list_item_1,
                cursor,
                new String[] { "name" },
                new int[] { android.R.id.text1 },
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        );


        Log.e("SearchView", "queryDataCount: " + cursor.getCount());

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if (name.equals("") && cursor.getCount() != 0) {
            tv_clear.setVisibility(VISIBLE);
        } else {
            tv_clear.setVisibility(INVISIBLE);
        }
    }


    private void initView() {
        //绑定视图
        LayoutInflater.from(context).inflate(R.layout.search_layout, this);

        et_search = findViewById(R.id.et_search);

        et_search.setTextSize(searchTextSize);
        et_search.setTextColor(textColor);
        et_search.setHint(textHint);

        searchBlock = findViewById(R.id.search_block);
        searchBlock.setBackgroundColor(searchBolokColor);
        LinearLayout.LayoutParams params = (LayoutParams) searchBlock.getLayoutParams();
        params.height = searchHeight;
        searchBlock.setLayoutParams(params);

        //搜索历史记录
        listView = findViewById(R.id.list_view);

        //删除历史记录按钮
        tv_clear = findViewById(R.id.tv_clear);
        tv_clear.setVisibility(INVISIBLE);

        //后退按钮
        searchBack = findViewById(R.id.search_back);

    }


    /**
     * 搜索按键回调接口
     *
     * @param iCallBack
     */
    public void setOnClickSearch(ICallBack iCallBack) {
        this.iCallBack = iCallBack;
    }

    public void setOnClickBack(BackCallBack bCallBack) {
        this.bCallBack = bCallBack;
    }

}
