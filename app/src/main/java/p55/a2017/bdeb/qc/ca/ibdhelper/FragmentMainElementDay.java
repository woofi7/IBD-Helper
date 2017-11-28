package p55.a2017.bdeb.qc.ca.ibdhelper;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import p55.a2017.bdeb.qc.ca.ibdhelper.Pain.PainActivity;
import p55.a2017.bdeb.qc.ca.ibdhelper.util.EventEmitter;

public class FragmentMainElementDay extends Fragment {
    private static final String ARG_ENUM_DAY = "ENUM_DAY";
    private static final String ARG_DAY_DATE = "DAY_DATE";
    private static final String ARG_ENABLED = "ENABLED";
    private static final String ARG_EXPANDED = "EXPANDED";

    private ImageView indicator;
    private View group;
    private View groupDay;
    private View groupInfo;

    private boolean enabled = true;

    private EventEmitter onSelected = new EventEmitter();

    public FragmentMainElementDay() {
    }

    public void setOnSelectListener(Observer e) {
        onSelected.addObserver(e);
    }

    public static FragmentMainElementDay newInstance(EnumDay enumDay, long dayDate, boolean enabled, boolean expanded) {
        FragmentMainElementDay fragment = new FragmentMainElementDay();
        Bundle bundle = new Bundle();
        bundle.putLong(ARG_DAY_DATE, dayDate);
        bundle.putInt(ARG_ENUM_DAY, enumDay.ordinal());
        bundle.putBoolean(ARG_ENABLED, enabled);
        bundle.putBoolean(ARG_EXPANDED, expanded);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Date dayDate = null;
        EnumDay enumDay = null;
        boolean expanded = false;
        if (getArguments() != null) {
            dayDate = new Date(getArguments().getLong(ARG_DAY_DATE));
            enumDay = EnumDay.values()[getArguments().getInt(ARG_ENUM_DAY)];
            expanded = getArguments().getBoolean(ARG_EXPANDED);
            this.enabled = getArguments().getBoolean(ARG_ENABLED);
        }

        final View rootView = inflater.inflate(R.layout.fragment_main_element_day, container, false);

        this.group = rootView.findViewById(R.id.activity_main_group);
        this.indicator = rootView.findViewById(R.id.activity_main_group_img_indicator);
        this.groupDay = rootView.findViewById(R.id.activity_main_day_group);
        this.groupInfo = rootView.findViewById(R.id.activity_main_group_info);

        ImageButton mealBtn = this.groupDay.findViewById(R.id.activity_main_day_imgbtn_meal);
        ImageButton toiletBtn = this.groupDay.findViewById(R.id.activity_main_day_imgbtn_toilet);
        ImageButton painBtn = this.groupDay.findViewById(R.id.activity_main_day_imgbtn_pain);

        TextView dayTxt = this.group.findViewById(R.id.activity_main_group_txt_day);
        TextView dateTxt = this.group.findViewById(R.id.activity_main_group_txt_date);

        dayTxt.setText(enumDay.getText(this.group.getContext()));
        dateTxt.setText(SimpleDateFormat.getDateInstance().format(dayDate));

        if (expanded) {
            expand();
        }
        setEnable(this.enabled);
        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enabled) {
                    onSelected.next();
                }
            }
        });

        mealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Open meal activity", Toast.LENGTH_SHORT).show();
                //TODO: Make the meal module
            }
        });

        toiletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Open bowel motion activity", Toast.LENGTH_SHORT).show();
                //TODO: Make the bowel motion module
            }
        });

        final Date finalDayDate = dayDate;
        painBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(groupInfo.getContext(), PainActivity.class);
                intent.putExtra(PainActivity.EXTRA_DATE, finalDayDate);
                groupInfo.getContext().startActivity(intent);
            }
        });
        return rootView;
    }


    /**
     * Afficher les éléments de la journée.
     */
    public void expand() {
        this.indicator.setBackgroundResource(R.drawable.ic_expand_less_black_24dp);
        this.groupDay.setVisibility(View.VISIBLE);
    }

    /**
     * Cacher les éléments de la journée.
     */
    public void collapse() {
        this.indicator.setBackgroundResource(R.drawable.ic_expand_more_black_24dp);
        this.groupDay.setVisibility(View.GONE);
    }

    public void setEnable(boolean enabled) {
        if (enabled) {
            int[] attrs = new int[] { android.R.attr.selectableItemBackground};
            TypedArray ta = this.group.getContext().obtainStyledAttributes(attrs);
            Drawable drawableFromTheme = ta.getDrawable(0);
            ta.recycle();
            group.setBackground(drawableFromTheme);
        } else {
            group.setBackgroundColor(Color.LTGRAY);
        }
        this.enabled = enabled;
    }

    public EnumDay getEnumDay() {
        return EnumDay.values()[getArguments().getInt(ARG_ENUM_DAY)];
    }
}
