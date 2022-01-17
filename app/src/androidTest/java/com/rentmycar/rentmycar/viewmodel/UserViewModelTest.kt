package com.rentmycar.rentmycar.viewmodel

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.fragment.UserRegisterFragment
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock


@RunWith(AndroidJUnit4::class)
class UserViewModelTest: TestCase() {

    private lateinit var scenario: FragmentScenario<UserRegisterFragment>

    @Before
    fun setup() {
        val mockNavController = mock(NavController::class.java)
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_RentMyCar)
        scenario.moveToState(Lifecycle.State.STARTED)

        // Set the NavController property on the fragment
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }
    }

    @Test
    fun testRegisterEmptyUser() {
        onView(withId(R.id.btnRegister)).perform((click()))

        onView(withId(R.id.test_first_name)).check(matches(hasErrorText("Name needs to have a minimum of 2 characters.")));
        onView(withId(R.id.test_last_name)).check(matches(hasErrorText("Name needs to have a minimum of 2 characters.")));
        onView(withId(R.id.test_email)).check(matches(hasErrorText("E-mail address invalid.")));
        onView(withId(R.id.test_password)).check(matches(hasErrorText("Password needs to have a minimum of 6 characters.")));
    }

    @Test
    fun testRegisterUserWithInvalidEmail() {
        val firstName = "Mark"
        var lastName = "Zuckerberg"
        var email = "NotAmEmail"
        var password = "MetaIsDaShizzle"
        onView(withId(R.id.test_first_name)).perform(typeText(firstName))
        onView(withId(R.id.test_last_name)).perform(typeText(lastName))
        onView(withId(R.id.test_email)).perform(typeText(email))
        onView(withId(R.id.test_password)).perform(typeText(password))

        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btnRegister)).perform((click()))

        onView(withId(R.id.test_email)).check(matches(hasErrorText("E-mail address invalid.")));
    }

    @Test
    fun testRegisterUser() {
        val firstName = "Mark"
        var lastName = "Zuckerberg"
        var email = "markzuck@meta.com"
        var password = "MetaIsDaShizzle"
        onView(withId(R.id.test_first_name)).perform(typeText(firstName))
        onView(withId(R.id.test_last_name)).perform(typeText(lastName))
        onView(withId(R.id.test_email)).perform(typeText(email))
        onView(withId(R.id.test_password)).perform(typeText(password))

        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btnRegister)).perform((click()))
    }

}