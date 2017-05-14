package com.leckan.popularmoviestwo.Utilities;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Simpa on 5/14/2017.
 */

public class Utility {
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }
}