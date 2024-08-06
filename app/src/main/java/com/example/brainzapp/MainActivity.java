package com.example.brainzapp;

import android.os.Bundle;
import android.app.AlertDialog;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView tvQuestion;
    TextView tvTotalQuestion;
    TextView ansA, ansB, ansC, ansD;
    TextView btn_submit;

    int score = 0;
    int totalQuestion = QuestionAnswers.questions.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTotalQuestion = findViewById(R.id.total_questions);
        tvQuestion = findViewById(R.id.question);
        ansA = findViewById(R.id.ansA);
        ansB = findViewById(R.id.ansB);
        ansC = findViewById(R.id.ansC);
        ansD = findViewById(R.id.ansD);
        btn_submit = findViewById(R.id.submit_button);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

        tvTotalQuestion.setText("Total Question: " + totalQuestion);

        loadNewQuestion();
    }

    public void loadNewQuestion(){
        if (currentQuestionIndex == QuestionAnswers.questions.length){
            finishQuiz();
            return;
        }

        tvQuestion.setText(QuestionAnswers.questions[currentQuestionIndex]);
        ansA.setText(QuestionAnswers.choices[currentQuestionIndex][0]);
        ansB.setText(QuestionAnswers.choices[currentQuestionIndex][1]);
        ansC.setText(QuestionAnswers.choices[currentQuestionIndex][2]);
        ansD.setText(QuestionAnswers.choices[currentQuestionIndex][3]);

        selectedAnswer="";
    }

    public void finishQuiz(){
        String passStatus;
        if (score > totalQuestion * 0.4){
            passStatus = "Passed";
        }else{
            passStatus = "Failed";
        }

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Your Score is " + score + "/" + totalQuestion)
                .setPositiveButton("Restart", ((dialog, i) -> restartQuiz() ))
                .setCancelable(false)
                .show();
    }

    private void restartQuiz(){
        score = 0;
        currentQuestionIndex = 0;
        btn_submit.setBackgroundResource(R.drawable.submitbuttonshape);
        loadNewQuestion();
    }

    @Override
    public void onClick(View view) {
        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);

        Button clickedButton = (Button) view;
        if (clickedButton.getId() == R.id.submit_button){
            if (!selectedAnswer.isEmpty()){
                if (selectedAnswer.equals(QuestionAnswers.correctAnswers[currentQuestionIndex])){
                    btn_submit.setBackgroundResource(R.drawable.submitbutton3);
                    score++;
                }else {
                    btn_submit.setBackgroundResource(R.drawable.submitbutton2);
//                    clickedButton.setBackgroundColor(Color.RED);
                }
                currentQuestionIndex++;
                loadNewQuestion();
            }
        }else {
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.GREEN);
        }
    }
}
