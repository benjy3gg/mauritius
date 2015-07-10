package com.support.android.mauritius;

/**
 * Created by benjy3gg on 09.07.2015.
 */


import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CommentItemView extends LinearLayout {

    private LinearLayout mRoot;

    private TextView mAuthor;
    private ImageView mAvatar;
    private TextView mBody;

    private AppCompatCheckBox mCheckbox;

    private View mViewSeparator;

    public CommentItemView(Context context) {
        super(context);
        init();
    }

    public CommentItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommentItemView(Context context, AttributeSet attrs,
                           int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.comment_item, this);

        mRoot = (LinearLayout) findViewById(R.id.comment_item_root);
        mAuthor = (TextView) findViewById(R.id.commentAuthor);
        mBody = (TextView) findViewById(R.id.commentBody);
        mAvatar = (ImageView) findViewById(R.id.commentAvatar);

//        this.mRoot.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mCheckbox.setChecked(!mCheckbox.isChecked());
//            }
//        });
    }

    public void setAuthor(String text) {
        this.mAuthor.setText(text);
    }

    public void setBody(String text) {
        this.mBody.setText(text);
    }

    public void setAvatar(final String url) {
        Picasso.with(mAvatar.getContext())
                .load(url)
                .resize(64, 64)
                .into(mAvatar);
    }

}