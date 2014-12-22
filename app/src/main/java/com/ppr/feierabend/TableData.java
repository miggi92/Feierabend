package com.ppr.feierabend;

import android.provider.BaseColumns;

/**
 * Created by Miguel on 17.12.2014.
 */
public class TableData {

    public TableData() {

    }

    public static abstract class TableInfo implements BaseColumns {
        public static final String Day = "day"; //Colum of the table
        public static final String Time = "time";
        public static final String DATABASE_NAME = "home_time";
        public static final String TABLE_NAME = "ht";
    }

}
