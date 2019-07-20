package com.indmind.moviecataloguetwo.ui.home.movielist;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.indmind.moviecataloguetwo.data.entity.Movie;
import com.indmind.moviecataloguetwo.data.repository.MoviesRepository;
import com.indmind.moviecataloguetwo.utils.LiveDataTestUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MoviesViewModelTest {
    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    private final MoviesRepository repository = mock(MoviesRepository.class);
    private final ArrayList<Movie> moviesMock = new ArrayList<>();
    private MoviesViewModel viewModel;

    private ArrayList<Movie> getMoviesMock(int size) {
        moviesMock.clear();

        for (int i = 0; i < size; i++) {
            moviesMock.add(mock(Movie.class));
        }

        return moviesMock;
    }

    @Before
    public void setup() {
        viewModel = new MoviesViewModel(repository);
    }

    @Test
    public void firstLoadMoviesTest() {
        doAnswer(invocation -> {
            ((MoviesRepository.DiscoverMoviesRepositoryListener) invocation.getArgument(1))
                    .onMoviesReceived(getMoviesMock(20));

            return null;
        }).when(repository).getMovies(eq(1), any(MoviesRepository.DiscoverMoviesRepositoryListener.class));

        viewModel.loadMovies(1, null);

        ArrayList<Movie> movies = LiveDataTestUtil.getValue(viewModel.getAllMovies());

        verify(repository, times(1)).getMovies(eq(1), any(MoviesRepository.DiscoverMoviesRepositoryListener.class));

        assertNotNull(movies);
        assertEquals(20, movies.size());
    }

    @Test
    public void searchMovies() {
        doAnswer(invocation -> {
            ((MoviesRepository.DiscoverMoviesRepositoryListener) invocation.getArgument(1))
                    .onMoviesReceived(getMoviesMock(19));

            return null;
        }).when(repository).searchMovies(eq("Toy Story"), any(MoviesRepository.DiscoverMoviesRepositoryListener.class));

        viewModel.searchMovies("Toy Story", null);

        ArrayList<Movie> movies = LiveDataTestUtil.getValue(viewModel.getAllMovies());

        verify(repository).searchMovies(eq("Toy Story"), any(MoviesRepository.DiscoverMoviesRepositoryListener.class));

        assertNotNull(movies);
        assertEquals(19, movies.size());
    }
}