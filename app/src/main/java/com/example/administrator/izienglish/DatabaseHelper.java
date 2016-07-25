package com.example.administrator.izienglish;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Administrator on 23/7/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    ArrayList<Verbs> list = new ArrayList<Verbs>();
    /*Tên database*/
    private static final String DATABASE_NAME = "DB_VERB";

    /*Version database*/
    private static final int DATABASE_VERSION = 1;

    /*Tên tabel và các column trong database*/
    private static final String TABLE_VERB = "VERB";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_V1 = "verb1";
    public static final String COLUMN_V2 = "verb2";
    public static final String COLUMN_V3 = "verb3";

    /*Các đối tượng khác*/
    static SQLiteDatabase db;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //nếu muốn ghi vào thẻ nhớ thì dùng hàm dựng này
        //cấp quyền ghi vào SD card trong Manifest
        //File.separator là dấu / , Environment.getExternalStorageDirectory() để lấy đường dẫn SD card
//            super(context, Environment.getExternalStorageDirectory()
//                    + File.separator + DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*Tạo mới database*/
    @Override
    public void onCreate(SQLiteDatabase arg0) {
        arg0.execSQL("CREATE TABLE " + TABLE_VERB + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_V1 + " TEXT NOT NULL, "
                + COLUMN_V2 + " TEXT NOT NULL, "
                + COLUMN_V3 + " TEXT NOT NULL);");
    }

    /*Kiểm tra phiên bản database nếu khác sẽ thay đổi*/
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_VERB);
        onCreate(arg0);
    }

    public DatabaseHelper open() throws SQLException {
        db = getWritableDatabase();
        return this;
    }


    /*Hàm đóng kết nối với database*/
    public void close(){
        try {
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Hàm createData dùng để chèn dữ mới dữ liệu vào database*/
    public long createData(String v1, String v2,String v3) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_V1, v1);
        cv.put(COLUMN_V2, v2);
        cv.put(COLUMN_V3, v3);

        return db.insert(TABLE_VERB, null, cv);
    }


    public ArrayList<Verbs> getData() {
        Cursor c = db.query(TABLE_VERB, null, null, null, null, null, null);
        //getColumnIndex(COLUMN_ID); là lấy chỉ số, vị trí của cột COLUMN_ID ...

        c.moveToFirst();
        //Vòng lặp lấy dữ liệu của con trỏ
        while(!c.isAfterLast()){
            Verbs v = new Verbs();
            v.setV1(c.getString(c.getColumnIndex(COLUMN_V1)));
            v.setV2(c.getString(c.getColumnIndex(COLUMN_V2)));
            v.setV3(c.getString(c.getColumnIndex(COLUMN_V3)));
            list.add(v);
            c.moveToNext();
        }
        c.close();
        //Log.v("Result", result);
        return list;
    }

    public ArrayList<Verbs> getList(){
        return this.list;
    }

}
