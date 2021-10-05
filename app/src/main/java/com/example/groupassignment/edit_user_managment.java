package com.example.groupassignment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class edit_user_managment extends AppCompatActivity {

//TODO: DELETE
    private String spinnerLabel, answer1, answer2 = "", answer3, answer4, answer5, answer6;
    private boolean checked, checked2, checked3, checked4, checked5, checked6;
    EditText et_username, et_fname, et_lname, et_password, et_conf_password, et_email, et_address, et_postcod, et_phone, et_passport, et_DOB;
    private CheckBox mcb1, mcb2, mcb3, mcb4, mcb5, mcb6, mcb7, mcb8, mcb9;
    private int index = 0;
    String dateMessage = "";
    LinearLayout linearLayout;
    String gender;
    private ArrayList<String> data = new ArrayList<>();
    DatabaseManager mDatabase;
    List<Users> user_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        try {
//            checkboxdata1();
//        } catch (RuntimeException e) {
//            e.printStackTrace();
//        }
//        Intent intent = getIntent();

    }

    public void showDatePicker(View view) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setFlag(DatePickerFragment.FLAG_USER);
        newFragment.show(getSupportFragmentManager(),getString(R.string.datepicker));
    }

    public void processDatePickerResult(int year, int month, int day) {
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        dateMessage = (day_string + "/" + month_string + "/" + year_string);
        et_DOB.setText(dateMessage);
    }
    private void checkboxdata1() {
        CheckBox[] checkBoxes = {mcb1, mcb2, mcb3, mcb4, mcb5, mcb6, mcb7, mcb8, mcb9};
        int id[] = {R.id.cb1, R.id.cb2, R.id.cb3, R.id.cb4, R.id.cb5, R.id.cb6, R.id.cb7, R.id.cb8, R.id.cb9};

        for (CheckBox checkBox : checkBoxes) {
            checkBox = findViewById(id[index]);
            CheckBox finalCheckBox = checkBox;
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalCheckBox.isChecked()) {
                        data.add(finalCheckBox.getText().toString());
                    } else {
                        data.remove(finalCheckBox.getText().toString());
                    }
                }
            });
            index++;
        }
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

    public void onRadioButtonClicked2(View view) {
        // get answer for question 2

        checked2 = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rb_qution2_yes:
                if (checked2) {
                    answer2 = "Yes";
                    linearLayout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rb_qution2_no:
                if (checked2) {
                    answer2 = "No";
                    linearLayout.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
    }

    public void onRadioButtonClicked3(View view) {
        // get answer for question 3
        checked3 = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rb_qution3_yes:
                if (checked3)
                    answer3 = "Yes";
                break;
            case R.id.rb_qution3_no:
                if (checked3)
                    answer3 = "No";
                break;
            default:
                break;
        }
    }

    public void onRadioButtonClicked4(View view) {
        // get answer for question 4
        checked4 = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rb_qution4_yes:
                if (checked4)
                    answer4 = "Yes";
                break;
            case R.id.rb_qution4_no:
                if (checked4)
                    answer4 = "No";
                break;
            default:
                break;
        }
    }

    public void onRadioButtonClicked5(View view) {
        // get answer for question 5
        checked5 = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rb_qution5_yes:
                if (checked5)
                    answer5 = "Yes";
                break;
            case R.id.rb_qution5_no:
                if (checked5)
                    answer5 = "No";
                break;
            default:
                break;
        }
    }

    public void onRadioButtonClicked6(View view) {
        // get answer for question 6
        checked6 = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rb_qution6_yes:
                if (checked6)
                    answer6 = "Yes";
                break;
            case R.id.rb_qution6_no:
                if (checked6)
                    answer6 = "No";
                break;
            default:
                break;
        }
    }

    public int CheckInteger(int index) {
        //to convert all checkbox from Yes to 1 and No to 0
        if (index == 1) {
            if (checked) return 1;
            return 0;
        } else if (index == 2) {
            if (checked2) return 1;
            return 0;
        } else if (index == 3) {
            if (checked3) return 1;
            return 0;
        } else if (index == 4) {
            if (checked4) return 1;
            return 0;
        } else if (index == 5) {
            if (checked5) return 1;
            return 0;
        } else if (index == 6) {
            if (checked6) return 1;
            return 0;
        }
        return 0;
    }

    AlertDialog alertDialog;
    public void float_btn_add_new_user(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        view = inflater.inflate(R.layout.add_user_managment, null);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();

        //from add_user_managment.xml
        et_username = view.findViewById(R.id.et_mang_username);
        et_fname = view.findViewById(R.id.et_mang_Fname);
        et_lname = view.findViewById(R.id.et_mang_Lname);
        et_password = view.findViewById(R.id.et_mang_password);
        et_conf_password = view.findViewById(R.id.et_mang_conf_password);
        et_email = view.findViewById(R.id.et_mang_email_addresss);
        et_address = view.findViewById(R.id.et_mang_addresss);
        et_postcod = view.findViewById(R.id.et_mang_postcode);
        et_phone = view.findViewById(R.id.et_mang_phone);
        et_passport = view.findViewById(R.id.et_mang_passport);
        et_DOB = view.findViewById(R.id.et_mang_DOB);
        linearLayout = view.findViewById(R.id.mang_expandableLayout);
        view.findViewById(R.id.btn_add_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("error444", "somthing wrong");
                try {
                    add_new_user();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        });
        view.findViewById(R.id.btn_cancel_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("error444", "somthing wrong");
                alertDialog.dismiss();
            }
        });

    }// Add user


    public void displayToast(String message, int type) {
        if (type == 0) {
            Toast.makeText(getApplicationContext(), message,
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), message,
                    Toast.LENGTH_LONG).show();
        }
    }

    public boolean validation() {
        boolean validPassed = true;
        if (!et_fname.getText().toString().matches("[a-zA-z]+.{3,}$") || et_fname.getText().toString().isEmpty()) {
            et_fname.setError("Name can't be empty OR less than 4 characters");
            et_fname.requestFocus();
            validPassed = false;
        }
        if (!et_lname.getText().toString().matches("[a-zA-z]+.{3,}$") || et_lname.getText().toString().isEmpty()) {
            et_lname.setError("Name can't be empty OR less than 4 characters");
            et_lname.requestFocus();
            validPassed = false;
        }
        if (et_passport.getText().toString().equals("") || et_passport.getText().toString().length() < 6) {
            et_passport.setError("passport/IC can't be empty OR it should be more than 6 characters");
            et_passport.requestFocus();
            validPassed = false;
        }
        if (et_username.getText().toString().isEmpty()) {
            et_username.setError("username can't be empty");
            et_username.requestFocus();
            validPassed = false;
        }
        if (dateMessage.equals("")) {
            et_DOB.setError("Date of birth can't be empty");
            et_DOB.requestFocus();
            validPassed = false;
        }
        if (et_address.getText().toString().isEmpty()) {
            et_address.setError("Address can't be empty");
            et_address.requestFocus();
            validPassed = false;
        }
        if (et_postcod.getText().toString().isEmpty()) {
            et_postcod.setError("Postcode can't be empty");
            et_postcod.requestFocus();
            validPassed = false;
        }
        if (et_password.getText().toString().isEmpty()) {
            et_password.setError("password can not left empty");
            et_password.requestFocus();
            validPassed = false;
        }
        if (!et_conf_password.getText().toString().matches(et_conf_password.getText().toString()) || et_conf_password.getText().toString().isEmpty()) {
            et_conf_password.setError("Confirm password can't be empty OR doesn't match with password");
            et_conf_password.requestFocus();
            validPassed = false;
        }
        if (!et_phone.getText().toString().matches("^(\\+?6?01)[0-46-9]-*[0-9]{7,8}$") || et_phone.getText().toString().isEmpty()) {
            et_phone.setError("Phone can't be empty OR need to be Malaysian Number");
            et_phone.requestFocus();
            validPassed = false;
        }
        if (et_email.getText().toString().isEmpty() || !et_email.getText().toString().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            et_email.setError("Invalid email");
            et_email.requestFocus();
            validPassed = false;
        }
        if (!checked) {
            displayToast("Please select answer for Question 1 above", 1);
            validPassed = false;
        }
        if (!checked2) {
            displayToast("Please select answer for Question 2 above", 1);
            validPassed = false;
        }
        if (data.isEmpty() && answer2.equals("Yes")) {
            displayToast("Please select answer for Question 2.1 above", 1);
            validPassed = false;
        }
        if (!checked3) {
            displayToast("Please select answer for Question 3 above", 1);
            validPassed = false;
        }
        if (!checked4) {
            displayToast("Please select answer for Question 4 above", 1);
            validPassed = false;
        }
        if (!checked5) {
            displayToast("Please select answer for Question 5 above", 1);
            validPassed = false;
        }
        if (!checked6) {
            displayToast("Please select answer for Question 6 above", 1);
            validPassed = false;
        }
        return validPassed;
    }

    public void add_new_user() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        DatabaseManager myDB = new DatabaseManager(edit_user_managment.this);
        String comorbidites = data.toString().replace("[", "").replace("]", "");
        if (validation() == true) // check if the validation passed
        {
            if (myDB.checkUserExist("username", et_username.getText().toString()) == false) // validation to confirm user not exist
            {
                if (myDB.checkUserExist("phone", et_phone.getText().toString()) == false) {
                    if (myDB.checkUserExist("email", et_email.getText().toString()) == false) {
                        if (myDB.checkUserExist("passport", et_passport.getText().toString()) == false) {
                            // start adding the user after confirm the user is not exist in the system by checking the email and phone and passport and username;
                            if (myDB.addNewUserByAdmin(et_fname.getText().toString(), et_lname.getText().toString(), et_username.getText().toString(), et_password.getText().toString(), dateMessage, et_phone.getText().toString(), et_email.getText().toString(), gender, 0, CheckInteger(5), CheckInteger(3), CheckInteger(6), Integer.parseInt(et_postcod.getText().toString()), CheckInteger(1), et_address.getText().toString(), et_passport.getText().toString(), comorbidites) == true) {
                                registerAlertDialog();// show message login successfully and send to another login page
                            }
                        } else {
                            et_passport.setError("Passport is exist,please try another");
                            et_passport.requestFocus();
                        }
                    } else {
                        et_email.setError("Email is exist,please try another");
                        et_email.requestFocus();
                    }
                } else {
                    et_phone.setError("Phone is exist,please try another");
                    et_phone.requestFocus();
                }
            } else {
                et_username.setError("Username is exist,please try another");
                et_username.requestFocus();
            }
        }
    }

    public void registerAlertDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(edit_user_managment.this);
        builder.setCancelable(true);
        builder.setTitle("Edit new user Successfully");
        builder.setMessage("You will be redirected to your user management interface.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        builder.show();
    }
}
