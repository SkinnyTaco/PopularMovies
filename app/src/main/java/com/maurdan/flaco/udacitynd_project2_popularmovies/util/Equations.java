package com.maurdan.flaco.udacitynd_project2_popularmovies.util;

import android.content.res.Resources;

public class Equations {

    public static int getNumberOfColums() {
        float dpWidth = getDpWidth();
        int numOfColumns = (int) dpWidth / 180;
        numOfColumns = (numOfColumns > 1) ? numOfColumns : 1;
        return numOfColumns;
    }

    public static float getDpWidth() {
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        return screenWidth / Resources.getSystem().getDisplayMetrics().density;
    }

}
