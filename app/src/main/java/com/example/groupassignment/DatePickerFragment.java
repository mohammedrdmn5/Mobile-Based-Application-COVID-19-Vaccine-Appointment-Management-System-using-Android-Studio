package com.example.groupassignment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    public static final int FLAG_REGISTER = 0;
    public static final int FLAG_USER = 1;
    public static final int FLAG_USER_EDIT = 2;
    private int flag = 0;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker.
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        // Create a new instance of DatePickerDialog and return it.
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
        dialog.getDatePicker().setMaxDate(new Date().getTime());
        return dialog;

    }

    public void setFlag(int i) {
        flag = i;
    }

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (flag == FLAG_REGISTER) {
            // Set the activity to the Main Activity.
            Register activity = (Register) getActivity();
            // Invoke Register Activity's processDatePickerResult() method.
            activity.processDatePickerResult(year, month, dayOfMonth);
        } else if (flag == FLAG_USER) {
            // Set the activity to the Main Activity.
            Users_Management activity = (Users_Management) getActivity();
            // Invoke Register Activity's processDatePickerResult() method.
            activity.processDatePickerResult(year, month, dayOfMonth);
        }
//        else if (flag == FLAG_USER_EDIT) {
//            // Set the activity to the Main Activity.
//            Users_Management_Adapter activity = new Users_Management_Adapter(getActivity(),R.layout.edit_user_managment);
//            // Invoke Register Activity's processDatePickerResult() method.
//            activity.processDatePickerResult1(year, month, dayOfMonth);
//        }

    }


}
