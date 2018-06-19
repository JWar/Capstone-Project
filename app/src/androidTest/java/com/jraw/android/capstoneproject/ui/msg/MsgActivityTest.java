package com.jraw.android.capstoneproject.ui.msg;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.jraw.android.capstoneproject.R;
import com.jraw.android.capstoneproject.ui.msgs.MsgsActivity;
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

import android.support.test.espresso.contrib.RecyclerViewActions;

//For now just forcing expected values.
@RunWith(AndroidJUnit4.class)
public class MsgActivityTest {

    private static final String MSG_BODY = Injection.getTestMsgBody();

    @Rule
    public ActivityTestRule<MsgsActivity> mActivityTestRule =
            new ActivityTestRule<MsgsActivity>(MsgsActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent result = new Intent(targetContext, MsgsActivity.class);
                    result.putExtra("coPublicId", 1l);
                    result.putExtra("coTitle", "First conv");
                    return result;
                }
            };

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
    public void testMsgList() {
        //Disables test if in install build... clumsy but temp solution.
        if (!MSG_BODY.equals("install")) {
            onView(withId(R.id.fragment_msgs_recycler_view))
                    .perform(RecyclerViewActions.
                            scrollToPosition(0));
            onView(withText(MSG_BODY))
                    .check(matches(isDisplayed()));
        }
    }
}
