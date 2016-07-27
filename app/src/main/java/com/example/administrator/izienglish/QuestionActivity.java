package com.example.administrator.izienglish;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;


import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {
    Firebase root;
    TextView tv_id,tv_question,tv_result;
    Button btn_check,btn_next,btn_show;
    RadioGroup radGroup;
    RadioButton rad1,rad2,rad3,rad4;
    Question currentQuestion;
    int arrayIndex=0;
    ArrayList<Question> arr = new ArrayList<Question>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Firebase.setAndroidContext(this);
        root = new Firebase("https://test-firebase-c80fc.firebaseio.com/");
        getFromWidget();
        getFirebaseData();

        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentQuestion = arr.get(arrayIndex);
                setViewQuestion(currentQuestion);
                arrayIndex++;
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentQuestion = arr.get(arrayIndex);
                setViewQuestion(currentQuestion);
                arrayIndex++;
                RadioButton rad_but = (RadioButton)findViewById(radGroup.getCheckedRadioButtonId());
                rad_but.setChecked(false);
            }
        });
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = CheckQuestion();
                if(check){
                    Toast.makeText(QuestionActivity.this,"You Right",Toast.LENGTH_LONG).show();
                    tv_result.setText(currentQuestion.getRightAnswer().toString());
                }
                else {
                    Toast.makeText(QuestionActivity.this,"You Wrong",Toast.LENGTH_LONG).show();
                    tv_result.setText(currentQuestion.getRightAnswer().toString());
                }
            }
        });

    }


    public void getFromWidget(){

        tv_id = (TextView)findViewById(R.id.tv_id);
        tv_result = (TextView)findViewById(R.id.tv_result);
        tv_question = (TextView)findViewById(R.id.tv_question);
        btn_check = (Button)findViewById(R.id.btn_check);
        btn_next = (Button)findViewById(R.id.btn_next);
        btn_show = (Button)findViewById(R.id.btn_show) ;
        radGroup = (RadioGroup)findViewById(R.id.radiogroup);
        rad1 = (RadioButton)findViewById(R.id.rad1);
        rad2 = (RadioButton)findViewById(R.id.rad2);
        rad3 = (RadioButton)findViewById(R.id.rad3);
        rad4 = (RadioButton)findViewById(R.id.rad4);

    }

    public void getFirebaseData(){
        Firebase up = root.child("Question");
        Firebase up2 = up.child("Part1");

        up2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Question q = dataSnapshot.getValue(Question.class);
                arr.add(q);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public void setViewQuestion(Question q){
        tv_id.setText("Question " + Integer.toString(q.getId()) );
        tv_question.setText(q.getQuestion().toString());
        rad1.setText(q.getAnswer1().toString());
        rad2.setText(q.getAnswer2().toString());
        rad3.setText(q.getAnswer3().toString());
        rad4.setText(q.getAnswer4().toString());
    }

    public boolean CheckQuestion(){
        RadioButton answer = (RadioButton)findViewById(radGroup.getCheckedRadioButtonId());
        if(currentQuestion.getRightAnswer().toString().equals(answer.getText().toString())){
            return true;
        }
        return false;
    }
}
