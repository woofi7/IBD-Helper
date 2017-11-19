package p55.a2017.bdeb.qc.ca.ibdhelper.Pain;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import p55.a2017.bdeb.qc.ca.ibdhelper.DbHelper.DbHelper;
import p55.a2017.bdeb.qc.ca.ibdhelper.DbHelper.Pain;
import p55.a2017.bdeb.qc.ca.ibdhelper.R;
import p55.a2017.bdeb.qc.ca.ibdhelper.util.DateHelper;
import p55.a2017.bdeb.qc.ca.ibdhelper.util.EnumDayTime;
import p55.a2017.bdeb.qc.ca.ibdhelper.util.EnumPainType;
import p55.a2017.bdeb.qc.ca.ibdhelper.util.EventEmitter;
import p55.a2017.bdeb.qc.ca.ibdhelper.util.FragmentTimePicker;

public class FragmentPainCardEdit extends Fragment {
    private static final String ARG_PAIN_ID = "PAIN_ID";

    private EventEmitter onClickSave = new EventEmitter();
    private EventEmitter onClickDelete = new EventEmitter();

    private Date hoursMinutes;

    public static FragmentPainCardEdit newInstance(long painId) {
        FragmentPainCardEdit fragment = new FragmentPainCardEdit();
        Bundle bundle = new Bundle();
        bundle.putLong(ARG_PAIN_ID, painId);
        fragment.setArguments(bundle);
        return fragment;
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
        long painId = -1;
        if (getArguments() != null) {
            painId = getArguments().getLong(ARG_PAIN_ID);
        }

        final Pain painData = DbHelper.getInstance(getContext()).loadPain(painId);

        final View rootView = inflater.inflate(R.layout.fragment_activity_pain_card_edit, container, false);

        Button saveBtn = rootView.findViewById(R.id.activity_pain_btn_save);
        Button deleteBtn = rootView.findViewById(R.id.activity_pain_btn_delete);
        View timeLyt = rootView.findViewById(R.id.activity_pain_lyt_time);
        final Spinner painTypeSpr = rootView.findViewById(R.id.activity_pain_type_spr);
        final SeekBar intensitySkb = rootView.findViewById(R.id.activity_pain_intensity_skb);

        painTypeSpr.setAdapter(new PainTypeAdapter(getContext()));

        if (painData == null) {
            setTime(rootView);
        }
        else {
            Calendar calendar = Calendar.getInstance();
            calendar.set(0, 0, 0, painData.getHours(), painData.getMinutes(), 0);
            setTime(rootView, calendar.getTime());
            intensitySkb.setProgress(painData.getIntensity());
            painTypeSpr.setSelection(painData.getPainType().ordinal());
        }


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pain pain = new Pain();
                pain.setId(getArguments().getLong(ARG_PAIN_ID));
                pain.setHours(hoursMinutes.getHours());
                pain.setMinutes(hoursMinutes.getMinutes());
                pain.setIntensity(intensitySkb.getProgress());
                pain.setPainType((EnumPainType) painTypeSpr.getSelectedItem());
                pain.setLocation("");

                DbHelper.getInstance(getContext()).savePain(pain, Calendar.getInstance().getTime());
                onClickSave.next(pain.getId());
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
                FragmentTimePicker newFragment = new FragmentTimePicker();
                newFragment.setHoursMinutes(hoursMinutes);
                newFragment.setOnTimeChangeListener(new Observer() {
                    @Override
                    public void update(Observable o, Object arg) {
                        setTime(rootView, (Date) arg);
                    }
                });
                newFragment.show(getFragmentManager(),"TimePicker");
            }
        });
        return rootView;
    }

    private void setTime(View rootView) {
        setTime(rootView, Calendar.getInstance().getTime());
    }

    private void setTime(View rootView, Date date) {
        this.hoursMinutes = date;
        Pair<String, String> localeFormatedHours = DateHelper.getLocaleFormatedHours(date, getContext());

        TextView hoursTxt = rootView.findViewById(R.id.activity_pain_txt_hours);
        TextView dayTimeTxt = rootView.findViewById(R.id.activity_pain_txt_dayTime);

        hoursTxt.setText(localeFormatedHours.first);
        dayTimeTxt.setText(localeFormatedHours.second);
    }
}
