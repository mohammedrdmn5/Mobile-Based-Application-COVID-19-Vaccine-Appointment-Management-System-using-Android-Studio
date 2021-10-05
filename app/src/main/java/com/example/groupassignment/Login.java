package com.example.groupassignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class Login extends AppCompatActivity {
    AnimationDrawable lightsAnimation;
    SharedPreferences shareRef; // SharedPreferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //get imageview and set to animation and make animation start by AnimationDrawable
        ImageView imgFrame = findViewById(R.id.imgv_animation);
        imgFrame.setBackgroundResource(R.drawable.animation);
        lightsAnimation = (AnimationDrawable) imgFrame.getBackground();
        checkUserLoginStatus();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lightsAnimation.stop();
            }
        }, 5000);
        shareRef = getSharedPreferences("shareRefUserInfo", Context.MODE_PRIVATE);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        lightsAnimation.start();
    }


    public void login(View view) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        DatabaseManager myDB = new DatabaseManager(Login.this);
        //get the data from textedit
        EditText username = findViewById(R.id.et_username);
        EditText password = findViewById(R.id.et_password);
        String getLoginFromDB = myDB.loginUser(username.getText().toString(), password.getText().toString());
        if (getLoginFromDB.equals("Valid")) {
            int userId = new Integer(Integer.valueOf(myDB.getOneUserInfo("username", username.getText().toString(), "id"))); // get the id as string and change it to int
            int isAdmin = new Integer(Integer.valueOf(myDB.getOneUserInfo("username", username.getText().toString(), "IsAdmin")));
            // save the data to SharedPreferences;
            SharedPreferences.Editor loginedit = shareRef.edit();
            loginedit.putInt("userId", userId);
            loginedit.putString("username", username.getText().toString());
            if (isAdmin == 1) {
                loginedit.putBoolean("isAdmin", true);
            } else {
                loginedit.putBoolean("isAdmin", false);
            }
            loginedit.putBoolean("isLogin", true);
            loginedit.commit();

            LoginAlertDialog();
        } else if (getLoginFromDB.equals("InValidUsername")) {
            username.setError("invalid username");
            username.requestFocus();
        } else if (getLoginFromDB.equals("InValidPass")) {
            password.setError("invalid password");
            password.requestFocus();
        }
    }


    public void LoginAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setCancelable(true);
        builder.setTitle("Login Successfully");
        builder.setMessage("You will be redirected to your profile page.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Login.this, Profile.class);
                startActivity(intent);
                finish();
            }
        });
        builder.show();
    }

    public void checkUserLoginStatus() {
       /* if (shareRef.getBoolean("isLogin", false)== true) // if the login is false means the user not logedin , send to the login interface
        {
            Intent intent = new Intent(Login.this, Profile.class);
            startActivity(intent);
        }*/
    }

    public void goRegister(View view) {
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);

    }
}