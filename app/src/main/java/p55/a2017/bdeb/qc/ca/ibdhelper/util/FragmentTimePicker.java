package p55.a2017.bdeb.qc.ca.ibdhelper.util;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Observer;

import p55.a2017.bdeb.qc.ca.ibdhelper.R;

public class FragmentTimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    private EventEmitter onTimeChange = new EventEmitter();
    private Date hoursMinutes;

    public void setOnTimeChangeListener(Observer e) {
        onTimeChange.subscribe(e);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(hoursMinutes);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),this, hour, minute,
                android.text.format.DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, hourOfDay, minute, 0);
        onTimeChange.next(calendar.getTime());
    }

    public void setHoursMinutes(Date hoursMinutes) {
        this.hoursMinutes = hoursMinutes;
    }
}