package com.indmind.moviecataloguetwo.data.repository;

import com.indmind.moviecataloguetwo.data.entity.TvShow;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TvShowsRepositoryTest {
    private TvShowsRepository repository;

    @Before
    public void setup() {
        repository = new TvShowsRepository();
    }

    @Test
    public void getTvShowsTest() {
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