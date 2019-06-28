package com.indmind.moviecataloguetwo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.indmind.moviecataloguetwo.models.TvShow;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class TvShowDetailActivity extends AppCompatActivity {
    public static final String EXTRA_TV_SHOW = "extra_tv_show";

    @BindView(R.id.tv_detail_tv_show_title)
    TextView tvTitle;
    @BindView(R.id.tv_detail_tv_show_overview)
    TextView tvOverview;
    @BindView(R.id.rb_detail_tv_show_score)
    RatingBar rbScore;

    @BindView(R.id.img_detail_tv_show_poster)
    ImageView imgPoster;
    @BindView(R.id.img_detail_tv_show_backdrop)
    ImageView imgBackdrop;

    @BindView(R.id.btn_detail_back)
    ImageButton btnBack;

    @BindString(R.string.text_percent)
    String textPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        TvShow show = getIntent().getParcelableExtra(EXTRA_TV_SHOW);

        tvTitle.setText(show.getName());
        tvOverview.setText(show.getOverview());
        rbScore.setRating((float) ((show.getVote_average() / 10) * 5));

        Glide.with(this)
                .load(Api.POSTER_BASE_URL_185 + show.getPoster_path())
                .into(imgPoster);

        Glide.with(this)
                .load(Api.POSTER_BASE_URL + show.getBackdrop_path())
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
