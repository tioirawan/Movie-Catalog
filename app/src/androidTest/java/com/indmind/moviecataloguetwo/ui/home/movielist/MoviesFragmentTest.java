package com.indmind.moviecataloguetwo.ui.home.movielist;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.rule.ActivityTestRule;

import com.indmind.moviecataloguetwo.R;
import com.indmind.moviecataloguetwo.testing.SingleFragmentActivity;
import com.indmind.moviecataloguetwo.utils.RecyclerViewItemCountAssertion;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class MoviesFragmentTest {
    private final MoviesFragment moviesFragment = new MoviesFragment();
    @Rule
    public ActivityTestRule<SingleFragmentActivity> activityRule = new ActivityTestRule<>(SingleFragmentActivity.class);

    @Before
    public void setUp() {
        activityRule.getActivity().setFragment(moviesFragment);
        IdlingRegistry.getInstance().register(moviesFragment.getIdlingResource());
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(moviesFragment.getIdlingResource());
    }

    @Test
    public void loadMovies() {
        onView(withId(R.id.rv_movie_container)).check(matches(isDisplayed()));

        onView(withId(R.id.rv_movie_container)).check(new RecyclerViewItemCountAssertion(20));
    }
}