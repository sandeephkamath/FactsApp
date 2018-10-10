package com.sandeep.factsapp;

import com.sandeep.factsapp.model.Fact;
import com.sandeep.factsapp.model.FactsModel;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FactModelTest {

    @Test
    public void isFactModelValid() {
        FactsModel factsModel = new FactsModel();
        factsModel.setFacts(null);
        assertFalse(factsModel.isValid());

        ArrayList<Fact> facts = new ArrayList<>();

        factsModel.setFacts(facts);
        assertFalse(factsModel.isValid());

        facts.add(new Fact());
        assertTrue(factsModel.isValid());
    }

}
