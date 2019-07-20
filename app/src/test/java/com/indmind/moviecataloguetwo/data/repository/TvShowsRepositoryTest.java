package com.indmind.moviecataloguetwo.data.repository;

import com.indmind.moviecataloguetwo.data.entity.TvShow;

import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class TvShowsRepositoryTest {
    private final TvShowsRepository repository = mock(TvShowsRepository.class);

    private final ArrayList<TvShow> showsMock = new ArrayList<>();

    private ArrayList<TvShow> getShowsMock(int size) {
        showsMock.clear();

        for (int i = 0; i < size; i++) {
            showsMock.add(mock(TvShow.class));
        }

        return showsMock;
    }

    @Test
    public void getTvShowsTest() {
        doAnswer(invocation -> {
            ((TvShowsRepository.DiscoverTvShowsListener) invocation.getArgument(1))
                    .onShowsReceived(getShowsMock(20));

            return null;
        }).when(repository).getShows(eq(1), any(TvShowsRepository.DiscoverTvShowsListener.class));

        CountDownLatch latch = new CountDownLatch(1);

        final ArrayList<TvShow> resultTvShows = new ArrayList<>();

        repository.getShows(1, new TvShowsRepository.DiscoverTvShowsListener() {
            @Override
            public void onShowsReceived(ArrayList<TvShow> shows) {
                resultTvShows.addAll(shows);
                latch.countDown();
            }

            @Override
            public void onFailure(Throwable t) {
                latch.countDown();
            }
        });

        try {
            latch.await();

            assertNotNull(resultTvShows);
            assertEquals(20, resultTvShows.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void searchShowsTest() {
        doAnswer(invocation -> {
            ((TvShowsRepository.DiscoverTvShowsListener) invocation.getArgument(1))
                    .onShowsReceived(getShowsMock(2));

            return null;
        }).when(repository).searchShows(eq("Mr Robot"), any(TvShowsRepository.DiscoverTvShowsListener.class));

        CountDownLatch latch = new CountDownLatch(1);

        ArrayList<TvShow> resultTvShows = new ArrayList<>();

        repository.searchShows("Mr Robot", new TvShowsRepository.DiscoverTvShowsListener() {
            @Override
            public void onShowsReceived(ArrayList<TvShow> shows) {
                resultTvShows.addAll(shows);
                latch.countDown();
            }

            @Override
            public void onFailure(Throwable t) {
                latch.countDown();
            }
        });

        try {
            latch.await();

            assertNotNull(resultTvShows);
            assertEquals(2, resultTvShows.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}