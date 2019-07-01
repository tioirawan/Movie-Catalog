package com.indmind.moviecataloguetwo.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.indmind.moviecataloguetwo.Api;
import com.indmind.moviecataloguetwo.CustomOnItemClickListener;
import com.indmind.moviecataloguetwo.MovieDetailActivity;
import com.indmind.moviecataloguetwo.R;
import com.indmind.moviecataloguetwo.models.Movie;
import com.indmind.moviecataloguetwo.utils.GenreMapper;
import com.squareup.phrase.Phrase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.MovieViewHolder> {
    private static final String TAG = "ListMovieAdapter";

    private final Context mContext;
    private final ArrayList<Movie> listMovie = new ArrayList<>();

    public ListMovieAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setListMovie(ArrayList<Movie> listMovie) {
        this.listMovie.clear();
        this.listMovie.addAll(listMovie);
        notifyDataSetChanged();
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
        movieViewHolder.tvScore.setText(String.valueOf(new DecimalFormat("0.0").format(movie.getVote_average())));

        Glide.with(mContext).load(Api.POSTER_BASE_URL + movie.getPoster_path()).into(movieViewHolder.imgPoster);

        movieViewHolder.imgPoster.setContentDescription(
                Phrase.from(movieViewHolder.posterWithTitle).put("title", movie.getTitle()).format()
        );

        movieViewHolder.itemView.setOnClickListener(new CustomOnItemClickListener(i, position -> {
            Intent intent = new Intent(mContext, MovieDetailActivity.class);

            intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, listMovie.get(position));

            mContext.startActivity(intent);
        }));

        Map genresMapper = GenreMapper.getGenres(mContext);
        int[] movieGenres = movie.getGenre_ids();

        Log.d(TAG, "onBindViewHolder: " + movieGenres[0]);

        StringBuilder genres = new StringBuilder();

        for (int idx = 0; idx < movieGenres.length; idx++) {
            genres.append(genresMapper.get(movieGenres[idx])).append(idx < movieGenres.length - 1 ? ", " : "");
        }

        Log.d(TAG, "onBindViewHolder: " + movie.getTitle() + genres);

        movieViewHolder.tvGenre.setText(genres.toString());
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_movie_title)
        TextView tvTitle;
        @BindView(R.id.tv_item_movie_score)
        TextView tvScore;
        @BindView(R.id.tv_item_movie_genre)
        TextView tvGenre;
        @BindView(R.id.img_item_movie_poster)
        ImageView imgPoster;

        @BindString(R.string.movie_title_transition)
        String titleTransition;
        @BindString(R.string.movie_poster_transition)
        String posterTransition;
        @BindString(R.string.poster_with_title)
        String posterWithTitle;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
