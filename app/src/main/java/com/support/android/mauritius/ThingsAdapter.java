package com.support.android.mauritius;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bingoogolapple.badgeview.BGABadgeTextView;

/**
 * Created by benjy3gg on 08.07.2015.
 */
public class ThingsAdapter extends RecyclerView.Adapter<ThingsAdapter.ViewHolder> {

    private ParseQueryAdapter<ParseObject> parseAdapter;

    private ViewGroup parseParent;

    private ThingsAdapter thingsAdapter = this;
    private ProgressDialog mProgressDialog;
    private Context mContext;
    private BGABadgeTextView badgeRating;

    public ThingsAdapter(final Context context, ViewGroup parentIn, final String mCategory) {
        parseParent = parentIn;
        mContext = context;

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
                animateBadge();
                //badgeRating.setBackgroundColor(v.getResources().getColor(R.color.amber_500));
                DecimalFormat df = new DecimalFormat("#.##");
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

    public void animateBadge() {
        badgeRating.setAlpha(0);
        badgeRating.setScaleX(0);
        badgeRating.setScaleY(0);
        badgeRating.animate().alpha(1).scaleX(1).scaleY(1).setDuration(500).setStartDelay(500).setInterpolator(new BounceInterpolator()).start();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        parseAdapter.getView(position, holder.cardView, parseParent);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();

                Intent intent = new Intent(context, CheeseDetailActivity.class);
                intent.putExtra(CheeseDetailActivity.EXTRA_NAME, parseAdapter.getItem(position).getString("name"));
                intent.putExtra(CheeseDetailActivity.EXTRA_URL, parseAdapter.getItem(position).getString("imgUrl"));
                intent.putExtra(CheeseDetailActivity.EXTRA_ID, parseAdapter.getItem(position).getObjectId());
                intent.putExtra(CheeseDetailActivity.EXTRA_RATING, parseAdapter.getItem(position).getNumber("rating"));

                ArrayList<String>  al = (ArrayList<String>)parseAdapter.getItem(position).get("ingredients");
                String[] array = new String[al.size()];
                al.toArray(array);

                animateBadge();

                intent.putExtra(CheeseDetailActivity.EXTRA_INGREDIENTS, Arrays.toString(array));
                Pair<View, String> p1 = Pair.create((View) v.findViewById(R.id.avatar), "drinkImage");
                View vp = (View) v.getParent().getParent().getParent();
                android.support.design.widget.FloatingActionButton fab = (android.support.design.widget.FloatingActionButton) vp.findViewById(R.id.fab);

                Pair<View, String> p2 = Pair.create((View) fab, "fab");
                ActivityOptionsCompat options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation((MainActivity) context, p1, p2);
                ActivityCompat.startActivity((MainActivity) context, intent, options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return parseAdapter.getCount();
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
//            mProgressDialog.show();
        }

        public void onLoaded(List<ParseObject> objects, Exception e) {
            thingsAdapter.notifyDataSetChanged();
//            mProgressDialog.dismiss();
        }
    }
}
