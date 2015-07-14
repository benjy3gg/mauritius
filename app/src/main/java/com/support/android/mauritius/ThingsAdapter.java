package com.support.android.mauritius;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import cn.bingoogolapple.badgeview.BGABadgeTextView;

/**
 * Created by benjy3gg on 08.07.2015.
 */
public class ThingsAdapter extends RecyclerView.Adapter<ThingsAdapter.ViewHolder> {

    private final String mCategory;
    private ParseQueryAdapter<ParseObject> parseAdapter;

    private ViewGroup parseParent;

    private ThingsAdapter thingsAdapter = this;
    private ProgressDialog mProgressDialog;
    private Context mContext;
    private BGABadgeTextView badgeRating;
    private RecyclerViewMaterialAdapter recyclerViewMaterialAdapter;
    private List<ParseObject> mDrinkList = new ArrayList<ParseObject>();

    public ThingsAdapter(final Context context, ViewGroup parentIn, final String category) {
        parseParent = parentIn;
        mContext = context;
        mCategory = category;

        ParseQueryAdapter.QueryFactory<ParseObject> factory =
            new ParseQueryAdapter.QueryFactory<ParseObject>() {
                public ParseQuery create() {
                    ParseQuery query = new ParseQuery("Drink");
                    query.whereEqualTo("category", mCategory);
                    query.orderByAscending("name");
                    return query;
                }
            };

        parseAdapter = new ParseQueryAdapter<ParseObject>(context, factory) {

            @Override
            public View getItemView(ParseObject object, View v, ViewGroup parent) {
                if (v == null) {
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                }
                super.getItemView(object, v, parent);
                //int position = parent.indexOfChild(v);

                ImageView imageView = (ImageView) v.findViewById(R.id.avatar);
                Picasso.with(context)
                        .load(object.getString("imgUrl"))
                        .fit()
                        .centerCrop()
                        .into(imageView);
                //imageView.loadInBackground();

                TextView nameView = (TextView) v.findViewById(R.id.item_text);
                nameView.setText(object.getString("name"));

                badgeRating = (BGABadgeTextView) v.findViewById(R.id.badgeRating);
                //badgeRating.setBackgroundColor(v.getResources().getColor(R.color.amber_500));
                DecimalFormat df = new DecimalFormat("#.#");
                String rating = df.format(object.getNumber("rating"));
                badgeRating.showTextBadge(rating);
                return v;
            }

        };
        parseAdapter.addOnQueryLoadListener(new OnQueryLoadListener());
        parseAdapter.loadObjects();
    }

    @Override
    public ThingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void animateBadge(int layoutPosition) {
        badgeRating.setAlpha(0);
        badgeRating.setScaleX(0);
        badgeRating.setScaleY(0);
        badgeRating.animate().alpha(1).scaleX(1).scaleY(1).setDuration(750).setStartDelay(200*layoutPosition).setInterpolator(new AccelerateDecelerateInterpolator()).start();
    }

    public void dataUpdateFromServer() {
        parseAdapter.loadObjects();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        parseAdapter.getView(position, holder.cardView, parseParent);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();

                Intent intent = new Intent(context, CheeseDetailActivity.class);
                intent.putExtra(CheeseDetailActivity.EXTRA_NAME, mDrinkList.get(position).getString("name"));
                intent.putExtra(CheeseDetailActivity.EXTRA_URL, mDrinkList.get(position).getString("imgUrl"));
                intent.putExtra(CheeseDetailActivity.EXTRA_ID, mDrinkList.get(position).getObjectId());
                double rating = mDrinkList.get(position).getDouble("rating");
                int ratingInt = (int)Math.round(rating * 10);

                intent.putExtra(CheeseDetailActivity.EXTRA_RATING, ratingInt);

                ArrayList<String> al = (ArrayList<String>) mDrinkList.get(position).get("ingredients");
                String[] array = new String[al.size()];
                al.toArray(array);

                animateBadge(holder.getAdapterPosition());

                intent.putExtra(CheeseDetailActivity.EXTRA_INGREDIENTS, Arrays.toString(array));
                Pair<View, String> p1 = Pair.create((View) v.findViewById(R.id.avatar), "drinkImage");
                View vp = (View) v.getParent().getParent().getParent();
                android.support.design.widget.FloatingActionButton fab = (android.support.design.widget.FloatingActionButton) vp.findViewById(R.id.fab);

                Pair<View, String> p2 = Pair.create((View) fab, "fab");
                ActivityOptionsCompat options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation((MainActivity) context, p1);
                ActivityCompat.startActivity((MainActivity) context, intent, options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return parseAdapter.getCount();
    }

    public void setMaterialAdapter(RecyclerViewMaterialAdapter materialAdapter) {
        recyclerViewMaterialAdapter = materialAdapter;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View cardView;

        public ViewHolder(View v) {
            super(v);
            cardView = v;
        }
    }

    public class OnQueryLoadListener implements ParseQueryAdapter.OnQueryLoadListener<ParseObject> {

        public void onLoading() {
//            ProgressBar pb = new ProgressBar(mContext);
//            pb.setIndeterminate(true);
//            pb.setIndeterminateTintMode(PorterDuff.Mode.SRC_ATOP);
//            pb.setIndeterminateTintList(ColorStateList.valueOf(R.color.amber_500));
//            parseParent.
//            parseParent.addView(pb);
//            mProgressDialog = new ProgressDialog(mContext);
//            mProgressDialog.setIndeterminate(true);
//            mProgressDialog.setTitle(mCategory);
//            mProgressDialog.show();
        }

        public void onLoaded(List<ParseObject> objects, Exception e) {
            mDrinkList.clear();
            mDrinkList.addAll(objects);
            thingsAdapter.notifyDataSetChanged();
            recyclerViewMaterialAdapter.mvp_notifyDataSetChanged();

            //drinks = (ObservableList<ParseObject>)objects;
//            mProgressDialog.dismiss();
        }
    }

    public void updateDrinkRating(int rating) {
        //parseAdapter.getItem().n
    }
}
