package com.indmind.moviecataloguetwo.fragments;


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

import com.indmind.moviecataloguetwo.R;
import com.indmind.moviecataloguetwo.adapters.ListTvShowAdapter;
import com.indmind.moviecataloguetwo.viewmodels.FavoriteTvShowViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvShowFragment extends Fragment {
    private RecyclerView rvFavoriteTvShow;

    public FavoriteTvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvFavoriteTvShow = view.findViewById(R.id.rv_favorite_tv_show);

        rvFavoriteTvShow.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFavoriteTvShow.setHasFixedSize(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListTvShowAdapter favoriteTvShowAdapter = new ListTvShowAdapter(getActivity());

        rvFavoriteTvShow.setAdapter(favoriteTvShowAdapter);

        FavoriteTvShowViewModel tvShowViewModel = ViewModelProviders.of(this).get(FavoriteTvShowViewModel.class);
        tvShowViewModel.getAllTvShows().observe(getViewLifecycleOwner(), favoriteTvShowAdapter::setListTvShow);
    }
}
