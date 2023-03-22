package com.example.examaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class quiz extends AppCompatActivity {
    // creating questions list
    private final List<QuestionsList> questionsLists = new ArrayList<>();

    private TextView quizTimer;

    private RelativeLayout option1Layout, option2Layout, option3Layout, option4Layout;
    private TextView option1TV, option2TV, option3TV, option4TV;
    private ImageView option1Icon, option2Icon, option3Icon, option4Icon;

    private  TextView questionTV;
    private  TextView skipTV;

    private TextView totalQuestionTV;
    private TextView currentQuestion;

    // creating Database Reference from URL
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://examaster-f826a-default-rtdb.firebaseio.com");

    // CountDown Timer for Quiz
    private CountDownTimer countDownTimer;

    // current question position. By default 0 (First Question)
    private int currentQuestionPosition = 0;

    // selected option number. Value must be between 1-4 (We have 4 options). 0 means no option is selected
    private int selectedOption = 0;
    private int SKIP = 1;
    private int SKIP1 = 0;
    //private int wrong = 0;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //wrong = Integer.parseInt((String.valueOf(questionsLists.size() - getCorrectAnswers() - SKIP1)));

        getSupportActionBar().hide();




        quizTimer = findViewById(R.id.quizTimer);

        option1Layout = findViewById(R.id.option1Layout);
        option2Layout = findViewById(R.id.option2Layout);
        option3Layout = findViewById(R.id.option3Layout);
        option4Layout = findViewById(R.id.option4Layout);

        option1TV = findViewById(R.id.option1TV);
        option2TV = findViewById(R.id.option2TV);
        option3TV = findViewById(R.id.option3TV);
        option4TV = findViewById(R.id.option4TV);

        option1Icon = findViewById(R.id.option1Icon);
        option2Icon = findViewById(R.id.option2Icon);
        option3Icon = findViewById(R.id.option3Icon);
        option4Icon = findViewById(R.id.option4Icon);

        questionTV = findViewById(R.id.questionTV);
        skipTV = findViewById(R.id.skipq);


        totalQuestionTV = findViewById(R.id.totalQuestionsTV);
        currentQuestion = findViewById(R.id.currentQuestionTV);

        final AppCompatButton nextBtn = findViewById(R.id.nextQuestionBtn);
        final AppCompatButton skipBtn = findViewById(R.id.SKIPQuestion);

        // show instructions dialog
        InstructionsDialog instructionsDialog = new InstructionsDialog(quiz.this);
        instructionsDialog.setCancelable(false);
        instructionsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        instructionsDialog.show();

        skipBtn.setOnClickListener(view -> {
            int i = SKIP++;
            skipTV.setText("Skip Question "+i);
            SKIP1 = i;


            // check if user has select an option or not
            if(selectedOption == 0){

                // set user selected answer
                questionsLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOption);

                // reset selected option to default value (0)
                selectedOption = 0;
                currentQuestionPosition++; // increase current question value to getting next question

                // check if list has more questions
                if(currentQuestionPosition < questionsLists.size()){
                    selectQuestion(currentQuestionPosition); //  select question /  next question
                }
                else{

                    // list has no questions left so finish the quiz
                    countDownTimer.cancel();  // stop countdown timer
                    finishQuiz(); // finish quiz
                }


            }

        });






        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                int getQuizTime = 0;
                for (DataSnapshot questions : snapshot.child("questions").getChildren()) {

                    String getQuestion = questions.child("question").getValue(String.class);
                    String getOption1 = questions.child("option1").getValue(String.class);
                    String getOption2 = questions.child("option2").getValue(String.class);
                    String getOption3 = questions.child("option3").getValue(String.class);
                    String getOption4 = questions.child("option4").getValue(String.class);
                    int getAnswer = Integer.parseInt(questions.child("answer").getValue(String.class));
                    getQuizTime = Integer.parseInt(questions.child("time").getValue(String.class));
                    // im using String here to database value must be in String form

                    // creating questions list object and add details
                    QuestionsList questionsList = new QuestionsList(getQuestion, getOption1, getOption2, getOption3, getOption4, getAnswer);

                    // adding questionsList object into the list
                    questionsLists.add(questionsList);
                }

                // setting total questions to TextView
                totalQuestionTV.setText("/" + questionsLists.size());

                // start quiz timer and pass max time in seconds
                startQuizTimer(getQuizTime);

                // select first question by default
                selectQuestion(currentQuestionPosition);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(quiz.this, "Failed to get data from Firebase", Toast.LENGTH_SHORT).show();
            }
        });

        option1Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                skipBtn.setVisibility(View.GONE);

                // assign 1 as first option is selected
                selectedOption = 1;

                // select option
                selectOption(option1Layout, option1Icon);

            }
        });
        option2Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                skipBtn.setVisibility(View.GONE);

                // assign 2 as second option is selected
                selectedOption = 2;

                // select option
                selectOption(option2Layout, option2Icon);
            }
        });
        option3Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipBtn.setVisibility(View.GONE);
                // assign 3 as third option is selected
                selectedOption = 3;

                // select option
                selectOption(option3Layout, option3Icon);
            }
        });
        option4Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipBtn.setVisibility(View.GONE);
                // assign 4 as fourth option is selected
                selectedOption = 4;

                // select option
                selectOption(option4Layout, option4Icon);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int i = SKIP++;
                //skipTV.setText("Skip Question "+i);
                skipBtn.setVisibility(View.VISIBLE);


                // check if user has select an option or not
                if(selectedOption != 0){

                    // set user selected answer
                    questionsLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOption);

                    // reset selected option to default value (0)
                    selectedOption = 0;
                    currentQuestionPosition++; // increase current question value to getting next question

                    // check if list has more questions
                    if(currentQuestionPosition < questionsLists.size()){
                        selectQuestion(currentQuestionPosition); //  select question /  next question
                    }
                    else{

                        // list has no questions left so finish the quiz
                        countDownTimer.cancel();  // stop countdown timer

                        finishQuiz(); // finish quiz
                    }
                }

            }
        });

    }

    private void finishQuiz(){

        // creating intent to open QuizResult activity
        Intent intent = new Intent(quiz.this, quiz_ans.class);
        intent.putExtra("myint",SKIP1);
        intent.putExtra("myint1",getCorrectAnswers());
        intent.putExtra("myint2",getWrong());


        // creating bundle to pass quizQuestionsLists
        Bundle bundle = new Bundle();
        bundle.putSerializable("quetions", (Serializable) questionsLists);

        // add bundle to intent
        intent.putExtras(bundle);

        // start activity to open QuizResult activity
        startActivity(intent);
        finish();
    }

    private void startQuizTimer(int maxTimeInSeconds){

        countDownTimer = new CountDownTimer(maxTimeInSeconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                long getHour = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                long getMinute = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                long getSecond = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);

                String generateTime = String.format(Locale.getDefault(), "%02d:%02d:%02d", getHour,
                        getMinute - TimeUnit.HOURS.toMinutes(getHour),
                        getSecond - TimeUnit.MINUTES.toSeconds(getMinute));

                quizTimer.setText(generateTime);
            }

            @Override
            public void onFinish() {

                // finish quiz when time i finished
                finishQuiz();
            }
        };

        // start timer
        countDownTimer.start();
    }

    @SuppressLint("SetTextI18n")
    private void selectQuestion(int questionListPosition){

        // reset options fro new question
        resetOptions();

        // getting question details and set to TextViews
        questionTV.setText(questionsLists.get(questionListPosition).getQuestion());
        option1TV.setText(questionsLists.get(questionListPosition).getOption1());
        option2TV.setText(questionsLists.get(questionListPosition).getOption2());
        option3TV.setText(questionsLists.get(questionListPosition).getOption3());
        option4TV.setText(questionsLists.get(questionListPosition).getOption4());

        // setting current question number to TextView
        currentQuestion.setText("Question"+(questionListPosition+1));
    }

    private void resetOptions(){

        option1Layout.setBackgroundResource(R.drawable.round_back_white50_10);
        option2Layout.setBackgroundResource(R.drawable.round_back_white50_10);
        option3Layout.setBackgroundResource(R.drawable.round_back_white50_10);
        option4Layout.setBackgroundResource(R.drawable.round_back_white50_10);

        option1Icon.setImageResource(R.drawable.round_back_white50_100);
        option2Icon.setImageResource(R.drawable.round_back_white50_100);
        option3Icon.setImageResource(R.drawable.round_back_white50_100);
        option4Icon.setImageResource(R.drawable.round_back_white50_100);
    }

    private void selectOption(RelativeLayout selectedOptionLayout, ImageView selectedOptionIcon){

        // reset options to select new option
        resetOptions();

        selectedOptionIcon.setImageResource(R.drawable.check_icon);
        selectedOptionLayout.setBackgroundResource(R.drawable.round_back_selected_option);
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


    public int getWrong(){


        int wrong = Integer.parseInt((String.valueOf(questionsLists.size() - getCorrectAnswers() - SKIP1)));


        return wrong;
    }




}
