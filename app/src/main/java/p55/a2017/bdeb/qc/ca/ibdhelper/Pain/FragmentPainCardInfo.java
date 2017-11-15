package p55.a2017.bdeb.qc.ca.ibdhelper.Pain;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Observer;

import p55.a2017.bdeb.qc.ca.ibdhelper.R;
import p55.a2017.bdeb.qc.ca.ibdhelper.util.EventEmitter;

public class FragmentPainCardInfo extends Fragment {
    private Button deleteBtn;
    private Button saveBtn;

    private EventEmitter onClickSave = new EventEmitter();
    private EventEmitter onClickDelete = new EventEmitter();

    public void setOnSaveClickistener(Observer e) {
        onClickSave.subscribe(e);
    }
    public void setOnDeleteClickistener(Observer e) {
        onClickDelete.subscribe(e);
    }
    public FragmentPainCardInfo() {
    }

    public static FragmentPainCardInfo newInstance() {
        FragmentPainCardInfo fragment = new FragmentPainCardInfo();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_activity_pain_card_edit, container, false);

        saveBtn = rootView.findViewById(R.id.activity_pain_btn_save);
        deleteBtn = rootView.findViewById(R.id.activity_pain_btn_delete);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSave.next();
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDelete.next();
            }
        });

        return rootView;
    }
}
