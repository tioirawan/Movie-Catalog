package com.indmind.moviecataloguetwo.ui.home.movielist;

import com.indmind.moviecataloguetwo.data.Movie;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DiscoverMoviesRepositoryTest {
    private DiscoverMoviesRepository repository;

    @Before
    public void setup() {
        repository = new DiscoverMoviesRepository();
    }

    @Test
    public void getMoviesTest() {
        repository.getMovies(1, new DiscoverMoviesRepository.DiscoverMoviesRepositoryListener() {
            @Override
            public void onMoviesReceived(ArrayList<Movie> movies) {
                assertNotNull(movies);
                assertEquals(20, movies.size());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @Test
    public void searchMoviesTest() {
        repository.searchMovies("Toy Story", new DiscoverMoviesRepository.DiscoverMoviesRepositoryListener() {
            @Override
            public void onMoviesReceived(ArrayList<Movie> movies) {
                assertNotNull(movies);
                assertEquals(19, movies.size());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @Test
    public void getReleaseNowTest() {
        repository.getReleaseNow(new DiscoverMoviesRepository.DiscoverMoviesRepositoryListener() {
            @Override
            public void onMoviesReceived(ArrayList<Movie> movies) {
                String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                        Calendar.getInstance().getTime()
                );

                assertNotNull(movies);
                assertEquals(currentDate, movies.get(0).getRelease_date());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        }, "popularity.asc");
    }
}