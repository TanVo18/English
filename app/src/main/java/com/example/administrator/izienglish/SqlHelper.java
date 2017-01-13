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

import com.example.administrator.izienglish.Model.Verbs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Administrator on 1/8/2016.
 */
public class SqlHelper extends SQLiteOpenHelper {

    ArrayList<Verbs> arr = new ArrayList<Verbs>();
    // All Static variables
    // Database Version
    public static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "db_verb2.db";

    //Table name
    private static final String TABLE_NAME = "VERB";

    // Fields Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_VERB1 = "verb1";
    private static final String KEY_VERB2 = "verb2";
    private static final String KEY_VERB3 = "verb3";
    private static final String KEY_CONTAIN = "contain";
    private static final String KEY_FAVORITE = "favorite";

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

                copyDatabase();

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


    //để tạo database một lần duy nhất lúc khởi chạy ứng dụng lần đầu
    private void copyDatabase() {
        CopyAsync copyAsync = new CopyAsync();
        copyAsync.execute(mContext);
        setDatabaseVersion();
        mInvalidDatabaseFile = false;
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
        Cursor cursor = db.query(TABLE_NAME, null, null,
                null, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        //Lấy giá trị tại đây
        while (cursor.isAfterLast() == false) {

            String v1 = cursor.getString(1);
            String v2 = cursor.getString(2);
            String v3 = cursor.getString(3);
            String contain = cursor.getString(4);
            int favorite = cursor.getInt(5);
            Verbs verb = new Verbs(v1, v2, v3, contain, favorite);
            arr.add(verb);
            cursor.moveToNext();
        }
        cursor.close();
        return arr;
    }

    public void update(String verbName, int favorite) {
        open();
        String query = "UPDATE " + TABLE_NAME + " SET favorite = " + favorite + " WHERE verb1 = '" + verbName+"'";
        mDb.execSQL(query);
        close();
    }
}

class CopyAsync extends AsyncTask<Context, Void, Void> {
    @Override
    protected void onPreExecute() {
        //      IrregularVerbActivity.mProgressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Context... params) {

        AssetManager assetManager = params[0].getResources().getAssets();
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(SqlHelper.DATABASE_NAME);
            out = new FileOutputStream(SqlHelper.DATABASE_FILE);
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        } catch (IOException e) {
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }

        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openDatabase(SqlHelper.DATABASE_FILE.getAbsolutePath(), null,
                    SQLiteDatabase.OPEN_READWRITE);
            db.execSQL("PRAGMA user_version = " + SqlHelper.DATABASE_VERSION);
        } catch (SQLiteException e) {
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}

