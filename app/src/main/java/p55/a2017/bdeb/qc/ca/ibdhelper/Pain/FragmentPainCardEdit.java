package p55.a2017.bdeb.qc.ca.ibdhelper.Pain;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import p55.a2017.bdeb.qc.ca.ibdhelper.DbHelper.DbHelper;
import p55.a2017.bdeb.qc.ca.ibdhelper.DbHelper.Pain;
import p55.a2017.bdeb.qc.ca.ibdhelper.R;
import p55.a2017.bdeb.qc.ca.ibdhelper.util.DateHelper;
import p55.a2017.bdeb.qc.ca.ibdhelper.util.EnumPainType;
import p55.a2017.bdeb.qc.ca.ibdhelper.util.EventEmitter;
import p55.a2017.bdeb.qc.ca.ibdhelper.util.FragmentTimePicker;

public class FragmentPainCardEdit extends Fragment {
    private static final String ARG_PAIN_ID = "PAIN_ID";

    private EventEmitter onClickSave = new EventEmitter();
    private EventEmitter onClickDelete = new EventEmitter();
    private EventEmitter onDraw = new EventEmitter();

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

    public void setOnDrawListener(Observer e) {
        onDraw.subscribe(e);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        long painId = -1;
        if (getArguments() != null) {
            painId = getArguments().getLong(ARG_PAIN_ID);
        }

        final View rootView = inflater.inflate(R.layout.fragment_activity_pain_card_edit, container, false);


        final SeekBar intensitySkb = rootView.findViewById(R.id.activity_pain_intensity_skb);
        final Spinner painTypeSpr = rootView.findViewById(R.id.activity_pain_type_spr);
        painTypeSpr.setAdapter(new PainTypeAdapter(getContext()));

        final LocationArray locationArray;
        final Pain painData = DbHelper.getInstance(getContext()).loadPain(painId);
        if (painData == null) {
            setTime(rootView);
            locationArray = new LocationArray();
        }
        else {
            Calendar calendar = Calendar.getInstance();
            calendar.set(0, 0, 0, painData.getHours(), painData.getMinutes(), 0);
            setTime(rootView, calendar.getTime());
            intensitySkb.setProgress(painData.getIntensity());
            painTypeSpr.setSelection(painData.getPainType().ordinal());
            Gson gson = new Gson();
            if (painData.getLocation().equals("")) {
                locationArray = new LocationArray();
            }
            else {
                locationArray = gson.fromJson(painData.getLocation(), LocationArray.class);
            }
        }
        setLocationComponent(rootView, locationArray);

        Button saveBtn = rootView.findViewById(R.id.activity_pain_btn_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pain pain = new Pain();
                pain.setId(getArguments().getLong(ARG_PAIN_ID));
                pain.setHours(hoursMinutes.getHours());
                pain.setMinutes(hoursMinutes.getMinutes());
                pain.setIntensity(intensitySkb.getProgress());
                pain.setPainType((EnumPainType) painTypeSpr.getSelectedItem());
                Gson gson = new Gson();
                String json = gson.toJson(locationArray);
                pain.setLocation(json);

                DbHelper.getInstance(getContext()).savePain(pain, Calendar.getInstance().getTime());
                onClickSave.next(pain.getId());
            }
        });

        Button deleteBtn = rootView.findViewById(R.id.activity_pain_btn_delete);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDelete.next();
            }
        });

        View timeLyt = rootView.findViewById(R.id.activity_pain_lyt_time);
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

    private void setLocationComponent(View rootView, LocationArray locationArray) {
        LinearLayout board = rootView.findViewById(R.id.activity_pain_location_img_board);
        final ImageButton brushButton = rootView.findViewById(R.id.activity_pain_location_ibtn_edit);
        final ImageButton eraseButton = rootView.findViewById(R.id.activity_pain_location_ibtn_erase);
        ImageButton deleteButton = rootView.findViewById(R.id.activity_pain_location_ibtn_delete);

        final DrawingView mDrawingView = new DrawingView(getContext(), locationArray);
        mDrawingView.setOnDrawListener(new Observer() {
            @Override
            public void update(Observable observable, Object o) {
                onDraw.next(o);
            }
        });

        board.addView(mDrawingView);
        mDrawingView.update();

        brushButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrawingView.getDrawState()) {
                    brushButton.setActivated(false);
                }
                else {
                    if (mDrawingView.getEraseState()) {
                        mDrawingView.changeEraseState();
                        eraseButton.setActivated(false);
                    }
                    brushButton.setActivated(true);
                }

                mDrawingView.changeDrawState();
            }
        });
        eraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrawingView.getEraseState()) {
                    eraseButton.setActivated(false);
                }
                else {
                    if (mDrawingView.getDrawState()) {
                        mDrawingView.changeDrawState();
                        brushButton.setActivated(false);
                    }
                    eraseButton.setActivated(true);
                }

                mDrawingView.changeEraseState();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawingView.clearCanvas();
            }
        });
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
