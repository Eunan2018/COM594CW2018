package com.eunan.tracey.com594cw2018;

import android.support.test.filters.LargeTest;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class GetDataTaskTest {

    GetDataTask.OnDownloadComplete callback;

    GetDataTask getDataTask;
    String[] arr;


    @Before
    public void setUp() {

        getDataTask = new GetDataTask(callback);
        arr = new String[2];
    }

    @Test
    public void testJsonData() {

        arr[0] = "http://api.openweathermap.org/data/2.5/forecast?q=London&APPID=2c5cacc84098ec6fdcfaeda8aa90dc1f";
        arr[1] = null;
        new GetDataTask(callback).execute(arr[0]);

        Assert.assertNotNull(getDataTask.doInBackground(arr[0]));
        Assert.assertNull(getDataTask.doInBackground(arr[1]));

    }

}