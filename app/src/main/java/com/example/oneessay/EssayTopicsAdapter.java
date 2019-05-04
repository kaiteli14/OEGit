package com.example.oneessay;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EssayTopicsAdapter extends ArrayAdapter<Essay> {

    private Context context;
    private List<Essay> values;

    private HashMap<String,String> tempMap;

    public static Essay selectedTopic = new Essay("0","None");

    public EssayTopicsAdapter(Context context, ArrayList<Essay> values) {
        super(context, R.layout.row_essaytopic, values);
        this.context = context;
        this.values = values;
        tempMap = new HashMap<String, String>();

        for(int i = 0;i<values.size();i++)
        {
            tempMap.put(values.get(i).getTopic(),values.get(i).getId());
        }

    }

    TextView essayText;
    ImageView minus;
    View rowView;

    int loc = 0;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        rowView = convertView;
        rowView = (rowView == null) ? inflater.inflate(R.layout.row_essaytopic, parent, false) : rowView;

        essayText = (TextView) rowView.findViewById(R.id.essayrow_topic);

        essayText.setText(values.get(position).getTopic());

        essayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View parentRow = (View) view.getParent();
                ListView lv = (ListView) parentRow.getParent();
                View prevView = (View) lv.getChildAt(loc);

                TextView prevRow = (TextView) prevView.findViewById(R.id.essayrow_topic);

                prevRow.setBackgroundColor(Color.WHITE);
                prevRow.setTextColor(Color.BLACK);

                TextView temp = (TextView) view;

                temp.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                temp.setTextColor(Color.WHITE);
                selectedTopic.setId(tempMap.get(temp.getText().toString()));
                selectedTopic.setTopic(temp.getText().toString());

                loc = lv.getPositionForView(parentRow);

            }
        });


        notifyDataSetChanged();

        return rowView;
    }

}
