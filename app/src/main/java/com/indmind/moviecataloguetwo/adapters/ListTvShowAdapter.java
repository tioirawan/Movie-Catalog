package com.indmind.moviecataloguetwo.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListTvShowAdapter extends RecyclerView.Adapter<ListTvShowAdapter.TvShowViewHolder> {
    private static final String TAG = "ListTvShowAdapter";

    private final Context mContext;
    private final ArrayList<TvShow> listTvShow = new ArrayList<>();

    public ListTvShowAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setListTvShow(ArrayList<TvShow> listTvShow) {
        this.listTvShow.clear();
        this.listTvShow.addAll(listTvShow);
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

        Glide.with(mContext).load(Api.POSTER_BASE_URL + show.getPoster_path()).into(tvShowViewHolder.imgPoster);

        tvShowViewHolder.imgPoster.setContentDescription(
                Phrase.from(tvShowViewHolder.posterWithTitle).put("title", show.getName()).format()
        );

        tvShowViewHolder.itemView.setOnClickListener(new CustomOnItemClickListener(i, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent(mContext, TvShowDetailActivity.class);

                intent.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW, listTvShow.get(position));

                mContext.startActivity(intent);
            }
        }));

        Map genresMapper = GenreMapper.getGenres(mContext);
        int[] showGenreIds = show.getGenre_ids();

        Log.d(TAG, "onBindViewHolder: " + showGenreIds[0]);

        StringBuilder genres = new StringBuilder();

        for (int idx = 0; idx < showGenreIds.length; idx++) {
            genres.append(genresMapper.get(showGenreIds[idx])).append(idx < showGenreIds.length - 1 ? ", " : "");
        }

        Log.d(TAG, "onBindViewHolder: " + show.getName() + genres);

        tvShowViewHolder.tvGenre.setText(genres.toString());
    }

    @Override
    public int getItemCount() {
        return listTvShow.size();
    }

    public class TvShowViewHolder extends RecyclerView.ViewHolder {
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
