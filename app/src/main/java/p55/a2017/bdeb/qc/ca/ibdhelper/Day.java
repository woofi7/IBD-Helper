package p55.a2017.bdeb.qc.ca.ibdhelper;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Day {
    private TextView day;
    private TextView date;
    private TextView mealTxt;
    private TextView painTxt;
    private TextView toiletTxt;
    private ImageView indicator;
    private ImageButton mealBtn;
    private ImageButton painBtn;
    private ImageButton toiletBtn;
    private View groupDay;
    private View group;
    private boolean isExpanded;

    public Day(TextView day, TextView date, TextView mealTxt, TextView painTxt, TextView toiletTxt,
               ImageView indicator, ImageButton mealBtn, ImageButton painBtn, ImageButton toiletBtn,
               View groupDay, View group, View groupInfo) {
        this.day = day;
        this.date = date;
        this.mealTxt = mealTxt;
        this.painTxt = painTxt;
        this.toiletTxt = toiletTxt;
        this.indicator = indicator;
        this.mealBtn = mealBtn;
        this.painBtn = painBtn;
        this.toiletBtn = toiletBtn;
        this.groupDay = groupDay;
        this.group = group;
        this.isExpanded = false;

        groupInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand();
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
                Toast.makeText(v.getContext(), "Open pain activity", Toast.LENGTH_SHORT).show();
                //TODO: Make the pain module
            }
        });
    }

    /**
     * Afficher les éléments de la journée.
     */
    private void expand() {
        if (isExpanded) {
            collapse();
        }
        else {
            this.indicator.setBackgroundResource(R.drawable.ic_expand_less_black_48dp);
            this.groupDay.setVisibility(View.VISIBLE);
            isExpanded = true;
        }
    }

    /**
     * Cacher les éléments de la journée.
     */
    private void collapse() {
        this.indicator.setBackgroundResource(R.drawable.ic_expand_more_black_48dp);
        this.groupDay.setVisibility(View.GONE);
        isExpanded = false;
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

    public View getGroup() {
        return group;
    }

    public void setHeight(int height) {
        ViewGroup.LayoutParams params = group.getLayoutParams();
        params.height = height;
        group.setLayoutParams(params);
    }
}
