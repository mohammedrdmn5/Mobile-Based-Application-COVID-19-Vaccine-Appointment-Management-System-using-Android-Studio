package com.example.groupassignment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.maps.SupportMapFragment;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Users_Management_Adapter extends ArrayAdapter<Users> {
    // edit dialog
    private String edit_answer1, edit_answer2 = "", edit_answer3, edit_answer4, edit_answer5, edit_answer6;
    private boolean edit_checked, edit_checked2, edit_checked3, edit_checked4, edit_checked5, edit_checked6;
    EditText edit_et_username, edit_et_fname, edit_et_lname, edit_et_password, edit_et_conf_password, edit_et_email, edit_et_address, edit_et_postcod, edit_et_phone, edit_et_passport, edit_et_DOB;
    private CheckBox mcb1, mcb2, mcb3, mcb4, mcb5, mcb6, mcb7, mcb8, mcb9;
    private int index = 0;
    String dateMessage = "";
    LinearLayout llout;
    String gender;
    private FragmentActivity myContext;


    //adapter stuff
    Context mCtx;
    int layoutRes;
    List<Users> user_list;
    DatabaseManager mDatabase;
    int myposition;

    public Users_Management_Adapter(Context mCtx, int layoutRes, List<Users> user_list, DatabaseManager mDatabase) {
        super(mCtx, layoutRes, user_list);
        this.mCtx = mCtx;
        this.layoutRes = layoutRes;
        this.user_list = user_list;
        this.mDatabase = mDatabase;
    }

    public Users_Management_Adapter(Context mCtx, int layoutRes) {
        super(mCtx, layoutRes);
        this.mCtx = mCtx;
        this.layoutRes = layoutRes;

    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(layoutRes, null);
        // from list_layout_user
        TextView tv_name = view.findViewById(R.id.tv_mang_user_name);
        TextView tv_gender = view.findViewById(R.id.tv_mang_user_gender);
        TextView tv_email = view.findViewById(R.id.tv_mang_user_email);
        TextView tv_phone = view.findViewById(R.id.tv_mang_user_phone);
        TextView tv_registered = view.findViewById(R.id.tv_mang_user_Registered);

        final Users users = user_list.get(position);
        this.myposition = position;
        tv_name.setText(users.getUser_Fname() + " " + users.getUser_Lname());
        tv_gender.setText(users.getUser_Gender());
        tv_email.setText((users.getUser_Email()));
        tv_phone.setText(users.getUser_Phone());

        tv_registered.setText(String.valueOf(users.getUser_IsIntrestTakenVaccine()));
        Log.e("error333", "somthing wrong");
        view.findViewById(R.id.tv_mang_user_delet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_user(users.User_id, users.User_Fname);
            }
        });
        view.findViewById(R.id.tv_mang_user_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                LayoutInflater inflater = LayoutInflater.from(mCtx);
                 view = inflater.inflate(R.layout.view_user_managment, null);
                builder.setView(view);

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                //get all textviews
                TextView textView1 =  view.findViewById(R.id.tv_view_username);
                TextView textView2 = view.findViewById(R.id.tv_view_fname);
                TextView textView3 = view.findViewById(R.id.tv_view_lname);
                TextView textView4 = view.findViewById(R.id.tv_view_password);
                TextView textView5 = view.findViewById(R.id.tv_view_email);
                TextView textView6 = view.findViewById(R.id.tv_view_address);
                TextView textView7 = view.findViewById(R.id.tv_view_postcode);
                TextView textView8 = view.findViewById(R.id.tv_view_phone);
                TextView textView9 = view.findViewById(R.id.tv_view_passport);
                TextView textView10 = view.findViewById(R.id.tv_view_dob);
                TextView textView11 = view.findViewById(R.id.tv_view_gender);
                TextView textView12 = view.findViewById(R.id.tv_view_Confirmation_answer1);
                TextView textView13 = view.findViewById(R.id.tv_view_Confirmation_answer2);
                TextView textView19 = view.findViewById(R.id.tv_view_Confirmation_qustion2_sub);
                TextView textView18 = view.findViewById(R.id.tv_view_Confirmation_sub_answer2);
                TextView textView14 = view.findViewById(R.id.tv_view_Confirmation_answer3);
                TextView textView15 = view.findViewById(R.id.tv_view_Confirmation_answer4);
                TextView textView16 = view.findViewById(R.id.tv_view_Confirmation_answer5);
                TextView textView17 = view.findViewById(R.id.tv_view_Confirmation_answer6);


                textView1.setText(users.getUser_Username());
                textView2.setText(users.getUser_Fname());
                textView3.setText(users.getUser_Lname());
                textView4.setText(users.getUser_Password());
                textView5.setText(users.getUser_Email());
                textView6.setText(users.getUser_Address());
                textView7.setText(String.valueOf(users.getUser_PostCode()));
                textView8.setText(users.getUser_Phone());
                textView9.setText(users.getUser_Passport());
                textView10.setText(users.getUser_dob());
                textView11.setText(users.getUser_Gender());
                if (users.getUser_IsIntrestTakenVaccine() == 1) textView12.setText("YES");
                else textView12.setText("NO");
                if (users.getComorbidites().equals("None")) textView13.setText("NO");
                else textView13.setText("Yes");
                if (textView13.getText().toString().equals("Yes")) {
                    textView18.setVisibility(View.VISIBLE);
                    textView19.setVisibility(View.VISIBLE);
                } else {
                    textView18.setVisibility(View.GONE);
                    textView19.setVisibility(View.GONE);
                }
                if (users.getUser_IsHascovid() == 1) textView14.setText("YES");
                else textView14.setText("NO");
                if (users.getUser_IsIntrestTakenVaccine() == 1) textView15.setText("YES");
                else textView15.setText("NO");
                if (users.getUser_IsTakenVaccine() == 1) textView16.setText("YES");
                else textView16.setText("NO");
                if (users.getUser_IsDoneQuiz() == 1) textView17.setText("YES");
                else textView17.setText("NO");

                        view.findViewById(R.id.btn_go_back).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.e("error444", "somthing wrong");
                                alertDialog.dismiss();

                            }
                        });

            }
        });
        view.findViewById(R.id.tv_mang_user_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("error444", "somthing wrong");
                display_edit_new_user(users);

//                Intent intent = new Intent(mCtx,edit_user_managment.class);
//                intent.putExtra("position",myposition);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                mCtx.startActivity(intent);
            }
        });
        return view;
    }//getView

//    private void viewuserdata(Users users) {
//
//
//
//    }

    private void delete_user(int myposition, String name) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(mCtx);

        builder.setCancelable(true);
        builder.setTitle("Are you sure want to delete");
        builder.setMessage("By pressing on \"OK\" you will delete username: " + name);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDatabase.deleteUser(myposition);
                // TODO update listview after delete
                loadEmployeesFromDatabaseAgain();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

    }


    private void display_edit_new_user(final Users user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.edit_user_managment, null);
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        //from edit_user_managment.xml
        edit_et_username = view.findViewById(R.id.et_edit_username);
        edit_et_fname = view.findViewById(R.id.et_edit_Fname);
        edit_et_lname = view.findViewById(R.id.et_edit_Lname);
        edit_et_password = view.findViewById(R.id.et_edit_password);
        edit_et_conf_password = view.findViewById(R.id.et_edit_conf_password);
        edit_et_email = view.findViewById(R.id.et_edit_email_addresss);
        edit_et_address = view.findViewById(R.id.et_edit_addresss);
        edit_et_postcod = view.findViewById(R.id.et_edit_postcode);
        edit_et_DOB = view.findViewById(R.id.et_edit_DOB);
        edit_et_passport = view.findViewById(R.id.et_edit_passport);
        edit_et_phone = view.findViewById(R.id.et_edit_phone);
        llout = view.findViewById(R.id.edit_expandableLayout);

        RadioButton rbtn_male = view.findViewById(R.id.radioButton);
        RadioButton rbtn_female = view.findViewById(R.id.radioButton2);
        RadioButton rbtn_isInterest0 = view.findViewById(R.id.rb_qution1_no);
        RadioButton rbtn_isInterest1 = view.findViewById(R.id.rb_qution1_yes);
        RadioButton rbtn_iscomor0 = view.findViewById(R.id.rb_qution2_no);
        RadioButton rbtn_iscomor1 = view.findViewById(R.id.rb_qution2_yes);
        RadioButton rbtn_hascovid0 = view.findViewById(R.id.rb_qution3_no);
        RadioButton rbtn_hascovid1 = view.findViewById(R.id.rb_qution3_yes);
        RadioButton rbtn_hasregistered0 = view.findViewById(R.id.rb_qution4_no);
        RadioButton rbtn_hasregistered1 = view.findViewById(R.id.rb_qution4_yes);
        RadioButton rbtn_hasTvaccine0 = view.findViewById(R.id.rb_qution5_no);
        RadioButton rbtn_hasTvaccine1 = view.findViewById(R.id.rb_qution5_yes);
        RadioButton rbtn_donequiz0 = view.findViewById(R.id.rb_qution6_no);
        RadioButton rbtn_donequiz1 = view.findViewById(R.id.rb_qution6_yes);

        // set data to edittext
        edit_et_username.setText(user.getUser_Username());
        edit_et_fname.setText(user.getUser_Fname());
        edit_et_lname.setText(user.getUser_Lname());
        edit_et_password.setText(user.getUser_Password());
        edit_et_email.setText(user.getUser_Email());
        edit_et_address.setText(user.getUser_Address());
        edit_et_postcod.setText(String.valueOf(user.getUser_PostCode()));
        edit_et_DOB.setText(user.getUser_dob());
        edit_et_passport.setText(user.getUser_Passport());
        edit_et_phone.setText(user.getUser_Phone());


        switch (user.getUser_Gender()) {
            case "Male":
                rbtn_male.setChecked(true);
                break;
            case "Female":
                rbtn_female.setChecked(true);
                break;
            default:
                rbtn_male.setChecked(true);
                break;
        }


        switch (user.getUser_IsIntrestTakenVaccine()) {
            case 0:
                rbtn_isInterest0.setChecked(true);
                break;
            case 1:
                rbtn_isInterest1.setChecked(true);
                break;
            default:
                rbtn_isInterest0.setChecked(true);
                break;
        }
        switch (user.getUser_IsHascovid()) {
            case 0:
                rbtn_hascovid0.setChecked(true);
                break;
            case 1:
                rbtn_hascovid1.setChecked(true);
                break;
            default:
                rbtn_hascovid0.setChecked(true);
                break;
        }

        switch (user.getUser_IsIntrestTakenVaccine()) {
            case 0:
                rbtn_hasregistered0.setChecked(true);
                break;
            case 1:
                rbtn_hasregistered1.setChecked(true);
                break;
            default:
                rbtn_hasregistered0.setChecked(true);
                break;
        }
        switch (user.getUser_IsTakenVaccine()) {
            case 0:
                rbtn_hasTvaccine0.setChecked(true);
                break;
            case 1:
                rbtn_hasTvaccine1.setChecked(true);
                break;
            default:
                rbtn_hasTvaccine0.setChecked(true);
                break;
        }
        switch (user.getUser_IsDoneQuiz()) {
            case 0:
                rbtn_donequiz0.setChecked(true);
                break;
            case 1:
                rbtn_donequiz1.setChecked(true);
                break;
            default:
                rbtn_donequiz0.setChecked(true);
                break;
        }


        view.findViewById(R.id.btn_edit_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO update to database ⚒⚒⚒⚒
            if (mDatabase.updateUserInfo(String.valueOf(user.getUser_id()), edit_et_fname.getText().toString(), edit_et_lname.getText().toString(), edit_et_username.getText().toString(), edit_et_password.getText().toString(), String.valueOf(CheckInteger(5)), String.valueOf(CheckInteger(3)), edit_et_address.getText().toString(), dateMessage, String.valueOf(CheckInteger(6)), edit_et_phone.getText().toString(), edit_et_email.getText().toString(), gender, edit_et_postcod.getText().toString(), String.valueOf(CheckInteger(1)), edit_et_passport.getText().toString()) == true) {

                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(mCtx);
                builder.setCancelable(true);
                builder.setTitle("Upadte user Successfully");
                builder.setMessage("You will be redirected to your user management interface.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        user_list.clear();
                        loadEmployeesFromDatabaseAgain();
                        alertDialog.dismiss();
                    }
                });
                builder.show();
            }

            }
        });

        // get all radio buttons
        view.findViewById(R.id.radioButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "Male";
            }
        });
        view.findViewById(R.id.radioButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "Female";
            }
        });
        view.findViewById(R.id.rb_qution2_edit_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_answer2 = "Yes";
                llout.setVisibility(View.VISIBLE);
            }
        });
        view.findViewById(R.id.rb_qution2_edit_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_answer2 = "No";
                llout.setVisibility(View.GONE);
            }
        });
        view.findViewById(R.id.rb_qution3_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_answer3 = "Yes";
            }
        });
        view.findViewById(R.id.rb_qution3_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_answer3 = "No";
            }
        });

        view.findViewById(R.id.rb_qution4_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_answer4 = "Yes";
            }
        });
        view.findViewById(R.id.rb_qution4_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_answer4 = "No";
            }
        });

        view.findViewById(R.id.rb_qution5_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_answer5 = "Yes";
            }
        });
        view.findViewById(R.id.rb_qution5_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_answer5 = "No";
            }
        });
        view.findViewById(R.id.rb_qution6_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_answer6 = "Yes";
            }
        });
        view.findViewById(R.id.rb_qution6_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_answer6 = "No";
            }
        });


        view.findViewById(R.id.btn_cancel_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


    }// Edit user


    public int CheckInteger(int index) {
        //to convert all checkbox from Yes to 1 and No to 0
        if (index == 1) {
            if (edit_answer1 == "yes") return 1;
            return 0;
        } else if (index == 2) {
            if (edit_answer2 == "yes") return 1;
            return 0;
        } else if (index == 3) {
            if (edit_answer3 == "yes") return 1;
            return 0;
        } else if (index == 4) {
            if (edit_answer4 == "yes") return 1;
            return 0;
        } else if (index == 5) {
            if (edit_answer5 == "yes") return 1;
            return 0;
        } else if (index == 6) {
            if (edit_answer6 == "yes") return 1;
            return 0;
        }
        return 0;
    }


    public void displayToast(String message, int type) {
        if (type == 0) {
            Toast.makeText(mCtx.getApplicationContext(), message,
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mCtx.getApplicationContext(), message,
                    Toast.LENGTH_LONG).show();
        }
    }


    public void loadEmployeesFromDatabaseAgain() {
        Cursor cursor = mDatabase.getUsersInfo();

        user_list.clear();
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
        }
        notifyDataSetChanged();
    }

    public void registerAlertDialog() {

    }
}
