package p55.a2017.bdeb.qc.ca.ibdhelper.Pain;

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

import p55.a2017.bdeb.qc.ca.ibdhelper.DbHelper.DbHelper;
import p55.a2017.bdeb.qc.ca.ibdhelper.DbHelper.Pain;
import p55.a2017.bdeb.qc.ca.ibdhelper.R;
import p55.a2017.bdeb.qc.ca.ibdhelper.util.DateHelper;
import p55.a2017.bdeb.qc.ca.ibdhelper.util.EventEmitter;

public class FragmentPainCardInfo extends Fragment {
    private static final String ARG_PAIN_ID = "PAIN_ID";

    private EventEmitter onClickEdit = new EventEmitter();
    private EventEmitter onClickDelete = new EventEmitter();

    public void setOnEditClickistener(Observer e) {
        onClickEdit.subscribe(e);
    }
    public void setOnDeleteClickistener(Observer e) {
        onClickDelete.subscribe(e);
    }

    public FragmentPainCardInfo() {

    }

    public static FragmentPainCardInfo newInstance(long painId) {
        FragmentPainCardInfo fragment = new FragmentPainCardInfo();
        Bundle bundle = new Bundle();
        bundle.putLong(ARG_PAIN_ID, painId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        long painId;
        if (getArguments() != null) {
            painId = getArguments().getLong(ARG_PAIN_ID);
        } else {
            painId = 1;
        }

        final Pain painData = DbHelper.getInstance(getContext()).loadPain(painId);
        assert painData != null;

        final View rootView = inflater.inflate(R.layout.fragment_pain_card_info, container, false);

        Button editBtn = rootView.findViewById(R.id.activity_pain_info_btn_edit);
        Button deleteBtn = rootView.findViewById(R.id.activity_pain_info_btn_delete);
        TextView hoursTxt = rootView.findViewById(R.id.activity_pain_info_txt_hours);
        TextView dayTimeTxt = rootView.findViewById(R.id.activity_pain_info_txt_dayTime);
        TextView intensityTxt = rootView.findViewById(R.id.activity_pain_info_txt_intensity);
        TextView typeTxt = rootView.findViewById(R.id.activity_pain_info_txt_type);

        LocationArray locationArray = new LocationArray();
        Gson gson = new Gson();
        if (!painData.getLocation().equals("")) {
            locationArray = gson.fromJson(painData.getLocation(), LocationArray.class);
        }

        DrawingView mDrawingView = new DrawingView(getContext(), locationArray);
        LinearLayout board = rootView.findViewById(R.id.activity_pain_location_board_info);
        board.addView(mDrawingView);

        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, painData.getHours(), painData.getMinutes(), 0);
        Pair<String, String> localeFormatedHours = DateHelper.getLocaleFormatedHours(calendar.getTime(), getContext());

        hoursTxt.setText(localeFormatedHours.first);
        dayTimeTxt.setText(localeFormatedHours.second);
        intensityTxt.setText(String.valueOf(painData.getIntensity()));
        typeTxt.setText(painData.getPainType().getText(getContext()));

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
