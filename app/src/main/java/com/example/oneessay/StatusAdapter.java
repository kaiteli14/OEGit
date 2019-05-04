package com.example.oneessay;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatusAdapter extends ArrayAdapter<Student> {

    private Context context;
    private List<Student> values;

    public StatusAdapter(Context context, ArrayList<Student> values) {
        super(context, R.layout.row_essaytopic, values);
        this.context = context;
        this.values = values;

    }

    TextView sno, name, time;
    View rowView;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        rowView = convertView;
        rowView = (rowView == null) ? inflater.inflate(R.layout.row_status, parent, false) : rowView;
        sno = rowView.findViewById(R.id.status_Number);
        name = rowView.findViewById(R.id.status_StudentName);
        time = rowView.findViewById(R.id.status_ETA);
        sno.setText((position + 1) + "");
        name.setText(values.get(position).getName());
        time.setText(((position + 1)* 5) + ":00");

        notifyDataSetChanged();

        return rowView;
    }


}
