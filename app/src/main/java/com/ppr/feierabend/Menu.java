package com.ppr.feierabend;

import android.app.Activity;
import android.content.Context;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import static android.widget.ToggleButton.*;
import static com.ppr.feierabend.R.id.switch1;
import static com.ppr.feierabend.R.string.lb_week_h;


public class Menu extends Activity {

    private String FILENAME = "week_hours.txt";
    private int weekHours;
    private String WeekHours;
    private int s_40 = 40;
    private int s_38_5 = 38;
    private int lv_value;
    private TextView lb_w_h;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Get tracker.
        Tracker t = (Menu.this.getTracker(
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

        ToggleButton sw_week_h = (ToggleButton) findViewById(switch1);
        lb_w_h = (TextView) findViewById(R.id.lb_W_h);

        weekHours = GetFileData(FILENAME);

        if( weekHours == s_40){
            sw_week_h.setChecked(true);
        }
        else{
            if( weekHours == s_38_5){
                sw_week_h.setChecked(false);
            }
            else{
                WriteFile(s_38_5, FILENAME);
            }
        }

        WeekHours = "40";
        lb_w_h.setText(getString(R.string.lb_week_h).replace("$", WeekHours));
        sw_week_h.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    WriteFile(s_40, FILENAME);
                    WeekHours = "40";
                } else {
                    WriteFile(s_38_5, FILENAME);
                    WeekHours = "38,5";
                }
                Toast.makeText(getBaseContext(), String.valueOf(R.string.ToastWeekHours).concat(WeekHours), Toast.LENGTH_SHORT).show();

            }
        });

    }
    public int GetFileData(String FILENAME){
        FileInputStream fis;

        try {
            fis = openFileInput(FILENAME);
            lv_value = fis.read();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lv_value;
    }

    public void WriteFile(int iv_value, String FILENAME){
        lv_value = iv_value;
        FileOutputStream fos;
        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(lv_value);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
