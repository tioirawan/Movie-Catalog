package com.indmind.moviecataloguetwo.ui.home.movielist;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.indmind.moviecataloguetwo.data.entity.Movie;
import com.indmind.moviecataloguetwo.utils.LiveDataTestUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DiscoverMoviesViewModelTest {
    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    private DiscoverMoviesViewModel viewModel;

    @Before
    public void setup() {
        viewModel = new DiscoverMoviesViewModel();
    }

    @Test

    public void firstLoadMoviesTest() {
        viewModel.loadMovies(1, null);

        ArrayList<Movie> movies = LiveDataTestUtil.getValue(viewModel.getAllMovies());

        assertNotNull(movies);
        assertEquals(20, movies.size());

    }

    @Test
    public void searchMovies() {
        viewModel.searchMovies("Toy Story", null);

        ArrayList<Movie> movies = LiveDataTestUtil.getValue(viewModel.getAllMovies());

        assertNotNull(movies);
        assertEquals(19, movies.size());
    }
}