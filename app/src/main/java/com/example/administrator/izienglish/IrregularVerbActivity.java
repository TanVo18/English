package com.example.administrator.izienglish;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;

@EActivity
public class IrregularVerbActivity extends AppCompatActivity {

    ListView lv_verb;
    public static MyArrayAdapter adapter = null;
    ArrayList<Verbs> arr = new ArrayList<Verbs>();
    SqlHelper db;
    public static ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irregular_verb);
    }

    @AfterViews
    public void Init(){
        lv_verb = (ListView)findViewById(R.id.lv_verb);
        db = new SqlHelper(IrregularVerbActivity.this);
        arr = db.getData();
        adapter = new MyArrayAdapter(IrregularVerbActivity.this,R.layout.my_item_layout,arr);
        lv_verb.setAdapter(adapter);
    }

}
