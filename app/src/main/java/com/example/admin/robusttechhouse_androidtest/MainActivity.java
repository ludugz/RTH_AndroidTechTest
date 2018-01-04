package com.example.admin.robusttechhouse_androidtest;

/**
 * Created by Truong Nhat Tan on 1/2/2018.
 *
 *       Main Activity for the project
 *          -Customize Action Bar
 *          -Set Myriad Font
 *          -Set up pop up window whenever click on "About Me" button.
 *          -Display current time (Day, Date, Month, Year) on the top-left of the screen.
 *          -Receive JSON information from API and then display in graph.
 *
 */
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String REQUEST_API ="https://rth-recruitment.herokuapp.com/api/prices/chart_data";
    ArrayList<GoldPriceData> goldPriceData = new ArrayList<GoldPriceData>();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/M");
    Dialog myDialog;
    //Set up path for Myriad Font
    public static Typeface typeface ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        typeface = Typeface.createFromAsset(getAssets(),"fonts/Myriad-Pro-Regular.ttf");
        //Customize Action Bar ( Name, icon, color ,... )
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar_layout);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.info_icon);

        //Set Myriad Font to "About Me" Button
        setMyriadFontButton();

        //Set up AsyncTask do receive data in BackGround Thread.
        MyTask myTask = new MyTask();
        myTask.execute(REQUEST_API);

        //Set up pop-up window
        myDialog = new Dialog(this);

        //Set current time & add to TextView
        Date currentTime = Calendar.getInstance().getTime();
        int currentDay = currentTime.getDay();
        int currentDate = currentTime.getDate();
        int currentMonth = currentTime.getMonth();
        int currentYear = currentTime.getYear()+1900;
        addTimeToTextView(currentDay,currentDate,currentMonth,currentYear);
    }

    /**
     * Initialize AsyncTask
     */
    private class MyTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return GetDataFromAPI.fetchData(strings[0]) ;
        }
        @Override
        protected void onPostExecute(String result) {
            goldPriceData = ExtractData.extractJSONData(result);
            GoldPriceAdapter goldPriceAdapter = new GoldPriceAdapter(MainActivity.this,goldPriceData);

            //Initialize ListView and link it to ArrayAdapter
            ListView listView =  findViewById(R.id.list_view);
            listView.setAdapter(goldPriceAdapter);

            // Create list of DataPoint
            List<DataPoint> dataPointList = new ArrayList<>();

            for(GoldPriceData item : goldPriceData) {
                DataPoint dataPoint = new DataPoint(item.getmDate().getDate(),Double.parseDouble(item.getmAmount()));
                dataPointList.add(dataPoint);
            }

            // Create Array of DataPoint
            DataPoint[] datePoint = dataPointList.toArray(new DataPoint[dataPointList.size()]);

            //  Create Graph
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(datePoint);

            // Set given data to GraphView and customize it.
            GraphView graph = findViewById(R.id.graph);

            graph.addSeries(series);
            series.setColor(Color.rgb(15, 133, 209));  // Set color to #0F85D1
            series.setThickness(7);
            series.setDrawBackground(true);
            series.setBackgroundColor(Color.argb(60,220, 242, 255));  // Set background color to #9DCDEC
            series.setDrawDataPoints(true);
            series.setDataPointsRadius(7);

            // Customize format of label
            graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
                @Override
                public String formatLabel(double value, boolean isValueX) {
                    if(isValueX)
                    {
                        return getFormatLabelByDate(value);
                    } else
                    {
                        return super.formatLabel(value, isValueX);
                    }
                }
            });
            graph.getGridLabelRenderer().setNumHorizontalLabels(goldPriceData.size());
            graph.getViewport().setXAxisBoundsManual(true);
        }

        public String getFormatLabelByDate(double date)
        {
            for(GoldPriceData item : goldPriceData) {
                if(item.getmDate().getDate() == date ) {
                    return sdf.format(item.getmDate());
                }
            }
            return "";
        }
    }

    public void ShowPopup(View v) {
        myDialog.setContentView(R.layout.custompopup);
        TextView textViewClose = myDialog.findViewById(R.id.txtclose);
        textViewClose.setText("X");
        textViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    private void addTimeToTextView(int currentDay,int currentDate,int currentMonth,int currentYear) {
        String stringCurrentDay = "";
        String stringCurrentMonth ="";
        switch (currentDay){
            case 0:
                stringCurrentDay="Sunday";
            break;
            case 1:
                stringCurrentDay="Monday";
                break;
            case 2:
                stringCurrentDay="Tuesday";
                break;
            case 3:
                stringCurrentDay="Wednesday";
                break;
            case 4:
                stringCurrentDay="Thursday";
                break;
            case 5:
                stringCurrentDay="Friday";
                break;
            case 6:
                stringCurrentDay="Saturday";
                break;
            default:
                break;
        }
        switch (currentMonth){
            case 0:
                stringCurrentMonth="January";
                break;
            case 1:
                stringCurrentMonth="Febuary";
                break;
            case 2:
                stringCurrentMonth="March";
                break;
            case 3:
                stringCurrentMonth="April";
                break;
            case 4:
                stringCurrentMonth="May";
                break;
            case 5:
                stringCurrentMonth="June";
                break;
            case 6:
                stringCurrentMonth="July";
                break;
            case 7:
                stringCurrentMonth="August";
                break;
            case 8:
                stringCurrentMonth="September";
                break;
            case 9:
                stringCurrentMonth="Octorber";
                break;
            case 10:
                stringCurrentMonth="November";
                break;
            case 11:
                stringCurrentMonth="December";
                break;
            default:
                break;
        }
        // Set value and Myriad font to current day,month,year
        TextView textViewCurrentDay = findViewById(R.id.current_time_day);
        textViewCurrentDay.setText(stringCurrentDay);
        textViewCurrentDay.setTypeface(typeface,1); // Style 1 = Bold

        TextView textViewCurrentDate = findViewById(R.id.current_time_date);
        textViewCurrentDate.setText(""+currentDate);
        textViewCurrentDate.setTypeface(typeface,1); // Style 1 = Bold

        TextView textViewCurrentMonth = findViewById(R.id.current_time_month);
        textViewCurrentMonth.setText(stringCurrentMonth);
        textViewCurrentMonth.setTypeface(typeface);

        TextView textViewCurrentYear = findViewById(R.id.current_time_year);
        textViewCurrentYear.setText(""+currentYear);
        textViewCurrentYear.setTypeface(typeface);
    }

    private  void setMyriadFontButton() {
        //Font for AboutMe Button
        Button btnAboutMe = findViewById(R.id.btn_about_me);
        btnAboutMe.setTypeface(typeface,1); // Style 1 = Bold
    }
}
