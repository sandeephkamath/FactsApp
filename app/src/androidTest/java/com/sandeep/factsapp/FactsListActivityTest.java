package com.sandeep.factsapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.sandeep.factsapp.facts_list_module.FactsListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class FactsListActivityTest {


    @Rule
    public ActivityTestRule<FactsListActivity> activityTestRule =
            new ActivityTestRule<>(FactsListActivity.class);


    @Test
    public void onSuccess() throws InterruptedException {
        Thread.sleep(5000);
        onView(withId(R.id.facts_list_view)).check(matches(isDisplayed()));
        onView(withId(R.id.progress_indicator)).check(matches(not(isDisplayed())));
        onView(withId(R.id.no_result_view)).check(matches(not(isDisplayed())));
    }

    @Test
    public void onFailure() throws InterruptedException {
        Thread.sleep(5000);
        onView(withId(R.id.facts_list_view)).check(matches(not(isDisplayed())));
        onView(withId(R.id.progress_indicator)).check(matches(not(isDisplayed())));
        onView(withId(R.id.no_result_view)).check(matches(isDisplayed()));
    }

}
