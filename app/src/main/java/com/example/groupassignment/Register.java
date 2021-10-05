package com.example.groupassignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class Register extends AppCompatActivity {
    AnimationDrawable animationDrawable;

    private boolean checked;
    String gender;
    EditText username ,fname ,lname ,password ,dob ,phone , email , passport , confirmPassword ;
    String dateMessage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //get imageview and set to animation and make animation start by AnimationDrawable
        ImageView imgFrame = findViewById(R.id.imgv_animation_pencil);
        imgFrame.setBackgroundResource(R.drawable.animation_pencil);
        animationDrawable = (AnimationDrawable) imgFrame.getBackground();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        animationDrawable.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animationDrawable.stop();
            }
        }, 5000);
    }

    public void showDatePicker(View view) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setFlag(DatePickerFragment.FLAG_REGISTER);
        newFragment.show(getSupportFragmentManager(),
                getString(R.string.datepicker));
    }


    public void processDatePickerResult(int year, int month, int day) {
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        dateMessage = (day_string +
                "/" + month_string +
                "/" + year_string);
        EditText meditText = findViewById(R.id.et_reg_DOB);
        meditText.setText(dateMessage);
    }


    public void onRadioButtonClicked(View view) {
        // get answer for question 1
        checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rb_reg_male:
                if (checked)
                    gender = "Male";
                break;
            case R.id.rb_reg_female:
                if (checked)
                    gender = "Female";
                break;
            default:
                break;
        }

    }


    public void register(View view) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        username = findViewById(R.id.et_reg_username);
        fname = findViewById(R.id.et_reg_Fname);
        lname = findViewById(R.id.et_reg_Lname);
        password = findViewById(R.id.et_reg_password);
        dob = findViewById(R.id.et_reg_DOB);
        phone = findViewById(R.id.et_reg_phone);
        email = findViewById(R.id.et_reg_email_addresss);
        passport = findViewById(R.id.et_reg_passport);
        confirmPassword = findViewById(R.id.et_reg_conf_password);






        DatabaseManager myDB = new DatabaseManager(Register.this);
        if (validation() == true) // check if the validation passed
        {
            if (myDB.checkUserExist("username", username.getText().toString())==false) // validation to confirm user not exist
            {
                if (myDB.checkUserExist("phone", phone.getText().toString()) == false) {
                    if (myDB.checkUserExist("email", email.getText().toString())==false) {
                        if (myDB.checkUserExist("passport", passport.getText().toString())==false) {
                            // start adding the user after confirm the user is not exist in the system by checking the email and phone and passport and username;
                            if (myDB.addNewUser(fname.getText().toString(), lname.getText().toString(), username.getText().toString(), password.getText().toString(), dateMessage, phone.getText().toString(), email.getText().toString(),gender, 0,passport.getText().toString(),"None") == true) {
                                registerAlertDialog();// show message login successfully and send to another login page
                            }
                        } else {
                            passport.setError("Passport is exist,please try another");
                            passport.requestFocus();
                        }
                    } else {
                        email.setError("Email is exist,please try another");
                        email.requestFocus();
                    }
                } else {
                    phone.setError("Phone is exist,please try another");
                    phone.requestFocus();
                }
            } else {
                username.setError("Username is exist,please try another");
                username.requestFocus();
            }
        }
    }


    public boolean validation() {
        username = findViewById(R.id.et_reg_username);
        fname = findViewById(R.id.et_reg_Fname);
        lname = findViewById(R.id.et_reg_Lname);
        password = findViewById(R.id.et_reg_password);
        dob = findViewById(R.id.et_reg_DOB);
        phone = findViewById(R.id.et_reg_phone);
        email = findViewById(R.id.et_reg_email_addresss);
        passport = findViewById(R.id.et_reg_passport);
        confirmPassword = findViewById(R.id.et_reg_conf_password);
        boolean validPassed = true;
        if (!fname.getText().toString().matches("[a-zA-z]+.{3,}$") || fname.getText().toString().isEmpty()) {
            fname.setError("Name can't be empty OR less than 4 characters");
            fname.requestFocus();
            validPassed = false;
        }
        if (!lname.getText().toString().matches("[a-zA-z]+.{3,}$") || lname.getText().toString().isEmpty()) {
            lname.setError("Name can't be empty OR less than 4 characters");
            lname.requestFocus();
            validPassed = false;
        }
        if (passport.getText().toString().isEmpty() || passport.getText().toString().length() < 6 ) {
            passport.setError("passport/IC can't be empty OR it should be more than 6 characters");
            passport.requestFocus();
            validPassed = false;
        }
        if (username.getText().toString().isEmpty()) {
            username.setError("username can't be empty");
            username.requestFocus();
            validPassed = false;
        }
        if (dateMessage.equals("")) {
            dob.setError("Date of birth can't be empty");
            dob.requestFocus();
            validPassed = false;
        }

        if (password.getText().toString().isEmpty()) {
            password.setError("password can not left empty");
            password.requestFocus();
            validPassed = false;
        }
        if (!confirmPassword.getText().toString().matches(password.getText().toString()) || confirmPassword.getText().toString().isEmpty()) {
            confirmPassword.setError("Confirm password can't be empty OR doesn't match with password");
            confirmPassword.requestFocus();
            validPassed = false;
        }
        if (!phone.getText().toString().matches("^(\\+?6?01)[0-46-9]-*[0-9]{7,8}$") || phone.getText().toString().isEmpty()) {
            phone.setError("Phone can't be empty OR need to be Malaysian Number");
            phone.requestFocus();
            validPassed = false;
        }
        if (email.getText().toString().isEmpty() || !email.getText().toString().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            email.setError("Invalid email");
            email.requestFocus();
            validPassed = false;
        }
        if (!checked) {
            displayToast("Please select the gender", 1);
            validPassed = false;
        }

        return validPassed;
    }

    public void displayToast(String message, int type) {

        if (type == 0) {
            Toast.makeText(getApplicationContext(), message,
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), message,
                    Toast.LENGTH_LONG).show();
        }
    }
    public void registerAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
        builder.setCancelable(true);
        builder.setTitle("Register Successfully");
        builder.setMessage("You will be redirected to your Login interface.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
        builder.show();
    }

    public void goLogin(View view) {
        startActivity(new Intent(Register.this, Login.class));
    }


}