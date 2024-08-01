package com.example.gameforgasir.ui.IdentifyAbbreviationOfState

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gameforgasir.LIST_OF_AVAILABLE_NUMBER_OF_QUESTIONS
import com.example.gameforgasir.ui.AppViewModelProvider
import com.example.gameforgasir.ui.ChooseNumberOfQuestionsDialog
import com.example.gameforgasir.ui.GameStatus
import com.example.gameforgasir.ui.IdentifyAbbreviationOfStateState
import com.example.gameforgasir.ui.PauseDialog
import com.example.gameforgasir.ui.navigation.NavigationDestination
import com.example.gameforgasir.ui.theme.GameForGASIRTheme

object IdentifyAbbreviationOfStatesDestination : NavigationDestination {
    override val route = "identify_abbreviation_of_states"
}

@Composable
fun IdentifyAbbreviationOfStatesScreen(
    viewModel: IdentifyAbbreviationOfStateViewModel
        = viewModel(factory = AppViewModelProvider.Factory),
    onExit: () -> Unit,
    onNavigateGameFinishedScreen: (Int, Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val windowInfo = LocalWindowInfo.current
    val focusRequesterForTextField = remember { FocusRequester() }
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
        snapshotFlow { windowInfo.isWindowFocused }.collect { isWindowFocused ->
            if (isWindowFocused) {
                viewModel.timer.start()
            } else {
                viewModel.timer.pause()
            }
        }
    }

    IdentifyAbbreviationOfStatesBody(
        state = uiState,
        onUserInputAbbreviationChange = viewModel::updateAbbreviationAnswer,
        focusRequesterForTextField = focusRequesterForTextField,
        onEnterAnswer = viewModel::checkAbbreviationAnswer,
        onPause = { isPauseDialogVisible.value = true },
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
            onDismissRequest = { isPauseDialogVisible.value = false }
        )
    }

    if (isChooseNumberOfQuestionsDialogVisible.value) {
        ChooseNumberOfQuestionsDialog(
            onDismissRequest = {
                onExit()
                isChooseNumberOfQuestionsDialogVisible.value = false
                               },
            onSelectNumberOfQuestions = {
                viewModel.resetGame(numberOfQuestions = it)
                isChooseNumberOfQuestionsDialogVisible.value = false
                focusRequesterForTextField.requestFocus()
                                        },
            listOfNumberOfQuestions = LIST_OF_AVAILABLE_NUMBER_OF_QUESTIONS
        )
    }
}

@Composable
fun IdentifyAbbreviationOfStatesBody(
    state: IdentifyAbbreviationOfStateState,
    onUserInputAbbreviationChange: (String) -> Unit,
    focusRequesterForTextField: FocusRequester? = null,
    onEnterAnswer: () -> Unit,
    onPause: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = 400.dp)
    ) {
        GameStatus(
            state = state.gameStatus,
            onPause = onPause
        )
        Spacer(modifier = Modifier.weight(1f))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 200.dp)
        ) {
            AnimatedVisibility(
                visible = !state.isCorrectAnswerShown,
                enter = scaleIn(),
                exit = scaleOut(
                    animationSpec = tween(
                        delayMillis = 700
                    )
                )
            ) {
                Text(
                    text = state.stateName,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            val textFieldValueColor = if (state.isCorrectAnswerShown) {
                if (state.isAnswerCorrect) Color.Green
                else Color.Red
            } else {
                MaterialTheme.typography.bodyMedium.color
            }

            OutlinedTextField(
                value = state.userInputAbbreviation,
                onValueChange = onUserInputAbbreviationChange,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium
                    .copy(
                        textAlign = TextAlign.Center,
                        color = textFieldValueColor
                    ),
                shape = RoundedCornerShape(24.dp),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { onEnterAnswer() }),
                modifier = Modifier.focusRequester(focusRequesterForTextField ?: FocusRequester())
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IdentifyAbbreviationOfStatesBodyPreview() {
    GameForGASIRTheme {
        IdentifyAbbreviationOfStatesBody(
            state = IdentifyAbbreviationOfStateState(
                stateName = "Alabama",
                userInputAbbreviation = "AL"
            ),
            onUserInputAbbreviationChange = {},
            onEnterAnswer = { },
            onPause = { }
        )
    }
}