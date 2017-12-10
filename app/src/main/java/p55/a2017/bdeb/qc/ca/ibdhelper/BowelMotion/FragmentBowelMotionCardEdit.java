package p55.a2017.bdeb.qc.ca.ibdhelper.BowelMotion;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import p55.a2017.bdeb.qc.ca.ibdhelper.R;

public class FragmentBowelMotionCardEdit extends Fragment {

    public FragmentBowelMotionCardEdit() {
    }

    public static FragmentBowelMotionCardEdit newInstance() {
        FragmentBowelMotionCardEdit fragment = new FragmentBowelMotionCardEdit();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bowel_motion_card_edit, container, false);
    }
}
