package com.sandeep.factsapp;

import com.sandeep.factsapp.model.Fact;
import com.sandeep.factsapp.model.FactsModel;

import org.junit.Test;

import java.util.ArrayList;

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

    @Test
    public void isFactModelValid() {
        FactsModel factsModel = new FactsModel();
        factsModel.setFacts(null);
        assertEquals(false, factsModel.isValid());

        ArrayList<Fact> facts = new ArrayList<>();

        factsModel.setFacts(facts);
        assertEquals(false, factsModel.isValid());

        facts.add(new Fact());
        assertEquals(true, factsModel.isValid());
    }

}
