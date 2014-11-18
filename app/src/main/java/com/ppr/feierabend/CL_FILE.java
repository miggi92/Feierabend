package com.ppr.feierabend;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Miguel on 13.11.2014.
 */
public class CL_FILE {
    private int file_output;
    Context context;

    public int GetFileData(String FILENAME){
        FileInputStream fis;

        try {
            fis = context.openFileInput(FILENAME);
            file_output = fis.read();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file_output;
    }

    public void WriteFile(int iv_file, String FILENAME){
        FileOutputStream fos;
        try {
            fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(iv_file);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
