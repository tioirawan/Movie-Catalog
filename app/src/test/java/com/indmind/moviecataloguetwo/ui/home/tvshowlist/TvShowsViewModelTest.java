package com.indmind.moviecataloguetwo.ui.home.tvshowlist;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.indmind.moviecataloguetwo.data.entity.TvShow;
import com.indmind.moviecataloguetwo.data.repository.TvShowsRepository;
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

public class TvShowsViewModelTest {
    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    private final TvShowsRepository repository = mock(TvShowsRepository.class);
    private final ArrayList<TvShow> showsMock = new ArrayList<>();
    private TvShowsViewModel viewModel;

    private ArrayList<TvShow> getShowsMock(int size) {
        showsMock.clear();

        for (int i = 0; i < size; i++) {
            showsMock.add(mock(TvShow.class));
        }

        return showsMock;
    }

    @Before
    public void setup() {
        viewModel = new TvShowsViewModel(repository);
    }

    @Test
    public void firstLoadMoviesTest() {
        doAnswer(invocation -> {
            ((TvShowsRepository.DiscoverTvShowsListener) invocation.getArgument(1))
                    .onShowsReceived(getShowsMock(20));

            return null;
        }).when(repository).getShows(eq(1), any(TvShowsRepository.DiscoverTvShowsListener.class));

        viewModel.loadShows(null);

        ArrayList<TvShow> shows = LiveDataTestUtil.getValue(viewModel.getAllShows());

        verify(repository, times(1)).getShows(eq(1), any(TvShowsRepository.DiscoverTvShowsListener.class));

        assertNotNull(shows);
        assertEquals(20, shows.size());

    }

    @Test
    public void searchMovies() {
        doAnswer(invocation -> {
            ((TvShowsRepository.DiscoverTvShowsListener) invocation.getArgument(1))
                    .onShowsReceived(getShowsMock(2));

            return null;
        }).when(repository).searchShows(eq("Mr Robot"), any(TvShowsRepository.DiscoverTvShowsListener.class));

        viewModel.searchShows("Mr Robot", null);

        ArrayList<TvShow> shows = LiveDataTestUtil.getValue(viewModel.getAllShows());

        verify(repository).searchShows(eq("Mr Robot"), any(TvShowsRepository.DiscoverTvShowsListener.class));

        assertNotNull(shows);
        assertEquals(2, shows.size());
    }
}
