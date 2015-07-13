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
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;

import com.github.florent37.materialviewpager.MaterialViewPagerAnimator;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DrinkListFragment extends Fragment {
    final String BUNDLE_RECYCLER_LAYOUT = "DrinkListFragment.recycler.layout";

    private List<Drink> drinkList = new ArrayList<Drink>();
    private String mCategory = "";
    public Context context;
    private int lastPosition = -1;
    private RecyclerView mRecyclerView;
    private RecyclerViewMaterialAdapter mAdapter;

    public DrinkListFragment(String categoryName) {
        mCategory = categoryName;
        setRetainInstance(true);
    }

    public DrinkListFragment() {

    }

    public static DrinkListFragment newInstance() {
        return new DrinkListFragment();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, mRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(savedInstanceState != null)
        {
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //setRetainInstance(true);
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_drink_list, container, false);
        setupRecyclerView(mRecyclerView);
        setRetainInstance(true);
        return mRecyclerView;
        //return inflater.inflate(R.layout.fragment_drink_list, container, false);
    }


    private void setupRecyclerView(final RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        //mRecyclerView.setHasFixedSize(true);
        ThingsAdapter tAdapter = new ThingsAdapter(getActivity(), recyclerView, mCategory);
        mAdapter = new RecyclerViewMaterialAdapter(tAdapter,2);
        tAdapter.setMaterialAdapter(mAdapter);
        recyclerView.setAdapter(mAdapter);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), recyclerView, null);
    }


}
