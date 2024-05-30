package com.example.gameforgasir.ui.IdentifyLicensePlateState

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gameforgasir.R
import com.example.gameforgasir.ui.AppViewModelProvider
import com.example.gameforgasir.ui.Choice
import com.example.gameforgasir.ui.IdentifyLicensePlateStateState
import com.example.gameforgasir.ui.PauseDialog
import com.example.gameforgasir.ui.TopGameStatusBottomChoices
import com.example.gameforgasir.ui.navigation.NavigationDestination
import com.example.gameforgasir.ui.theme.GameForGASIRTheme

object IdentifyLicensePlateStateDestination : NavigationDestination {
    override val route = "identify_license_plate_state"
}

@Composable
fun IdentifyLicensePlateStateScreen(
    viewModel: IdentifyLicensePlateStateViewModel
        = viewModel(factory = AppViewModelProvider.Factory),
    onExit: () -> Unit,
    onNavigateGameFinishedScreen: (Int, Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val windowInfo = LocalWindowInfo.current
    val isPauseDialogVisible = rememberSaveable { mutableStateOf(false) }

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

    IdentifyLicensePlateStateBody(
        state = uiState,
        onPause = { isPauseDialogVisible.value = true },
        onSelect = { answer ->
            viewModel.checkUsStateAnswer(answer)
        },
        modifier = modifier
    )

    if (isPauseDialogVisible.value) {
        PauseDialog(
            onRestart = {
                viewModel.resetGame()
                isPauseDialogVisible.value = false
            },
            onExit = {
                onExit()
                isPauseDialogVisible.value = false
            },
            onDismissRequest = { isPauseDialogVisible.value = false },
        )
    }
}

@Composable
fun IdentifyLicensePlateStateBody(
    state: IdentifyLicensePlateStateState,
    onPause: () -> Unit,
    onSelect: (Choice) -> Unit,
    modifier: Modifier = Modifier
) {
    TopGameStatusBottomChoices(
        numberOfAnsweredQuestion = state.gameStatus.numberOfAnsweredQuestions,
        numberOfQuestions = state.gameStatus.numberOfQuestions,
        remainingTime = state.gameStatus.remainingTime,
        currentScore = state.gameStatus.currentScore,
        choices = state.choices,
        isCorrectAnswerShown = state.isCorrectAnswerShown,
        correctAnswerIndex = state.correctAnswerIndex,
        userAnswerIndex = state.userAnswerIndex,
        onPause = onPause,
        onSelect = onSelect,
        modifier = modifier
    ){
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(state.usLicensePlate),
                contentDescription = stringResource(R.string.image_of_us_license_plate),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(197.dp)
            )
            /*            Canvas(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .width(197.dp)
                        ) {
                            drawRect(
                                color = Color.Red.copy(alpha = 0.45f),
                                topLeft = Offset(1f, -180f),
                                size = Size(500f, 360f)
                            )
                        }*/
            /*            Text(
                            text = "Indiana",
                            style = TextStyle(
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Medium
                            ),
                            color = Color.White,
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .background(Color.Red)
                                .padding(2.dp)
                        )*/
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IdentifyLicensePlateStateScreenPreview() {
    GameForGASIRTheme {
        val sampleChoices = List(4) {
            Choice("Indiana")
        }
        IdentifyLicensePlateStateBody(
            state = IdentifyLicensePlateStateState(
                usLicensePlate = R.drawable.indiana_lp,
                choices = sampleChoices
            ),
            onPause = { /*TODO*/ },
            onSelect = { }
        )
    }
}