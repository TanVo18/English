package com.example.administrator.izienglish;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btn_listen,btn_grammar,btn_exercise,btn_irregularV;
    ListView lv_main;
    ArrayAdapter<String> adapter = null;
    ArrayList<String> arr = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        getFromWidget();


        for(int i = 0 ; i < 2;i++){
            String k = Integer.toString(i);
            Firebase root = new Firebase("https://test-firebase-c80fc.firebaseio.com/Name/"+k);
            root.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    arr.add(dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
        arr.add("fdsakfdsakf");
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arr);
        lv_main.setAdapter(adapter);



        btn_irregularV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,IrregularVerbActivity.class);
                startActivity(i);
            }
        });
    }

    public void getFromWidget(){
        btn_listen = (Button)findViewById(R.id.btn_lis);
        btn_grammar = (Button)findViewById(R.id.btn_gram);
        btn_exercise = (Button)findViewById(R.id.btn_exer);
        btn_irregularV = (Button)findViewById(R.id.btn_verb);
        lv_main = (ListView)findViewById(R.id.lv_main);
    }


}
