package p55.a2017.bdeb.qc.ca.ibdhelper.Pain;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import p55.a2017.bdeb.qc.ca.ibdhelper.R;
import p55.a2017.bdeb.qc.ca.ibdhelper.util.EnumEditMode;
import p55.a2017.bdeb.qc.ca.ibdhelper.util.EventEmitter;

public class FragmentPainCardElementList extends Fragment {
    private static final String ARG_EDIT_MODE = "EDIT_MODE";
    private static final String ARG_PAIN_ID = "PAIN_ID";
    private static final String ARG_DAY_TIME = "DAY_TIME";

    private EventEmitter onDelete = new EventEmitter();
    private EventEmitter onDraw = new EventEmitter();
    private long painId = -1;

    public FragmentPainCardElementList() {
    }

    public static FragmentPainCardElementList newInstance(EnumEditMode editMode, long painId, Date dayTime) {
        FragmentPainCardElementList fragment = new FragmentPainCardElementList();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_EDIT_MODE, editMode.name());
        bundle.putLong(ARG_PAIN_ID, painId);
        bundle.putLong(ARG_DAY_TIME, dayTime.getTime());
        fragment.setArguments(bundle);
        return fragment;

    }

    public void setOnDeleteListener (Observer e) {
        onDelete.subscribe(e);
    }
    public void setOnDrawListener (Observer e) {
        onDraw.subscribe(e);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EnumEditMode editMode = EnumEditMode.INFO;
        if (getArguments() != null) {
            painId = getArguments().getLong(ARG_PAIN_ID);
            String editModeStr = getArguments().getString(ARG_EDIT_MODE);
            editMode = EnumEditMode.valueOf(editModeStr);
        }

        changeMode(editMode);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pain_card_element_list, container, false);
    }

    public void changeMode(EnumEditMode edit) {
        getArguments().putString(ARG_EDIT_MODE, edit.name());

        if (edit == EnumEditMode.EDIT) {

            FragmentPainCardEdit fragmentEdit = FragmentPainCardEdit.newInstance(painId, new Date(getArguments().getLong(ARG_DAY_TIME)));
            fragmentEdit.setOnSaveClickListener(new Observer() {
                @Override
                public void update(Observable o, Object arg) {
                    painId = (Long) arg;
                    getArguments().putLong(ARG_PAIN_ID, painId);
                    changeMode(EnumEditMode.INFO);
                }
            });
            fragmentEdit.setOnDeleteClickListener(new Observer() {
                @Override
                public void update(Observable o, Object arg) {
                    onDelete.next(painId);
                }
            });
            fragmentEdit.setOnDrawListener(new Observer() {
                @Override
                public void update(Observable observable, Object o) {
                    onDraw.next(o);
                }
            });
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.fragment_pain_card_element_list, fragmentEdit)
                    .commit();
        } else if (edit == EnumEditMode.INFO) {
            FragmentPainCardInfo fragmentInfo = FragmentPainCardInfo.newInstance(painId);

            fragmentInfo.setOnEditClickistener(new Observer() {
                @Override
                public void update(Observable o, Object arg) {
                    changeMode(EnumEditMode.EDIT);
                }
            });
            fragmentInfo.setOnDeleteClickistener(new Observer() {
                @Override
                public void update(Observable o, Object arg) {
                    onDelete.next(painId);
                }
            });
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.fragment_pain_card_element_list, fragmentInfo)
                    .commit();
        }
    }
}
