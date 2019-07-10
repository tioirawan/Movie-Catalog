package com.indmind.moviecataloguetwo.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.indmind.moviecataloguetwo.R;
import com.indmind.moviecataloguetwo.adapters.ListTvShowAdapter;
import com.indmind.moviecataloguetwo.models.TvShow;
import com.indmind.moviecataloguetwo.viewmodels.DiscoverTvShowsViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment implements SearchView.OnQueryTextListener {
    private final int page_number = 1;
    @BindView(R.id.rv_tv_show_container)
    RecyclerView tvShowsContainer;
    @BindView(R.id.pb_tv_show)
    ProgressBar pbTvShow;
    @BindView(R.id.sv_tv_show)
    SearchView svMovie;
    @BindView(R.id.tv_tv_show_not_found)
    TextView tvNotFound;
    private ListTvShowAdapter adapter;
    private final Observer<ArrayList<TvShow>> showChangesObserver = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(@Nullable ArrayList<TvShow> shows) {
            if (shows != null) {
                adapter.setListTvShow(shows);
                setProgressBarVisibility(false);

                if (shows.size() <= 0) {
                    tvNotFound.setVisibility(View.VISIBLE);
                } else {
                    tvNotFound.setVisibility(View.GONE);
                }
            }
        }
    };
    private DiscoverTvShowsViewModel mShowViewModel;
    private Handler mHandler;

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
        mHandler = new Handler();

        tvShowsContainer.setLayoutManager(new LinearLayoutManager(getContext()));
        tvShowsContainer.setAdapter(new ScaleInAnimationAdapter(adapter));

        svMovie.setOnQueryTextListener(this);

        mShowViewModel = ViewModelProviders.of(this).get(DiscoverTvShowsViewModel.class);
        mShowViewModel.getAllShows().observe(getViewLifecycleOwner(), showChangesObserver);

        if (adapter.getItemCount() <= 0 && (tvNotFound.getVisibility() == View.GONE)) {
            mShowViewModel.loadShows(page_number);
        }
    }


    private void setProgressBarVisibility(Boolean state) {
        if (state) pbTvShow.setVisibility(View.VISIBLE);
        else pbTvShow.setVisibility(View.GONE);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query.trim().isEmpty()) return false;

        setProgressBarVisibility(true);
        adapter.clearShows();
        tvNotFound.setVisibility(View.GONE);

        mShowViewModel.searchShows(query);

        tvShowsContainer.smoothScrollToPosition(0);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mHandler.removeCallbacksAndMessages(null);

        mHandler.postDelayed(() -> {
            if (TextUtils.isEmpty(newText)) {
                mShowViewModel.loadShows(page_number);
            } else {
                mShowViewModel.searchShows(newText);
            }
        }, 300);

        return false;
    }
}
