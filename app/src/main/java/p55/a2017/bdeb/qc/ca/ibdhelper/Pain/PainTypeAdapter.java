package p55.a2017.bdeb.qc.ca.ibdhelper.Pain;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import p55.a2017.bdeb.qc.ca.ibdhelper.util.EnumPainType;

public class PainTypeAdapter extends ArrayAdapter<EnumPainType> {

    public PainTypeAdapter (Context context) {
        super(context, 0, EnumPainType.values());
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        CheckedTextView text = (CheckedTextView) convertView;
        if (text == null) {
            text = (CheckedTextView) LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item,  parent, false);
        }
        text.setText(getItem(position).getTextRessource());

        return text;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        CheckedTextView text = (CheckedTextView) convertView;
        if (text == null) {
            text = (CheckedTextView) LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item,  parent, false);
        }
        text.setText(getItem(position).getTextRessource());

        return text;
    }
}
