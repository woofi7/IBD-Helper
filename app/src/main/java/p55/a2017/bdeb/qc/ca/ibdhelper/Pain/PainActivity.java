package p55.a2017.bdeb.qc.ca.ibdhelper.Pain;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import p55.a2017.bdeb.qc.ca.ibdhelper.R;

public class PainActivity extends AppCompatActivity {

    enum EditMode {
        VIEW,
        EDIT
    }

    private EditMode editMode = EditMode.VIEW;

    private FloatingActionButton newCardButton;

    private FragmentPainCard fragmentView;
    private FragmentPainCardInfo fragmentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pain);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        newCardButton = findViewById(R.id.activity_pain_fab_newCard);
        newCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PainActivity.this, "Add new card.", Toast.LENGTH_SHORT).show();
            }
        });


        fragmentView = FragmentPainCard.newInstance();
        fragmentInfo = FragmentPainCardInfo.newInstance();


        fragmentView.setOnEditClickistener(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                changeMode(EditMode.EDIT);
            }
        });
        fragmentView.setOnDeleteClickistener(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                //TODO: Delete the card
            }
        });
        fragmentInfo.setOnSaveClickistener(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                changeMode(EditMode.VIEW);
            }
        });
        fragmentInfo.setOnDeleteClickistener(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                //TODO: Delete the card
            }
        });

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_activity_pain_card, fragmentView)
                .commit();
    }

    private void changeMode(EditMode edit) {
        if (edit == editMode) {
            return;
        }
        editMode = edit;

        if (edit == EditMode.EDIT) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_activity_pain_card, fragmentInfo)
                    .commit();
        } else if (edit == EditMode.VIEW) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_activity_pain_card, fragmentView)
                    .commit();
        }
    }
}
