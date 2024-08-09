package com.example.gameforgasir.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.gameforgasir.R
import com.example.gameforgasir.ui.theme.GameForGASIRTheme

enum class ChoiceBtnBackgroundColor {
    Default,
    Green,
    Red
}
@Composable
fun TopGameStatusBottomChoices(
    numberOfAnsweredQuestion: Int,
    numberOfQuestions: Int,
    remainingTime: Long,
    currentScore: Int,
    onPause: () -> Unit,
    choices: List<Choice>,
    isCorrectAnswerShown: Boolean,
    correctAnswerIndex: Int?,
    userAnswerIndex: Int?,
    onSelect: (Choice) -> Unit,
    modifier: Modifier = Modifier,
    middleContent: @Composable () -> Unit
) {
    Column(
        modifier =  modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.weight(1f)
        ) {
            GameStatus(
                state = GameStatusState(
                    currentQuestionNumber = numberOfAnsweredQuestion,
                    numberOfQuestions = numberOfQuestions,
                    remainingTime = remainingTime,
                    currentScore = currentScore,
                ),
                onPause = onPause,
            )

            val density = LocalDensity.current

            AnimatedVisibility(
                visible = !isCorrectAnswerShown,
                enter = slideInHorizontally {
                    with(density) {
                        200.dp.roundToPx()
                    }
                },
                exit = slideOutHorizontally(
                    animationSpec = tween(
                        delayMillis = 500
                    )
                ) {
                    with(density) {
                        -500.dp.roundToPx()
                    }
                },
                modifier = modifier
            ) {
                middleContent()
            }
        }
        Box(
            modifier
                .fillMaxWidth()
                .weight(2f)
        ){
            Choices(
                choices = choices,
                onSelect = onSelect,
                isCorrectAnswerShown = isCorrectAnswerShown,
                correctAnswerIndex = correctAnswerIndex,
                userAnswerIndex = userAnswerIndex,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun GameStatus(
    state: GameStatusState,
    onPause: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            IconButton(
                onClick = onPause,
                modifier = Modifier.size(dimensionResource(R.dimen.pause_icon_size))
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_pause_circle_filled_24),
                    contentDescription = stringResource(R.string.pause_icon),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_small)))
            Row(
                modifier = Modifier.weight(1f)
            ) {
                GameStatusColumn(
                    label = stringResource(R.string.question),
                    data = stringResource(
                        R.string.slash,
                        state.currentQuestionNumber.toString(),
                        state.numberOfQuestions.toString()
                    ),
                    modifier = Modifier.weight(1f)
                )
                GameStatusColumn(
                    label = stringResource(R.string.time),
                    data = stringResource(R.string.colon, state.remainingTime.toString()),
                    modifier = Modifier.weight(1f)
                )
                GameStatusColumn(
                    label = stringResource(R.string.score),
                    data = state.currentScore.toString(),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun GameStatusColumn(
    label: String,
    data: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxHeight()
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            text = data,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
fun PauseDialog(
    onDismissRequest: () -> Unit,
    onRestart: () -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Card(
            modifier = modifier
                .width(312.dp)
                .height(144.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.padding_medium))
            ) {
                Text(
                    text = stringResource(R.string.game_paused),
                    style = MaterialTheme.typography.titleLarge
                )
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onRestart) {
                        Text(
                            text = stringResource(R.string.restart),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                    TextButton(onClick = onExit) {
                        Text(
                            text = stringResource(R.string.exit),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChooseNumberOfQuestionsDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onSelectNumberOfQuestions: (Int) -> Unit,
    listOfNumberOfQuestions: List<Int>
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Card(
            modifier = modifier
        ) {
            Column(
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_large)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_large))
            ) {
                Text(
                    text = stringResource(R.string.choose_your_desire_number_of_questions),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Column {
                    listOfNumberOfQuestions.forEach {
                        Button(onClick = { onSelectNumberOfQuestions(it) }) {
                            Text(
                                text = it.toString(),
                                style = MaterialTheme.typography.labelLarge,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun Choices(
    choices: List<Choice>,
    onSelect: (Choice) -> Unit,
    isCorrectAnswerShown: Boolean,
    correctAnswerIndex: Int?,
    userAnswerIndex: Int?,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current

    AnimatedVisibility(
        visible = !isCorrectAnswerShown,
        enter = slideInHorizontally {
            with(density) {
                200.dp.roundToPx()
            }
        },
        exit = slideOutHorizontally(
            animationSpec = tween(
                delayMillis = 600
            )
        ) {
            with(density) {
                -500.dp.roundToPx()
            }
        },
        modifier = modifier
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_extra_small))
        ) {
            if (isCorrectAnswerShown) {
                choices.forEachIndexed { index, choice ->
                    ChoiceBtn(
                        choice = choice,
                        choiceBtnBackgroundColor = when {
                            correctAnswerIndex == index -> ChoiceBtnBackgroundColor.Green
                            userAnswerIndex == index -> ChoiceBtnBackgroundColor.Red
                            else -> ChoiceBtnBackgroundColor.Default
                        }
                    )
                }
            } else {
                choices.forEachIndexed { index, choice ->
                    ChoiceBtn(
                        choice = choice,
                        onSelect = onSelect,
                        choiceBtnBackgroundColor = ChoiceBtnBackgroundColor.Default
                    )
                }
            }

        }
    }

}

@Composable
fun ChoiceBtn(
    choice: Choice,
    onSelect: (Choice) -> Unit = {},
    enabled: Boolean = true,
    choiceBtnBackgroundColor: ChoiceBtnBackgroundColor,

    modifier: Modifier = Modifier
) {
    var btnBorderColor: BorderStroke
    var btnColor: ButtonColors

    when (choiceBtnBackgroundColor) {
        ChoiceBtnBackgroundColor.Default -> {
            btnBorderColor = ButtonDefaults.outlinedButtonBorder
            btnColor = ButtonDefaults.outlinedButtonColors()
        }

        ChoiceBtnBackgroundColor.Green -> {
            btnBorderColor = BorderStroke(0.dp, Color.Transparent)
            btnColor = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.White,
                containerColor = Color.Green
            )
        }

        ChoiceBtnBackgroundColor.Red -> {
            btnBorderColor = BorderStroke(0.dp, Color.Transparent)
            btnColor = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.White,
                containerColor = Color.Red
            )
        }
    }
    OutlinedButton(
        onClick = { onSelect(choice) },
        border = btnBorderColor,
        colors = btnColor,
        enabled = enabled,
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = choice.text,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopGameStatusBottomChoicesPreview() {
    val sampleChoices = listOf(
        Choice( "YELLOWHAMMER STATE", true),
        Choice( "THE LSAT FRONTIER"),
        Choice( "GRAND CANYON STATE"),
        Choice( "THE NATURAL STATE")
    )
    GameForGASIRTheme {
        TopGameStatusBottomChoices(
            numberOfAnsweredQuestion = 0,
            numberOfQuestions = 0,
            remainingTime = 0,
            currentScore = 0,
            onPause = {},
            choices = sampleChoices,
            isCorrectAnswerShown = false,
            userAnswerIndex = null,
            correctAnswerIndex = null,
            onSelect = {}
        ) {
            Text(text = "Middle Content")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChoicesPreview() {
    val mockData = listOf(
        Choice( "YELLOWHAMMER STATE", true),
        Choice( "THE LSAT FRONTIER"),
        Choice( "GRAND CANYON STATE"),
        Choice( "THE NATURAL STATE")
    )

    GameForGASIRTheme {
        Choices(
            choices = mockData,
            isCorrectAnswerShown = false,
            correctAnswerIndex = null,
            userAnswerIndex = null,
            onSelect = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChoiceBtnPreview() {
    GameForGASIRTheme {
        Column {
            ChoiceBtn(
                choice = Choice( "YELLOWHAMMER STATE"),
                onSelect = {},
                choiceBtnBackgroundColor = ChoiceBtnBackgroundColor.Default
            )
            ChoiceBtn(
                choice = Choice( "YELLOWHAMMER STATE"),
                onSelect = {},
                choiceBtnBackgroundColor = ChoiceBtnBackgroundColor.Green
            )
            ChoiceBtn(
                choice = Choice( "YELLOWHAMMER STATE"),
                onSelect = {},
                choiceBtnBackgroundColor = ChoiceBtnBackgroundColor.Red
            )
        }
    }
}

@Preview
@Composable
fun GameStatusPreview() {
    GameForGASIRTheme {
        GameStatus(
            state = GameStatusState(
                currentQuestionNumber = 10,
                numberOfQuestions = 50,
                remainingTime = 10,
                currentScore = 100
            ),
            onPause = {}
        )
    }
}

@Preview
@Composable
fun PauseDialogPreview() {
    GameForGASIRTheme {
        PauseDialog(
            onDismissRequest = {},
            onRestart = {},
            onExit = {}
        )
    }
}

@Preview
@Composable
fun ChooseNumberOfQuestionsPreview() {
    GameForGASIRTheme {
        Surface {
            ChooseNumberOfQuestionsDialog(
                onSelectNumberOfQuestions = {},
                onDismissRequest = {},
                listOfNumberOfQuestions = listOf(10, 20, 30, 40, 50)
            )
        }
    }
}
