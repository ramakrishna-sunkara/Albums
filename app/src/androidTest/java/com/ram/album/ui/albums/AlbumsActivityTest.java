package com.ram.album.ui.albums;

import android.content.Intent;

import androidx.test.rule.ActivityTestRule;

import com.ram.album.R;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(MockitoJUnitRunner.class)
public class AlbumsActivityTest {
    @Rule
    public ActivityTestRule<AlbumsActivity> activityTestRule = new ActivityTestRule<AlbumsActivity>(AlbumsActivity.class, true, false) {
        @Override
        protected void beforeActivityLaunched() {
            super.beforeActivityLaunched();
        }
    };

    @BeforeClass
    public static void setupOnce() {
    }

    private void launchActivity() {
        Intent intent = new Intent();
        activityTestRule.launchActivity(intent);
    }

    @Test
    public void test_Title() throws InterruptedException {
        launchActivity();
        onView(withText(R.string.app_name)).check(matches(isDisplayed()));
    }
}