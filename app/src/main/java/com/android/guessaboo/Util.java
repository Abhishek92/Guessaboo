package com.android.guessaboo;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by hp pc on 28-09-2015.
 */
public class Util {

public static void showTimePicker(Context context,final TextView time){
    Calendar mcurrentTime = Calendar.getInstance();
    int hour = 0;
    int minute = 0;
    TimePickerDialog mTimePicker;
    mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
            time.setText( selectedHour + ":" + selectedMinute);
        }
    }, hour, minute, true);//Yes 24 hour time
    mTimePicker.setTitle("Set Time");
    mTimePicker.show();

}
}
