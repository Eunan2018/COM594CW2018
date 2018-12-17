package com.eunan.tracey.com594cw2018;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class PropertyManagerTest{
    private Context mContext;
    private PropertyManager mPropertyManager;

    @Before
    public void setUp(){
        mContext = InstrumentationRegistry.getTargetContext();
        mPropertyManager = new PropertyManager(mContext);
        assertNotNull(mContext);
    }

    @Test
    public void testFileReader(){
        String file = mPropertyManager.getProperties("stack.properties").getProperty("url");
        assertNotNull(file);
    }

    @After
    public void tearDown() {
        mContext = null;
        mPropertyManager = null;
    }
}