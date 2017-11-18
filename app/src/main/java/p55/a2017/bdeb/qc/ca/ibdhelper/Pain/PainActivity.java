package p55.a2017.bdeb.qc.ca.ibdhelper.Pain;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import p55.a2017.bdeb.qc.ca.ibdhelper.DbHelper.DbHelper;
import p55.a2017.bdeb.qc.ca.ibdhelper.DbHelper.Pain;
import p55.a2017.bdeb.qc.ca.ibdhelper.R;

public class PainActivity extends AppCompatActivity {
    public static final String EXTRA_DATE = "DATE";
    private Date dayDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dayDate = (Date) getIntent().getSerializableExtra(EXTRA_DATE);

        setContentView(R.layout.activity_pain);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton newCardButton = findViewById(R.id.activity_pain_fab_newCard);
        newCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCard(-1);
            }
        });


        List<Long> painIds =  DbHelper.getInstance(this).loadPainDataId(dayDate);

        for (Long painId : painIds) {
            addCard(painId);
        }
    }

    private void addCard(long painId) {
        FragmentPainCardElementList.EditMode editMode = FragmentPainCardElementList.EditMode.EDIT;
        if (painId > 0) {
            editMode = FragmentPainCardElementList.EditMode.INFO;
        }

        final FragmentPainCardElementList fragment = FragmentPainCardElementList.newInstance(editMode, painId);
        fragment.setOnDeleteListener(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                getSupportFragmentManager().beginTransaction()
                        .remove(fragment)
                        .commit();
            }
        });
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_activity_pain_card_list, fragment)
                .commit();
    }
}
