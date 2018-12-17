package com.eunan.tracey.com594cw2018;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StackModelTest {

    @Test
    public void createStackModelAndReturnTitle() {

        // Given
        String title = "testTitle";
        String image = "testImage";
        String link = "testLink";

        // When
        StackModel stackModel = new StackModel();
        stackModel.setLink(link);
        stackModel.setImage(image);
        stackModel.setTitle(title);


        // Then
        Assert.assertTrue(stackModel.getTitle().equals(title));
        Assert.assertTrue(stackModel.getImage().equals(image));
        Assert.assertTrue(stackModel.getLink().equals(link));


        // When
        StackModel stackModel1 = null;
        Assert.assertNull(stackModel1);
        Assert.assertNotNull(stackModel);
//        Assert.assertTrue(stackModel.getLink().equals(link));


    }
}