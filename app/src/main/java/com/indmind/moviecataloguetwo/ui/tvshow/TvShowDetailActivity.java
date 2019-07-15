package com.indmind.moviecataloguetwo.ui.tvshow;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.indmind.moviecataloguetwo.R;
import com.indmind.moviecataloguetwo.data.entity.TvShow;
import com.indmind.moviecataloguetwo.utils.apis.ApiClient;

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

        TvShow currentTvShow = getIntent().getParcelableExtra(EXTRA_TV_SHOW);

        tvTitle.setText(currentTvShow.getName());
        tvOverview.setText(currentTvShow.getOverview());
        rbScore.setRating((float) ((currentTvShow.getVote_average() / 10) * 5));

        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.img_backdrop_placeholder);

        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(ApiClient.POSTER_BASE_URL_185 + currentTvShow.getPoster_path())
                .into(imgPoster);

        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(ApiClient.POSTER_BASE_URL + currentTvShow.getBackdrop_path())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(10, 3)))
                .into(imgBackdrop);

        btnBack.setOnClickListener(v -> onBackPressed());
    }
}
