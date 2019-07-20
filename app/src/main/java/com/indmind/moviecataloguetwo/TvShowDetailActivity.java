package com.indmind.moviecataloguetwo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.indmind.moviecataloguetwo.model.TvShow;
import com.squareup.phrase.Phrase;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TvShowDetailActivity extends AppCompatActivity {
    public static final String EXTRA_TV_SHOW = "extra_tv_tv_show";

    @BindView(R.id.tv_detail_tv_show_title)
    TextView tvTitle;
    @BindView(R.id.tv_detail_tv_show_overview)
    TextView tvOverview;
    @BindView(R.id.tv_detail_tv_show_runtime)
    TextView tvRuntime;
    @BindView(R.id.tv_detail_tv_show_score)
    TextView tvScore;

    @BindView(R.id.img_detail_tv_show_poster)
    ImageView imgPoster;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindString(R.string.text_percent)
    String textPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);
        ButterKnife.bind(this);

        TvShow show = getIntent().getParcelableExtra(EXTRA_TV_SHOW);

        tvTitle.setText(show.getTitle());
        tvOverview.setText(show.getOverview());
        tvRuntime.setText(show.getRuntime());

        tvScore.setText(Phrase.from(textPercent).put("value", show.getScore()).format());

        imgPoster.setImageResource(show.getPoster());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
