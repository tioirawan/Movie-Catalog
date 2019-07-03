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
import com.indmind.moviecataloguetwo.models.TvShow;
import com.indmind.moviecataloguetwo.repositories.FavoriteTvShowRepository;
import com.indmind.moviecataloguetwo.viewmodels.FavoriteTvShowViewModel;
import com.squareup.phrase.Phrase;

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
    @BindView(R.id.btn_add_tv_show_favorite)
    ImageButton btnAddFavoriteTvShow;

    @BindString(R.string.text_percent)
    String textPercent;
    @BindString(R.string.add_to_favorite)
    String addToFavorite;
    @BindString(R.string.remove_from_favorite)
    String removeFromFavorite;

    private FavoriteTvShowViewModel tvShowViewModel;
    private boolean showIsInFavorite = true;
    private TvShow currentTvShow;
    private final FavoriteTvShowRepository.TvShowFactoryListener tvShowFactoryListener = new FavoriteTvShowRepository.TvShowFactoryListener() {
        @Override
        public void onTvShowReceived(TvShow tvShow) {
            if (tvShow == null) showIsInFavorite = false;
            updateFavoriteButton();
        }

        @Override
        public void onTvShowInserted() {
            showIsInFavorite = true;
            updateFavoriteButton();

        }

        @Override
        public void onTvShowDeleted() {
            showIsInFavorite = false;
            updateFavoriteButton();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        currentTvShow = getIntent().getParcelableExtra(EXTRA_TV_SHOW);
        updateFavoriteButton();

        tvShowViewModel = ViewModelProviders.of(this).get(FavoriteTvShowViewModel.class);
        tvShowViewModel.getTvShowById(currentTvShow.getId(), tvShowFactoryListener);

        tvTitle.setText(currentTvShow.getName());
        tvOverview.setText(currentTvShow.getOverview());
        rbScore.setRating((float) ((currentTvShow.getVote_average() / 10) * 5));

        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.img_backdrop_placeholder);

        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(Api.POSTER_BASE_URL_185 + currentTvShow.getPoster_path())
                .into(imgPoster);

        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(Api.POSTER_BASE_URL + currentTvShow.getBackdrop_path())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(10, 3)))
                .into(imgBackdrop);

        btnBack.setOnClickListener(v -> onBackPressed());
    }

    private void updateFavoriteButton() {
        if (showIsInFavorite) {
            btnAddFavoriteTvShow.setImageDrawable(getDrawable(R.drawable.ic_favorite_red_24dp));

            btnAddFavoriteTvShow.setOnClickListener(v -> {
                Toast.makeText(this, Phrase.from(removeFromFavorite).put("show", currentTvShow.getName()).format(), Toast.LENGTH_SHORT).show();
                tvShowViewModel.delete(currentTvShow, tvShowFactoryListener);
            });
        } else {
            btnAddFavoriteTvShow.setImageDrawable(getDrawable(R.drawable.ic_favorite_white_24dp));

            btnAddFavoriteTvShow.setOnClickListener(v -> {
                Toast.makeText(this, Phrase.from(addToFavorite).put("show", currentTvShow.getName()).format(), Toast.LENGTH_SHORT).show();
                tvShowViewModel.insert(currentTvShow, tvShowFactoryListener);
            });
        }
    }
}
