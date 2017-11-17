package p55.a2017.bdeb.qc.ca.ibdhelper.Pain;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Observer;

import p55.a2017.bdeb.qc.ca.ibdhelper.R;
import p55.a2017.bdeb.qc.ca.ibdhelper.util.EnumDayTime;
import p55.a2017.bdeb.qc.ca.ibdhelper.util.EventEmitter;
import p55.a2017.bdeb.qc.ca.ibdhelper.util.FragmentTimePicker;

public class FragmentPainCardEdit extends Fragment {
    private EventEmitter onClickSave = new EventEmitter();
    private EventEmitter onClickDelete = new EventEmitter();

    public static FragmentPainCardEdit newInstance(Pain painData) {
        return new FragmentPainCardEdit();
    }

    public FragmentPainCardEdit() {
    }

    public void setOnSaveClickListener(Observer e) {
        onClickSave.subscribe(e);
    }

    public void setOnDeleteClickListener(Observer e) {
        onClickDelete.subscribe(e);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_activity_pain_card_edit, container, false);

        Button saveBtn = rootView.findViewById(R.id.activity_pain_btn_save);
        Button deleteBtn = rootView.findViewById(R.id.activity_pain_btn_delete);
        View timeLyt = rootView.findViewById(R.id.activity_pain_lyt_time);
        Spinner painTypeSpr = rootView.findViewById(R.id.activity_pain_type_spr);
        painTypeSpr.setAdapter(new PainTypeAdapter(getContext()));

        setTime(rootView);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSave.next();
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDelete.next();
            }
        });
        timeLyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new FragmentTimePicker();
                newFragment.show(getFragmentManager(),"TimePicker");
            }
        });
        return rootView;
    }

    private void setTime(View rootView) {
        DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
        Calendar calendar = Calendar.getInstance();

        TextView hoursTxt = rootView.findViewById(R.id.activity_pain_txt_hours);
        TextView dayTimeTxt = rootView.findViewById(R.id.activity_pain_txt_dayTime);

        hoursTxt.setText(dateFormat.format(calendar.getTime()));
        for (EnumDayTime enumDayTime : EnumDayTime.values()) {
            if (enumDayTime.getStartHour() <= calendar.get(Calendar.HOUR_OF_DAY)) {
                dayTimeTxt.setText(enumDayTime.getText(getContext()));
            }
            else if (enumDayTime.getStartHour() > calendar.get(Calendar.HOUR_OF_DAY)) {
                break;
            }
        }

        if (calendar.get(Calendar.HOUR_OF_DAY) < 6) {
            dayTimeTxt.setText(EnumDayTime.MIDNIGHT.getText(getContext()));
        }
    }
}
