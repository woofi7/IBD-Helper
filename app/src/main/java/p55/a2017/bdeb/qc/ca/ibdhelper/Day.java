package p55.a2017.bdeb.qc.ca.ibdhelper;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observer;

import p55.a2017.bdeb.qc.ca.ibdhelper.Pain.PainActivity;
import p55.a2017.bdeb.qc.ca.ibdhelper.util.EventEmitter;

public class Day {
    public final EnumDay enumDay;
    private ImageView indicator;
    private View group;
    private View groupDay;
    private View groupInfo;

    private boolean enabled;

    private EventEmitter onSelected = new EventEmitter();

    public Day(EnumDay enumDay, View group, final Date dayDate) {
        this.enumDay = enumDay;
        this.group = group;
        this.indicator = this.group.findViewById(R.id.activity_main_group_img_indicator);
        this.groupDay = this.group.findViewById(R.id.activity_main_day_group);
        this.groupInfo = this.group.findViewById(R.id.activity_main_group_info);

        ImageButton mealBtn = this.groupDay.findViewById(R.id.activity_main_day_imgbtn_meal);
        ImageButton toiletBtn = this.groupDay.findViewById(R.id.activity_main_day_imgbtn_toilet);
        ImageButton painBtn = this.groupDay.findViewById(R.id.activity_main_day_imgbtn_pain);

        TextView dayTxt = this.group.findViewById(R.id.activity_main_group_txt_day);
        TextView dateTxt = this.group.findViewById(R.id.activity_main_group_txt_date);

        dayTxt.setText(enumDay.getText(this.group.getContext()));
        dateTxt.setText(SimpleDateFormat.getDateInstance().format(dayDate));

        //Remove the bottom separator of the last element
        if (enumDay == EnumDay.SUNDAY) {
            this.group.findViewById(R.id.activity_main_group_border_bottom).setVisibility(View.GONE);
        }

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

        painBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(groupInfo.getContext(), PainActivity.class);
                intent.putExtra(PainActivity.EXTRA_DATE, dayDate);
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
}
