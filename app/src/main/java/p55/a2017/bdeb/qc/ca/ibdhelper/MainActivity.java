package p55.a2017.bdeb.qc.ca.ibdhelper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity {

    private Day week[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseWeek();
    }

    private void setDay(EnumDay day) {
        for (Day dayComponent : week) {
            if (dayComponent.enumDay == day) {
                dayComponent.expand();
            }
            else {
                dayComponent.collapse();
            }
        }
    }
    /**
     * Initialiser l'ensemble des journées en leur assignant leurs éléments d'interface.
     */
    private void initialiseWeek() {
        week = new Day[EnumDay.values().length];

        for (final EnumDay enumDay : EnumDay.values()) {
            View group;
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

            week[enumDay.getId()] = new Day(enumDay, group);
            week[enumDay.getId()].setOnSelectListener(new Observer() {
                @Override
                public void update(Observable o, Object arg) {
                    setDay(enumDay);
                }
            });

        }

        expandCurrentDay();
    }

    private void expandCurrentDay() {
        //todo: aligner les date de la lib et de l'enum plus clean?
        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        if (currentDay == -1)
            currentDay = EnumDay.SUNDAY.getId();

        week[currentDay].expand();
    }
}
