package p55.a2017.bdeb.qc.ca.ibdhelper.Pain;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.app.Dialog;
import java.util.Calendar;
import android.widget.TimePicker;

import p55.a2017.bdeb.qc.ca.ibdhelper.R;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        TextView hoursTxt = getActivity().findViewById(R.id.activity_pain_txt_hours);
        hoursTxt.setText(String.valueOf(hourOfDay) + "h" + String.valueOf(minute));
    }
}