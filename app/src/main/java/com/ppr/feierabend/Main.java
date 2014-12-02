package com.ppr.feierabend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;


public class Main extends Activity {

    private static final String PROPERTY_ID = "UA-56135998-1";
    private Button buttonCalc;
    private Button buttonSettings;
    private Button buttonDB;

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
        setContentView(R.layout.activity_main);

        // Get tracker.
        Tracker t = (Main.this.getTracker(
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

        buttonCalc = (Button) findViewById(R.id.calculator);
        buttonCalc.setText(R.string.TxtCalculator);
        buttonCalc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Clicked on Calculator Button", Toast.LENGTH_LONG).show();
                OpenCalculator();
            }
        });

        buttonDB = (Button) findViewById(R.id.database);
        buttonDB.setText(R.string.db);
        //TODO: delete next line when DB is implemented
        buttonDB.setEnabled(false);
        //TODO.
        buttonDB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Clicked on the Database Button", Toast.LENGTH_LONG).show();
            }
        });

        buttonSettings = (Button) findViewById(R.id.settings);
        buttonSettings.setText(R.string.settings);
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Clicked on the Settings Button", Toast.LENGTH_LONG).show();
                OpenSettings();
            }
        });
    }

    private void OpenCalculator() {
        Intent intent = new Intent(this, com.ppr.feierabend.MyActivity.class);
        startActivity(intent);
    }
    private void OpenSettings() {
        Intent intent = new Intent(this, com.ppr.feierabend.Menu.class);
        startActivity(intent);
    }
}
