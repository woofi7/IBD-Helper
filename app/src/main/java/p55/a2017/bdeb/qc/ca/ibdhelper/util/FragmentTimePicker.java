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

import p55.a2017.bdeb.qc.ca.ibdhelper.R;

public class FragmentTimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),this, hour, minute,
                android.text.format.DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, hourOfDay, minute, 0);

        TextView hoursTxt = getActivity().findViewById(R.id.activity_pain_txt_hours);
        TextView dayTimeTxt = getActivity().findViewById(R.id.activity_pain_txt_dayTime);

        hoursTxt.setText(dateFormat.format(calendar.getTime()));
        for (EnumDayTime enumDayTime : EnumDayTime.values()) {
            if (enumDayTime.getStartHour() <= hourOfDay) {
                dayTimeTxt.setText(enumDayTime.getText(getContext()));
            }
            else if (enumDayTime.getStartHour() > hourOfDay) {
                break;
            }
        }

        if (hourOfDay < 6) {
            dayTimeTxt.setText(EnumDayTime.MIDNIGHT.getText(getContext()));
        }
    }
}