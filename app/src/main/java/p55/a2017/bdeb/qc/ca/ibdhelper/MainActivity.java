package p55.a2017.bdeb.qc.ca.ibdhelper;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity {

    private FragmentMainElementDay week[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            initialiseWeek();
        }
    }

    private void setDay(EnumDay day) {
        for (FragmentMainElementDay dayComponent : week) {
            if (dayComponent.getEnumDay() == day) {
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
        week = new FragmentMainElementDay[EnumDay.values().length];

        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        EnumDay currentDay = EnumDay.fromDayOfWeek(dayOfWeek);

        for (final EnumDay enumDay : EnumDay.values()) {
            int difference = enumDay.getId() - currentDay.getId();
            calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, difference);

            FragmentMainElementDay fragment = FragmentMainElementDay.newInstance(enumDay, calendar.getTime().getTime());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main_group_weeklayout, fragment)
                    .commit();

            //fragment.setEnable(difference <= 0);
            fragment.setOnSelectListener(new Observer() {
                @Override
                public void update(Observable o, Object arg) {
                    setDay(enumDay);
                }
            });

            week[enumDay.getId()] = fragment;
        }
        //week[currentDay.getId()].expand();
    }
}
