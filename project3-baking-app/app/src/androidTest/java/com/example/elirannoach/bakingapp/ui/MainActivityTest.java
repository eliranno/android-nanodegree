package com.example.elirannoach.bakingapp.ui;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.CoreMatchers.allOf;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.elirannoach.bakingapp.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    private static final int STEP_WITH_VIDEO = 0;
    private static final int STEP_WITHOUT_VIDEO = 1;

    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init(){
        RecipeListFragment fragment = new RecipeListFragment();
        activityActivityTestRule.getActivity()
                .getFragmentManager().beginTransaction();
    }


    @Test
    public void clickOnRecyclerViewItem_opensRecipeStepActivity() {
        RecipeListFragment fragment = (RecipeListFragment) activityActivityTestRule.getActivity().getFragmentManager().findFragmentById(R.id.main_activity_frame_id);
        CountingIdlingResource mainActivityIdlingResource = fragment.
                getEspressoIdlingResourceForMainActivity();

        // registering MainActivity's idling resource for enabling Espresso sync with MainActivity's background threads
        Espresso.registerIdlingResources(mainActivityIdlingResource);

        onView(withId(R.id.rv_recipe_list_id))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.ingredient_list_id))
                .check(matches(isDisplayed()));
    }
}
