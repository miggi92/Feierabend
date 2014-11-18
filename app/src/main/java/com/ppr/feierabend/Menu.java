package com.ppr.feierabend;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import static android.widget.ToggleButton.*;
import static com.ppr.feierabend.R.id.switch1;


public class Menu extends Activity {

    private String FILENAME = "week_hours.txt";
    private int weekHours;
    private int s_40 = 40;
    private int s_38_5 = 38;
    private TextView txt1;
    CL_FILE CLFILE;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ToggleButton sw_week_h = (ToggleButton) findViewById(switch1);

        weekHours = CLFILE.GetFileData(FILENAME);

        if( weekHours == s_40){
            sw_week_h.setChecked(true);
        }
        else{
            if( weekHours == s_38_5){
                sw_week_h.setChecked(false);
            }
        }
        sw_week_h.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CLFILE.WriteFile(s_40,FILENAME);

                } else {
                    CLFILE.WriteFile(s_38_5,FILENAME);
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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
