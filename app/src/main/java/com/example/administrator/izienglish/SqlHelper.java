package com.example.administrator.izienglish;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import com.example.administrator.izienglish.model.Verbs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class SqlHelper extends SQLiteOpenHelper {

    private ArrayList<Verbs> mVerbs = new ArrayList<>();
    private ArrayList<String> mExps = new ArrayList<>();
    // All Static variables
    // Database Version
    public static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "db_verbs.db";

    //Table name
    private static final String TABLE_VERB = "verbs";
    private static final String TABLE_EXAMPLE = "examples";
    private SQLiteDatabase mDb;
    private Context mContext;
    public static File DATABASE_FILE;
    private boolean mInvalidDatabaseFile = false;
    private boolean mIsUpgraded = false;

    public SqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
        SQLiteDatabase db = null;

        try {
            db = getReadableDatabase();
            if (db != null) {
                db.close();
            }
            DATABASE_FILE = context.getDatabasePath(DATABASE_NAME);
            if (mInvalidDatabaseFile) {

                //   copyDatabase();
                try {
                    copyDataBase(context, DATABASE_NAME);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (mIsUpgraded) {
                doUpgrade();
            }
        } catch (SQLiteException e) {
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    public void open() {
        try {
            mDb = getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * close database
     */
    public void close() {
        if (mDb != null && mDb.isOpen()) {
            try {
                mDb.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        mInvalidDatabaseFile = true;
        Log.e("------", "first");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mInvalidDatabaseFile = true;
        mIsUpgraded = true;
    }

    private void doUpgrade() {
        // implement the database upgrade here.
    }

    private void setDatabaseVersion() {
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openDatabase(DATABASE_FILE.getAbsolutePath(), null,
                    SQLiteDatabase.OPEN_READWRITE);
            db.execSQL("PRAGMA user_version = " + DATABASE_VERSION);
        } catch (SQLiteException e) {
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    public ArrayList<Verbs> getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        //câu lệnh Query tại đây
        Cursor cursor = db.query(TABLE_VERB, null, null,
                null, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        //Lấy giá trị tại đây
        while (cursor.isAfterLast() == false) {
            String v1 = cursor.getString(1);
            String v2 = cursor.getString(3);
            String v3 = cursor.getString(5);
            String ipa1 = cursor.getString(2);
            String ipa2 = cursor.getString(4);
            String ipa3 = cursor.getString(6);
            String contain = cursor.getString(9);
            int favorite = cursor.getInt(7);
            Verbs verb = new Verbs(v1, ipa1, v2, ipa2, v3, ipa3, contain, favorite);
            mVerbs.add(verb);
            cursor.moveToNext();
        }
        cursor.close();
        getListExample();
        int count = 0;
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < mVerbs.size(); i++) {
            for (int k = 0; k < 3; k++) {
                temp.append(mExps.get(count) + "\n");
                count++;
            }
            mVerbs.get(i).setExample(temp + "");
            temp = new StringBuilder();
        }
        return mVerbs;
    }

    public ArrayList<String> getListExample() {
        SQLiteDatabase db = this.getReadableDatabase();
        //câu lệnh Query tại đây
        Cursor cursor = db.query(TABLE_EXAMPLE, null, null,
                null, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        //Lấy giá trị tại đây
        while (cursor.isAfterLast() == false) {
            String exp = cursor.getString(2);
            mExps.add(exp);
            cursor.moveToNext();
        }
        cursor.close();
        return mExps;
    }

    public void update(String verbName, int favorite) {
        open();
        String query = "UPDATE " + TABLE_VERB + " SET favorite = " + favorite + " WHERE verb1 = '" + verbName + "'";
        mDb.execSQL(query);
        close();
    }

    //để tạo database một lần duy nhất lúc khởi chạy ứng dụng lần đầu
    private void copyDataBase(Context context, String dbname) throws IOException {
        // Open your local db as the input stream
        InputStream myInput = context.getAssets().open(dbname);
        // Path to the just created empty db
        File outFileName = context.getDatabasePath(dbname);
        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
        setDatabaseVersion();
        mInvalidDatabaseFile = false;
    }
}

