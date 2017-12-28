package p55.a2017.bdeb.qc.ca.ibdhelper.BowelMotion;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import p55.a2017.bdeb.qc.ca.ibdhelper.DbHelper.BowelMotion;
import p55.a2017.bdeb.qc.ca.ibdhelper.DbHelper.DbHelper;
import p55.a2017.bdeb.qc.ca.ibdhelper.R;
import p55.a2017.bdeb.qc.ca.ibdhelper.util.DateHelper;
import p55.a2017.bdeb.qc.ca.ibdhelper.util.EventEmitter;
import p55.a2017.bdeb.qc.ca.ibdhelper.util.FragmentTimePicker;

public class FragmentBowelMotionCardEdit extends Fragment {
    private static final String ARG_BOWEL_MOTION_ID = "BOWEL_MOTION_ID";
    private static final String ARG_DAY_TIME = "DAY_TIME";

    private EventEmitter onClickSave = new EventEmitter();
    private EventEmitter onClickDelete = new EventEmitter();

    private Date hoursMinutes;

    public void setOnSaveClickListener(Observer e) {
        onClickSave.subscribe(e);
    }
    public void setOnDeleteClickListener(Observer e) {
        onClickDelete.subscribe(e);
    }

    public FragmentBowelMotionCardEdit() {
    }

    public static FragmentBowelMotionCardEdit newInstance(long bowelMotionId, Date dayTime) {
        FragmentBowelMotionCardEdit fragment = new FragmentBowelMotionCardEdit();
        Bundle bundle = new Bundle();
        bundle.putLong(ARG_BOWEL_MOTION_ID, bowelMotionId);
        bundle.putLong(ARG_DAY_TIME, dayTime.getTime());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        long bowelMotionId = -1;
        Date dayTime = Calendar.getInstance().getTime();
        if (getArguments() != null) {
            bowelMotionId = getArguments().getLong(ARG_BOWEL_MOTION_ID);
            dayTime = new Date(getArguments().getLong(ARG_DAY_TIME));
        }

        final View rootView = inflater.inflate(R.layout.fragment_bowel_motion_card_edit, container, false);

        Button saveBtn = rootView.findViewById(R.id.activity_bowel_motion_btn_save);
        final Date finalDayTime = dayTime;
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BowelMotion bowelMotion = new BowelMotion();
                bowelMotion.setId(getArguments().getLong(ARG_BOWEL_MOTION_ID));
                bowelMotion.setHours(hoursMinutes.getHours());
                bowelMotion.setMinutes(hoursMinutes.getMinutes());

                DbHelper.getInstance(getContext()).saveBowelMotion(bowelMotion, finalDayTime);
                onClickSave.next(bowelMotion.getId());
            }
        });

        Button deleteBtn = rootView.findViewById(R.id.activity_bowel_motion_btn_delete);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDelete.next();
            }
        });

        View timeLyt = rootView.findViewById(R.id.activity_bowel_motion_lyt_time);
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

        TextView hoursTxt = rootView.findViewById(R.id.activity_bowel_motion_txt_hours);
        TextView dayTimeTxt = rootView.findViewById(R.id.activity_bowel_motion_txt_dayTime);

        hoursTxt.setText(localeFormatedHours.first);
        dayTimeTxt.setText(localeFormatedHours.second);
    }
}
