package com.example.admin.robusttechhouse_androidtest;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Truong Nhat Tan on 1/3/2018.
 *
 *
 * The function of this method is to handle the date from JSON data into proper Date/Month/Year data and link it to List View Item.
 */

public class GoldPriceAdapter extends ArrayAdapter<GoldPriceData> {
    // Get typeface to use Myriad Font
    Typeface typeface = MainActivity.typeface;

    public GoldPriceAdapter(Activity context, ArrayList<GoldPriceData> EarthQuakes) {
        super(context, 0, EarthQuakes);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        GoldPriceData currentGoldPrice = getItem(position);

        //Reduce the time from dd/MM/yyyy hh:mm:ss to dd/MM/yyyy
        String formatDate = currentGoldPrice.getmDate().toString().substring(4,11)+currentGoldPrice.getmDate().toString().substring(29);

        //Set Information, Text Size, Text Style and Myriad Font to List Item
        TextView date = (TextView) listItemView.findViewById(R.id.date);
        date.setText(formatDate);
        date.setTypeface(typeface);
        date.setTextSize(15);

        TextView amount = (TextView) listItemView.findViewById(R.id.amount);
        amount.setText("$"+currentGoldPrice.getmAmount());
        amount.setTypeface(typeface,1);
        amount.setTextSize(15);

        return listItemView;
    }



}
