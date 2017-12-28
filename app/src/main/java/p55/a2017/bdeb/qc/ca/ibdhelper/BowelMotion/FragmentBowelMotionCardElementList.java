package p55.a2017.bdeb.qc.ca.ibdhelper.BowelMotion;

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

public class FragmentBowelMotionCardElementList extends Fragment {
    private static final String ARG_EDIT_MODE = "EDIT_MODE";
    private static final String ARG_BOWEL_MOTION_ID = "BOWEL_MOTION_ID";
    private static final String ARG_DAY_TIME = "DAY_TIME";

    private EventEmitter onDelete = new EventEmitter();
    private long bowelMotionId = -1;

    public FragmentBowelMotionCardElementList() {
    }

    public static FragmentBowelMotionCardElementList newInstance(EnumEditMode editMode, long bowelMotionId, Date dayTime) {
        FragmentBowelMotionCardElementList fragment = new FragmentBowelMotionCardElementList();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_EDIT_MODE, editMode.name());
        bundle.putLong(ARG_BOWEL_MOTION_ID, bowelMotionId);
        bundle.putLong(ARG_DAY_TIME, dayTime.getTime());
        fragment.setArguments(bundle);
        return fragment;

    }

    public void setOnDeleteListener (Observer e) {
        onDelete.subscribe(e);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EnumEditMode editMode = EnumEditMode.INFO;
        if (getArguments() != null) {
            bowelMotionId = getArguments().getLong(ARG_BOWEL_MOTION_ID);
            String editModeStr = getArguments().getString(ARG_EDIT_MODE);
            editMode = EnumEditMode.valueOf(editModeStr);
        }

        changeMode(editMode);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bowel_motion_card_element_list, container, false);
    }

    public void changeMode(EnumEditMode edit) {
        getArguments().putString(ARG_EDIT_MODE, edit.name());

        if (edit == EnumEditMode.EDIT) {

            FragmentBowelMotionCardEdit fragmentEdit = FragmentBowelMotionCardEdit.newInstance(bowelMotionId, new Date(getArguments().getLong(ARG_DAY_TIME)));
            fragmentEdit.setOnSaveClickListener(new Observer() {
                @Override
                public void update(Observable o, Object arg) {
                    bowelMotionId = (Long) arg;
                    getArguments().putLong(ARG_BOWEL_MOTION_ID, bowelMotionId);
                    changeMode(EnumEditMode.INFO);
                }
            });
            fragmentEdit.setOnDeleteClickListener(new Observer() {
                @Override
                public void update(Observable o, Object arg) {
                    onDelete.next(bowelMotionId);
                }
            });
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.fragment_bowel_motion_card_element_list, fragmentEdit)
                    .commit();
        } else if (edit == EnumEditMode.INFO) {
            FragmentBowelMotionCardInfo fragmentInfo = FragmentBowelMotionCardInfo.newInstance(bowelMotionId);

            fragmentInfo.setOnEditClickistener(new Observer() {
                @Override
                public void update(Observable o, Object arg) {
                    changeMode(EnumEditMode.EDIT);
                }
            });
            fragmentInfo.setOnDeleteClickistener(new Observer() {
                @Override
                public void update(Observable o, Object arg) {
                    onDelete.next(bowelMotionId);
                }
            });
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.fragment_bowel_motion_card_element_list, fragmentInfo)
                    .commit();
        }
    }
}
