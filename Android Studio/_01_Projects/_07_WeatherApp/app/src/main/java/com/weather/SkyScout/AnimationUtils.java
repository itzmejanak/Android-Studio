package com.weather.SkyScout;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;

public class AnimationUtils {

    public static void fadeIn(Context context, View view) {
        Animation fadeIn = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.fade_in);
        view.startAnimation(fadeIn);
    }

    public static void fadeOut(Context context, View view) {
        Animation fadeOut = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.fade_out);
        view.startAnimation(fadeOut);
    }

    public static void fadeIn(Context context, View... views) {
        for (View view : views) {
            Animation fadeIn = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.fade_in);
            view.startAnimation(fadeIn);
        }
    }
}
