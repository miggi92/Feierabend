package com.ppr.feierabend;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class MyActivity extends Activity {

    static final int dialog_id = 0;
    int hour, minute;
    private EditText arrv_time;
    private EditText home_time;
    private EditText pause_time;
    private Button button;
    private int work_time;
    private CheckBox next_day;
    private EditText time_left;
    private TextView lb_left;
    private int s_week_hours;
    private int lv_value;
    private String FILENAME = "week_hours.txt";
    //TODO:implement DB
   // DBAdapter myDB;
    // The following line should be changed to include the correct property id.
    private static final String PROPERTY_ID = "UA-56135998-1";

    public enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
        ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
    }
    HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

    private static final String TEST_DEVICE_ID = "TEST_EMULATOR";
    //Google Analytics
    synchronized Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(R.xml.app_tracker)
                    : (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(PROPERTY_ID)
                    : analytics.newTracker(R.xml.ecommerce_tracker);
            mTrackers.put(trackerId, t);

        }
        return mTrackers.get(trackerId);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /** Called when the activity is first created. */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);


        // Get tracker.
        Tracker t = (MyActivity.this.getTracker(
                TrackerName.APP_TRACKER));

        // Enable Advertising Features.
        t.enableAdvertisingIdCollection(true);


        // The "loadAdOnCreate" and "testDevices" XML attributes no longer available.
            AdView adView = (AdView) this.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .addTestDevice(TEST_DEVICE_ID)
                    .build();
            adView.loadAd(adRequest);

        arrv_time = (EditText) findViewById(R.id.arrv_time);
        pause_time = (EditText)findViewById(R.id.pause_time);
        home_time = (EditText)findViewById(R.id.home_time);
        time_left = (EditText)findViewById(R.id.time_left);
        lb_left = (TextView)findViewById(R.id.lb_left);
        button = (Button) findViewById(R.id.button);
        next_day = (CheckBox) findViewById(R.id.next_day);

        button.setText(R.string.button);

        arrv_time.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                button.setEnabled(s.length() > 0);
            }
        });

        button.setEnabled(false);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//*****hide Keyboard**********************************************************************
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
//***************************************************************************************
                String pause;
                int time;
                int hour;
                int hour2;
                int min;
                int pt;
                String arrival;
                String s1;
                String s2;
                boolean nd;
                int act_time;
                int act_hour;
                int act_min;
                String time_lft;
                Date Arrival;

                arrv_time.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        showDialog(dialog_id);
                    }
                });


                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_WEEK);

                s_week_hours = GetFileData(FILENAME);
                //Falls Datei nicht vorhanden wieder eine schreiben und auslesen

                switch (s_week_hours){
                    case 38:
                        //Freitags Arbeitszeit ermitteln
                        if(day == 6){
                            work_time = 630;
                        }
                        else{
                            work_time = 800;
                        }
                        break;
                    case 40: work_time = 800;
                        break;
                    default:WriteFile(38, FILENAME);
                        s_week_hours = GetFileData(FILENAME);
                        break;
                }

        //        Arrival = new SimpleDateFormat("HH:mm").parse(arrv_time.getText());
                act_hour = c.get(Calendar.HOUR_OF_DAY);
                act_min = c.get(Calendar.MINUTE);
                act_time = act_hour * 100 + act_min;

                arrival = arrv_time.getText().toString();
                arrival = arrival.replace(":", "");
                time = Integer.parseInt(arrival);
                if(arrival.length()<= 2) {
                    if (time < 100) {
                        time = time * 100;
                    }
                }
                pause = pause_time.getText().toString();

                if(pause.matches("")){
                    if(work_time == 630) {
                        pause = "00:30";
                    }
                    if(work_time == 800) {
                        pause = "01:00";
                    }
                }
                pause = pause.replace(":", "");
                pt = Integer.parseInt(pause);
                if(pause.length()<= 2) {
                    if (pt < 100) {
                        pt = pt * 100;
                    }
                }
                //Zeit berechnen
                time = time + pt + work_time;
                String t1;
                String t2;

                arrival = String.valueOf(time);

                int length = arrival.length();
                int half = length / 2;

                s1 = arrival.substring(0, half);
                s2 = arrival.substring(half, length);

                hour = Integer.parseInt(s1);
                min = Integer.parseInt(s2);

                if(time < act_time){
                    lb_left.setText(R.string.lv_overTime);

                    if(min > act_min){

                        if(min == 0 ){
                            min = 60;
                            hour = hour - 1;
                        }
                        act_min = min - act_min;
                    }
                    else{
                        act_min = act_min - min;
                    }
                    act_hour = act_hour - hour;
                    if(act_min == 60){
                        act_min = 0;
                        act_hour = act_hour + 1;
                    }

                }
                else {
                    lb_left.setText(R.string.lv_left);
                    if (min == 0) {
                        min = 60;
                        hour = hour - 1;
                    }

                    //   if(min == 60){
                    act_min = min - act_min;
                    //     }

                    act_hour = hour - act_hour + 1;
                    act_hour = act_hour * 100;


                    act_time = act_hour + act_min;

                    time_lft = String.valueOf(act_time);

                    int length_t = time_lft.length();
                    int half_t = length_t / 2;

                    t1 = time_lft.substring(0, half_t);
                    t2 = time_lft.substring(half_t, length_t);

                    act_hour = Integer.parseInt(t1);
                    act_min = Integer.parseInt(t2);

                    nd = false;
                    //Uhrzeiten richtig anzeigen
                    while (min >= 60) {
                        hour = hour + 1;
                        min = min - 60;
                    }
                    while (hour >= 24) {
                        hour2 = +1;
                        hour = hour - 1;
                        if (hour == 24) {
                            hour = 0;
                            nd = true;
                        }
                    }
                    hour2 = 0;
                    // Time LEft richtig anzeigen
                    act_hour = act_hour - 1;
                    while (act_min >= 60) {
                        act_hour = act_hour + 1;
                        act_min = act_min - 60;
                    }
                    while (act_hour >= 24) {
                        hour2 = +1;
                        act_hour = act_hour - 1;
                        if (act_hour == 24) {
                            act_hour = 0;
                            nd = true;
                        }
                    }

                    next_day.setChecked(nd);

                }
                    time_lft = String.valueOf(act_time);

                    s2 = convert(min, 2); //fügt eine 0 hinzu, damit die Minuten immer 2 Stellen haben
                    s1 = convert(hour, 2);

                    t2 = convert(act_min, 2); //fügt eine 0 hinzu, damit die Minuten immer 2 Stellen haben
                    t1 = convert(act_hour, 2);

                    arrival = s1 + ":" + s2;
                    time_lft = t1 + ":" + t2;
                    home_time.setText(arrival);
                    time_left.setText(time_lft);

            }
        });

    }

    protected Dialog onCreateDialog(int id){
        switch (id){

            case dialog_id:
                return new TimePickerDialog(this, mTimeSetListener, hour, minute, true);
        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int hour_minute) {
            hour = hourOfDay;
            minute = hour_minute;
            Toast.makeText(getBaseContext(),"Setted time: " + hour + " : "+ minute, Toast.LENGTH_LONG).show();

        }
    };
    public void onStart() {
        super.onStart();

    }
    @Override
    public void onStop() {
        super.onStop();

    }

    public String convert(int number, int digit) {
        String buffer = String.valueOf(number);
        while(buffer.length() != digit)
            buffer="0" + buffer;
        return buffer;
    }

    public int GetFileData(String FILENAME){
        FileInputStream fis;

        try {
            fis = openFileInput(FILENAME);
            s_week_hours = fis.read();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s_week_hours;
    }

    public void WriteFile(int iv_value, String FILENAME){
        lv_value = iv_value;
        FileOutputStream fos;
        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(38);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
    @Override
    protected void onDestroy() {
        super.onDestroy();

        closeDB();
    }

    private void openDB(){
        myDB = new DBAdapter(this);
        myDB.open();

    }
    private void closeDB() {
        myDB.close();
    }

//TODO: https://www.youtube.com/watch?v=Aui-kFuXFYE
    private void addRowToDB(){
      //  long newID = myDB.insertRow();
    }
 // Display an entire recordset to the screen.
    private void displayRecordSet(Cursor cursor){
        //Reset cursor to start, checking if there is any data:
        if(cursor.moveToFirst()){
            int id = cursor.getInt(0);
        }

    }
    */
}
