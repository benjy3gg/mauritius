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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 */
public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private ViewPager viewPager;
    private MaterialViewPager materialViewPager;
    private TabLayout tabLayout;
    private Adapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(newDrinkBroadcastReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        registerReceiver(newDrinkBroadcastReceiver, new IntentFilter("NEW_DRINK"));

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle("Mauritius");
//        toolbar.setSubtitle("Die beste Cocktail App");
        final String[] urlArray = {"http://cache4.asset-cache.net/gc/483636951-bartender-serves-two-mojito-cocktails-during-gettyimages.jpg?v=1&c=IWSAsset&k=2&d=X7WJLa88Cweo9HktRLaNXj%2fbyJLJoGDgnM08yE1wUIkYtJe9BJFcNp623v2oHLdd7XFgMTeUXJOjXAAsH1CRvG3pbL8S%2bhx9wL5X8fe8ywA%3d&b=NkI2",
                             "http://cache3.asset-cache.net/gc/553331677-cocktail-making-gettyimages.jpg?v=1&c=IWSAsset&k=2&d=eXI8V6OIbW%2b0S%2f1zVordgmQs7FrInnbTvDj6G3Wp1uDyCMXBp77fBjjHotXHohI7&b=Qzg=",
                             "http://cache3.asset-cache.net/gc/540070819-barware-gettyimages.jpg?v=1&c=IWSAsset&k=2&d=WeJFXXjPSy8iTSi6l2XDX%2fnKsolB%2bjCc2jh8yT6fgtbAYuZELX3uU1jGNxKm%2bfq6FZRcn7QhUtTL%2f96mhkTKfg%3d%3d&b=Njk=",
                             "http://cache4.asset-cache.net/gc/543231027-basil-lime-and-summer-cocktail-gettyimages.jpg?v=1&c=IWSAsset&k=2&d=XVov1qoymwwJTUfJLCm19MYHnWoKaDXF%2f74jFPVztCq%2f2tSfu6q29pnxAGUfkxXcC8jHrNcLehfTLp8NHezxIw%3d%3d&b=QjY=",
                             "http://cache1.asset-cache.net/gc/483636925-bartender-serves-cocktails-during-the-mix-gettyimages.jpg?v=1&c=IWSAsset&k=2&d=X7WJLa88Cweo9HktRLaNXsdJ0bd3pIUcgnCTMpmyX7Y6RWLKL47pnDvSw5KVVbTJFkVLjdYG30n4pXZGfRWtSNmqKUdP2yZWVW9x%2b7eohrk%3d&b=MkI=",
                             "http://cache4.asset-cache.net/gc/475516205-whiskey-cocktail-on-ice-with-a-straw-and-gettyimages.jpg?v=1&c=IWSAsset&k=2&d=ETNETh8aF5yzJ3WFyHhOmRhQlxWNKLe0N%2fHVgAW7vtHFW9LUhUI8H3cJJPH9W%2fTU&b=ODU2"};

        final String[] nameArray = {"Men\'s Health",
                "Women\'s Health",
                "Grasshopper",
                "Touchdown",
                "Zombie",
                "Long Island Icetea"};

        final String[] categoryArray = {"Alkoholfrei",
                "Fancy Classics",
                "Favourites",
                "Burner",
                "Top Rated",
                "Gangster"};

//        final ActionBar ab = getSupportActionBar();
//        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
//        ab.setDisplayHomeAsUpEnabled(true);

        materialViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        toolbar = materialViewPager.getToolbar();
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        if (toolbar != null) {
            setSupportActionBar(toolbar);

            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(false);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
        mDrawer.setDrawerListener(mDrawerToggle);

        setupViewPager(materialViewPager.getViewPager());


//        tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                int randA = 0 + (int)(Math.random() * (((urlArray.length-1) - 0) + 1));
                int randB = 0 + (int)(Math.random() * (((urlArray.length-1) - 0) + 1));
                int randC = 0 + (int)(Math.random() * (((urlArray.length-1) - 0) + 1));
                JSONArray myArray = new JSONArray();
                myArray.put("Ananas");
                myArray.put("Erdbeerpueree");
                myArray.put("Mangosirup");
                myArray.put("Kokossirup");
                myArray.put("Marshmallow");
                ParseObject drinkObject = new ParseObject("Drink");
                drinkObject.put("name", nameArray[randA]);
                drinkObject.put("category", categoryArray[randB]);
                drinkObject.put("price", 4.99);
                drinkObject.put("ingredients",myArray);
                drinkObject.put("imgUrl", urlArray[randC]);
                drinkObject.saveInBackground();
            }
        });

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null ) {
            TextView userText = (TextView) findViewById(R.id.usernameTextview);
            userText.setText(currentUser.getUsername());
            currentUser.getString("avatarUrl");

        } else {
            Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivityForResult(myIntent, 0);
        }


    }

    BroadcastReceiver newDrinkBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newDrinkId = intent.getStringExtra("drinkId");
            Log.d("MainActivityBroadcast", "drinkId: " + newDrinkId);
            setupViewPager(viewPager);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_logout:
                ParseUser.logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivityForResult(myIntent, 0);
                    }
                });

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(final ViewPager viewPager) {
        adapter = new Adapter(getSupportFragmentManager());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Category");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> categoryList, ParseException e) {

                if (e == null) {
                    for (int i = 0; i < categoryList.size(); i++) {
                        String categoryName = categoryList.get(i).getString("name");
                        adapter.addFragment(new DrinkListFragment(categoryName), categoryName);
                        Log.d("score", "CategoryName: " + categoryName);
                    }
                    Log.d("score", "Categories: " + categoryList.size());
                    materialViewPager.getViewPager().setAdapter(adapter);
                    materialViewPager.getViewPager().setOffscreenPageLimit(6);
                    materialViewPager.getPagerTitleStrip().setViewPager(materialViewPager.getViewPager());
                    materialViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
                        @Override
                        public HeaderDesign getHeaderDesign(int page) {
                            switch (page) {
                                case 0:
                                    return HeaderDesign.fromColorAndDrawable(
                                            getResources().getColor(R.color.teal_500),
                                            getResources().getDrawable(R.drawable.header_1, getTheme()));
                                case 1:
                                    return HeaderDesign.fromColorAndDrawable(
                                            getResources().getColor(R.color.teal_500),
                                            getResources().getDrawable(R.drawable.header_2, getTheme()));
                                case 2:
                                    return HeaderDesign.fromColorAndDrawable(
                                            getResources().getColor(R.color.teal_500),
                                            getResources().getDrawable(R.drawable.header_3, getTheme()));
                                case 3:
                                    return HeaderDesign.fromColorAndDrawable(
                                            getResources().getColor(R.color.teal_500),
                                            getResources().getDrawable(R.drawable.header_4, getTheme()));
                                case 4:
                                    return HeaderDesign.fromColorAndDrawable(
                                            getResources().getColor(R.color.teal_500),
                                            getResources().getDrawable(R.drawable.header_5, getTheme()));
                                case 5:
                                    return HeaderDesign.fromColorAndDrawable(
                                            getResources().getColor(R.color.teal_500),
                                            getResources().getDrawable(R.drawable.header_6, getTheme()));
                                case 6:
                                    return HeaderDesign.fromColorAndDrawable(
                                            getResources().getColor(R.color.teal_500),
                                            getResources().getDrawable(R.drawable.header_7, getTheme()));
                                case 7:
                                    return HeaderDesign.fromColorAndDrawable(
                                            getResources().getColor(R.color.teal_500),
                                            getResources().getDrawable(R.drawable.header_8, getTheme()));
                            }

                            //execute others actions if needed (ex : modify your header logo)

                            return null;
                        }
                    });
                    materialViewPager.getViewPager().setCurrentItem(0);

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }

            }

        });

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawer.closeDrawers();
                return true;
            }
        });
    }

    static class Adapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
            this.notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
