package com.indmind.moviecataloguetwo.ui.tvshow;

import android.content.Context;
import android.content.Intent;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.indmind.moviecataloguetwo.R;
import com.indmind.moviecataloguetwo.data.entity.TvShow;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class TvShowDetailActivityTest {
    @SuppressWarnings("SpellCheckingInspection")
    private final TvShow dummyShow = new TvShow(
            1, "Stranger Things", null, "Stranger Things", 316.609f,
            null, 2395, "2016-07-15",
            "/56v2KjBlU4XaOv9rVYEQypROD7P.jpg", "en", 8.3f,
            "When a young boy vanishes, a small town uncovers a mystery involving...",
            "/x2LSRK2Cm7MZhjluni1msVJ3wDF.jpg"
    );

    @Rule
    public ActivityTestRule<TvShowDetailActivity> activityRule = new ActivityTestRule<TvShowDetailActivity>(TvShowDetailActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent result = new Intent(targetContext, TvShowDetailActivity.class);

            result.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW, dummyShow);

            return result;
        }
    };

    @Test
    public void loadMovie() {
        onView(withId(R.id.tv_detail_tv_show_title)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_detail_tv_show_title)).check(matches(withText(dummyShow.getName())));

        onView(withId(R.id.tv_detail_tv_show_overview)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_detail_tv_show_overview)).check(matches(withText(dummyShow.getOverview())));

        onView(withId(R.id.rb_detail_tv_show_score)).check(matches(isDisplayed()));
        onView(withId(R.id.img_detail_tv_show_backdrop)).check(matches(isDisplayed()));
        onView(withId(R.id.img_detail_tv_show_poster)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_detail_back)).check(matches(isDisplayed()));
    }
}
