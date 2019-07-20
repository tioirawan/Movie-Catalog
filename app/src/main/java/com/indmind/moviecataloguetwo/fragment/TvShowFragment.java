package com.indmind.moviecataloguetwo.fragment;


import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.indmind.moviecataloguetwo.R;
import com.indmind.moviecataloguetwo.adapter.ListTvShowAdapter;
import com.indmind.moviecataloguetwo.model.TvShow;

import java.util.ArrayList;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {
    @BindView(R.id.rv_tv_show_container)
    RecyclerView tvShowContainer;

    @BindArray(R.array.tvshow_title)
    String[] tvShowTitle;
    @BindArray(R.array.tvshow_overview)
    String[] tvShowOverview;
    @BindArray(R.array.tvshow_runtime)
    String[] tvShowRuntime;

    @BindArray(R.array.tvshow_score)
    int[] tvShowScore;
    @BindArray(R.array.tvshow_poster)
    TypedArray tvShowPoster;

    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvShowContainer.setHasFixedSize(true);
        tvShowContainer.setLayoutManager(new LinearLayoutManager(getContext()));

        ListTvShowAdapter adapter = new ListTvShowAdapter(getContext(), prepareMovieData());
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(adapter);

        scaleAdapter.setFirstOnly(false);

        tvShowContainer.setAdapter(scaleAdapter);
    }

    private ArrayList<TvShow> prepareMovieData() {
        ArrayList<TvShow> tvShows = new ArrayList<>();

        for (int i = 0; i < tvShowTitle.length; i++) {
            TvShow show = new TvShow();

            show.setTitle(tvShowTitle[i]);
            show.setOverview(tvShowOverview[i]);
            show.setScore(tvShowScore[i]);
            show.setRuntime(tvShowRuntime[i]);

            show.setPoster(tvShowPoster.getResourceId(i, -1));

            tvShows.add(show);
        }

        tvShowPoster.recycle();

        return tvShows;
    }

}
