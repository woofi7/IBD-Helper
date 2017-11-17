package p55.a2017.bdeb.qc.ca.ibdhelper.Pain;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.text.DateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import p55.a2017.bdeb.qc.ca.ibdhelper.R;

public class PainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pain);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton newCardButton = findViewById(R.id.activity_pain_fab_newCard);
        newCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCard();
            }
        });
    }

    private void addCard() {
        final FragmentPainCardElementList fragment = FragmentPainCardElementList.newInstance(FragmentPainCardElementList.EditMode.EDIT);
        fragment.setOnDeleteListener(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                getSupportFragmentManager().beginTransaction()
                        .remove(fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_activity_pain_card_list, fragment)
                .addToBackStack(null)
                .commit();
    }
}
