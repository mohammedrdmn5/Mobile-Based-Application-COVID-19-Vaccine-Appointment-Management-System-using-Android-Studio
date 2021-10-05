package com.example.groupassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class Profile extends AppCompatActivity {
    Button mButton;

    SharedPreferences shareRef; // SharedPreferences

    ImageView Vaccinated, HasCovid, doneQuiz, Registerd, userImage;
    TextView name, user_passport, user_dob, user_phone, user_email, user_gender, user_address, user_Postcode, comorbidities, username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        // SharedPreferences
        shareRef = getSharedPreferences("shareRefUserInfo", Context.MODE_PRIVATE);
        checkUserLoginStatus();
        checkIsManger();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // new code for navigation bar
        NavigationItemSelected();
    }

    private void NavigationItemSelected() {
        BottomNavigationView navigationView;
        //initialize navigation and assign variable
        navigationView = findViewById(R.id.bottom_navigation);
        // set covid page selected
        navigationView.setSelectedItemId(R.id.nav_profile);
        //this to know which of navigation bar are selected to go next
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_covid:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_vaccine:
                        startActivity(new Intent(getApplicationContext(), Vaccinations_COVID.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_profile:
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Unexpected value: " + item.getItemId(), Toast.LENGTH_LONG);
                }
                return false;
            }
        });
    }

    private void checkIsManger() {
        mButton = findViewById(R.id.btn_managment);
        if (shareRef.getBoolean("isAdmin", false) == true) {
            mButton.setVisibility(View.VISIBLE);
        } else {
            mButton.setVisibility(View.GONE);
        }

    }

    public void edit(View view) {
    }

    public void goManagment(View view) {
        startActivity(new Intent(getApplicationContext(), Users_Management.class));
    }

    public void logout(View view) {

        SharedPreferences.Editor loginedit = shareRef.edit();
        loginedit.putBoolean("isLogin", false);
        loginedit.commit();
        LoginAlertDialog();// show logout message

    }

    // for check if the user if logged in or now , if no , send the user to login page
    public void checkUserLoginStatus() {
        if (shareRef.getBoolean("isLogin", false) == false) // if the login is false means the user not logedin , send to the login interface
        {
            Intent intent = new Intent(Profile.this, Login.class);
            startActivity(intent);
            finish();
        } else
            showUserInfo(); // show the user info in the profile page

    }

    public void showUserInfo() {
        DatabaseManager myDB = new DatabaseManager(Profile.this);
        name = findViewById(R.id.tv_user_name); // fname and l name
        user_passport = findViewById(R.id.tv_user_passport);
        user_dob = findViewById(R.id.tv_user_dob);
        user_phone = findViewById(R.id.tv_user_phone);
        user_email = findViewById(R.id.tv_user_email);
        user_gender = findViewById(R.id.tv_user_gender);
        user_address = findViewById(R.id.tv_user_address);
        user_Postcode = findViewById(R.id.tv_user_Postcode);
        comorbidities = findViewById(R.id.tv_user_comorbidities);
        username = findViewById(R.id.tv_user_username);
        Cursor cursor = myDB.getWholeOneUserInfo("username", shareRef.getString("username", "None")); // get the data from the data base based on the login info from the sharedRef
        name.setText(cursor.getString(2).toString() + " " + cursor.getString(3).toString());
        user_passport.setText(new String(String.valueOf(cursor.getString(17))));
        user_dob.setText(new String(String.valueOf(cursor.getString(8))));
        user_phone.setText(new String(String.valueOf(cursor.getString(12))));
        user_email.setText(new String(String.valueOf(cursor.getString(13))));
        user_gender.setText(new String(String.valueOf(cursor.getString(14))));
        user_address.setText(new String(String.valueOf(cursor.getString(7))));
        user_Postcode.setText(new String(String.valueOf(cursor.getInt(15))));
        comorbidities.setText(new String(String.valueOf(cursor.getString(18))));
        username.setText(new String(String.valueOf(cursor.getString(1))));

        Vaccinated = findViewById(R.id.img_Vaccinated);
        if (new String(String.valueOf(cursor.getInt(5))).equals("1")) {
            Vaccinated.setBackgroundResource(R.drawable.approved);
        } else
            Vaccinated.setBackgroundResource(R.drawable.rejected);


        HasCovid = findViewById(R.id.img_HasCovid);
        if (new String(String.valueOf(cursor.getInt(6))).equals("1")) {
            HasCovid.setBackgroundResource(R.drawable.rejected);
        } else
            HasCovid.setBackgroundResource(R.drawable.approved);


        doneQuiz = findViewById(R.id.img_doneQuiz);
        if (new String(String.valueOf(cursor.getInt(10))).equals("1")) {
            doneQuiz.setBackgroundResource(R.drawable.approved);
        } else
            doneQuiz.setBackgroundResource(R.drawable.rejected);



        Registerd = findViewById(R.id.img_Registered);
        if (new String(String.valueOf(cursor.getInt(16))).equals("1")) {
            Registerd.setBackgroundResource(R.drawable.approved);
        } else
            Registerd.setBackgroundResource(R.drawable.rejected);




        userImage = findViewById(R.id.tv_user_image);
        if (new String(String.valueOf(cursor.getString(11))).equals("None")) {

            if (new String(String.valueOf(cursor.getString(14))).equals("Female")) { // if gender is femal
                userImage.setBackgroundResource(R.drawable.girl);
            } else {
                // gender is male
                userImage.setBackgroundResource(R.drawable.boy);
            }

        } else {
            // user has img
            Resources res = getResources();
            // get img id from drawble based on the name which store in the database to use it as background
            String mDrawableName = new String(String.valueOf(cursor.getString(11)));
            int resID = res.getIdentifier(mDrawableName, "drawable", getPackageName());
            userImage.setBackgroundResource(resID);

        }
    }

    public void LoginAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        builder.setCancelable(true);
        builder.setTitle("Logout Successfully");
        builder.setMessage("Hope we can see you soon ,stay safe  :(  ");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Profile.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
        builder.show();
    }

    public void takeQuiz(View view) {
        DatabaseManager myDB = new DatabaseManager(Profile.this);
        if (myDB.getOneUserInfo("username",shareRef.getString("username", "None"),"IsDoneQuiz").equals("1"))// check if the user has taken the quiz already
        {
            //the user took the quiz;
            checkTakenQuizAlertDialog();
        }
        else
        {
            // the user dosn't take the quiz yet
            Intent intent=new Intent(Profile.this,Quiz.class);
            startActivity(intent);
        }
    }


    public void checkTakenQuizAlertDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
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

    public void registerVaccine(View view) {

        DatabaseManager myDB = new DatabaseManager(Profile.this);
        if (myDB.getOneUserInfo("username",shareRef.getString("username", "None"),"IsIntrestTakenVaccine").equals("1"))
        {

            DoneregisterVaccineAlertDialog ();
        }
        else
        {


            if (myDB.getOneUserInfo("username",shareRef.getString("username", "None"),"IsDoneQuiz").equals("1"))
            {
                askregisterVaccineAlertDialog ();
            }
            else
            {
                askToFinishQuizFirstAlertDialog ();
            }
        }

    }
    public void askToFinishQuizFirstAlertDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        builder.setCancelable(true);
        builder.setTitle("quiz is not done yet ");
        builder.setMessage("Please finish the quiz first to be able to register for getting vaccine !");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    public void askregisterVaccineAlertDialog () {
        DatabaseManager myDB = new DatabaseManager(Profile.this);

        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        builder.setCancelable(true);
        builder.setTitle("Vaccine registration  ");
        builder.setMessage("Do you want to register getting vaccine? ");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myDB.updateTakenVaccine(shareRef.getString("username", "None"),"1");
                registerVaccineAlertDialog ();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }



    public void registerVaccineAlertDialog () {
        DatabaseManager myDB = new DatabaseManager(Profile.this);

        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        builder.setCancelable(true);
        builder.setTitle("Registration has been done successfully!");
        builder.setMessage("we will contact you soon");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(Profile.this,MainActivity.class);
                startActivity(intent);
            }
        });

        builder.show();
    }

    public void DoneregisterVaccineAlertDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        builder.setCancelable(true);
        builder.setTitle("Vaccine registration ");
        builder.setMessage("You have registered before !");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }


}