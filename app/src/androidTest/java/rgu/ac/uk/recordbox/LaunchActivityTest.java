package rgu.ac.uk.recordbox;


import android.view.View;
import android.widget.FrameLayout;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import rgu.ac.uk.recordbox.Fragment.home_frag;
import rgu.ac.uk.recordbox.Fragment.search_frag;
import rgu.ac.uk.recordbox.OurActivity.MainActivity;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class LaunchActivityTest {

    //set rule to get the main activity that will load the fragment
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    //create Variable for MainActivity in our folder to find our container
    private MainActivity mActivity = null;



    @Before
    public void yourSetUPFragment() {
        //load the activity
       mActivity = activityTestRule.getActivity();
    }

    @Test
    @SmallTest
    public void CreateNewFragmentView () {
        //Get frame layout that will load fragment
        FrameLayout fragContainer = mActivity.findViewById(R.id.frag_container);
        //check if is not empty
        assertNotNull(fragContainer);

        //Load home_frag
        home_frag fragment = new home_frag();
        mActivity.getSupportFragmentManager().beginTransaction().add(fragContainer.getId(), fragment).commitAllowingStateLoss();

        //wait for fragment to load on screen
        getInstrumentation().waitForIdleSync();

        //Once fragment is loaded we get view
        View v = fragment.getView().findViewById(R.id.fragment_home);
        //check if the view is visible
        assertNotNull(v);

        //Load search_frag
        search_frag fragment2 = new search_frag();
        mActivity.getSupportFragmentManager().beginTransaction().add(fragContainer.getId(), fragment2).commitAllowingStateLoss();

        //wait for fragment to load on screen
        getInstrumentation().waitForIdleSync();

        //Once fragment is loaded we get view
        View v2 = fragment.getView().findViewById(R.id.fragment_search);
        //check if the view is visible
        assertNotNull(v);


    }


    @After
    public void tearDown()throws Exception{
        //Once test is finish reset mActivity to null
        mActivity = null ;
    }




}