package com.androidnetworking.appweather_hoang.utils;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class DeviceUtils {

    public static void hideKeyboard(View view, Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
