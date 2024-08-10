package com.example.gameforgasir

import com.example.gameforgasir.data.local.UsState
import com.example.gameforgasir.rules.TestDispatcherRule
import com.example.gameforgasir.ui.IdentifyLicensePlateState.IdentifyLicensePlateStateViewModel
import com.example.gameforgasir.ui.IdentifyStateNickname.IdentifyStateNicknameViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class IdentifyLicensePlateStateViewModelTest {
    @get: Rule
    val testDispatcherRule = TestDispatcherRule()

    // Boundary case
    @Test
    fun identifyLicensePlateStateViewModel_Initialization_FirstQuestionLoaded() {
        val selectedNumberOfQuestions = 10

        val viewModel = IdentifyLicensePlateStateViewModel(
            usStates = TestData.usStates,
            soundEffectsRepository = SoundEffectRepositoryTest()
        ).apply { startGame(selectedNumberOfQuestions) }

        val currentUiState = viewModel.uiState.value

        assertNotEquals(TestData.usStates[0].licensePlate, currentUiState.currentLicensePlate)
        assertEquals(selectedNumberOfQuestions, currentUiState.gameStatus.numberOfQuestions)
        assertEquals(1, currentUiState.gameStatus.currentQuestionNumber)
        assertEquals(0, currentUiState.gameStatus.currentScore)
        assertFalse(currentUiState.gameStatus.isGameOver)
    }

    @Test
    fun identifyLicensePlateStateViewModel_AllLicensePlateStateCorrect_UiStateUpdatedCorrectly() = runTest {
        val selectedNumberOfQuestions = 10

        val viewModel = IdentifyLicensePlateStateViewModel(
            usStates = TestData.usStates,
            soundEffectsRepository = SoundEffectRepositoryTest()
        ).apply { startGame(selectedNumberOfQuestions) }

        var currentUiState = viewModel.uiState.value
        var indexOfCorrectLicensePlateState: Int = TestData.getCorrectAnswerIndex(currentUiState.choices)

        var expectedScore = 0
        var expectedQuestionNumber = 0

        repeat(selectedNumberOfQuestions) {
            expectedQuestionNumber += 1

            assertEquals(expectedScore, currentUiState.gameStatus.currentScore)
            assertEquals(expectedQuestionNumber, currentUiState.gameStatus.currentQuestionNumber)

            viewModel.checkUsStateAnswer(currentUiState.choices[indexOfCorrectLicensePlateState])

            expectedScore += 100

            delay(1000)
            currentUiState = viewModel.uiState.value
            indexOfCorrectLicensePlateState = TestData.getCorrectAnswerIndex(currentUiState.choices)
        }

        assertEquals(expectedScore, currentUiState.gameStatus.currentScore)
        assertTrue(currentUiState.gameStatus.isGameOver)
    }

    // Success path
    @Test
    fun identifyLicensePlateStateViewModel_CorrectLicensePlateState_ScoreUpdatedAndAnswerIndexEqualsToCorrectAnswerIndex() = runTest {
        val selectedNumberOfQuestions = 10

        val viewModel = IdentifyLicensePlateStateViewModel(
            usStates = TestData.usStates,
            soundEffectsRepository = SoundEffectRepositoryTest()
        ).apply { startGame(selectedNumberOfQuestions) }

        var currentUiState = viewModel.uiState.value
        val indexOfCorrectLicensePlateState = TestData.getCorrectAnswerIndex(currentUiState.choices)

        viewModel.checkUsStateAnswer(currentUiState.choices[indexOfCorrectLicensePlateState])

        currentUiState = viewModel.uiState.value

        assertEquals(indexOfCorrectLicensePlateState, currentUiState.correctAnswerIndex)
        assertEquals(100, currentUiState.gameStatus.currentScore)
    }

    @Test
    fun identifyLicensePlateStateViewModel_SelectNumberOfQuestions_NumberOfQuestionsUpdatedCorrectly() = runTest {
        val availableNumberOfQuestions = listOf(10, 20, 30, 40, 50)
        val viewModel = IdentifyLicensePlateStateViewModel(
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
    fun identifyLicensePlateStateViewModel_RestartGame_UiStateResetSuccessfully() = runTest {
        var selectedNumberOfQuestions = 20

        val viewModel = IdentifyLicensePlateStateViewModel(
            usStates = TestData.usStates,
            soundEffectsRepository = SoundEffectRepositoryTest()
        ).apply { startGame(selectedNumberOfQuestions) }

        var currentUiState = viewModel.uiState.value
        var indexOfCorrectLicensePlateState = TestData.getCorrectAnswerIndex(currentUiState.choices)

        repeat(10) {
            viewModel.checkUsStateAnswer(currentUiState.choices[indexOfCorrectLicensePlateState])
            delay(1000)
            currentUiState = viewModel.uiState.value
            indexOfCorrectLicensePlateState = TestData.getCorrectAnswerIndex(currentUiState.choices)
        }

        selectedNumberOfQuestions = 10
        viewModel.startGame(selectedNumberOfQuestions)
        currentUiState = viewModel.uiState.value

        assertEquals(selectedNumberOfQuestions, currentUiState.gameStatus.numberOfQuestions)
        assertEquals(0, currentUiState.gameStatus.currentScore)
        assertEquals(1, currentUiState.gameStatus.currentQuestionNumber)
    }
    // Error path
    @Test
    fun identifyLicensePlateStateViewModel_IncorrectLicensePlateState_AnswerIndexNotEqualsToCorrectAnswerIndex() = runTest {
        val selectedNumberOfQuestions = 10

        val viewModel = IdentifyLicensePlateStateViewModel(
            usStates = TestData.usStates,
            soundEffectsRepository = SoundEffectRepositoryTest()
        ).apply { startGame(selectedNumberOfQuestions) }

        var currentUiState = viewModel.uiState.value
        val incorrectAnswerIndex = TestData.getIncorrectAnswerIndex(currentUiState.choices)

        viewModel.checkUsStateAnswer(currentUiState.choices[incorrectAnswerIndex])

        currentUiState = viewModel.uiState.value

        assertNotEquals(incorrectAnswerIndex, currentUiState.correctAnswerIndex)
        assertEquals(0, viewModel.uiState.value.gameStatus.currentScore)
    }
}