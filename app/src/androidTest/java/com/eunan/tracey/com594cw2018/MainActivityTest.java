package com.eunan.tracey.com594cw2018;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private MainActivity mainActivity = null;

    @Before
    public void setUp(){
        mainActivity = mainActivityActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){
        View view =  mainActivity.findViewById(R.id.button_login);
        assertNotNull(view);
    }
    @After
    public void tearDown() {
        mainActivity = null;
    }
}