package com.indmind.moviecataloguetwo.ui.home.movielist;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
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


        viewModel.getAllMovies();

    }

    @Test
    public void searchMovies() {

    }

    @Test
    public void getAllMovies() {
    }
}