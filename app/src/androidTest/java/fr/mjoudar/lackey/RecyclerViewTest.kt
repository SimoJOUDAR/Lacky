package fr.mjoudar.lackey

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import fr.mjoudar.lackey.CustomMatchers.Companion.withItemCount
import fr.mjoudar.lackey.presentation.mainActivity.MainActivity
import org.hamcrest.CoreMatchers.startsWith
import org.junit.Rule
import org.junit.Test

class RecyclerViewTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    /***********************************************************************************************
     ** Tests on "All Devices" tab
     ***********************************************************************************************/
    @Test
    fun shouldDisplayAllItems() {
        Thread.sleep(2000)
        onView(withId(R.id.recyclerview))
            .check(matches(withItemCount(12)))
    }

    @Test
    fun shouldDisplayAllDevices() {
        Thread.sleep(2000)
        onView(withId(R.id.recyclerview))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        Thread.sleep(1000)
        onView(withId(R.id.device_name_rs))
            .check(matches(ViewMatchers.withText("Volet roulant - Salon")))
    }


    /***********************************************************************************************
     ** Tests on "Light Devices" tab
     ***********************************************************************************************/
    @Test
    fun shouldDisplayLightItems() {
        Thread.sleep(2000)
        onView(withId(R.id.recyclerview))
            .perform(swipeLeft())
        Thread.sleep(1000);
        onView(withId(R.id.recyclerview))
            .check(matches(withItemCount(4)))
    }

    @Test
    fun shouldDisplayOnlyLightItems() {
        Thread.sleep(2000)
        onView(withId(R.id.recyclerview))
            .perform(swipeLeft())
        Thread.sleep(1000)
        onView(withId(R.id.recyclerview))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        Thread.sleep(1000)
        onView(withId(R.id.device_name_light))
            .check(matches(ViewMatchers.withText(startsWith("Lampe"))))
    }

    /***********************************************************************************************
     ** Tests on "RollerShutter Devices" tab
     ***********************************************************************************************/
    @Test
    fun shouldDisplayRollerShutterItems() {
        Thread.sleep(2000)
        for (i in 0..1) {
            onView(withId(R.id.recyclerview))
                .perform(swipeLeft())
            Thread.sleep(500)
        }
        onView(withId(R.id.recyclerview))
            .check(matches(withItemCount(4)))
    }

    @Test
    fun shouldDisplayOnlyRollerShutterItems() {
        Thread.sleep(2000)
        for (i in 0..1) {
            onView(withId(R.id.recyclerview))
                .perform(swipeLeft())
            Thread.sleep(500)
        }
        onView(withId(R.id.recyclerview))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        onView(withId(R.id.device_name_rs))
            .check(matches(ViewMatchers.withText(startsWith("Volet"))))
    }

    /***********************************************************************************************
     ** Tests on "Heater Devices" tab
     ***********************************************************************************************/
    @Test
    fun shouldDisplayHeaterItems() {
        Thread.sleep(2000)
        for (i in 0..2) {
            onView(withId(R.id.recyclerview))
                .perform(swipeLeft())
            Thread.sleep(500)
        }
        onView(withId(R.id.recyclerview))
            .check(matches(withItemCount(4)))
    }

    @Test
    fun shouldDisplayOnlyHeaterItems() {
        Thread.sleep(2000)
        for (i in 0..2) {
            onView(withId(R.id.recyclerview))
                .perform(swipeLeft())
            Thread.sleep(500)
        }
        onView(withId(R.id.recyclerview))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        onView(withId(R.id.device_name_heater))
            .check(matches(ViewMatchers.withText(startsWith("Radiateur"))))
    }

}