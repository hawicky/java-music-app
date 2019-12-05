package rgu.ac.uk.recordbox;



import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuItem;


import androidx.test.annotation.UiThreadTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;





@RunWith(AndroidJUnit4.class)
public class MainActivityBottomNavTest {
    @Rule
    public final ActivityTestRule<MainActivity> activityTestRule  = new ActivityTestRule<>(MainActivity.class);



    private static final int[] MENU_CONTENT_ITEM_ID = {
            R.id.home_nav,R.id.search_nav,R.id.contact_Nav
    };

    private Map<Integer,String> menuStringContent;
    private BottomNavigationView bottomNavigation;

    @Before
    public void setUP() throws Exception {
        final  MainActivity activity = activityTestRule.getActivity();
        //activity.setTheme(R.style.Theme_MaterialComponents_Light);
        bottomNavigation = activity.findViewById(R.id.bottom_nav);



        final Resources res = activity.getResources();
        menuStringContent = new HashMap<>(MENU_CONTENT_ITEM_ID.length);
        menuStringContent.put(R.id.home_nav,res.getString(R.string.homeNav));
        menuStringContent.put(R.id.search_nav,res.getString(R.string.searchNav));
        menuStringContent.put(R.id.home_nav,res.getString(R.string.contactUs));
    }


    @UiThreadTest
    @Test
    @SmallTest
    public void testAddItemsWithoutMenuInflation() {
        BottomNavigationView navigation = new BottomNavigationView(activityTestRule.getActivity());
        activityTestRule.getActivity().setContentView(navigation);
        navigation.getMenu().add("Item1");
        navigation.getMenu().add("Item2");
        assertEquals(2, navigation.getMenu().size());
        navigation.getMenu().removeItem(0);
        navigation.getMenu().removeItem(0);
        assertEquals(0, navigation.getMenu().size());
    }


    @Test
    @SmallTest
    public void testBasics() {
        // Check the contents of the Menu object
        final Menu menu = bottomNavigation.getMenu();
        assertNotNull("Menu should not be null", menu);
        assertEquals("Should have matching number of items", MENU_CONTENT_ITEM_ID.length, menu.size());
        for (int i = 0; i < MENU_CONTENT_ITEM_ID.length; i++) {
            final MenuItem currItem = menu.getItem(i);
            assertEquals("ID for Item #" + i, MENU_CONTENT_ITEM_ID[i], currItem.getItemId());
        }
    }





}