package com.popularmovies2.vignesh.popm2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.popularmovies2.vignesh.popm2.data.Reviews;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.reviewHolder>{
    private final int mBackground;
    private final Reviews[] reviewData;

    public ReviewAdapter(Context context, Reviews[] review) {
        TypedValue mTypedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        reviewData = review;
    }

    @Override
    public ReviewAdapter.reviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        view.setBackgroundResource(mBackground);

        return new ReviewAdapter.reviewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.reviewHolder holder, int position) {
        holder.authorTextView.setText(reviewData[position].getAuthorName());
        holder.contentTextView.setText(reviewData[position].getContent());
    }

    @Override
    public int getItemCount() {
        if (reviewData == null) return 0;
        return reviewData.length;
    }

    public class reviewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_author_name)
        TextView authorTextView;
        @BindView(R.id.tv_review_content)
        TextView contentTextView;

        public reviewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

    }

}
