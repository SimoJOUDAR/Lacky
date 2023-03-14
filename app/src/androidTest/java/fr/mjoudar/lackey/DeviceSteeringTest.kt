package fr.mjoudar.lackey

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import fr.mjoudar.lackey.presentation.mainActivity.MainActivity
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.junit.Test

class DeviceSteeringTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    /***********************************************************************************************
     ** Tests on "All Devices" tab
     ***********************************************************************************************/
    @Test
    fun shouldSteerLightModeAndPersistItThroughoutAppLifecycle() {
        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withId(R.id.recyclerview))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                    ViewActions.click()
                ))
        Thread.sleep(1000)

        Espresso.onView(ViewMatchers.withId(R.id.mode_light))
            .check(ViewAssertions.matches(ViewMatchers.withText(CoreMatchers.startsWith("ON"))))

        Espresso.onView(ViewMatchers.withId(R.id.mode_light))
            .perform(click())

        Espresso.onView(ViewMatchers.withId(R.id.mode_light))
            .check(ViewAssertions.matches(ViewMatchers.withText(CoreMatchers.startsWith("OFF"))))

        Espresso.pressBackUnconditionally()

        Thread.sleep(2000)

        Espresso.onView(ViewMatchers.withId(R.id.recyclerview))
            .perform(ViewActions.swipeLeft())

        Thread.sleep(500)
        Espresso.onView(ViewMatchers.withId(R.id.recyclerview))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        Espresso.onView(ViewMatchers.withId(R.id.mode_light))
            .check(ViewAssertions.matches(ViewMatchers.withText(CoreMatchers.startsWith("OFF"))))

        Espresso.onView(ViewMatchers.withId(R.id.mode_light))
            .perform(click())

        Espresso.onView(ViewMatchers.withId(R.id.mode_light))
            .check(ViewAssertions.matches(ViewMatchers.withText(CoreMatchers.startsWith("ON"))))

    }

    @Test
    fun shouldSteerLightIntensityAndPersistItThroughoutAppLifecycle() {
        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withId(R.id.recyclerview))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                    ViewActions.click()
                ))
        Thread.sleep(1000)

        Espresso.onView(ViewMatchers.withId(R.id.specs_light))
            .check(ViewAssertions.matches(ViewMatchers.withText(CoreMatchers.startsWith("50"))))

        Espresso.onView(ViewMatchers.withId(R.id.button_incr_light))
            .perform(click())

        Espresso.onView(ViewMatchers.withId(R.id.specs_light))
            .check(ViewAssertions.matches(ViewMatchers.withText(CoreMatchers.startsWith("60"))))

        Espresso.pressBackUnconditionally()

        Thread.sleep(2000)

        Espresso.onView(ViewMatchers.withId(R.id.recyclerview))
            .perform(ViewActions.swipeLeft())

        Thread.sleep(500)
        Espresso.onView(ViewMatchers.withId(R.id.recyclerview))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        Espresso.onView(ViewMatchers.withId(R.id.specs_light))
            .check(ViewAssertions.matches(ViewMatchers.withText(CoreMatchers.startsWith("60"))))

        Espresso.onView(ViewMatchers.withId(R.id.button_decr_light))
            .perform(click())

        Espresso.onView(ViewMatchers.withId(R.id.specs_light))
            .check(ViewAssertions.matches(ViewMatchers.withText(CoreMatchers.startsWith("50"))))
    }

    @Test
    fun shouldSteerRollerShutterPositionAndPersistItThroughoutAppLifecycle() {
        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withId(R.id.recyclerview))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1,
                    ViewActions.click()
                ))
        Thread.sleep(1000)

        Espresso.onView(ViewMatchers.withId(R.id.specs_rs))
            .check(ViewAssertions.matches(ViewMatchers.withText(CoreMatchers.startsWith("70"))))

        Espresso.onView(ViewMatchers.withId(R.id.button_incr_rs))
            .perform(click())

        Espresso.onView(ViewMatchers.withId(R.id.specs_rs))
            .check(ViewAssertions.matches(ViewMatchers.withText(CoreMatchers.startsWith("80"))))

        Espresso.pressBackUnconditionally()

        Thread.sleep(2000)

        for (i in 0..1) {
            Espresso.onView(ViewMatchers.withId(R.id.recyclerview))
                .perform(ViewActions.swipeLeft())
            Thread.sleep(500)
        }

        Thread.sleep(500)
        Espresso.onView(ViewMatchers.withId(R.id.recyclerview))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        Espresso.onView(ViewMatchers.withId(R.id.specs_rs))
            .check(ViewAssertions.matches(ViewMatchers.withText(CoreMatchers.startsWith("80"))))

        Espresso.onView(ViewMatchers.withId(R.id.button_decr_rs))
            .perform(click())

        Espresso.onView(ViewMatchers.withId(R.id.specs_rs))
            .check(ViewAssertions.matches(ViewMatchers.withText(CoreMatchers.startsWith("70"))))
    }

    @Test
    fun shouldSteerHeaterModeAndPersistItThroughoutAppLifecycle() {
        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withId(R.id.recyclerview))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2,
                    ViewActions.click()
                ))
        Thread.sleep(1000)

        Espresso.onView(ViewMatchers.withId(R.id.mode_heater))
            .check(ViewAssertions.matches(ViewMatchers.withText(CoreMatchers.startsWith("OFF"))))

        Espresso.onView(ViewMatchers.withId(R.id.mode_heater))
            .perform(click())

        Espresso.onView(ViewMatchers.withId(R.id.mode_heater))
            .check(ViewAssertions.matches(ViewMatchers.withText(CoreMatchers.startsWith("ON"))))

        Espresso.pressBackUnconditionally()

        Thread.sleep(2000)

        for (i in 0..2) {
            Espresso.onView(ViewMatchers.withId(R.id.recyclerview))
                .perform(ViewActions.swipeLeft())
            Thread.sleep(500)
        }

        Thread.sleep(500)
        Espresso.onView(ViewMatchers.withId(R.id.recyclerview))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        Espresso.onView(ViewMatchers.withId(R.id.mode_heater))
            .check(ViewAssertions.matches(ViewMatchers.withText(CoreMatchers.startsWith("ON"))))

        Espresso.onView(ViewMatchers.withId(R.id.mode_heater))
            .perform(click())

        Espresso.onView(ViewMatchers.withId(R.id.mode_heater))
            .check(ViewAssertions.matches(ViewMatchers.withText(CoreMatchers.startsWith("OFF"))))
    }

    @Test
    fun shouldSteerHeaterTemperatureAndPersistItThroughoutAppLifecycle() {
        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withId(R.id.recyclerview))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2,
                    ViewActions.click()
                ))
        Thread.sleep(1000)

        Espresso.onView(ViewMatchers.withId(R.id.specs_heater))
            .check(ViewAssertions.matches(ViewMatchers.withText(CoreMatchers.startsWith("20.0"))))

        Espresso.onView(ViewMatchers.withId(R.id.button_incr_heater))
            .perform(click())

        Espresso.onView(ViewMatchers.withId(R.id.specs_heater))
            .check(ViewAssertions.matches(ViewMatchers.withText(CoreMatchers.startsWith("20.5"))))

        Espresso.pressBackUnconditionally()

        Thread.sleep(2000)

        for (i in 0..2) {
            Espresso.onView(ViewMatchers.withId(R.id.recyclerview))
                .perform(ViewActions.swipeLeft())
            Thread.sleep(500)
        }

        Thread.sleep(500)
        Espresso.onView(ViewMatchers.withId(R.id.recyclerview))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        Espresso.onView(ViewMatchers.withId(R.id.specs_heater))
            .check(ViewAssertions.matches(ViewMatchers.withText(CoreMatchers.startsWith("20.5"))))

        Espresso.onView(ViewMatchers.withId(R.id.button_decr_heater))
            .perform(click())

        Espresso.onView(ViewMatchers.withId(R.id.specs_heater))
            .check(ViewAssertions.matches(ViewMatchers.withText(CoreMatchers.startsWith("20.0"))))
    }

}