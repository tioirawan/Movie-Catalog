package com.indmind.moviecataloguetwo.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.indmind.moviecataloguetwo.CustomOnItemClickListener;
import com.indmind.moviecataloguetwo.MovieDetailActivity;
import com.indmind.moviecataloguetwo.R;
import com.indmind.moviecataloguetwo.model.Movie;
import com.squareup.phrase.Phrase;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.MovieViewHolder> {
    private Context mContext;
    private ArrayList<Movie> listMovie;

    public ListMovieAdapter(Context mContext, ArrayList<Movie> listMovie) {
        this.mContext = mContext;
        this.listMovie = listMovie;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_row_movies, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder movieViewHolder, int i) {
        Movie movie = listMovie.get(i);

        movieViewHolder.tvTitle.setText(movie.getTitle());
        movieViewHolder.tvOverview.setText(movie.getOverview());
        movieViewHolder.imgPoster.setImageResource(movie.getPoster());

        movieViewHolder.imgPoster.setContentDescription(
                Phrase.from(movieViewHolder.posterWithTitle).put("title", movie.getTitle()).format()
        );

        movieViewHolder.itemView.setOnClickListener(new CustomOnItemClickListener(i, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(mContext, MovieDetailActivity.class);

                Pair[] pairs = new Pair[2];

                pairs[0] = new Pair<>(movieViewHolder.tvTitle, movieViewHolder.titleTransition);
                pairs[1] = new Pair<>(movieViewHolder.imgPoster, movieViewHolder.posterTransition);

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                        (Activity) mContext, pairs
                );

                intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, listMovie.get(position));

                mContext.startActivity(intent, options.toBundle());
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_movie_title)
        TextView tvTitle;
        @BindView(R.id.tv_item_movie_overview)
        TextView tvOverview;
        @BindView(R.id.img_item_movie_poster)
        ImageView imgPoster;

        @BindString(R.string.movie_title_transition)
        String titleTransition;
        @BindString(R.string.movie_poster_transition)
        String posterTransition;
        @BindString(R.string.poster_with_title)
        String posterWithTitle;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
