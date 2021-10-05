package com.example.groupassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Quiz extends AppCompatActivity {
    private TextView tv_Question, tv_State, tv_QuestionNo;
    private RadioGroup mradioGroup;
    private RadioButton mradioButton1, mradioButton2, mradioButton3, mradioButton4;
    private Button btnNext;
    SharedPreferences shareRef; // SharedPreferences
    int totalQuestions;
    int questionqCounter = 0;

    private QuestionsList currentQuestion;

    private List<QuestionsList> questionsList;
    ColorStateList mcolorStateList;
    boolean answered;


    private ImageView mimageView;

    boolean i_understand = false;
    private CheckBox mcheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        shareRef = getSharedPreferences("shareRefUserInfo", Context.MODE_PRIVATE);
        questionsList = new ArrayList<>();
        tv_Question = findViewById(R.id.tv_Question);
        tv_QuestionNo = findViewById(R.id.tv_Question_No);
        tv_State = findViewById(R.id.tv_Question_state);
        mradioGroup = findViewById(R.id.rg_Options);
        mradioButton1 = findViewById(R.id.rb_Option1);
        mradioButton2 = findViewById(R.id.rb_Option2);
        mradioButton3 = findViewById(R.id.rb_Option3);
        mradioButton4 = findViewById(R.id.rb_Option4);
        btnNext = findViewById(R.id.btn_next_question);

        mcolorStateList = mradioButton1.getTextColors();


        addNewQuestions();

        totalQuestions = questionsList.size();
        showNextQuestion();
        mcheckBox = findViewById(R.id.cb_i_understand);

    }


    private void showNextQuestion() {
        answered = false;

        mradioGroup.clearCheck();
        mradioButton1.setTextColor(mcolorStateList);
        mradioButton2.setTextColor(mcolorStateList);
        mradioButton3.setTextColor(mcolorStateList);
        mradioButton4.setTextColor(mcolorStateList);
        tv_State.setText("");
        if (questionqCounter < totalQuestions) {
            currentQuestion = questionsList.get(questionqCounter);
            tv_Question.setText(currentQuestion.getQuestion());
            mradioButton1.setText(currentQuestion.getOption1());
            mradioButton2.setText(currentQuestion.getOption2());
            mradioButton3.setText(currentQuestion.getOption3());
            mradioButton4.setText(currentQuestion.getOption4());


            questionqCounter++;
            btnNext.setText("Submit");
            tv_QuestionNo.setText("Question: " + questionqCounter + "/" + totalQuestions);
        }
    }

    private void addNewQuestions() {
        // set all questions
        questionsList.add(new QuestionsList("How effective are the authorized coronavirus vaccines against hospitalization and death from COVID-19?", "68 percent", "72 percent", "95 percent", "100 percent", 4));
        questionsList.add(new QuestionsList("The Centers for Disease Control and Prevention (CDC) recommends COVID-19 vaccines unless:", "You are allergic to eggs", "You've had COVID-19 already", "You are allergic to an ingredient in the vaccines", " You have an underlying medical condition", 3));
        questionsList.add(new QuestionsList("Which is NOT a common side effect of COVID-19 vaccines?", "Headache", "Fever/chills", "Muscle and joint pain", "Cough", 4));
        questionsList.add(new QuestionsList("How long after your last vaccine dose until you are considered fully protected?", "28 days", "2 weeks", "1 week", "24 hours", 2));
        questionsList.add(new QuestionsList("How long should you quarantine if you are exposed to someone with COVID-19 after you've been vaccinated?", "0 days", "10 days", "14 days", "Until you receive a negative test result", 1));
        questionsList.add(new QuestionsList("Possible Side Effects \n" +
                "    In the arm where you got the shot:", "Pain", "Redness", "Swelling", "All above correct", 4));
        questionsList.add(new QuestionsList("Possible Side Effects \n" +
                "    Throughout the rest of your body:", "Tiredness", "Muscle pain", "Fever", "All above correct", 4));
    }

    public void radioButtonchecked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        if (checked) {
            btnNext.setEnabled(true);
        } else {
            btnNext.setEnabled(false);
        }
    }

    public void btnNextQuestion(View view) {
        if (btnNext.getText().equals("Confirm quiz submission") && i_understand) {
            DatabaseManager myDB = new DatabaseManager(Quiz.this);
            myDB.updateTakenQuiz(shareRef.getString("username", "None"),"1");
            answered = true;
            Intent intent = new Intent(this, Profile.class);
            startActivity(intent);
        }
        else if(mcheckBox.getVisibility()==view.VISIBLE){
            Toast.makeText(Quiz.this, "You need first to check the check box Below ", Toast.LENGTH_LONG).show();
        }
        else if (answered == false) {
            if (mradioButton1.isChecked() || mradioButton2.isChecked() || mradioButton3.isChecked() || mradioButton4.isChecked()) {
                cheackAnswer();
            } else {
                Toast.makeText(Quiz.this, "Please Select an option", Toast.LENGTH_SHORT).show();
            }
        } else {
            showNextQuestion();
        }
    }

    private void cheackAnswer() {

        MediaPlayer mediaPlayer;
        RadioButton rb_selected = findViewById(mradioGroup.getCheckedRadioButtonId());
        int answerNo = mradioGroup.indexOfChild(rb_selected) + 1;
        if (answerNo == currentQuestion.getCorrectAnsNo()) {
            tv_State.setText(Html.fromHtml("State:<font color='green'> Correct </font>"));
            answered = true;
            mediaPlayer = MediaPlayer.create(this, R.raw.correct_sound_effect);
            mediaPlayer.start();
            if (questionqCounter < totalQuestions) {
                btnNext.setText("NEXT");
            } else {
                btnNext.setText("Confirm quiz submission");
                mimageView = findViewById(R.id.imgv_success);
                mimageView.setVisibility(View.VISIBLE);
                mcheckBox.setVisibility(View.VISIBLE);
                mradioGroup.setVisibility(View.INVISIBLE);
                tv_Question.setVisibility(View.INVISIBLE);

            }
        } else {
            tv_State.setText(Html.fromHtml("State:<font color='red'> Wong </font>"));
            answered = false;
            mediaPlayer = MediaPlayer.create(this, R.raw.wrong_sound_effect);
            mediaPlayer.start();
        }
        // change color
        mradioButton1.setTextColor(Color.RED);
        mradioButton2.setTextColor(Color.RED);
        mradioButton3.setTextColor(Color.RED);
        mradioButton4.setTextColor(Color.RED);
        switch (currentQuestion.getCorrectAnsNo()) {
            case 1:
                mradioButton1.setTextColor(Color.GREEN);
                break;
            case 2:
                mradioButton2.setTextColor(Color.GREEN);
                break;
            case 3:
                mradioButton3.setTextColor(Color.GREEN);
                break;
            case 4:
                mradioButton4.setTextColor(Color.GREEN);
                break;
        }

    }
    // checkbox
    public void checkIunderstand(View view) {
        i_understand = ((CheckBox) view).isChecked();

    }

}
