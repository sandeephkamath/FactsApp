package com.sandeep.factsapp;

import com.sandeep.factsapp.model.Fact;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FactTest {

    @Test
    public void isFactValid() {
        Fact fact = new Fact();
        fact.setDescription("Description");
        fact.setTitle("Title");
        fact.setImageHref("Image");
        assertEquals(false, fact.isInvalid());


        fact.setDescription(null);
        fact.setTitle(null);
        fact.setImageHref(null);
        assertEquals(true, fact.isInvalid());


    }

}
