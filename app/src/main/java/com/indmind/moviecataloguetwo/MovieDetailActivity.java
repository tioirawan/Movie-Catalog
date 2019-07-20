package com.indmind.moviecataloguetwo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.indmind.moviecataloguetwo.model.Movie;
import com.squareup.phrase.Phrase;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";

    @BindView(R.id.tv_detail_movie_title)
    TextView tvTitle;
    @BindView(R.id.tv_detail_movie_overview)
    TextView tvOverview;
    @BindView(R.id.tv_detail_movie_runtime)
    TextView tvRuntime;
    @BindView(R.id.tv_detail_movie_score)
    TextView tvScore;

    @BindView(R.id.img_detail_movie_poster)
    ImageView imgPoster;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindString(R.string.text_percent)
    String textPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        tvRuntime.setText(movie.getRuntime());

        tvScore.setText(Phrase.from(textPercent).put("value", movie.getScore()).format());

        imgPoster.setImageResource(movie.getPoster());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
