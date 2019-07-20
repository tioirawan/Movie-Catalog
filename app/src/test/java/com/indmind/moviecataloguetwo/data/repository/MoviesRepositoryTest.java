package com.indmind.moviecataloguetwo.data.repository;

import com.indmind.moviecataloguetwo.data.entity.Movie;

import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class MoviesRepositoryTest {
    private final MoviesRepository repository = mock(MoviesRepository.class);

    private final ArrayList<Movie> moviesMock = new ArrayList<>();

    private ArrayList<Movie> getMoviesMock(int size) {
        moviesMock.clear();

        for (int i = 0; i < size; i++) {
            moviesMock.add(mock(Movie.class));
        }

        return moviesMock;
    }

    @Test
    public void getMoviesTest() {
        doAnswer(invocation -> {
            ((MoviesRepository.DiscoverMoviesRepositoryListener) invocation.getArgument(1))
                    .onMoviesReceived(getMoviesMock(20));

            return null;
        }).when(repository).getMovies(eq(1), any(MoviesRepository.DiscoverMoviesRepositoryListener.class));

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
        doAnswer(invocation -> {
            ((MoviesRepository.DiscoverMoviesRepositoryListener) invocation.getArgument(1))
                    .onMoviesReceived(getMoviesMock(19));

            return null;
        }).when(repository).searchMovies(eq("Toy Story"), any(MoviesRepository.DiscoverMoviesRepositoryListener.class));

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
}
