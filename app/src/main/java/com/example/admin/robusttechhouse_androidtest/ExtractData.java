package com.example.admin.robusttechhouse_androidtest;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Truong Nhat Tan on 1/3/2018.
 * The function of this method is to convert JSON data to value that we need (Amount, Day, Month, Year).
 */


public final class ExtractData {
    private ExtractData() {
    }
    public static ArrayList<GoldPriceData> extractJSONData(String jsondata){

        // Create an empty ArrayList
        ArrayList<GoldPriceData> goldPriceData = new ArrayList<GoldPriceData>();
        try {
            JSONArray jsonArray = new JSONArray(jsondata);
            for (int i=0;i<=jsonArray.length()-1;i++){
                JSONObject jsonObjectInfo = (JSONObject) jsonArray.get(i);
                String amount = jsonObjectInfo.getString("amount");
                String dateString = jsonObjectInfo.getString("date");
                int day =0;int month =0;int year=0;
                int count=0;
                //Split day,month,year from "/"
                for (String item: dateString.split("/")) {
                    if (count==0){
                        day = Integer.parseInt(item);
                        count++;
                    }
                    else if (count==1){
                        month = Integer.parseInt(item);
                        count++;
                    }
                    else {
                        year = Integer.parseInt(item);
                        count = 0;
                    }
                }
                Date date = new Date(year-1900,month-1,day);
                // Add elements to goldPrice ArrayList
                goldPriceData.add(new GoldPriceData(amount,date));
            }
        } catch (JSONException e) {
            // catch the exception
            Log.e("ExtracData", "Problem parsing the JSON results", e);
        }
        return goldPriceData;
    }

}
