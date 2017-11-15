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

public class FragmentPainCard extends Fragment {
    private Button deleteBtn;
    private Button editBtn;

    private EventEmitter onClickEdit = new EventEmitter();
    private EventEmitter onClickDelete = new EventEmitter();

    public FragmentPainCard() {
    }

    public static FragmentPainCard newInstance() {
        FragmentPainCard fragment = new FragmentPainCard();
        return fragment;
    }

    public void setOnEditClickistener(Observer e) {
        onClickEdit.subscribe(e);
    }
    public void setOnDeleteClickistener(Observer e) {
        onClickDelete.subscribe(e);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_activity_pain_card, container, false);

        editBtn = rootView.findViewById(R.id.activity_pain_btn_edit);
        deleteBtn = rootView.findViewById(R.id.activity_pain_btn_delete);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEdit.next();
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
