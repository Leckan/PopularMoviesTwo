package com.leckan.popularmoviestwo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leckan.popularmoviestwo.Model.MovieReview;
import com.leckan.popularmoviestwo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Leckan on 6/26/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();
    public List<MovieReview> reviewList;
    LayoutInflater inflater;


    public ReviewAdapter(List<MovieReview> reviews, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.reviewList = reviews;

    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.review_item, parent, false);

        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        MovieReview review = reviewList.get(position);
        holder.authorTextView.setText(review.getAuthor());
        holder.contentTextView.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
      @BindView(R.id.review_author)  TextView authorTextView;
      @BindView(R.id.review_content)  TextView contentTextView;
      @BindView(R.id.review_item_root)  View container;

        public ReviewViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }
    }
}
