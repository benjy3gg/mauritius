package com.support.android.mauritius;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RatingBar;

/**
 * Created by benjy3gg on 09.07.2015.
 */
public class CustomRatingBar extends RatingBar {
    public CustomRatingBar(Context context) {
        super(context);
    }

    @Override
    public OnRatingBarChangeListener getOnRatingBarChangeListener() {
        return new OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        };
    }
}
