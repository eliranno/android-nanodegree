package com.eliran.builditbigger;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import static junit.framework.TestCase.assertFalse;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.udacity.gradle.builditbigger.MainActivity;
import com.udacity.gradle.builditbigger.data.EndpointsAsyncTask;

@RunWith(AndroidJUnit4.class)
public class AsyncTaskTest {

    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testAsyncTaskJoke() throws Exception {

        EndpointsAsyncTask testAsyncTask = new EndpointsAsyncTask(new EndpointsAsyncTask.JokeReceivable() {
            @Override
            public void onReceivedJoke(String joke) {
                assertFalse(TextUtils.isEmpty(joke));
            }
        });

        testAsyncTask.execute();
        String result = testAsyncTask.get();

        assertFalse(TextUtils.isEmpty(result));
    }
}