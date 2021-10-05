package com.example.groupassignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Book_Appointment extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {


    private String spinnerLabel, answer1, answer2, sname, sage, spassport, sconfpassport, sphone, sconfphone, semail, spostcode, saddress;
    private boolean i_declare = false, checked, checked2;
    private EditText fullname, age, passport, confpassport, phone, confphone, email, postcode, address;
    private CheckBox mcb1, mcb2, mcb3, mcb4, mcb5, mcb6, mcb7, mcb8, mcb9;
    private int index = 0;
    private ArrayList<String> data = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);


        setspinner();

        //    get all EditText
        fullname = findViewById(R.id.et_PersonName);
        age = findViewById(R.id.et_age);
        passport = findViewById(R.id.et_passport);
        confpassport = findViewById(R.id.et_confirm_passport);
        phone = findViewById(R.id.et_phone);
        confphone = findViewById(R.id.et_confirm_phone);
        email = findViewById(R.id.et_email);
        address = findViewById(R.id.et_address);
        postcode = findViewById(R.id.et_postcode);


        checkboxdata();
    }

    private void checkboxdata() {
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

    private void setspinner() {
        Spinner spinner = findViewById(R.id.spinner_personal_title);
        if (spinner != null)
            spinner.setOnItemSelectedListener(this);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.personal_title,
                android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner.
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerLabel = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void submit(View view) {
        if (validation() && i_declare && checked && checked2) {
            Intent intent = new Intent(this, Confirmation_Book_Appointment.class);
            if (spinnerLabel == null) {
                spinnerLabel = "Mr";
            }
            intent.putExtra("title", spinnerLabel);
            intent.putExtra("name", sname);
            intent.putExtra("age", sage);
            intent.putExtra("passport", spassport);
            intent.putExtra("phone", sphone);
            intent.putExtra("email", semail);
            intent.putExtra("address", saddress);
            intent.putExtra("postcode", spostcode);
            intent.putExtra("answer1", answer1);
            intent.putExtra("answer2", answer2);
            intent.putExtra("checkbox_selections", data.toString());
            startActivity(intent);
        }


    }

    public void onRadioButtonClicked(View view) {

        // get answer for question 1
        checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rb_qution1_yes:
                if (checked)
                    answer1 = "Yes";
                break;
            case R.id.rb_qution1_no:
                if (checked)
                    answer1 = "No";
                break;
            default:
                break;
        }

    }

    public void onRadioButtonClicked2(View view) {
        // get answer for question 2
        LinearLayout linearLayout = findViewById(R.id.expandableLayout);
        checked2 = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rb_qution2_yes:
                if (checked2)
                    answer2 = "Yes";
                linearLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_qution2_no:
                if (checked2)
                    answer2 = "No";
                linearLayout.setVisibility(View.GONE);
                break;

            default:
                break;
        }
    }


    private boolean validation() {

        // extract the entered data from the all EditTexts
        sname = fullname.getText().toString();
        sage = age.getText().toString();
        spassport = passport.getText().toString();
        sconfpassport = confpassport.getText().toString();
        sphone = phone.getText().toString();
        sconfphone = confphone.getText().toString();
        semail = email.getText().toString();
        saddress = address.getText().toString();
        spostcode = postcode.getText().toString();

        if (!sname.matches("[a-zA-z]+.{3,}$") || sname.isEmpty()) {
            fullname.setError("Name can't be empty OR less than 4 characters");
            fullname.requestFocus();
            return false;
        } else if (sage.isEmpty()) {
            age.setError("Age can't be empty");
            age.requestFocus();
            return false;
        } else if (spassport.isEmpty()) {
            passport.setError("Passport/IC can't be empty");
            passport.requestFocus();
            return false;
        } else if (!sconfpassport.matches(spassport) || sconfpassport.isEmpty()) {
            confpassport.setError("Confirm passport/IC can't be empty OR doesn't match with passport/IC");
            confpassport.requestFocus();
            return false;
        } else if (!sphone.matches("^(\\+?6?01)[0-46-9]-*[0-9]{7,8}$") || sphone.isEmpty()) {
            phone.setError("Phone can't be empty OR need to be Malaysian Number");
            phone.requestFocus();
            return false;
        } else if (sconfphone.isEmpty() || !sconfphone.equals(sphone)) {
            confphone.setError("Confirm phone can't be empty OR doesn't match with Number");
            confphone.requestFocus();
            return false;
        } else if (!semail.isEmpty() && !semail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            email.setError("Invalid email");
            email.requestFocus();
            return false;
        } else if (saddress.isEmpty()) {
            address.setError("Address can't be empty");
            address.requestFocus();
            return false;
        } else if (spostcode.isEmpty()) {
            postcode.setError("Postcode can't be empty");
            postcode.requestFocus();
            return false;
        } else if (!checked) {
            displayToast("Please select answer for Question 1 above", 1);
            return false;
        } else if (!checked2) {
            displayToast("Please select answer for Question 2 above", 1);
            return false;
        } else if (answer2.equals("Yes") && data.isEmpty()) {
            displayToast("Please select answer for Question 2.1 above", 1);
            return false;
        } else if (!i_declare) {
            displayToast("Please check the check box above", 1);
            return false;
        }

        return true;
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

    public void checkIAgreeinformation(View view) {
        i_declare = ((CheckBox) view).isChecked();
    }


}