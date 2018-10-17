package com.sandeep.factsapp;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;

import com.sandeep.factsapp.model.FactsModel;
import com.sandeep.factsapp.viewmodel.FactsViewModel;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.schedulers.Schedulers;

import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class FactViewModelTest {

    @Mock
    private FactsViewModel factsViewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private LiveData<FactsModel> liveData;

    @Before
    public void setup() {
        RxAndroidPlugins.getInstance().reset();
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.trampoline();
            }
        });
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void factAPIValidationTest() throws InterruptedException {
        factsViewModel = new FactsViewModel(RuntimeEnvironment.application);
        liveData = factsViewModel.getFactListObservable();
        assertNotNull(liveData.getValue());
        // Loading State
        assertEquals(liveData.getValue().getState(), FactsModel.LOADING);
        Thread.sleep(3000);
        // API SUCCESS
        assertEquals(liveData.getValue().getState(), FactsModel.SUCCESS);
        assertTrue(liveData.getValue().isValid());
        //Error case
        liveData = factsViewModel.getFacts(new MockRepo().getFactListObservable());
        Thread.sleep(1000);
        assertNotNull(liveData.getValue());
        assertEquals(liveData.getValue().getState(), FactsModel.ERROR);  // Added error case bu mocking response.
        assertFalse(liveData.getValue().isValid()); // Invalid case
    }

    @Test
    public void factListTest() throws InterruptedException {
        factsViewModel = new FactsViewModel(RuntimeEnvironment.application);
        liveData = factsViewModel.getFactListObservable();
        Thread.sleep(3000);
        assertNotNull(liveData.getValue());
        // Fact list check
        assertFalse(liveData.getValue().getFacts().isEmpty());
        // Fact list empty check
        assertThat(liveData.getValue().getFacts(), not(IsEmptyCollection.empty()));
        //Error case
        liveData = factsViewModel.getFacts(new MockRepo().getFactListObservable());
        Thread.sleep(1000);
        assertNotNull(liveData.getValue());
        assertNull(liveData.getValue().getFacts());
    }

    @Test
    public void factTitleTest() throws InterruptedException {
        factsViewModel = new FactsViewModel(RuntimeEnvironment.application);
        liveData = factsViewModel.getFactListObservable();
        Thread.sleep(3000);
        assertNotNull(liveData.getValue());
        // Title check
        assertFalse(liveData.getValue().getTitle().isEmpty());

        //Error case
        liveData = factsViewModel.getFacts(new MockRepo().getFactListObservable());
        Thread.sleep(1000);
        assertNotNull(liveData.getValue());
        assertNull(liveData.getValue().getTitle());
    }

}
