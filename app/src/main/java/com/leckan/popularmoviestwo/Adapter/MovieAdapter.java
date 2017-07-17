package com.leckan.popularmoviestwo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leckan.popularmoviestwo.Model.Movie;
import com.leckan.popularmoviestwo.R;
import com.leckan.popularmoviestwo.UI.MoviesDetail;
import com.leckan.popularmoviestwo.Utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Leckan on 5/14/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {


    private static final String TAG = MovieAdapter.class.getSimpleName();
    // final private ListItemClickListener mOnClickListener;
    private static int viewHolderCount;
   public List<Movie> movieList;
    LayoutInflater inflater;

    // private int mNumberItems;


    public MovieAdapter(List<Movie> movies, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.movieList = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.movie_item,parent,false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.viewMovieName.setText(movie.getOriginal_title());
        URL url = NetworkUtils.buildImageUrl(movie.getPoster_path());
        Picasso.with(this.inflater.getContext()).load(url.toString()).placeholder(R.drawable.ic_photo)
                .error(R.drawable.ic_photo)
                .into( holder.listMovieImageView);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

       @BindView(R.id.movie_image) ImageView listMovieImageView;
       @BindView(R.id.movie_name) TextView viewMovieName;
       @BindView(R.id.movie_item_root) View container;


        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int clickedPosition = getAdapterPosition();

            Movie selectedMovie = movieList.get(clickedPosition);
            Intent intent = new Intent(inflater.getContext(), MoviesDetail.class);
            intent.putExtra("theMovie",selectedMovie);
            inflater.getContext().startActivity(intent);
        }
    }
}
