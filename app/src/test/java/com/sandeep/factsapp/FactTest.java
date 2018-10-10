package com.sandeep.factsapp;

import android.text.TextUtils;

import com.sandeep.factsapp.model.Fact;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
        assertFalse(fact.isInvalid());


        fact.setDescription(null);
        fact.setTitle(null);
        fact.setImageHref(null);
        assertTrue(fact.isInvalid());
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

        assertTrue(fact.isContentSame(fact1));

        fact.setTitle("tt");
        assertFalse(fact.isContentSame(fact1));

        fact.setTitle(null);
        fact1.setTitle(null);
        assertTrue(fact.isContentSame(fact1));
    }

}
