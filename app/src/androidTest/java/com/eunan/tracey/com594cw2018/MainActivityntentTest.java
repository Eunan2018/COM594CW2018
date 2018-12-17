package com.eunan.tracey.com594cw2018;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class MainActivityntentTest {

    @Rule
    public ActivityTestRule<DisplayWeather> mainActivityActivityTestRule = new ActivityTestRule<>(DisplayWeather.class);
    private DisplayWeather displayWeather = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(MainActivity.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        displayWeather = mainActivityActivityTestRule.getActivity();
    }

    @Test
    public void testLaunchOfWeatherActivity() {
        assertNotNull(displayWeather.findViewById(R.id.buttonLogout));

        onView(withId(R.id.buttonLogout)).perform(click());

        Activity activity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);

        assertNotNull(activity);

        activity.finish();
    }


    @After
    public void tearDown() throws Exception {
        displayWeather = null;
    }
}