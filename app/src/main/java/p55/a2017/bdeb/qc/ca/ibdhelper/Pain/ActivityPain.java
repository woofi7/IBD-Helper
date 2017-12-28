package p55.a2017.bdeb.qc.ca.ibdhelper.Pain;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import p55.a2017.bdeb.qc.ca.ibdhelper.DbHelper.DbHelper;
import p55.a2017.bdeb.qc.ca.ibdhelper.R;
import p55.a2017.bdeb.qc.ca.ibdhelper.util.CustomScrollView;
import p55.a2017.bdeb.qc.ca.ibdhelper.util.EnumEditMode;

public class ActivityPain extends AppCompatActivity {
    public static final String EXTRA_DATE = "DATE";
    private Date dayDate;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dayDate = (Date) getIntent().getSerializableExtra(EXTRA_DATE);
        this.setTitle(this.getTitle() + " (" + SimpleDateFormat.getDateInstance().format(dayDate) + ")");

        setContentView(R.layout.activity_pain);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        FloatingActionButton newCardButton = findViewById(R.id.activity_pain_fab_newCard);
        newCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCard(-1);
            }
        });

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction()
                    .remove(fragment)
                    .commit();
        }

        List<Long> painIds = DbHelper.getInstance(this).loadPainDataId(dayDate);

        for (Long painId : painIds) {
            addCard(painId);
        }
    }

    private void addCard(long painId) {
        EnumEditMode editMode = EnumEditMode.EDIT;
        if (painId > 0) {
            editMode = EnumEditMode.INFO;
        }

        final FragmentPainCardElementList fragment = FragmentPainCardElementList.newInstance(editMode, painId, dayDate);
        fragment.setOnDeleteListener(new Observer() {
            @Override
            public void update(Observable o, final Object arg) {
                DbHelper.getInstance(ActivityPain.this).deletePain((Long) arg);
                getSupportFragmentManager().beginTransaction()
                        .remove(fragment)
                        .commit();
                Snackbar snackbar = Snackbar.make(ActivityPain.this.findViewById(R.id.activity_pain_main), R.string.activity_pain_delete_confirm, Snackbar.LENGTH_LONG);
                snackbar.setAction(R.string.activity_pain_delete_undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DbHelper.getInstance(ActivityPain.this).undeletePain((Long) arg);
                        addCard((Long) arg);
                    }
                });
                snackbar.show();

            }
        });
        fragment.setOnDrawListener(new Observer() {
            @Override
            public void update(Observable observable, Object o) {
                CustomScrollView scrollView = findViewById(R.id.activity_pain_main);

                if ((Boolean) o) {
                    scrollView.enableScrolling();
                }
                else {
                    scrollView.disableScrolling();
                }
            }
        });
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_activity_pain_card_list, fragment)
                .commit();
    }
}
