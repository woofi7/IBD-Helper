package p55.a2017.bdeb.qc.ca.ibdhelper;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Day week[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseWeek();
    }

    /**
     * Initialiser l'ensemble des journées en leur assignant leurs éléments d'interface.
     */
    private void initialiseWeek() {
        week = new Day[EnumDay.values().length];

        View group;
        View groupDay;
        View groupInfo;
        TextView day;
        TextView date;
        TextView mealTxt;
        TextView toiletTxt;
        TextView painTxt;
        ImageView indicator;
        ImageButton mealBtn;
        ImageButton toiletBtn;
        ImageButton painBtn;

        for (EnumDay enumDay : EnumDay.values()) {
            switch (enumDay) {
                case MONDAY:
                    group = findViewById(R.id.activity_main_group_monday);
                    break;
                case TUESDAY:
                    group = findViewById(R.id.activity_main_group_tuesday);
                    break;
                case WEDNESDAY:
                    group = findViewById(R.id.activity_main_group_wednesday);
                    break;
                case THURSDAY:
                    group = findViewById(R.id.activity_main_group_thursday);
                    break;
                case FRIDAY:
                    group = findViewById(R.id.activity_main_group_friday);
                    break;
                case SATURDAY:
                    group = findViewById(R.id.activity_main_group_saturday);
                    break;
                case SUNDAY:
                    group = findViewById(R.id.activity_main_group_sunday);

                    break;
                default:
                    return;
            }

            day = group.findViewById(R.id.activity_main_group_txt_day);
            date = group.findViewById(R.id.activity_main_group_txt_date);
            indicator = group.findViewById(R.id.activity_main_group_img_indicator);
            groupDay = group.findViewById(R.id.activity_main_day_group);
            groupInfo = group.findViewById(R.id.activity_main_group_info);

            mealTxt = groupDay.findViewById(R.id.activity_main_day_txt_meal);
            toiletTxt = groupDay.findViewById(R.id.activity_main_day_txt_toilet);
            painTxt = groupDay.findViewById(R.id.activity_main_day_txt_pain);
            mealBtn = groupDay.findViewById(R.id.activity_main_day_imgbtn_meal);
            toiletBtn = groupDay.findViewById(R.id.activity_main_day_imgbtn_toilet);
            painBtn = groupDay.findViewById(R.id.activity_main_day_imgbtn_pain);

            day.setText(enumDay.getText(this));
            groupInfo.setContentDescription(String.valueOf(enumDay.getId()));

            //Remove the bottom separator of the last element
            if (enumDay == EnumDay.SUNDAY) {
                //group.findViewById(R.id.activity_main_group_border_bottom).setVisibility(View.GONE);
            }

            week[enumDay.getId()] = new Day(day, date, mealTxt, painTxt, toiletTxt, indicator,
                    mealBtn, painBtn, toiletBtn, groupDay, groupInfo);
        }

        updateLayoutHeight();
    }

    private void updateLayoutHeight() {
        final LinearLayout weeklayout = findViewById(R.id.activity_main_group_weeklayout);
        final ConstraintLayout layout = findViewById(R.id.activity_main_group_layout);
        final android.support.v7.widget.Toolbar toolbar = findViewById(R.id.activity_main_group_toolbar);

        layout.post(new Runnable() {
            @Override
            public void run() {
                int  height = (layout.getHeight() - toolbar.getHeight() - week[0].getGroupInfo().getHeight() * 2) / 7;

                for (Day day : week) {
                    day.setHeight(height);
                }
                Toast.makeText(MainActivity.this,  "Screen: " + layout.getHeight() + " Week: " + weeklayout.getHeight() + " Toolbar: " + toolbar.getHeight(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
