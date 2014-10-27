package com.ppr.feierabend;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MyActivity extends Activity {
    private EditText arrv_time;
    private EditText home_time;
    private EditText pause_time;
    private Button button;
    private int work_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        arrv_time = (EditText) findViewById(R.id.arrv_time);
        home_time = (EditText) findViewById(R.id.home_time);
        pause_time = (EditText)findViewById(R.id.pause_time);
        button = (Button) findViewById(R.id.button);

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

                String pause;
                int time;
                int pt;
                String arrival;
                String s1;
                String s2;

                //TODO: Freitag mit einberechnen.
                work_time = 800;
                arrival = arrv_time.getText().toString();
                arrival = arrival.replace(":", "");
                time = Integer.parseInt(arrival);
                pause = pause_time.getText().toString();
                if(pause.matches("")){
                    pause = "01:00";
                }
                pause = pause.replace(":", "");
                pt = Integer.parseInt(pause);
                if (time < 1700)
                {
                    time = time + pt + work_time;
                }
                else {
                    //TODO: Abfangen dass er über die 24h schreibt.
                    time = 0000;
                }
                arrival = String.valueOf(time);
                s1 = arrival.substring(0,2);
                s2 = arrival.substring(2,4);
                arrival = s1 + ":" + s2;
                home_time.setText(arrival);
            }
        });

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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
