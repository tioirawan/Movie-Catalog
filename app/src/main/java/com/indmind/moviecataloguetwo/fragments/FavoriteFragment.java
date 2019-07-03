package com.indmind.moviecataloguetwo.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.indmind.moviecataloguetwo.R;
import com.indmind.moviecataloguetwo.adapters.SectionStatePagerAdapter;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    @BindView(R.id.tl_nav_favorite)
    TabLayout tvNavFavorite;

    @BindView(R.id.vp_favorite_container)
    ViewPager viewPager;

    @BindString(R.string.movies)
    String movie;
    @BindString(R.string.tv_show)
    String tvShow;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViewPager(viewPager);

        viewPager.setCurrentItem(0);

        tvNavFavorite.addTab(tvNavFavorite.newTab().setText(movie));
        tvNavFavorite.addTab(tvNavFavorite.newTab().setText(tvShow));

        tvNavFavorite.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tvNavFavorite.getTabAt(i).select();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionStatePagerAdapter adapter = new SectionStatePagerAdapter(getFragmentManager());

        adapter.addFragment(new FavoriteMovieFragment());
        adapter.addFragment(new FavoriteTvShowFragment());

        viewPager.setAdapter(adapter);
    }
}
