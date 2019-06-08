package com.example.taller_6_enderson_salas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.taller_6_enderson_salas.R;
import com.example.taller_6_enderson_salas.models.Item;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item> {
    int mLayoutId;

    public ItemAdapter(Context context, int layoutId, List<Item> items) {
        super(context, layoutId, items);
        mLayoutId = layoutId;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Item item = getItem(position);
        String title = item.getTitle();
        String subTitle = item.getSubTitle();
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(mLayoutId, parent, false);
        }
        TextView titleTextView = (TextView) view.findViewById(R.id.txtDescription);
        TextView subTitleTextView = (TextView) view.findViewById(R.id.txtQuantity);

        titleTextView.setText(title);
        subTitleTextView.setText(subTitle);
        return view;
    }
}