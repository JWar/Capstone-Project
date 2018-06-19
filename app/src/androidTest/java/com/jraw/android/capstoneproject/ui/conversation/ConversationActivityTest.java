package com.jraw.android.capstoneproject.ui.conversation;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.jraw.android.capstoneproject.R;
import com.jwar.android.capstoneproject.Injection;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ConversationActivityTest {

    private static final String CONV_TITLE = Injection.getTestConvTitle();

    @Rule
    public ActivityTestRule<ConversationActivity> mActivityTestRule =
            new ActivityTestRule<>(ConversationActivity.class);

    @Before
    public void setUp() throws Exception {
        IdlingRegistry.getInstance().register(
                mActivityTestRule.getActivity().getCountingIdlingResource());
    }
    @After
    public void tearDown() throws Exception {
        IdlingRegistry.getInstance().unregister(
                mActivityTestRule.getActivity().getCountingIdlingResource());
    }
    @Test
    public void testConvList() {
        onView(withId(R.id.fragment_conversation_recycler_view))
                .perform(RecyclerViewActions.
                        scrollToPosition(0));
        onView(withText(CONV_TITLE))
                .check(matches(isDisplayed()));
    }
}
