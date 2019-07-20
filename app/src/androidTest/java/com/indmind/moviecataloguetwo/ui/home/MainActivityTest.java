package com.indmind.moviecataloguetwo.ui.home;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import com.indmind.moviecataloguetwo.R;
import com.indmind.moviecataloguetwo.utils.EspressoIdlingResource;
import com.indmind.moviecataloguetwo.utils.EspressoUtils;
import com.indmind.moviecataloguetwo.utils.RecyclerViewItemCountAssertion;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
    }

    @Test
    public void loadView() {
        onView(withId(R.id.view_container)).check(matches(isDisplayed()));
        onView(withId(R.id.nav_bottom)).check(matches(isDisplayed()));
    }

    @Test
    public void toMovieDetailActivityTest() {
        onView(withId(R.id.rv_movie_container)).check(matches(isDisplayed()));

        onView(withId(R.id.nav_bottom))
                .check(matches(isDisplayed()))
                .check(matches(EspressoUtils.hasCheckedItem(R.id.nav_movies)));

        onView(withId(R.id.rv_movie_container)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.tv_detail_movie_title)).check(matches(isDisplayed()));

        onView(withId(R.id.btn_detail_back))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.rv_movie_container)).check(matches(isDisplayed()));
    }

    @Test
    public void searchMoviesTest() throws InterruptedException {
        onView(withId(R.id.sv_movie))
                .check(matches(isDisplayed()))
                .perform(click(), typeText("Toy Story 4"));

        Thread.sleep(1000);

        onView(withId(R.id.rv_movie_container))
                .check(new RecyclerViewItemCountAssertion(1))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.tv_detail_movie_title))
                .check(matches(isDisplayed()))
                .check(matches(withText("Toy Story 4")));

        onView(withId(R.id.btn_detail_back))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.rv_movie_container))
                .check(matches(isDisplayed()))
                .check(new RecyclerViewItemCountAssertion(1));
    }

    @Test
    public void toTvShowDetailActivityTest() {
        onView(withId(R.id.view_container)).perform(swipeLeft());

        onView(withId(R.id.nav_bottom)).check(matches(EspressoUtils.hasCheckedItem(R.id.nav_tv_show)));

        onView(withId(R.id.rv_tv_show_container))
                .check(matches(isDisplayed()))
                .check(new RecyclerViewItemCountAssertion(20));

        onView(withId(R.id.rv_tv_show_container)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.tv_detail_tv_show_title)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_detail_back)).check(matches(isDisplayed())).perform(click());

        onView(withId(R.id.rv_tv_show_container)).check(matches(isDisplayed()));
    }

    @Test
    public void searchShowsTest() throws InterruptedException {
        onView(withId(R.id.view_container)).perform(swipeLeft());

        onView(withId(R.id.nav_bottom))
                .check(matches(isDisplayed()))
                .check(matches(EspressoUtils.hasCheckedItem(R.id.nav_tv_show)));

        onView(withId(R.id.rv_tv_show_container)).check(matches(isDisplayed()));

        onView(withId(R.id.sv_tv_show))
                .check(matches(isDisplayed()))
                .perform(click(), typeText("Mr. Robot"));

        Thread.sleep(1000);

        onView(withId(R.id.rv_tv_show_container))
                .check(matches(isDisplayed()))
                .check(new RecyclerViewItemCountAssertion(2))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.tv_detail_tv_show_title))
                .check(matches(isDisplayed()))
                .check(matches(withText("Mr. Robot")));

        onView(withId(R.id.btn_detail_back))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.rv_tv_show_container))
                .check(matches(isDisplayed()))
                .check(new RecyclerViewItemCountAssertion(2));
    }
}
