package com.example.groupassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

////////////////////////////////////////////////////////////////////////Users_Management
public class Users_Management extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    private String spinnerLabel, answer1, answer2 = "", answer3, answer4, answer5, answer6;
    private boolean checked, checked2, checked3, checked4, checked5, checked6;
    EditText et_username, et_fname, et_lname, et_password, et_conf_password, et_email, et_address, et_postcod, et_phone, et_passport, et_DOB, txtsearch;
    private CheckBox mcb1, mcb2, mcb3, mcb4, mcb5, mcb6, mcb7, mcb8, mcb9;
    private int index = 0;
    String dateMessage = "";
    LinearLayout linearLayout;
    String gender;
    private ArrayList<String> data = new ArrayList<>();

    //for listview
    DatabaseManager mDatabase;
    List<Users> user_list;

    //    ListView mListView;
    Users_Management_Adapter myadapter;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_management);
            try {
                checkboxdata1();
            }catch (NullPointerException e)
            {
                e.printStackTrace();
            }



        //show ListView data
        mDatabase = new DatabaseManager(this);
        user_list = new ArrayList<>();
        loadUsersFromDatabase();

        txtsearch = findViewById(R.id.et_search);
        txtsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                search();
            }
        });

        setspinner();
    }


    private void setspinner() {
        Spinner spinner = findViewById(R.id.spinner_filter_by);
        if (spinner != null)
            spinner.setOnItemSelectedListener(Users_Management.this);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.filter,
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
        Cursor cursor = null;
        user_list.clear();
        switch (spinnerLabel) {
            case "All": {
                cursor = mDatabase.getUsersInfo();
                break;
            }
            case "Registered": {
                cursor = mDatabase.databassedonfiltering("IsIntrestTakenVaccine");
                break;
            }
            case "Done quiz": {
                cursor = mDatabase.databassedonfiltering("IsDoneQuiz");
                break;
            }
            case "Female": {
                cursor = mDatabase.databassedonfiltering("Female");
                break;
            }
            case "Male": {
                cursor = mDatabase.databassedonfiltering("Male");
                break;
            }
            case "Vaccinated": {
                cursor = mDatabase.databassedonfiltering("IsTakenVaccine");
                break;
            }
            case "ByAdmin": {
                cursor = mDatabase.databassedonfiltering("IsAdmin");
            }
        }
        if (cursor.moveToFirst()) {
            do {
                user_list.add(new Users(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5),
                        cursor.getInt(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getInt(9),
                        cursor.getInt(10),
                        cursor.getString(11),
                        cursor.getString(12),
                        cursor.getString(13),
                        cursor.getString(14),
                        cursor.getInt(15),
                        cursor.getInt(16),
                        cursor.getString(17),
                        cursor.getString(18)
                ));
            } while (cursor.moveToNext());

            myadapter = new Users_Management_Adapter(Users_Management.this, R.layout.adapter_list_user_managment, user_list, mDatabase);
            ExpandableHeightListView listView = new ExpandableHeightListView(this);
            listView = findViewById(R.id.expandableHeightListView);
            listView.setAdapter(myadapter);
            listView.setExpanded(true);
            myadapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void loadUsersFromDatabase() {
        Cursor cursor = mDatabase.getUsersInfo();


        if (cursor.moveToFirst()) {
            do {
                user_list.add(new Users(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5),
                        cursor.getInt(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getInt(9),
                        cursor.getInt(10),
                        cursor.getString(11),
                        cursor.getString(12),
                        cursor.getString(13),
                        cursor.getString(14),
                        cursor.getInt(15),
                        cursor.getInt(16),
                        cursor.getString(17),
                        cursor.getString(18)
                ));
            } while (cursor.moveToNext());

            myadapter = new Users_Management_Adapter(Users_Management.this, R.layout.adapter_list_user_managment, user_list, mDatabase);
            ExpandableHeightListView listView = new ExpandableHeightListView(this);
            listView = findViewById(R.id.expandableHeightListView);
            listView.setAdapter(myadapter);
            listView.setExpanded(true);
            myadapter.notifyDataSetChanged();
        }
    }

    public void showDatePicker(View view) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setFlag(DatePickerFragment.FLAG_USER);
        newFragment.show(getSupportFragmentManager(), getString(R.string.datepicker));
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
        displayToast(data.toString(), 0);
    }

    public void onRadioButtonClickedgender(View view) {
        // get answer for question 1
        checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButton:
                if (checked)
                    gender = "Male";
                break;
            case R.id.radioButton2:
                if (checked)
                    gender = "Female";
                break;
            default:
                break;
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
            if (answer1 == "Yes") return 1;
            return 0;
        } else if (index == 2) {
            if (answer2 == "Yes") return 1;
            return 0;
        } else if (index == 3) {
            if (answer3 == "Yes") return 1;
            return 0;
        } else if (index == 4) {
            if (answer4 == "Yes") return 1;
            return 0;
        } else if (index == 5) {
            if (answer5 == "Yes") return 1;
            return 0;
        } else if (index == 6) {
            if (answer6 == "Yes") return 1;
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
        DatabaseManager myDB = new DatabaseManager(Users_Management.this);


        displayToast(data.toString(), 0);
        String comorbidites;
        if (!data.isEmpty()) {
            comorbidites = data.toString().replace("[", "").replace("]", "");
        } else {
            comorbidites = "None";
        }
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
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Users_Management.this);
        builder.setCancelable(true);
        builder.setTitle("Added new user Successfully");
        builder.setMessage("You will be redirected to your user management interface.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO check this by testing
                user_list.clear();
                loadUsersFromDatabase();
                alertDialog.dismiss();
            }
        });
        builder.show();
    }

    public void search() {

        user_list.clear();
        Cursor cursor = mDatabase.searchByName(txtsearch.getText().toString());

        if (cursor.moveToFirst()) {
            do {
                user_list.add(new Users(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5),
                        cursor.getInt(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getInt(9),
                        cursor.getInt(10),
                        cursor.getString(11),
                        cursor.getString(12),
                        cursor.getString(13),
                        cursor.getString(14),
                        cursor.getInt(15),
                        cursor.getInt(16),
                        cursor.getString(17),
                        cursor.getString(18)
                ));
            } while (cursor.moveToNext());

            myadapter = new Users_Management_Adapter(Users_Management.this, R.layout.adapter_list_user_managment, user_list, mDatabase);
            ExpandableHeightListView listView = new ExpandableHeightListView(this);
            listView = findViewById(R.id.expandableHeightListView);
            listView.setAdapter(myadapter);
            listView.setExpanded(true);
            myadapter.notifyDataSetChanged();
        }

    }
}