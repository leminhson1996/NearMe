package com.example.leminhson.nearme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by leminhson on 5/6/17.
 */

public class HomeAdapter extends ArrayAdapter<Model> {

    private final Context context;
    private final ArrayList<Model> modelsArrayList;

    public HomeAdapter(HomeActivity context, ArrayList<Model> modelsArrayList) {

        super(context, R.layout.target_item, modelsArrayList);

        this.context = context;
        this.modelsArrayList = modelsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater

        View rowView = null;
        rowView = inflater.inflate(R.layout.target_item, parent, false);

        // 3. Get icon,title & counter views from the rowView
        ImageView imgView = (ImageView) rowView.findViewById(R.id.item_icon);
        TextView titleView = (TextView) rowView.findViewById(R.id.item_title);
        TextView counterView = (TextView) rowView.findViewById(R.id.item_counter);

        // 4. Set the text for textView
        Picasso.with(context).load(modelsArrayList.get(position).getImageLink()).into(imgView);
        titleView.setText(modelsArrayList.get(position).getTitle());
        counterView.setText(modelsArrayList.get(position).getCounter());

        // 5. retrn rowView
        return rowView;
    }
}
