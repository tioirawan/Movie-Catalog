package com.indmind.moviecataloguetwo.ui.home;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.indmind.moviecataloguetwo.R;
import com.indmind.moviecataloguetwo.ui.home.movielist.MoviesFragment;
import com.indmind.moviecataloguetwo.ui.home.tvshowlist.TvShowFragment;
import com.indmind.moviecataloguetwo.utils.adapter.SectionStatePagerAdapter;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.nav_bottom)
    BottomNavigationView bottomNavigation;
    @BindView(R.id.view_container)
    ViewPager viewPager;
    @BindString(R.string.movies)
    String movie;
    @BindString(R.string.tv_show)
    String tvShow;
    @BindString(R.string.favorite)
    String favorite;
    @BindString(R.string.movie_release_today)
    String movieReleaseToday;
    @BindString(R.string.settings)
    String settings;

    private MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setTitle(movie);
        setupViewPager(viewPager);

        viewPager.setCurrentItem(0, true);
        bottomNavigation.getMenu().getItem(0).setChecked(true);

        bottomNavigation.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.nav_movies:
                    viewPager.setCurrentItem(0, true);
                    return true;
                case R.id.nav_tv_show:
                    viewPager.setCurrentItem(1, true);
                    return true;
            }

            return false;
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null)
                    prevMenuItem.setChecked(false);
                else
                    bottomNavigation.getMenu().getItem(0).setChecked(false);

                bottomNavigation.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigation.getMenu().getItem(position);

                switch (position) {
                    case 0:
                        setTitle(movie);
                        break;
                    case 1:
                        setTitle(tvShow);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionStatePagerAdapter adapter = new SectionStatePagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new MoviesFragment());
        adapter.addFragment(new TvShowFragment());

        viewPager.setAdapter(adapter);
    }

    private void setTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
}
