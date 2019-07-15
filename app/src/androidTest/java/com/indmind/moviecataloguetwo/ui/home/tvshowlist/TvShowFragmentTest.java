package com.indmind.moviecataloguetwo.ui.home.tvshowlist;

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

public class TvShowFragmentTest {
    private final TvShowFragment tvShowFragment = new TvShowFragment();
    @Rule
    public ActivityTestRule<SingleFragmentActivity> activityRule = new ActivityTestRule<>(SingleFragmentActivity.class);

    @Before
    public void setUp() {
        activityRule.getActivity().setFragment(tvShowFragment);
        IdlingRegistry.getInstance().register(tvShowFragment.getIdlingResource());
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(tvShowFragment.getIdlingResource());
    }

    @Test
    public void loadShows() {
        onView(withId(R.id.rv_tv_show_container)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_tv_show_container)).check(new RecyclerViewItemCountAssertion(20));
    }
}