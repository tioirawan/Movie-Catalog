package com.indmind.moviecataloguetwo.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.indmind.moviecataloguetwo.R;
import com.indmind.moviecataloguetwo.apis.ApiClient;
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
    public static final String EXTRA_MOVIE_ID = "extra_movie_id";

    @BindView(R.id.tv_detail_movie_title)
    TextView tvTitle;
    @BindView(R.id.tv_detail_movie_overview)
    TextView tvOverview;
    @BindView(R.id.tv_detail_movie_release)
    TextView tvRelease;
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
    @BindString(R.string.movie_not_found_desc)
    String movieNotFoundDesc;
    @BindString(R.string.movie_not_found)
    String movieNotFoundTitle;

    private FavoriteMovieViewModel favMovieViewModel;

    private boolean movieIsInFavorite = false;
    private boolean extraIsId = false;
    private boolean movieNotFound = false;
    private Movie currentMovie;

    private final FavoriteMovieRepository.FavoriteMovieRepositoryListener favoriteMovieRepositoryListener = new FavoriteMovieRepository.FavoriteMovieRepositoryListener() {
        @Override
        public void onMovieReceived(Movie movie, int source) {
            if (movie != null) {
                movieIsInFavorite = source == FavoriteMovieRepository.SOURCE_DB;
            }

            if (extraIsId) {
                if (movie != null) {
                    currentMovie = movie;
                } else {
                    currentMovie = getNotFoundMovieTemplate();
                    movieNotFound = true;
                }

                setupMovieData();
            }

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
        int movieId = getIntent().getIntExtra(EXTRA_MOVIE_ID, 0);

        if (currentMovie == null) {
            if (movieId > 0) {
                extraIsId = true;

                Movie movie = getNotFoundMovieTemplate();

                movie.setTitle("Loading...");

                currentMovie = movie;
            } else {
                currentMovie = getNotFoundMovieTemplate();
            }
        }

        updateFavoriteButton();

        favMovieViewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
        favMovieViewModel.getMovieById(movieId > 0 ? movieId : currentMovie.getId(), favoriteMovieRepositoryListener);

        if (currentMovie != null) setupMovieData();

        btnBack.setOnClickListener(v -> {
            if (extraIsId) {
                Intent intent = new Intent(this, MainActivity.class);

                intent.putExtra(MainActivity.EXTRA_INITIAL_PAGE, MainActivity.PAGE_FAVORITE);

                startActivity(intent);
                finish();
            } else {
                onBackPressed();
            }
        });
    }

    private Movie getNotFoundMovieTemplate() {
        return new Movie(
                0, 0, false, 0, movieNotFoundTitle, 0.0,
                "", "", "", "",
                false, movieNotFoundDesc, "", null, null
        );
    }

    private void setupMovieData() {
        tvTitle.setText(currentMovie.getTitle());
        tvOverview.setText(currentMovie.getOverview());
        tvRelease.setText(currentMovie.getRelease_date());
        rbScore.setRating(((currentMovie.getVote_average() / 10) * 5));

        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.img_backdrop_placeholder);

        String poster = currentMovie.getPoster_path();
        String backdrop = currentMovie.getBackdrop_path();

        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(ApiClient.POSTER_BASE_URL_185 + poster)
                .into(imgPoster);


        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(ApiClient.POSTER_BASE_URL + backdrop)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(10, 3)))
                .into(imgBackdrop);

        if (movieNotFound || backdrop == null) {
            imgBackdrop.setImageDrawable(getResources().getDrawable(R.drawable.img_backdrop_placeholder));
        }
    }

    private void updateFavoriteButton() {
        if (movieIsInFavorite) {
            btnAddFavoriteMovie.setImageDrawable(getDrawable(R.drawable.ic_favorite_red_24dp));

            btnAddFavoriteMovie.setOnClickListener(v -> {
                Toast.makeText(this, Phrase.from(removeFromFavorite).put("show", currentMovie.getTitle()).format(), Toast.LENGTH_SHORT).show();
                favMovieViewModel.delete(currentMovie, favoriteMovieRepositoryListener);
            });
        } else {
            btnAddFavoriteMovie.setImageDrawable(getDrawable(R.drawable.ic_favorite_white_24dp));

            btnAddFavoriteMovie.setOnClickListener(v -> {
                Toast.makeText(this, Phrase.from(addToFavorite).put("show", currentMovie.getTitle()).format(), Toast.LENGTH_SHORT).show();
                favMovieViewModel.insert(currentMovie, favoriteMovieRepositoryListener);
            });
        }
    }
}
