package p55.a2017.bdeb.qc.ca.ibdhelper.Pain;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observer;

import p55.a2017.bdeb.qc.ca.ibdhelper.R;
import p55.a2017.bdeb.qc.ca.ibdhelper.util.EventEmitter;

public class FragmentPainCardInfo extends Fragment {
    private EventEmitter onClickEdit = new EventEmitter();
    private EventEmitter onClickDelete = new EventEmitter();

    public static FragmentPainCardInfo newInstance(Pain painData) {
        return new FragmentPainCardInfo();
    }

    public FragmentPainCardInfo() {

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
        final View rootView = inflater.inflate(R.layout.fragment_activity_pain_card_info, container, false);

        Button editBtn = rootView.findViewById(R.id.activity_pain_btn_edit);
        Button deleteBtn = rootView.findViewById(R.id.activity_pain_btn_delete);

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
