package com.aghajari.videosharing

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.aghajari.videosharing.utils.TEST_TAG_CODE_ROW
import com.aghajari.videosharing.utils.TEST_TAG_FLOAT_BUTTON
import com.aghajari.videosharing.utils.TEST_TAG_KEYBOARD
import com.aghajari.videosharing.utils.TEST_TAG_TEXT_FIELD
import org.junit.Rule
import org.junit.Test
import org.junit.Before

@ExperimentalTestApi
class LoginTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        composeTestRule.setContent {
            MainScreen()
        }
    }

    @Test
    fun goToValidationScreenWithValidEmail() {
        composeTestRule.onNodeWithTag(TEST_TAG_TEXT_FIELD)
            .performTextInput("test@gmail.com")
        composeTestRule.onNodeWithTag(TEST_TAG_FLOAT_BUTTON)
            .performClick()
        composeTestRule.waitUntilAtLeastOneExists(
            hasText("Check your email address"),
            5000
        )
        composeTestRule.onNodeWithText("Check your email address")
            .assertIsDisplayed()
    }

    @Test
    fun goToUsernameScreenWithValidCode() {
        goToValidationScreenWithValidEmail()
        composeTestRule.onNodeWithTag(TEST_TAG_KEYBOARD + "1").performClick()
        composeTestRule.onNodeWithTag(TEST_TAG_KEYBOARD + "2").performClick()
        composeTestRule.onNodeWithTag(TEST_TAG_KEYBOARD + "3").performClick()
        composeTestRule.onNodeWithTag(TEST_TAG_KEYBOARD + "4").performClick()
        composeTestRule.onNodeWithTag(TEST_TAG_KEYBOARD + "5").performClick()
        composeTestRule.waitUntilAtLeastOneExists(
            hasText("Welcome!"),
            5000
        )
        composeTestRule.onNodeWithText("Welcome!")
            .assertIsDisplayed()
    }

    @Test
    fun checkInvalidCode() {
        goToValidationScreenWithValidEmail()
        composeTestRule.onNodeWithTag(TEST_TAG_KEYBOARD + "1").performClick()
        composeTestRule.onNodeWithTag(TEST_TAG_KEYBOARD + "1").performClick()
        composeTestRule.onNodeWithTag(TEST_TAG_KEYBOARD + "1").performClick()
        composeTestRule.onNodeWithTag(TEST_TAG_KEYBOARD + "1").performClick()
        composeTestRule.onNodeWithTag(TEST_TAG_KEYBOARD + "1").performClick()
        composeTestRule.onAllNodesWithTag(TEST_TAG_CODE_ROW)
            .assertAll(hasText("1", substring = false))
        Thread.sleep(1000)
        composeTestRule.onAllNodesWithTag(TEST_TAG_CODE_ROW)
            .assertAll(hasText("", substring = false))
    }

    @Test
    fun checkKeyboard() {
        goToValidationScreenWithValidEmail()
        composeTestRule.onNodeWithTag(TEST_TAG_KEYBOARD + "2").performClick()
        composeTestRule.onNodeWithTag(TEST_TAG_KEYBOARD + "2").performClick()
        composeTestRule.onNodeWithTag(TEST_TAG_KEYBOARD + "2").performClick()
        composeTestRule.onNodeWithTag(TEST_TAG_KEYBOARD + "2").performClick()
        composeTestRule.onNodeWithTag(TEST_TAG_KEYBOARD + "2").performClick()
        composeTestRule.onAllNodesWithTag(TEST_TAG_CODE_ROW)
            .assertAll(hasText("2", substring = false))
    }

}