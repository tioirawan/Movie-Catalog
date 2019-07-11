package com.indmind.moviecataloguetwo.ui.movie;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.indmind.moviecataloguetwo.R;
import com.indmind.moviecataloguetwo.data.Movie;
import com.indmind.moviecataloguetwo.utils.apis.ApiClient;

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

    @BindString(R.string.text_percent)
    String textPercent;
    @BindString(R.string.movie_not_found_desc)
    String movieNotFoundDesc;
    @BindString(R.string.movie_not_found)
    String movieNotFoundTitle;

    private Movie currentMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        currentMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        if (currentMovie == null) {
            currentMovie = getNotFoundMovieTemplate();
        }

        setupMovieData();

        btnBack.setOnClickListener(v -> onBackPressed());
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

        if (backdrop == null) {
            imgBackdrop.setImageDrawable(getResources().getDrawable(R.drawable.img_backdrop_placeholder));
        }
    }
}
