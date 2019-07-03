package com.indmind.moviecataloguetwo;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.indmind.moviecataloguetwo.models.Movie;
import com.indmind.moviecataloguetwo.repositories.FavoriteMovieRepository;
import com.indmind.moviecataloguetwo.viewmodels.FavoriteMovieViewModel;
import com.squareup.phrase.Phrase;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";

    @BindView(R.id.tv_detail_movie_title)
    TextView tvTitle;
    @BindView(R.id.tv_detail_movie_overview)
    TextView tvOverview;
    @BindView(R.id.rb_detail_movie_score)
    RatingBar rbScore;

    @BindView(R.id.img_detail_movie_poster)
    ImageView imgPoster;
    @BindView(R.id.img_detail_movie_backdrop)
    ImageView imgBackdrop;

    @BindView(R.id.btn_detail_back)
    ImageButton btnBack;
    @BindView(R.id.btn_add_movie_favorite)
    ImageButton btnAddFavoriteMovie;

    @BindString(R.string.text_percent)
    String textPercent;
    @BindString(R.string.add_to_favorite)
    String addToFavorite;
    @BindString(R.string.remove_from_favorite)
    String removeFromFavorite;

    private FavoriteMovieViewModel movieViewModel;
    // set to true because we don't know until we check it
    private boolean movieIsInFavorite = true;
    private Movie currentMovie;
    private final FavoriteMovieRepository.MovieFactoryListener movieFactoryListener = new FavoriteMovieRepository.MovieFactoryListener() {

        @Override
        public void onMovieReceived(Movie movie) {
            if (movie == null) movieIsInFavorite = false;
            updateFavoriteButton();
        }

        @Override
        public void onMovieInserted() {
            movieIsInFavorite = true;
            updateFavoriteButton();
        }

        @Override
        public void onMovieDeleted() {
            movieIsInFavorite = false;
            updateFavoriteButton();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        currentMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        updateFavoriteButton();

        movieViewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
        movieViewModel.getMovieById(currentMovie.getId(), movieFactoryListener);

        tvTitle.setText(currentMovie.getTitle());
        tvOverview.setText(currentMovie.getOverview());
        rbScore.setRating(((currentMovie.getVote_average() / 10) * 5));

        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.img_backdrop_placeholder);

        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(Api.POSTER_BASE_URL_185 + currentMovie.getPoster_path())
                .into(imgPoster);

        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(Api.POSTER_BASE_URL + currentMovie.getBackdrop_path())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(10, 3)))
                .into(imgBackdrop);

        btnBack.setOnClickListener(v -> onBackPressed());
    }

    private void updateFavoriteButton() {
        if (movieIsInFavorite) {
            btnAddFavoriteMovie.setImageDrawable(getDrawable(R.drawable.ic_favorite_red_24dp));

            btnAddFavoriteMovie.setOnClickListener(v -> {
                Toast.makeText(this, Phrase.from(removeFromFavorite).put("show", currentMovie.getTitle()).format(), Toast.LENGTH_SHORT).show();
                movieViewModel.delete(currentMovie, movieFactoryListener);
            });
        } else {
            btnAddFavoriteMovie.setImageDrawable(getDrawable(R.drawable.ic_favorite_white_24dp));

            btnAddFavoriteMovie.setOnClickListener(v -> {
                Toast.makeText(this, Phrase.from(addToFavorite).put("show", currentMovie.getTitle()).format(), Toast.LENGTH_SHORT).show();
                movieViewModel.insert(currentMovie, movieFactoryListener);
            });
        }
    }
}
