package com.ppr.feierabend;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.widget.ToggleButton.*;
import static com.ppr.feierabend.R.id.switch1;


public class Menu extends Activity {

    private String FILENAME = "week_hours.txt";
    private int weekHours;
    private int s_40 = 40;
    private int s_38_5 = 38;
    private int lv_value;
    private TextView txt1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ToggleButton sw_week_h = (ToggleButton) findViewById(switch1);

        weekHours = GetFileData(FILENAME);

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
                    WriteFile(s_40, FILENAME);

                } else {
                    WriteFile(s_38_5, FILENAME);
                }
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
            fos.write(38);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
