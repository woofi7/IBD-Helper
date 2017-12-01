package p55.a2017.bdeb.qc.ca.ibdhelper;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity {

    private FragmentMainElementDay week[];
    private Date weekDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            initialiseWeek();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
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
        if (weekDate == null) {
            weekDate = Calendar.getInstance().getTime();
        }

        week = new FragmentMainElementDay[EnumDay.values().length];

        final Calendar[] calendar = {Calendar.getInstance()};
        calendar[0].setTime(weekDate);
        int dayOfWeek = calendar[0].get(Calendar.DAY_OF_WEEK);
        EnumDay currentDay = EnumDay.fromDayOfWeek(dayOfWeek);

        for (final EnumDay enumDay : EnumDay.values()) {
            int difference = enumDay.getId() - currentDay.getId();
            calendar[0] = Calendar.getInstance();
            calendar[0].setTime(weekDate);
            calendar[0].add(Calendar.DATE, difference);
            long time = calendar[0].getTime().getTime();

            FragmentMainElementDay fragment = FragmentMainElementDay.newInstance(enumDay, time, difference <= 0, currentDay == enumDay);
            fragment.setOnSelectListener(new Observer() {
                @Override
                public void update(Observable o, Object arg) {
                    setDay(enumDay);
                }
            });

            fragment.setOnSwipeListener(new Observer() {
                @Override
                public void update(Observable observable, Object o) {
                    swipeWeek((boolean) o);
                }
            });

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main_group_weeklayout, fragment)
                    .commit();
            week[enumDay.getId()] = fragment;
        }
    }

    private void swipeWeek(boolean side) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        for (FragmentMainElementDay fragment : week) {
            fragmentTransaction.remove(fragment);
        }
        fragmentTransaction.commit();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(weekDate);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int daysToMonday = calendar.get(Calendar.DAY_OF_WEEK) - calendar.getFirstDayOfWeek() - 1;

        if (side) {
            calendar.add(Calendar.DATE, daysToMonday + 7);
        }
        else {
            calendar.add(Calendar.DATE, daysToMonday - 7);
        }
        weekDate = calendar.getTime();

        Toast.makeText(this, calendar.getTime().toString(), Toast.LENGTH_SHORT).show();
        initialiseWeek();
    }
}
