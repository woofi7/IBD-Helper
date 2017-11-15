package p55.a2017.bdeb.qc.ca.ibdhelper;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observer;

import p55.a2017.bdeb.qc.ca.ibdhelper.Pain.PainActivity;
import p55.a2017.bdeb.qc.ca.ibdhelper.util.EventEmitter;

public class Day {
    public final EnumDay enumDay;
    private TextView day;
    private TextView date;
    private TextView mealTxt;
    private TextView painTxt;
    private TextView toiletTxt;
    private ImageView indicator;
    private ImageButton mealBtn;
    private ImageButton painBtn;
    private ImageButton toiletBtn;
    private View group;
    private View groupDay;
    private View groupInfo;
    private boolean isExpanded;

    private EventEmitter onSelected = new EventEmitter();

    public Day(EnumDay enumDay, TextView day, TextView date, TextView mealTxt, TextView painTxt, TextView toiletTxt,
               ImageView indicator, ImageButton mealBtn, ImageButton painBtn, ImageButton toiletBtn,
                 View group) {
        this.enumDay = enumDay;
        this.day = day;
        this.date = date;
        this.mealTxt = mealTxt;
        this.painTxt = painTxt;
        this.toiletTxt = toiletTxt;
        this.indicator = indicator;
        this.mealBtn = mealBtn;
        this.painBtn = painBtn;
        this.toiletBtn = toiletBtn;
        this.group = group;
        this.groupDay = group.findViewById(R.id.activity_main_day_group);
        this.groupInfo = group.findViewById(R.id.activity_main_group_info);

        this.isExpanded = false;

        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelected.next();
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

        painBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(groupInfo.getContext(), PainActivity.class);
                groupInfo.getContext().startActivity(intent);
            }
        });
    }

    public void setOnSelectListener(Observer e) {
        onSelected.addObserver(e);
    }

    /**
     * Afficher les éléments de la journée.
     */
    public void expand() {
        this.indicator.setBackgroundResource(R.drawable.ic_expand_less_black_24dp);
        this.groupDay.setVisibility(View.VISIBLE);
        //this.group.setBackground(null);
    }

    /**
     * Cacher les éléments de la journée.
     */
    public void collapse() {
        this.indicator.setBackgroundResource(R.drawable.ic_expand_more_black_24dp);
        this.groupDay.setVisibility(View.GONE);
        //int[] attrs = new int[] { android.R.attr.selectableItemBackground};
        //TypedArray ta = this.group.getContext().obtainStyledAttributes(attrs);
        //Drawable drawableFromTheme = ta.getDrawable(0);
        //ta.recycle();
        //this.group.setBackground(drawableFromTheme);
    }

    public TextView getDay() {
        return day;
    }

    public TextView getDate() {
        return date;
    }

    public TextView getMealTxt() {
        return mealTxt;
    }

    public TextView getPainTxt() {
        return painTxt;
    }

    public TextView getToiletTxt() {
        return toiletTxt;
    }

    public ImageButton getMealBtn() {
        return mealBtn;
    }

    public ImageButton getPainBtn() {
        return painBtn;
    }

    public ImageButton getToiletBtn() {
        return toiletBtn;
    }
}
