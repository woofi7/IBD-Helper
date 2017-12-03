package p55.a2017.bdeb.qc.ca.ibdhelper;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

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
        initialiseWeek(false);
    }

    private void initialiseWeek (boolean pastWeek) {
        if (weekDate == null) {
            Calendar currentTime = Calendar.getInstance();
            currentTime.set(Calendar.HOUR, 0);
            currentTime.set(Calendar.MINUTE, 0);
            currentTime.set(Calendar.SECOND, 0);
            weekDate = currentTime.getTime();
        }

        week = new FragmentMainElementDay[EnumDay.values().length];

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(weekDate);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        EnumDay currentDay = EnumDay.fromDayOfWeek(dayOfWeek);

        for (final EnumDay enumDay : EnumDay.values()) {
            int difference = enumDay.getId() - currentDay.getId();
            calendar = Calendar.getInstance();
            calendar.setTime(weekDate);
            calendar.add(Calendar.DATE, difference);
            long time = calendar.getTime().getTime();

            FragmentMainElementDay fragment = FragmentMainElementDay.newInstance(enumDay, time, pastWeek || difference <= 0, currentDay == enumDay);
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

    private void swipeWeek(boolean right) {
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(weekDate);
        if (right) {
            currentDate.add(Calendar.DATE, 7);
        }
        else {
            currentDate.add(Calendar.DATE, -7);
        }

        Calendar firstDayOfWeek = Calendar.getInstance();
        firstDayOfWeek.setFirstDayOfWeek(Calendar.MONDAY);
        firstDayOfWeek.setTime(currentDate.getTime());
        int daysToMonday = firstDayOfWeek.get(Calendar.DAY_OF_WEEK) - firstDayOfWeek.getFirstDayOfWeek();
        if (daysToMonday < 0) {
            daysToMonday += 7;
        }
        firstDayOfWeek.add(Calendar.DATE, -daysToMonday);

        Calendar currentTime = Calendar.getInstance();
        currentTime.setFirstDayOfWeek(Calendar.MONDAY);
        if (firstDayOfWeek.getTime().after(currentTime.getTime())) {
            return;
        }

        Calendar nextWeek = Calendar.getInstance();
        nextWeek.setTime(firstDayOfWeek.getTime());
        nextWeek.add(Calendar.DATE, 7);

        boolean pastWeek = !(currentTime.after(firstDayOfWeek) && currentTime.before(nextWeek));
        if (pastWeek) {
            currentDate.setTime(firstDayOfWeek.getTime());
        } else {
            currentDate.setTime(currentTime.getTime());
        }

        weekDate = currentDate.getTime();
        clearFragments();
        initialiseWeek(pastWeek);
    }

    private void clearFragments() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        for (FragmentMainElementDay fragment : week) {
            fragmentTransaction.remove(fragment);
        }
        fragmentTransaction.commit();
    }
}
