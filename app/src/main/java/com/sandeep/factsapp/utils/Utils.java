package com.sandeep.factsapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {

    public static boolean isNetworkAvailable(Context context) {
        if (context == null) return false;
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm != null ? cm.getActiveNetworkInfo() : null;
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
