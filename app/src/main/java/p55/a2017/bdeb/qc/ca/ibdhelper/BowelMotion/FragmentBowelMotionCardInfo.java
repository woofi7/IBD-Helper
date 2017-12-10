package p55.a2017.bdeb.qc.ca.ibdhelper.BowelMotion;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import p55.a2017.bdeb.qc.ca.ibdhelper.R;

public class FragmentBowelMotionCardInfo extends Fragment {

    public FragmentBowelMotionCardInfo() {
    }

    public static FragmentBowelMotionCardInfo newInstance() {
        FragmentBowelMotionCardInfo fragment = new FragmentBowelMotionCardInfo();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bowel_motion_card_info, container, false);
    }
}
