package com.indmind.moviecataloguetwo.ui.movie;

import android.content.Context;
import android.content.Intent;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.indmind.moviecataloguetwo.R;
import com.indmind.moviecataloguetwo.data.entity.Movie;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class MovieDetailActivityTest {
    @SuppressWarnings("SpellCheckingInspection")
    private final Movie dummyMovie = new Movie(
            1, 10, false, 7.8f, "Movie Title Test",
            557.36, "/rjbNpRMoVvqHmhmksbokcyCr7wn.jpg", "en",
            "Original Movie Title Test", "/dihW2yTsvQlust7mSuAqJDtqW7k.jpg",
            false, "This is the movie overview", "2019-06-28", null
    );

    @Rule
    public ActivityTestRule<MovieDetailActivity> activityRule = new ActivityTestRule<MovieDetailActivity>(MovieDetailActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent result = new Intent(targetContext, MovieDetailActivity.class);

            result.putExtra(MovieDetailActivity.EXTRA_MOVIE, dummyMovie);

            return result;
        }
    };

    @Test
    public void loadMovie() {
        onView(withId(R.id.tv_detail_movie_title)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_detail_movie_title)).check(matches(withText(dummyMovie.getTitle())));

        onView(withId(R.id.tv_detail_movie_overview)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_detail_movie_overview)).check(matches(withText(dummyMovie.getOverview())));

        onView(withId(R.id.tv_detail_movie_release)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_detail_movie_release)).check(matches(withText(dummyMovie.getRelease_date())));

        onView(withId(R.id.rb_detail_movie_score)).check(matches(isDisplayed()));
        onView(withId(R.id.img_detail_movie_backdrop)).check(matches(isDisplayed()));
        onView(withId(R.id.img_detail_movie_poster)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_detail_back)).check(matches(isDisplayed()));
    }

}
