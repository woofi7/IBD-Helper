package p55.a2017.bdeb.qc.ca.ibdhelper;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import p55.a2017.bdeb.qc.ca.ibdhelper.Pain.PainActivity;

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
    private View groupInfo;
    private boolean isExpanded;

    public Day(TextView day, TextView date, TextView mealTxt, TextView painTxt, TextView toiletTxt,
               ImageView indicator, ImageButton mealBtn, ImageButton painBtn, ImageButton toiletBtn,
                View groupDay, final View groupInfo) {
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
        this.groupInfo = groupInfo;
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
                Intent intent = new Intent(groupInfo.getContext(), PainActivity.class);
                groupInfo.getContext().startActivity(intent);
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

    public void setHeight(int height) {
        ViewGroup.LayoutParams params = groupInfo.getLayoutParams();
        params.height = height;
        groupInfo.setLayoutParams(params);
    }
}
