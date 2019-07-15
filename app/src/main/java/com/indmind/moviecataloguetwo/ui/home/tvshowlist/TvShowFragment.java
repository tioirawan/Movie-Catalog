package com.indmind.moviecataloguetwo.ui.home.tvshowlist;


import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingResource;

import com.indmind.moviecataloguetwo.R;
import com.indmind.moviecataloguetwo.data.entity.TvShow;
import com.indmind.moviecataloguetwo.utils.SimpleIdlingResource;

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
    private Handler mHandler;

    private DiscoverTvShowsViewModel mShowViewModel;
    private final DiscoverTvShowsViewModel.FailureListener failureListener = t -> Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

    private SimpleIdlingResource mIdlingResource;

    private final Observer<ArrayList<TvShow>> showChangesObserver = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(@Nullable ArrayList<TvShow> shows) {
            if (shows != null) {
                adapter.setListTvShow(shows);
                setProgressBarVisibility(false);

                mIdlingResource.setIdleState(true);

                if (shows.size() <= 0) {
                    tvNotFound.setVisibility(View.VISIBLE);
                } else {
                    tvNotFound.setVisibility(View.GONE);
                }
            }
        }
    };

    public TvShowFragment() {
        // Required empty public constructor
    }

    @VisibleForTesting
    @NonNull
    IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }

        return mIdlingResource;
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

        getIdlingResource();
        mIdlingResource.setIdleState(false);

        adapter = new ListTvShowAdapter(getContext());
        mHandler = new Handler();

        tvShowsContainer.setLayoutManager(new LinearLayoutManager(getContext()));
        tvShowsContainer.setAdapter(new ScaleInAnimationAdapter(adapter));

        svMovie.setOnQueryTextListener(this);

        mShowViewModel = ViewModelProviders.of(this).get(DiscoverTvShowsViewModel.class);
        mShowViewModel.getAllShows().observe(getViewLifecycleOwner(), showChangesObserver);

        if (adapter.getItemCount() <= 0 && (tvNotFound.getVisibility() == View.GONE)) {
            mShowViewModel.loadShows(failureListener);
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

        mShowViewModel.searchShows(query, failureListener);

        tvShowsContainer.smoothScrollToPosition(0);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mHandler.removeCallbacksAndMessages(null);

        mHandler.postDelayed(() -> {
            if (TextUtils.isEmpty(newText)) {
                mShowViewModel.loadShows(failureListener);
            } else {
                mShowViewModel.searchShows(newText, failureListener);
            }
        }, 300);

        return false;
    }
}
