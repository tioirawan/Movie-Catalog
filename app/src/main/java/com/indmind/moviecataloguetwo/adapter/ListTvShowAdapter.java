package com.indmind.moviecataloguetwo.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.indmind.moviecataloguetwo.CustomOnItemClickListener;
import com.indmind.moviecataloguetwo.R;
import com.indmind.moviecataloguetwo.TvShowDetailActivity;
import com.indmind.moviecataloguetwo.model.TvShow;
import com.squareup.phrase.Phrase;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListTvShowAdapter extends RecyclerView.Adapter<ListTvShowAdapter.TvShowViewHolder> {
    private static final String TAG = "ListTvShowAdapter";

    private Context mContext;
    private ArrayList<TvShow> listShow;

    public ListTvShowAdapter(Context mContext, ArrayList<TvShow> listShow) {
        this.mContext = mContext;
        this.listShow = listShow;
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_row_tv_shows, viewGroup, false);
        return new TvShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvShowViewHolder tvShowViewHolder, int i) {
        TvShow show = listShow.get(i);

        tvShowViewHolder.tvTitle.setText(show.getTitle());
        tvShowViewHolder.tvOverview.setText(show.getOverview());
        tvShowViewHolder.imgPoster.setImageResource(show.getPoster());

        tvShowViewHolder.imgPoster.setContentDescription(
                Phrase.from(tvShowViewHolder.posterWithTitle).put("title", show.getTitle()).format()
        );

        tvShowViewHolder.itemView.setOnClickListener(new CustomOnItemClickListener(i, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(mContext, TvShowDetailActivity.class);

                Pair[] pairs = new Pair[2];

                pairs[0] = new Pair<>(tvShowViewHolder.tvTitle, tvShowViewHolder.titleTransition);
                pairs[1] = new Pair<>(tvShowViewHolder.imgPoster, tvShowViewHolder.posterTransition);

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                        (Activity) mContext, pairs
                );

                Log.d(TAG, "onItemClicked: " + listShow.get(position));

                intent.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW, listShow.get(position));

                mContext.startActivity(intent, options.toBundle());
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listShow.size();
    }

    public class TvShowViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_tvshow_title)
        TextView tvTitle;
        @BindView(R.id.tv_item_tvshow_overview)
        TextView tvOverview;
        @BindView(R.id.img_item_tvshow_poster)
        ImageView imgPoster;

        @BindString(R.string.tv_show_title_transition)
        String titleTransition;
        @BindString(R.string.tv_show_poster_transition)
        String posterTransition;
        @BindString(R.string.poster_with_title)
        String posterWithTitle;


        public TvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
