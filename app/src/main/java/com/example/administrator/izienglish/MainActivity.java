package com.example.administrator.izienglish;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {
    Button btn_listen,btn_grammar,btn_exercise,btn_irregularV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        getFromWidget();

        Firebase fb = new Firebase("https://test-firebase-c80fc.firebaseio.com/");

        btn_irregularV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,IrregularVerbActivity.class);
                startActivity(i);
            }
        });
        btn_exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent e = new Intent(MainActivity.this,QuestionActivity.class);
                startActivity(e);
            }
        });


    }

    public void getFromWidget(){
        btn_listen = (Button)findViewById(R.id.btn_lis);
        btn_grammar = (Button)findViewById(R.id.btn_gram);
        btn_exercise = (Button)findViewById(R.id.btn_exer);
        btn_irregularV = (Button)findViewById(R.id.btn_verb);
    }


}
