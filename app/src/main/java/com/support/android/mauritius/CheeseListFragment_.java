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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CheeseListFragment_ extends Fragment {

    private List<Drink> drinkList = new ArrayList<Drink>();
    private String mCategory = "";
    public Context context;
    private int lastPosition = -1;
    private RecyclerView mRecyclerView;
    private SimpleStringRecyclerViewAdapter mAdapter;

    public CheeseListFragment_(String categoryName) {
        mCategory = categoryName;
    }

    public CheeseListFragment_() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDrinksByCategoryFromServer(mCategory);

    }

    public void getDrinksByCategoryFromServer(String category) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Drink");
        query.whereEqualTo("category", category);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> drinkListRes, ParseException e) {
                if (e == null) {
                    if (drinkListRes != null) {
                        Log.d("score", "Retrieved " + drinkListRes.size() + " scores");
                        for (int i = 0; i < drinkListRes.size(); i++) {
                            Drink di = new Drink(drinkListRes.get(i).getString("name"), drinkListRes.get(i).getString("category"), drinkListRes.get(i).getInt("price"), drinkListRes.get(i).getString("imgUrl"));
                            Log.d("added", "Name " + di.getName() + "Category " + di.getCategory() + "Price " + di.getPrice());
                            drinkList.add(di);
                        }
                        Log.d("drinks: ", "" + drinkList.size());

                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
                mAdapter.update(drinkList);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_drink_list, container, false);
        setupRecyclerView(rv);
        setRetainInstance(true);
        return rv;
    }

    private void setupRecyclerView(final RecyclerView recyclerView) {
        mAdapter = new SimpleStringRecyclerViewAdapter(getActivity(), drinkList);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(mAdapter);
    }

    private List<String> getRandomSublist(String[] array, int amount) {
        ArrayList<String> list = new ArrayList<>(amount);
        Random random = new Random();
        while (list.size() < amount) {
            list.add(array[random.nextInt(array.length)]);
        }
        return list;
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<Drink> mValues;
        private Context mContext;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public String mBoundString;
            public String mBoundUrl;

            public final View mView;
            public final ImageView mImageView;
            public final TextView mTextView;
            public Ingredient[] mBoundIngredients;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.avatar);
                mTextView = (TextView) view.findViewById(android.R.id.text1);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }

        public Drink getValueAt(int position) {
            return mValues.get(position);
        }

        public SimpleStringRecyclerViewAdapter(Context context, List<Drink> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
            mContext = context;
        }

        public void update(List<Drink> items) {
            mValues = items;
            this.notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mBoundString = mValues.get(position).getName();
            holder.mBoundIngredients = mValues.get(position).getIngredients();
            holder.mBoundUrl = mValues.get(position).getImgUrl();
            holder.mTextView.setText(mValues.get(position).getName());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, CheeseDetailActivity.class);
                    intent.putExtra(CheeseDetailActivity.EXTRA_NAME, holder.mBoundString);
                    intent.putExtra(CheeseDetailActivity.EXTRA_URL, holder.mBoundUrl);
                    intent.putExtra(CheeseDetailActivity.EXTRA_URL, holder.mBoundUrl);
                    Pair<View, String> p1 = Pair.create((View) holder.mImageView, "drinkImage");
                    View vp = (View) v.getParent().getParent().getParent();
                    android.support.design.widget.FloatingActionButton fab = (android.support.design.widget.FloatingActionButton) vp.findViewById(R.id.fab);

                    Pair<View, String> p2 = Pair.create((View) fab, "fab");
                    ActivityOptionsCompat options =
                            ActivityOptionsCompat.makeSceneTransitionAnimation((MainActivity) context, p1, p2);
                    ActivityCompat.startActivity((MainActivity) context, intent, options.toBundle());
                }
            });

            Picasso.with(holder.mImageView.getContext())
                    .load(holder.mBoundUrl)
                    .resize(64,64)
                    .into(holder.mImageView);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }
}
