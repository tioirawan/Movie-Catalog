package com.indmind.moviecataloguetwo.data.repository;

import com.indmind.moviecataloguetwo.data.entity.Movie;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MoviesRepositoryTest {
    private MoviesRepository repository;

    @Before
    public void setup() {
        repository = new MoviesRepository();
    }

    @Test
    public void getMoviesTest() {
        CountDownLatch latch = new CountDownLatch(1);

        final ArrayList<Movie> resultMovies = new ArrayList<>();

        repository.getMovies(1, new MoviesRepository.DiscoverMoviesRepositoryListener() {
            @Override
            public void onMoviesReceived(ArrayList<Movie> movies) {
                resultMovies.addAll(movies);
                latch.countDown();
            }

            @Override
            public void onFailure(Throwable t) {
                latch.countDown();
            }
        });

        try {
            latch.await();

            assertNotNull(resultMovies);
            assertEquals(20, resultMovies.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void searchMoviesTest() {
        CountDownLatch latch = new CountDownLatch(1);

        ArrayList<Movie> resultMovies = new ArrayList<>();

        repository.searchMovies("Toy Story", new MoviesRepository.DiscoverMoviesRepositoryListener() {
            @Override
            public void onMoviesReceived(ArrayList<Movie> movies) {
                resultMovies.addAll(movies);
                latch.countDown();
            }

            @Override
            public void onFailure(Throwable t) {
                latch.countDown();
            }
        });

        try {
            latch.await();

            assertNotNull(resultMovies);
            assertEquals(19, resultMovies.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getReleaseNowTest() {
        CountDownLatch latch = new CountDownLatch(1);

        ArrayList<Movie> resultMovies = new ArrayList<>();

        repository.getReleaseNow(new MoviesRepository.DiscoverMoviesRepositoryListener() {
            @Override
            public void onMoviesReceived(ArrayList<Movie> movies) {
                resultMovies.addAll(movies);
                latch.countDown();
            }

            @Override
            public void onFailure(Throwable t) {
                latch.countDown();
            }
        });

        try {
            latch.await();

            String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                    Calendar.getInstance().getTime()
            );

            assertNotNull(resultMovies);
            assertEquals(currentDate, resultMovies.get(0).getRelease_date());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
