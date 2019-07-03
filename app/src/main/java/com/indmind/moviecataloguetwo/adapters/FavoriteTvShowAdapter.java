package com.indmind.moviecataloguetwo.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.indmind.moviecataloguetwo.Api;
import com.indmind.moviecataloguetwo.CustomOnItemClickListener;
import com.indmind.moviecataloguetwo.R;
import com.indmind.moviecataloguetwo.TvShowDetailActivity;
import com.indmind.moviecataloguetwo.models.TvShow;
import com.indmind.moviecataloguetwo.utils.GenreMapper;
import com.squareup.phrase.Phrase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteTvShowAdapter extends RecyclerView.Adapter<FavoriteTvShowAdapter.FavoriteViewHolder> {
    private final Context mContext;
    private List<TvShow> tvShows = new ArrayList<>();

    public FavoriteTvShowAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setTvShows(List<TvShow> tvShows) {
        this.tvShows = tvShows;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_tv_shows, viewGroup, false);

        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder favoriteViewHolder, int i) {
        TvShow tvShow = tvShows.get(i);

        favoriteViewHolder.tvTitle.setText(tvShow.getName());
        favoriteViewHolder.tvScore.setText(String.valueOf(new DecimalFormat("0.0").format(tvShow.getVote_average())));

        Glide.with(mContext).load(Api.POSTER_BASE_URL + tvShow.getPoster_path()).into(favoriteViewHolder.imgPoster);

        favoriteViewHolder.imgPoster.setContentDescription(
                Phrase.from(favoriteViewHolder.posterWithTitle).put("title", tvShow.getName()).format()
        );

        favoriteViewHolder.itemView.setOnClickListener(new CustomOnItemClickListener(i, position -> {
            Intent intent = new Intent(mContext, TvShowDetailActivity.class);

            intent.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW, tvShows.get(position));

            mContext.startActivity(intent);
        }));

        SparseArray<String> genresMapper = GenreMapper.getGenres(mContext);
        int[] tvShowGenres = tvShow.getGenre_ids();

        StringBuilder genres = new StringBuilder();

        for (int idx = 0; idx < tvShowGenres.length; idx++) {
            genres.append(genresMapper.get(tvShowGenres[idx])).append(idx < tvShowGenres.length - 1 ? ", " : "");
        }

        favoriteViewHolder.tvGenre.setText(genres.toString());
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_tv_show_title)
        TextView tvTitle;
        @BindView(R.id.tv_item_tv_show_score)
        TextView tvScore;
        @BindView(R.id.tv_item_tv_show_genre)
        TextView tvGenre;
        @BindView(R.id.img_item_tv_show_poster)
        ImageView imgPoster;

        @BindString(R.string.poster_with_title)
        String posterWithTitle;

        FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
