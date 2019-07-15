package com.indmind.moviecataloguetwo.ui.home.tvshowlist;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.indmind.moviecataloguetwo.data.entity.TvShow;
import com.indmind.moviecataloguetwo.utils.LiveDataTestUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DiscoverTvShowsViewModelTest {
    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    private DiscoverTvShowsViewModel viewModel;

    @Before
    public void setup() {
        viewModel = new DiscoverTvShowsViewModel();
    }

    @Test

    public void firstLoadMoviesTest() {
        viewModel.loadShows(null);

        ArrayList<TvShow> shows = LiveDataTestUtil.getValue(viewModel.getAllShows());

        assertNotNull(shows);
        assertEquals(20, shows.size());

    }

    @Test
    public void searchMovies() {
        viewModel.searchShows("Mr Robot", null);

        ArrayList<TvShow> shows = LiveDataTestUtil.getValue(viewModel.getAllShows());

        assertNotNull(shows);
        assertEquals(2, shows.size());
    }
}
