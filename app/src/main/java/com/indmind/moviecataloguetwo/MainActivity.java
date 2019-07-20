package com.indmind.moviecataloguetwo;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.indmind.moviecataloguetwo.adapter.SectionStatePagerAdapter;
import com.indmind.moviecataloguetwo.fragment.MoviesFragment;
import com.indmind.moviecataloguetwo.fragment.TvShowFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.nav_bottom)
    BottomNavigationView bottomNavigation;
    @BindView(R.id.view_container)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupViewPager(viewPager);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_movies:
                        viewPager.setCurrentItem(0, true);
                        return true;
                    case R.id.nav_tv_show:
                        viewPager.setCurrentItem(1, true);
                        return true;
                    case R.id.nav_lang_setting:
                        Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);

                        startActivity(intent);
                        return true;
                }

                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        bottomNavigation.findViewById(R.id.nav_movies).performClick();
                        break;
                    case 1:
                        bottomNavigation.findViewById(R.id.nav_tv_show).performClick();
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
}
