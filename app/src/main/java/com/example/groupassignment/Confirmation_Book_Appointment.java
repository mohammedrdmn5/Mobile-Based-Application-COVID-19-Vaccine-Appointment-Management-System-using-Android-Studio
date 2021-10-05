package com.example.groupassignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Confirmation_Book_Appointment extends AppCompatActivity {
    private TextView mtextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_book_appointment);


        Intent intent = getIntent();
        mtextView = findViewById(R.id.tv_name);
        mtextView.setText(new StringBuilder().append(intent.getStringExtra("title")).append(" ").append(intent.getStringExtra("name")).toString());

        mtextView = findViewById(R.id.tv_dob);
        mtextView.setText(intent.getStringExtra("age"));

        mtextView = findViewById(R.id.tv_passport);
        mtextView.setText(intent.getStringExtra("passport"));

        mtextView = findViewById(R.id.tv_phone);
        mtextView.setText(intent.getStringExtra("phone"));

        mtextView = findViewById(R.id.tv_email);
        mtextView.setText(intent.getStringExtra("email"));

        mtextView = findViewById(R.id.tv_address);
        mtextView.setText(intent.getStringExtra("address"));

        mtextView = findViewById(R.id.tv_postcode);
        mtextView.setText(intent.getStringExtra("postcode"));

        mtextView = findViewById(R.id.tv_Confirmation_answer1);
        mtextView.setText(intent.getStringExtra("answer1"));

        mtextView = findViewById(R.id.tv_Confirmation_answer2);
        mtextView.setText(intent.getStringExtra("answer2"));

        if(mtextView.getText().toString().equals("Yes")){
        mtextView = findViewById(R.id.tv_Confirmation_qustion2_sub);
        mtextView.setVisibility(View.VISIBLE);
        mtextView = findViewById(R.id.tv_Confirmation_sub_answer2);
        mtextView.setVisibility(View.VISIBLE);
        mtextView.setText(intent.getStringExtra("checkbox_selections").replace("[","").replace("]",""));
        }
    }

    public void update(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Confirmation_Book_Appointment.this);

        builder.setCancelable(true);
        builder.setTitle("Successful Registration");
        builder.setMessage("You will soon receive a message regarding your appointment.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent2 = new Intent(Confirmation_Book_Appointment.this, MainActivity.class);
                startActivity(intent2);
            }
        });
        builder.show();
    }
}