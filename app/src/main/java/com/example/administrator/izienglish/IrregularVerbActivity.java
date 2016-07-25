package com.example.administrator.izienglish;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class IrregularVerbActivity extends AppCompatActivity {

    ListView lv_verb;
    ArrayAdapter adapter2 = null;
    MyArrayAdapter adapter = null;
    ArrayList<Verbs> arr = new ArrayList<Verbs>();
    DatabaseHelper db = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irregular_verb);
        getFromWidget();
        getDatabase();
    }

    public void getFromWidget(){
        lv_verb = (ListView)findViewById(R.id.lv_verb);

    }

    public void getDatabase(){
        db.open();
//        long i = db.createData("abide","abode","abode");
//        if(i>0){
//            Toast.makeText(this,"Success",Toast.LENGTH_LONG).show();
//        }
        arr = db.getData();
        adapter2 = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,arr);
        adapter = new MyArrayAdapter(this,R.layout.my_item_layout,arr);
        lv_verb.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        db.close();
    }
}
