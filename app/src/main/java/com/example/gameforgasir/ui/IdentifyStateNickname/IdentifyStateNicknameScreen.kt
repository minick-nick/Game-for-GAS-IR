package com.example.gameforgasir.ui.IdentifyStateNickname

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gameforgasir.LIST_OF_AVAILABLE_NUMBER_OF_QUESTIONS
import com.example.gameforgasir.R
import com.example.gameforgasir.ui.AppViewModelProvider
import com.example.gameforgasir.ui.Choice
import com.example.gameforgasir.ui.ChooseNumberOfQuestionsDialog
import com.example.gameforgasir.ui.IdentifyStateNicknameState
import com.example.gameforgasir.ui.PauseDialog
import com.example.gameforgasir.ui.TopGameStatusBottomChoices
import com.example.gameforgasir.ui.navigation.NavigationDestination
import com.example.gameforgasir.ui.theme.GameForGASIRTheme

object IdentifyStateNicknameDestination : NavigationDestination {
    override val route = "identify_state_nickname"
}

@Composable
fun IdentifyStateNicknameScreen(
    viewModel: IdentifyStateNicknameViewModel
        = viewModel(factory = AppViewModelProvider.Factory),
    onExit: () -> Unit,
    onNavigateGameFinishedScreen: (Int, Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val windowInfo = LocalWindowInfo.current
    val isPauseDialogVisible = rememberSaveable { mutableStateOf(false) }
    val isChooseNumberOfQuestionsDialogVisible = rememberSaveable { mutableStateOf(true) }

    if (uiState.gameStatus.isGameOver) {
        onNavigateGameFinishedScreen(
            uiState.gameStatus.currentScore,
            uiState.gameStatus.numberOfCorrectAnswers,
            uiState.gameStatus.numberOfQuestions
        )
    }

    LaunchedEffect(windowInfo) {
        snapshotFlow { windowInfo.isWindowFocused  }.collect { isWindowFocused ->
            if (isWindowFocused)
                viewModel.timer.start()
            else viewModel.timer.pause()
        }
    }

    IdentifyStateNicknameBody(
        state = uiState,
        onSelect = { answer ->
            viewModel.checkNicknameAnswer(answer)
        },
        onPauseGame = { isPauseDialogVisible.value = true },
        modifier = modifier
    )

    if (isPauseDialogVisible.value) {
        PauseDialog(
            onRestart = {
                isPauseDialogVisible.value = false
                isChooseNumberOfQuestionsDialogVisible.value = true
                        },
            onExit = {
                onExit()
                isPauseDialogVisible.value = false
                     },
            onDismissRequest = { isPauseDialogVisible.value = false },
        )
    }

    if (isChooseNumberOfQuestionsDialogVisible.value) {
        ChooseNumberOfQuestionsDialog(
            onDismissRequest = {
                onExit()
                isChooseNumberOfQuestionsDialogVisible.value = false
                               },
            onSelectNumberOfQuestions = {
                viewModel.startGame(numberOfQuestions = it)
                isChooseNumberOfQuestionsDialogVisible.value = false
            },
            listOfNumberOfQuestions = LIST_OF_AVAILABLE_NUMBER_OF_QUESTIONS
        )
    }
}

@Composable
fun IdentifyStateNicknameBody(
    state: IdentifyStateNicknameState,
    onSelect: (Choice) -> Unit,
    onPauseGame: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopGameStatusBottomChoices(
        numberOfAnsweredQuestion = state.gameStatus.currentQuestionNumber,
        numberOfQuestions = state.gameStatus.numberOfQuestions,
        remainingTime = state.gameStatus.remainingTime,
        currentScore = state.gameStatus.currentScore,
        onPause = onPauseGame,
        choices = state.choices,
        isCorrectAnswerShown = state.isCorrectAnswerShown,
        correctAnswerIndex = state.correctAnswerIndex,
        userAnswerIndex = state.userAnswerIndex,
        onSelect = onSelect,
        modifier = modifier
    ) {
        Text(
            text = stringResource(
                R.string.open_and_close_parenthesis,
                state.stateName,
                state.stateAbbreviation
            ),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IdentifyStateNicknameBodyPreview() {
    GameForGASIRTheme {
        var sampleChoices = List(4) {
            Choice("Arizona")
        }
        IdentifyStateNicknameBody(
            state = IdentifyStateNicknameState(
                stateName = "Alabama",
                stateAbbreviation = "AL",
                choices = sampleChoices
            ),
            onSelect = {},
            onPauseGame = {}
        )
    }
}