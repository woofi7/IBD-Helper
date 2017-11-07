package p55.a2017.bdeb.qc.ca.ibdhelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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

            day = (TextView) group.findViewById(R.id.activity_main_group_txt_day);
            date = (TextView) group.findViewById(R.id.activity_main_group_txt_date);
            indicator = (ImageView) group.findViewById(R.id.activity_main_group_img_indicator);
            groupDay = group.findViewById(R.id.activity_main_day);
            groupInfo = group.findViewById(R.id.activity_main_group_info);

            mealTxt = (TextView) groupDay.findViewById(R.id.activity_main_day_txt_meal);
            toiletTxt = (TextView) groupDay.findViewById(R.id.activity_main_day_txt_toilet);
            painTxt = (TextView) groupDay.findViewById(R.id.activity_main_day_txt_pain);
            mealBtn = (ImageButton) groupDay.findViewById(R.id.activity_main_day_imgbtn_meal);
            toiletBtn = (ImageButton) groupDay.findViewById(R.id.activity_main_day_imgbtn_toilet);
            painBtn = (ImageButton) groupDay.findViewById(R.id.activity_main_day_imgbtn_pain);

            day.setText(enumDay.getText(this));
            groupInfo.setContentDescription(enumDay.getId() + "");

            week[enumDay.getId()] = new Day(day, date, mealTxt, painTxt, toiletTxt, indicator,
                    mealBtn, painBtn, toiletBtn, groupDay, groupInfo);
        }
    }
}
