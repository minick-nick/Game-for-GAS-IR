package com.example.gameforgasir

import com.example.gameforgasir.data.local.UsState
import com.example.gameforgasir.rules.TestDispatcherRule
import com.example.gameforgasir.ui.IdentifyAbbreviationOfState.IdentifyAbbreviationOfStateViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class IdentifyAbbreviationOfStateViewModelTest {
    @get: Rule
    val testDispatcherRule = TestDispatcherRule()

    // Boundary case
    @Test
    fun identifyAbbreviationOfStateViewModel_Initialization_FirstQuestionLoaded() = runTest {
        val selectedNumberOfQuestions = 10

        val viewModel = IdentifyAbbreviationOfStateViewModel(
            usStates = TestData.usStates,
            soundEffectsRepository = SoundEffectRepositoryTest()
        ).apply { startGame(selectedNumberOfQuestions) }

        viewModel.startGame(selectedNumberOfQuestions)
        val currentUiState = viewModel.uiState.value

        assertNotEquals(TestData.usStates[0], currentUiState.stateName)
        assertEquals(selectedNumberOfQuestions, currentUiState.gameStatus.numberOfQuestions)
        assertEquals(1, currentUiState.gameStatus.currentQuestionNumber)
        assertEquals(0, currentUiState.gameStatus.currentScore)
        assertFalse(currentUiState.gameStatus.isGameOver)
    }

    @Test
    fun identifyAbbreviationOfStateViewModel_AllAbbreviationsCorrect_UiStateUpdatedCorrectly() = runTest {
        val selectedNumberOfQuestions = 10

        val viewModel = IdentifyAbbreviationOfStateViewModel(
            usStates = TestData.usStates,
            soundEffectsRepository = SoundEffectRepositoryTest()
        ).apply { startGame(selectedNumberOfQuestions) }

        var currentUiState = viewModel.uiState.value
        var correctAbbreviation = TestData.getAbbreviation(currentUiState.stateName)

        var expectedScore = 0
        var expectedQuestionNumber = 0

        repeat(selectedNumberOfQuestions) {
            expectedQuestionNumber += 1

            assertEquals(expectedScore, currentUiState.gameStatus.currentScore)
            assertEquals(expectedQuestionNumber, currentUiState.gameStatus.currentQuestionNumber)

            viewModel.updateAbbreviationAnswer(correctAbbreviation)
            viewModel.checkAbbreviationAnswer()

            expectedScore += 100

            delay(1000)
            currentUiState = viewModel.uiState.value
            correctAbbreviation = TestData.getAbbreviation(currentUiState.stateName)
        }

        assertEquals(expectedScore, currentUiState.gameStatus.currentScore)
        assertTrue(currentUiState.gameStatus.isGameOver)
    }


    // Success path
    @Test
    fun identifyAbbreviationOfStateViewModel_CorrectStateAbbreviation_ScoreUpdatedAndErrorFlagUnset() = runTest {
        val selectedNumberOfQuestions = 10

        val viewModel = IdentifyAbbreviationOfStateViewModel(
            usStates = TestData.usStates,
            soundEffectsRepository = SoundEffectRepositoryTest()
        ).apply { startGame(selectedNumberOfQuestions) }

        var currentUiState = viewModel.uiState.value
        val currentUsState = currentUiState.stateName
        val correctAbbreviation = TestData.getAbbreviation(currentUsState)

        viewModel.updateAbbreviationAnswer(correctAbbreviation)
        viewModel.checkAbbreviationAnswer()

        currentUiState = viewModel.uiState.value

        assertTrue(currentUiState.isAnswerCorrect)
        assertEquals(100, currentUiState.gameStatus.currentScore)
    }

    @Test
    fun identifyAbbreviationOfStateViewModel_SelectNumberOfQuestions_NumberOfQuestionsUpdatedCorrectly() = runTest {
        val availableNumberOfQuestions = listOf(10, 20, 30, 40, 50)
        val viewModel = IdentifyAbbreviationOfStateViewModel(
            usStates = List(50) { UsState() },
            soundEffectsRepository = SoundEffectRepositoryTest()
        )

        for (expectedNumberOfQuestions in availableNumberOfQuestions) {
            viewModel.startGame(expectedNumberOfQuestions)
            val currentUiState = viewModel.uiState.value
            assertEquals(expectedNumberOfQuestions, currentUiState.gameStatus.numberOfQuestions)
        }
    }

    @Test
    fun identifyAbbreviationOfStateViewModel_RestartGame_UiStateResetSuccessfully() = runTest {
        var selectedNumberOfQuestions = 20

        val viewModel = IdentifyAbbreviationOfStateViewModel(
            usStates = TestData.usStates,
            soundEffectsRepository = SoundEffectRepositoryTest()
        ).apply { startGame(selectedNumberOfQuestions) }

        repeat(10) {
            viewModel.updateAbbreviationAnswer(
                TestData.getAbbreviation(viewModel.uiState.value.stateName)
            )
            viewModel.checkAbbreviationAnswer()
            delay(1000)
        }

        selectedNumberOfQuestions = 10
        viewModel.startGame(selectedNumberOfQuestions)

        assertEquals(selectedNumberOfQuestions, viewModel.uiState.value.gameStatus.numberOfQuestions)
        assertEquals(0, viewModel.uiState.value.gameStatus.currentScore)
        assertEquals(1, viewModel.uiState.value.gameStatus.currentQuestionNumber)
    }

    // Error path
    @Test
    fun identifyAbbreviationOfStateViewModel_IncorrectAbbreviation_ErrorFlagSet() = runTest {
        val selectedNumberOfQuestions = 10
        val incorrectAbbreviation = "XXX"

        val viewModel = IdentifyAbbreviationOfStateViewModel(
            usStates = TestData.usStates,
            soundEffectsRepository = SoundEffectRepositoryTest()
        ).apply { startGame(selectedNumberOfQuestions) }

        viewModel.updateAbbreviationAnswer(incorrectAbbreviation)
        viewModel.checkAbbreviationAnswer()

        assertFalse(viewModel.uiState.value.isAnswerCorrect)
        assertEquals(0, viewModel.uiState.value.gameStatus.currentScore)
    }
}