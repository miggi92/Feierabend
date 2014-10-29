package com.ppr.feierabend;

import android.app.Activity;
import android.content.Context;
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

import java.util.Calendar;


public class MyActivity extends Activity {
    private EditText arrv_time;
    private EditText home_time;
    private EditText pause_time;
    private Button button;
    private int work_time;
    private CheckBox next_day;
    private EditText time_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        arrv_time = (EditText) findViewById(R.id.arrv_time);
        pause_time = (EditText)findViewById(R.id.pause_time);
        home_time = (EditText)findViewById(R.id.home_time);
        time_left = (EditText)findViewById(R.id.time_left);
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
// hide Keyboard
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

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

                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_WEEK);

                act_hour = c.get(Calendar.HOUR_OF_DAY);
                act_hour = act_hour * 100;
                act_min = c.get(Calendar.MINUTE);
                act_time = act_hour + act_min;



                //Freitags Arbeitszeit ermitteln
                if(day == 6){
                    work_time = 630;
                }
                else{
                    work_time = 800;
                }

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
                    if(day == 6) {
                        pause = "00:30";
                    }
                    else {
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
                act_time = time - act_time;

                arrival = String.valueOf(time);
                time_lft = String.valueOf(act_time);

                int length = arrival.length();
                int half = length / 2;

                int length_t = time_lft.length();
                int half_t = length_t / 2;

                s1 = arrival.substring(0, half);
                s2 = arrival.substring(half, length);

                String t1 = time_lft.substring(0, half_t);
                String t2 = time_lft.substring(half_t, length_t);

                hour = Integer.parseInt(s1);
                min = Integer.parseInt(s2);

                act_hour = Integer.parseInt(t1);
                act_min = Integer.parseInt(t2);

                nd = false;
                //Uhrzeiten richtig anzeigen
                while(min >= 60){
                    hour = hour + 1;
                    min = min - 60;
                }
                while(hour >= 24) {
                    hour2 = + 1;
                    hour = hour - 1;
                    if (hour == 24){
                        hour = 0;
                        nd = true;
                    }
                }

                next_day.setChecked(nd);


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
    public String convert(int number, int digit) {
        String buffer = String.valueOf(number);
        while(buffer.length() != digit)
            buffer="0" + buffer;
        return buffer;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            OpenSettings();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void OpenSettings() {

    }
}
