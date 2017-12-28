package p55.a2017.bdeb.qc.ca.ibdhelper.BowelMotion;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Observer;

import p55.a2017.bdeb.qc.ca.ibdhelper.DbHelper.BowelMotion;
import p55.a2017.bdeb.qc.ca.ibdhelper.DbHelper.DbHelper;
import p55.a2017.bdeb.qc.ca.ibdhelper.DbHelper.Pain;
import p55.a2017.bdeb.qc.ca.ibdhelper.Pain.DrawingView;
import p55.a2017.bdeb.qc.ca.ibdhelper.Pain.FragmentPainCardInfo;
import p55.a2017.bdeb.qc.ca.ibdhelper.Pain.LocationArray;
import p55.a2017.bdeb.qc.ca.ibdhelper.R;
import p55.a2017.bdeb.qc.ca.ibdhelper.util.DateHelper;
import p55.a2017.bdeb.qc.ca.ibdhelper.util.EventEmitter;

public class FragmentBowelMotionCardInfo extends Fragment {
    private static final String ARG_BOWEL_MOTION_ID = "BOWEL_MOTION_ID";

    private EventEmitter onClickEdit = new EventEmitter();
    private EventEmitter onClickDelete = new EventEmitter();

    public void setOnEditClickistener(Observer e) {
        onClickEdit.subscribe(e);
    }
    public void setOnDeleteClickistener(Observer e) {
        onClickDelete.subscribe(e);
    }

    public FragmentBowelMotionCardInfo() {
    }

    public static FragmentBowelMotionCardInfo newInstance(long bowelMotionId) {
        FragmentBowelMotionCardInfo fragment = new FragmentBowelMotionCardInfo();
        Bundle bundle = new Bundle();
        bundle.putLong(ARG_BOWEL_MOTION_ID, bowelMotionId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        long bowelMotionId;
        if (getArguments() != null) {
            bowelMotionId = getArguments().getLong(ARG_BOWEL_MOTION_ID);
        } else {
            bowelMotionId = 1;
        }

        final BowelMotion bowelMotion = DbHelper.getInstance(getContext()).loadBowelMotion(bowelMotionId);
        assert bowelMotion != null;

        final View rootView = inflater.inflate(R.layout.fragment_bowel_motion_card_info, container, false);

        Button editBtn = rootView.findViewById(R.id.activity_pain_info_btn_edit);
        Button deleteBtn = rootView.findViewById(R.id.activity_pain_info_btn_delete);
        TextView hoursTxt = rootView.findViewById(R.id.activity_pain_info_txt_hours);
        TextView dayTimeTxt = rootView.findViewById(R.id.activity_pain_info_txt_dayTime);

        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, bowelMotion.getHours(), bowelMotion.getMinutes(), 0);
        Pair<String, String> localeFormatedHours = DateHelper.getLocaleFormatedHours(calendar.getTime(), getContext());

        hoursTxt.setText(localeFormatedHours.first);
        dayTimeTxt.setText(localeFormatedHours.second);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEdit.next();
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDelete.next();
            }
        });

        return rootView;
    }
}
