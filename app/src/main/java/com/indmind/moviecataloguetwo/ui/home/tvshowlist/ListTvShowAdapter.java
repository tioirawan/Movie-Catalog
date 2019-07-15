package com.indmind.moviecataloguetwo.ui.home.tvshowlist;

import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.indmind.moviecataloguetwo.R;
import com.indmind.moviecataloguetwo.data.entity.TvShow;
import com.indmind.moviecataloguetwo.ui.tvshow.TvShowDetailActivity;
import com.indmind.moviecataloguetwo.utils.CustomOnItemClickListener;
import com.indmind.moviecataloguetwo.utils.GenreMapper;
import com.indmind.moviecataloguetwo.utils.apis.ApiClient;
import com.squareup.phrase.Phrase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListTvShowAdapter extends RecyclerView.Adapter<ListTvShowAdapter.TvShowViewHolder> {
    private final Context mContext;
    private final ArrayList<TvShow> listTvShow = new ArrayList<>();

    ListTvShowAdapter(Context mContext) {
        this.mContext = mContext;
    }

    void setListTvShow(List<TvShow> listTvShow) {
        this.listTvShow.clear();
        this.listTvShow.addAll(listTvShow);
        notifyDataSetChanged();
    }

    void clearShows() {
        this.listTvShow.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_row_tv_shows, viewGroup, false);
        return new TvShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvShowViewHolder tvShowViewHolder, int i) {
        TvShow show = listTvShow.get(i);

        tvShowViewHolder.tvTitle.setText(show.getName());
        tvShowViewHolder.tvScore.setText(String.valueOf(new DecimalFormat("0.0").format(show.getVote_average())));

        if (show.getPoster_path() != null) {
            Glide.with(mContext).load(ApiClient.POSTER_BASE_URL + show.getPoster_path()).into(tvShowViewHolder.imgPoster);
        }

        tvShowViewHolder.imgPoster.setContentDescription(
                Phrase.from(tvShowViewHolder.posterWithTitle).put("title", show.getName()).format()
        );

        tvShowViewHolder.itemView.setOnClickListener(new CustomOnItemClickListener(i, position -> {
            Intent intent = new Intent(mContext, TvShowDetailActivity.class);

            intent.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW, listTvShow.get(position));

            mContext.startActivity(intent);
        }));

        SparseArray<String> genresMapper = GenreMapper.getGenres(mContext);
        int[] showGenreIds = show.getGenre_ids();

        StringBuilder genres = new StringBuilder();

        for (int idx = 0; idx < showGenreIds.length; idx++) {
            genres.append(genresMapper.get(showGenreIds[idx])).append(idx < showGenreIds.length - 1 ? ", " : "");
        }

        tvShowViewHolder.tvGenre.setText(genres.toString());
    }

    @Override
    public int getItemCount() {
        return listTvShow.size();
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder {
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

        TvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
