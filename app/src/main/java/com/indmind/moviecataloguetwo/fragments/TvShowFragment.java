package com.indmind.moviecataloguetwo.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.indmind.moviecataloguetwo.R;
import com.indmind.moviecataloguetwo.adapters.ListTvShowAdapter;
import com.indmind.moviecataloguetwo.models.TvShow;
import com.indmind.moviecataloguetwo.viewmodels.TvShowViewModel;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    @BindView(R.id.rv_tv_show_container)
    RecyclerView tvShowsContainer;

    @BindView(R.id.pb_tv_show)
    ProgressBar pbTvShow;

    private ListTvShowAdapter adapter;

    private final Observer<ArrayList<TvShow>> observer = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(@Nullable ArrayList<TvShow> shows) {
            if (shows != null) {
                adapter.setListTvShow(shows);
                setProgressBarVisible();
            }
        }
    };

    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new ListTvShowAdapter(getContext());

        TvShowViewModel tvShowViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(TvShowViewModel.class);
        tvShowViewModel.getTvShows().observe(getActivity(), observer);

        int displayedPage = 1;
        tvShowViewModel.loadTvShows(getContext(), displayedPage);

        tvShowsContainer.setLayoutManager(new LinearLayoutManager(getContext()));
        tvShowsContainer.setAdapter(new ScaleInAnimationAdapter(adapter));
    }

    private void setProgressBarVisible() {
        pbTvShow.setVisibility(View.GONE);
    }
}
