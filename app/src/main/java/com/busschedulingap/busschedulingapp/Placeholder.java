package com.busschedulingap.busschedulingapp;

import androidx.appcompat.app.AppCompatActivity;

public class Placeholder extends AppCompatActivity {
    public static boolean editClicked = false;
    public static String cbuskey;
    public static int resid, availseatno, rescount;
    public static String reskey, currentdate, selectedbus, cplate_number;
    public static boolean availseatsChanged = false;

    public static Boolean getEditClick(){
        return editClicked;
    }
    public static void setEditClick(boolean b){ editClicked = b; }

}


