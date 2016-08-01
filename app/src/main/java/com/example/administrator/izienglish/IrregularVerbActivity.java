package com.example.administrator.izienglish;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class IrregularVerbActivity extends AppCompatActivity {

    ListView lv_verb;
    public static MyArrayAdapter adapter = null;
    ArrayList<Verbs> arr = new ArrayList<Verbs>();
    SqlHelper db = new SqlHelper(IrregularVerbActivity.this);
    public static ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irregular_verb);

        lv_verb = (ListView)findViewById(R.id.lv_verb);

        //init progress dialog
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Đang khởi tạo dữ liệu ban đầu");
        mProgressDialog.setMessage("Vui lòng chờ...");
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();

        //initData();
        arr = db.getData();
        adapter = new MyArrayAdapter(IrregularVerbActivity.this,R.layout.my_item_layout,arr);
        lv_verb.setAdapter(adapter);

    }

}
