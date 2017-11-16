package p55.a2017.bdeb.qc.ca.ibdhelper.Pain;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Observable;
import java.util.Observer;

import p55.a2017.bdeb.qc.ca.ibdhelper.R;
import p55.a2017.bdeb.qc.ca.ibdhelper.util.EventEmitter;

public class FragmentPainCardElementList extends Fragment {

    private static final String ARG_EDIT_MODE = "EDIT_MODE";

    enum EditMode {
        INFO,
        EDIT
    }

    private EditMode editMode;

    private FragmentPainCardInfo fragmentInfo;
    private FragmentPainCardEdit fragmentEdit;

    private EventEmitter onDelete = new EventEmitter();

    public FragmentPainCardElementList() {
    }

    public static FragmentPainCardElementList newInstance(EditMode edit) {
        FragmentPainCardElementList fragment = new FragmentPainCardElementList();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_EDIT_MODE, edit.name());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            String editModeStr = getArguments().getString(ARG_EDIT_MODE);
            editMode = EditMode.valueOf(editModeStr);
        }

        fragmentInfo = FragmentPainCardInfo.newInstance();
        fragmentEdit = FragmentPainCardEdit.newInstance();

        fragmentInfo.setOnEditClickistener(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                changeMode(EditMode.EDIT);
            }
        });
        fragmentInfo.setOnDeleteClickistener(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                onDelete.next();
            }
        });
        fragmentEdit.setOnSaveClickistener(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                changeMode(EditMode.INFO);
            }
        });
        fragmentEdit.setOnDeleteClickistener(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                onDelete.next();
            }
        });

        changeMode(editMode);
    }

    public void setOnDeleteListener (Observer e) {
        onDelete.subscribe(e);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_activity_pain_card_element_list, container, false);
    }

    //TODO: Add BackStack on edit and save
    public void changeMode(EditMode edit) {
        editMode = edit;

        if (edit == EditMode.EDIT) {
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.fragment_pain_card_element_list, fragmentEdit)
                    .addToBackStack(null)
                    .commit();
        } else if (edit == EditMode.INFO) {
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.fragment_pain_card_element_list, fragmentInfo)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
