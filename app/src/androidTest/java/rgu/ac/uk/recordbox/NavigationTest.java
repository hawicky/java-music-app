package rgu.ac.uk.recordbox;


import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class NavigationTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void yourSetUPFragment() {
        activityTestRule.getActivity()
                .getFragmentManager().beginTransaction();
    }

    @Test
    public void CreateNewFragmentView () {}


}