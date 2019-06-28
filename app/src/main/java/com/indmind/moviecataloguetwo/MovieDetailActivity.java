package com.indmind.moviecataloguetwo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.indmind.moviecataloguetwo.models.Movie;

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

    @BindString(R.string.text_percent)
    String textPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        rbScore.setRating((float) ((movie.getVote_average() / 10) * 5));

        Glide.with(this)
                .load(Api.POSTER_BASE_URL_185 + movie.getPoster_path())
                .into(imgPoster);

        Glide.with(this)
                .load(Api.POSTER_BASE_URL + movie.getBackdrop_path())
                .transform(new BlurTransformation(25, 3))
                .into(imgBackdrop);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
