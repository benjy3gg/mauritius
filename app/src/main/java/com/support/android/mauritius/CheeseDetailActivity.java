/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.support.android.mauritius;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;


import com.expandable.view.ExpandableView;
import com.github.alexkolpa.fabtoolbar.FabToolbar;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;
import com.txusballesteros.widgets.AnimationMode;
import com.txusballesteros.widgets.FitChart;
import com.txusballesteros.widgets.FitChartValue;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

import static com.parse.ParseObject.*;

public class CheeseDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "cheese_name";
    public static final String EXTRA_URL = "imgUrl";
    public static final String EXTRA_INGREDIENTS = "ingredients";
    public static final String EXTRA_ID = "drinkid";
    public static final String EXTRA_RATING = "drinkrating";

    private String imgUrl;
    private String ingredients;
    private String drinkId;
    private float drinkrating;
    private final String[] testAuthors = new String[]{"tester", "Hase", "Mongo"};
    private final String[] testComments = new String[]{"Ganz schoen gut", "Mega Geil", "Scheisse"};
    private final String[] testUrls = new String[]{ "http://cache3.asset-cache.net/gc/553331677-cocktail-making-gettyimages.jpg?v=1&c=IWSAsset&k=2&d=eXI8V6OIbW%2b0S%2f1zVordgmQs7FrInnbTvDj6G3Wp1uDyCMXBp77fBjjHotXHohI7&b=Qzg=",
                          "http://cache3.asset-cache.net/gc/540070819-barware-gettyimages.jpg?v=1&c=IWSAsset&k=2&d=WeJFXXjPSy8iTSi6l2XDX%2fnKsolB%2bjCc2jh8yT6fgtbAYuZELX3uU1jGNxKm%2bfq6FZRcn7QhUtTL%2f96mhkTKfg%3d%3d&b=Njk=",
                          "http://cache4.asset-cache.net/gc/543231027-basil-lime-and-summer-cocktail-gettyimages.jpg?v=1&c=IWSAsset&k=2&d=XVov1qoymwwJTUfJLCm19MYHnWoKaDXF%2f74jFPVztCq%2f2tSfu6q29pnxAGUfkxXcC8jHrNcLehfTLp8NHezxIw%3d%3d&b=QjY="};

    BroadcastReceiver updateDrinkBroadcastReciever;
    private String drinkName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        drinkName = intent.getStringExtra(EXTRA_NAME);
        imgUrl = intent.getStringExtra(EXTRA_URL);
        ingredients = intent.getStringExtra(EXTRA_INGREDIENTS);
        drinkId = intent.getStringExtra(EXTRA_ID);
        drinkrating = intent.getIntExtra(EXTRA_RATING, 0);

        TextView textView = (TextView) findViewById(R.id.ingredientTextview);
        textView.setText(ingredients);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(drinkName);



        BroadcastReceiver newDrinkBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String newDrinkId = intent.getStringExtra("drinkId");

            }
        };

        setupDetail();

    }

    private void updateComments() {

    }

    private void updateRating(int rating) {

    }



    private void setupDetail() {

        loadBackdrop();

        registerReceiver(updateDrinkBroadcastReciever, new IntentFilter("UPDATE_DRINK"));
        //
        ExpandableView expView = (ExpandableView) findViewById(R.id.expView);
        createTopExpandableView(expView);

        final FabToolbar fabToolbar = (FabToolbar) findViewById(R.id.fab_toolbar);
        findViewById(R.id.main_content).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fabToolbar.hide();
            }
        });
        findViewById(R.id.nestView).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fabToolbar.hide();
            }
        });



//        fabToolbar.setButtonOnClickListener(new View.OnClickListener() {
//
//            private LinearLayout detailOverlay;
//
//            @Override
//            public void onClick(View v) {
//                detailOverlay = (LinearLayout) findViewById(R.id.detailOverlay);
//                detailOverlay.animate().alpha(0.3f).setDuration(1000).setInterpolator(new AccelerateDecelerateInterpolator()).start();
//                detailOverlay.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        fabToolbar.hide();
//                        detailOverlay.animate().alpha(0f).setDuration(1000).setInterpolator(new AccelerateDecelerateInterpolator()).start();
//                    }
//                });
//            }
//        });

        fabToolbar.setColor(getResources().getColor(R.color.amber_500));

        final DiscreteSeekBar progressBar = (DiscreteSeekBar) findViewById(R.id.ratingIndicator);
        progressBar.setAlpha(0);
        progressBar.showFloater();
        progressBar.setTrackColor(getResources().getColor(R.color.white));
        progressBar.setThumbColor(getResources().getColor(R.color.white), getResources().getColor(R.color.cyan_500));
        progressBar.setScrubberColor(getResources().getColor(R.color.white));
        animateRatingIndicator(progressBar);
        progressBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            int mRating;
            @Override
            public void onProgressChanged(DiscreteSeekBar discreteSeekBar, int i, boolean b) {
                mRating = i;
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar discreteSeekBar) {

            }

            @Override
            public void onStopTrackingTouch(final DiscreteSeekBar discreteSeekBar) {
                fabToolbar.hide();
                // Create a pointer to an object of class Point with id dlkj83d
                ParseObject point = createWithoutData("Drink", drinkId);

                // Set a new value on quantity
                JSONObject myObject = new JSONObject();
                ParseUser currentUser = ParseUser.getCurrentUser();
                ParseUser pu = ParseUser.createWithoutData(ParseUser.class, currentUser.getObjectId());
                try {
                    myObject.put("user", pu);

                    myObject.put("rating", mRating);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                point.add("likes", myObject);

                // Save
                point.saveInBackground(new SaveCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            Snackbar.make((View) discreteSeekBar.getParent(), "Du hast " + drinkName + " " + mRating + " Sterne gegeben!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                            //ratingBar.animate().alpha(0).setDuration(500);
                            //ratingBar.animate().translationXBy(1000).setDuration(1000).setInterpolator(new AccelerateDecelerateInterpolator());
                        } else {
                            // The save failed.
                        }
                    }
                });
            }
        });

        //reveal the bottom view
        final View myView = findViewById(R.id.nestView);
        myView.setAlpha(0);
        myView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    myView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    myView.animate().alpha(1f).setDuration(1000).setStartDelay(500).start();
                } else {
                    myView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int cx = (myView.getLeft() + myView.getRight()) / 2;
                    int cy = (myView.getTop() + myView.getBottom()) / 2;

                    // get the final radius for the clipping circle
                    int finalRadius = Math.max(myView.getWidth(), myView.getHeight());

                    // create the animator for this view (the start radius is zero)
                    Animator anim =
                            ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);
                    anim.setStartDelay(500);
                    anim.setDuration(1000);
                    anim.setInterpolator(new AccelerateDecelerateInterpolator());
                    // make the view visible and start the animation
                    //v.setVisibility(View.VISIBLE);
                    myView.animate().alpha(1f).setDuration(1000).setStartDelay(500).start();
                    anim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            setupCounter(progressBar.getProgress(), drinkrating);
                        }
                        @Override
                        public void onAnimationCancel(Animator animation) {
                        }
                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }

                    });
                    anim.start();
                }

            }
        });
    }

    private void setupCounter(int progress, float drinkrating) {
        FitChart fitChart = (FitChart) findViewById(R.id.fitChart);
        fitChart.setMinValue(0f);
        fitChart.setMaxValue(30f);
        Collection<FitChartValue> values = new ArrayList<>();
        values.add(new FitChartValue((int)(this.drinkrating *10), Color.parseColor("#FF0000")));
        values.add(new FitChartValue(progress, Color.parseColor("#00FF00")));
        fitChart.setValues(values);
    }


    private void createTopExpandableView(ExpandableView topExpandableView) {
        ExpandableView expandableViewLevel1 = new ExpandableView(this);
        topExpandableView.fillData(0, "Kommentare", true);
        addContentView(topExpandableView, testAuthors, testComments, testUrls, true);
        topExpandableView.addContentView(expandableViewLevel1);
        expandableViewLevel1.setOutsideContentLayout(topExpandableView.getContentLayout());
    }

    public void addContentView(ExpandableView view, String[] authorList, String[] commentList, String[] urlList, boolean showCheckbox) {

        for (int i = 0; i < authorList.length; i++) {
            CommentItemView itemView = new CommentItemView(this);
            itemView.setAuthor(authorList[i]);
            itemView.setBody(commentList[i]);
            itemView.setAvatar(urlList[i]);
            view.addContentView(itemView);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    finish();
                } else {
                    finishAfterTransition();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void animateRatingIndicator(DiscreteSeekBar progressBar) {
        //progressBar.setIndeterminate(false);
        progressBar.setProgress(0);
        progressBar.setIndicatorPopupEnabled(true);
        progressBar.animate().alpha(1).setDuration(500);
        float progress = drinkrating;
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, (int) progress);
        animation.setDuration(1000);
        animation.setStartDelay(1000);
        animation.start();
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        //Picasso.with(this).load(Cheeses.getRandomCheeseDrawable()).resize(256, 256).into(imageView);
        //TODO: check why <LOLLIPOP the backdrop isn't showing
        imageView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    // Wait until layout to call Picasso
                    @Override
                    public void onGlobalLayout() {
                        // Ensure we call this only once
                        imageView.getViewTreeObserver()
                                .removeOnGlobalLayoutListener(this);


                        Picasso.with(imageView.getContext())
                                .load(imgUrl)
                                .resize(imageView.getWidth()/2, 0)
                                .into(imageView);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }
}
