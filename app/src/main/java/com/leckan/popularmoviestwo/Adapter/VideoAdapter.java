package com.leckan.popularmoviestwo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.leckan.popularmoviestwo.Model.MovieVideo;
import com.leckan.popularmoviestwo.R;
import com.leckan.popularmoviestwo.Utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Leckan on 6/27/2017.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();
    public List<MovieVideo> videoList;
    LayoutInflater inflater;


    public VideoAdapter(List<MovieVideo> videos, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.videoList = videos;

    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.video_item, parent, false);

        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoAdapter.VideoViewHolder holder, int position) {
        MovieVideo video = videoList.get(position);

        URL img_url = NetworkUtils.buildYoutubeImageUrl(video.getKey());
        Picasso.with(this.inflater.getContext())
                .load(img_url.toString())
                .placeholder(R.drawable.ic_video)
                .into(holder.trailerImage);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }
    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
      // @BindView(R.id.tvVideoTitle) TextView nameTextView;
        @BindView(R.id.video_item_root)  View container;
        @BindView(R.id.ivVideo) ImageView trailerImage;

        public VideoViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            MovieVideo selectedMovie = videoList.get(clickedPosition);

            Intent intent = new Intent(Intent.ACTION_VIEW, NetworkUtils.buildYouTubeUri(selectedMovie.getKey()));
            inflater.getContext().startActivity(intent);
        }
    }
}