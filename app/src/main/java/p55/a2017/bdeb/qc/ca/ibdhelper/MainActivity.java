package p55.a2017.bdeb.qc.ca.ibdhelper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Day week[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            initialiseWeek();
        }
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
        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_main_group_weeklayout, FragmentMainElementDay.newInstance())
                .add(R.id.activity_main_group_weeklayout, FragmentMainElementDay.newInstance())
                .add(R.id.activity_main_group_weeklayout, FragmentMainElementDay.newInstance())
                .commit();

        /*week = new Day[EnumDay.values().length];

        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        EnumDay currentDay = EnumDay.fromDayOfWeek(dayOfWeek);

        for (final EnumDay enumDay : EnumDay.values()) {



            int difference = enumDay.getId() - currentDay.getId();
            calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, difference);

            Day day = new Day(enumDay, group, calendar.getTime());
            day.setEnable(difference <= 0);
            day.setOnSelectListener(new Observer() {
                @Override
                public void update(Observable o, Object arg) {
                    setDay(enumDay);
                }
            });
            week[enumDay.getId()] = day;
        }*/
        //week[currentDay.getId()].expand();
    }
}
