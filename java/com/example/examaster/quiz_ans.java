package com.example.examaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class quiz_ans extends AppCompatActivity {

    private List<QuestionsList> questionsLists = new ArrayList<>();


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_ans);
        //right= Integer.parseInt(getCorrectAnswers() + "");


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment chartFragment = new chartFragment();
        fragmentTransaction.replace(R.id.frame1,chartFragment).commit();
        Bundle bundle = new Bundle();
        Intent i222 = getIntent();
        String bb = String.valueOf(i222.getIntExtra("myint", 0));
        bundle.putString("otherurl" , bb);
        int rr = Integer.parseInt(String.valueOf(i222.getIntExtra("myint1", 0)));
        bundle.putString("otherurl1" ,String.valueOf(rr));
        int ww = Integer.parseInt(String.valueOf(i222.getIntExtra("myint2", 0)));
        bundle.putString("otherurl2" , String.valueOf((ww)));
        chartFragment.setArguments(bundle);
        //fragmentTransaction.commit();






        getSupportActionBar().hide();
        Intent i22 = getIntent();
        int val = i22.getIntExtra("myint", 0);

        final TextView scoreTV = findViewById(R.id.scoreTV);
        final TextView skipTV = findViewById(R.id.skiped);
        final TextView totalScoreTV = findViewById(R.id.totalScoreTV);
        final TextView correctTV = findViewById(R.id.correctTV);
        final TextView incorrectTV = findViewById(R.id.inCorrectTV);
        final AppCompatButton shareBtn = findViewById(R.id.shareBtn);
        final AppCompatButton reTakeBtn = findViewById(R.id.reTakeQuizBtn);

        // getting questions list from MainActivity
        questionsLists = (List<QuestionsList>) getIntent().getSerializableExtra("quetions");


        totalScoreTV.setText("/" + questionsLists.size());
        scoreTV.setText(getCorrectAnswers() + "");
        correctTV.setText(getCorrectAnswers() + "");
        incorrectTV.setText(String.valueOf(questionsLists.size() - getCorrectAnswers() - val));
        skipTV.setText(String.valueOf(val));



        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // open share intent
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "My Score = " + scoreTV.getText());

                Intent shareIntent = Intent.createChooser(sendIntent, "Share Via");
                startActivity(shareIntent);
            }
        });

        reTakeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // re start quiz go to MainActivity
                startActivity(new Intent(quiz_ans.this, quiz.class));
                finish();
            }
        });


    }

    

    public int getCorrectAnswers() {
        int correctAnswer = 0;


        for(int i = 0; i < questionsLists.size(); i++){

            int getUserSelectedOption = questionsLists.get(i).getUserSelectedAnswer(); // get user selected option
            int getQuestionAnswer = questionsLists.get(i).getAnswer(); //  get correct answer for the question

            // check of user selected answer matches with correct answer
            if(getQuestionAnswer == getUserSelectedOption){
                correctAnswer++; //  increase correct answers
            }


        }


        return correctAnswer;
    }
    }
