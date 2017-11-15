package p55.a2017.bdeb.qc.ca.ibdhelper;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.Observer;

import p55.a2017.bdeb.qc.ca.ibdhelper.Pain.PainActivity;
import p55.a2017.bdeb.qc.ca.ibdhelper.util.EventEmitter;

public class Day {
    public final EnumDay enumDay;
    private TextView dayTxt;
    private TextView dateTxt;
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

    private Date date;
    private boolean disable;

    private EventEmitter onSelected = new EventEmitter();

    public Day(EnumDay enumDay, View group) {
        this.enumDay = enumDay;
        this.group = group;

        this.dayTxt = this.group.findViewById(R.id.activity_main_group_txt_day);
        this.dateTxt = this.group.findViewById(R.id.activity_main_group_txt_date);
        this.indicator = this.group.findViewById(R.id.activity_main_group_img_indicator);
        this.groupDay = this.group.findViewById(R.id.activity_main_day_group);
        this.groupInfo = this.group.findViewById(R.id.activity_main_group_info);

        this.mealTxt = this.groupDay.findViewById(R.id.activity_main_day_txt_meal);
        this.toiletTxt = this.groupDay.findViewById(R.id.activity_main_day_txt_toilet);
        this.painTxt = this.groupDay.findViewById(R.id.activity_main_day_txt_pain);
        this.mealBtn = this.groupDay.findViewById(R.id.activity_main_day_imgbtn_meal);
        this.toiletBtn = this.groupDay.findViewById(R.id.activity_main_day_imgbtn_toilet);
        this.painBtn = this.groupDay.findViewById(R.id.activity_main_day_imgbtn_pain);

        this.dayTxt.setText(enumDay.getText(this.group.getContext()));
        this.groupInfo.setContentDescription(String.valueOf(enumDay.getId()));

        //Remove the bottom separator of the last element
        if (enumDay == EnumDay.SUNDAY) {
            this.group.findViewById(R.id.activity_main_group_border_bottom).setVisibility(View.GONE);
        }

        this.isExpanded = false;

        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!disable)
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

    public void disableDay() {
        group.setBackgroundColor(Color.LTGRAY);
        this.disable = true;
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

    public TextView getDayTxt() {
        return dayTxt;
    }

    public TextView getDateTxt() {
        return dateTxt;
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
