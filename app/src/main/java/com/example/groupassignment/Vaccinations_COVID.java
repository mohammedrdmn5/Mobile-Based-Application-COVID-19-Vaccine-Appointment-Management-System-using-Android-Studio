package com.example.groupassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.transition.TransitionValues;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class Vaccinations_COVID extends AppCompatActivity {
    private Button mbutton;

    SharedPreferences shareRef; // SharedPreferences
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccinations_covid);

        mbutton = findViewById(R.id.btn_take_quiz);


        NavigationItemSelected();

        // SharedPreferences
        shareRef = getSharedPreferences("shareRefUserInfo", Context.MODE_PRIVATE);
    }

    private void NavigationItemSelected() {

        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.nav_vaccine);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_covid:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_vaccine:
                        return true;
                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0, 0);
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(),"Unexpected value: " + item.getItemId(),Toast.LENGTH_LONG);
                }
                return false;
            }
        });
    }

    public void finishActivity(View view) {
        finish();
    }



    public void checkIAgree(View view) {

        boolean checked = ((CheckBox) view).isChecked();
        if (checked) {
            mbutton.setEnabled(true);
        } else {
            mbutton.setEnabled(false);
        }
    }
    public void takeQuiz(View view) {
        DatabaseManager myDB = new DatabaseManager(Vaccinations_COVID.this);
        if (myDB.getOneUserInfo("username",shareRef.getString("username", "None"),"IsDoneQuiz").equals("1"))// check if the user has taken the quiz already
        {
            //the user took the quiz;
            checkTakenQuizAlertDialog();
        }
        else
        {
            // the user dosn't take the quiz yet
            Intent intent=new Intent(Vaccinations_COVID.this,Quiz.class);
            startActivity(intent);
        }
    }
    public void checkTakenQuizAlertDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder(Vaccinations_COVID.this);
        builder.setCancelable(true);
        builder.setTitle("Quiz done successfully");
        builder.setMessage("You have done the quiz before !");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }
}