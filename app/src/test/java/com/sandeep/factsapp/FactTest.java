package com.sandeep.factsapp;

import android.text.TextUtils;

import com.sandeep.factsapp.model.Fact;
import com.sandeep.factsapp.model.FactsModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TextUtils.class})
public class FactTest {

    @Before
    public void setup() {
        PowerMockito.mockStatic(TextUtils.class);
        PowerMockito.when(TextUtils.isEmpty(any(CharSequence.class))).thenAnswer(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                CharSequence a = (CharSequence) invocation.getArguments()[0];
                return !(a != null && a.length() > 0);
            }
        });
    }

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

    @Test
    public void isFactContentSame() {
        Fact fact = new Fact();
        fact.setDescription("Description");
        fact.setTitle("Title");
        fact.setImageHref("Image");

        Fact fact1 = new Fact();
        fact1.setDescription("Description");
        fact1.setTitle("Title");
        fact1.setImageHref("Image");

        assertEquals(true, fact.isContentSame(fact1));

        fact.setTitle("tt");
        assertEquals(false, fact.isContentSame(fact1));

        fact.setTitle(null);
        fact1.setTitle(null);
        assertEquals(true, fact.isContentSame(fact1));
    }

}
