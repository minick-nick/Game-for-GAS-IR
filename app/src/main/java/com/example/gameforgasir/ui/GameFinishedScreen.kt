package com.example.gameforgasir.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gameforgasir.R
import com.example.gameforgasir.ui.navigation.NavigationDestination
import com.example.gameforgasir.ui.theme.GameForGASIRTheme

object GameFinishedDestination : NavigationDestination {
    override val route = "game_finished"
    const val scoreArg = "score"
    const val questionTypeArg = "questionType"
    const val numberOfCorrectAnswersArg = "numberOfCorrectAnswers"
    const val numberOfQuestionsArg = "numberOfQuestions"
    const val previousNavigationDestinationRouteArg = "previousNavigationDestinationRoute"
    val routeWithArgs = "$route/{$scoreArg}/{$questionTypeArg}/{$numberOfCorrectAnswersArg}" +
                        "/{$numberOfQuestionsArg}/{$previousNavigationDestinationRouteArg}"
    fun putArgs(score: Int,
                @StringRes
                questionType: Int,
                numberOfCorrectAnswers: Int,
                numberOfQuestions: Int,
                previousNavigationDestinationRoute: String): String {
        return "$route/$score/$questionType/$numberOfCorrectAnswers" +
                "/$numberOfQuestions/$previousNavigationDestinationRoute"
    }
}

@Composable
fun GameFinishedScreen(
    state: GameFinishedState,
    onExit: () -> Unit,
    onReplay: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = 470.dp)
    ) {
        ScoreDetailsCard(
            score = state.score.toString(),
            questionType = state.questionType,
            numberOfCorrectAnswers = state.numberOfCorrectAnswers.toString(),
            numberOfQuestions = state.numberOfQuestions.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 378.dp)
        )
        Spacer(modifier.weight(1f))
        Controller(
            onExit = onExit,
            onReplay = onReplay,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun Controller(
    onExit: () -> Unit,
    onReplay: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        OutlinedButton(onClick = onExit) {
            Text(
                text = stringResource(R.string.exit)
            )
        }
        Spacer(Modifier.size(dimensionResource(R.dimen.padding_small)))
        Button(onClick = onReplay) {
            Text(
                text = stringResource(R.string.replay)
            )
        }
    }
}

@Composable
fun ScoreDetailsCard(
    score: String,
    @StringRes
    questionType: Int,
    numberOfCorrectAnswers: String,
    numberOfQuestions: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_extra_small))
        ) {
            ScoreDetailsCardRow(
                label = R.string.score,
                data = score,
                dataTextStyle = MaterialTheme.typography.displayMedium,
                modifier = modifier.weight(1f)
            )
            ScoreDetailsCardRow(
                labelPrefix = stringResource(questionType),
                label = R.string.you_got_right,
                data = stringResource(R.string.of, numberOfCorrectAnswers, numberOfQuestions),
                dataTextStyle = MaterialTheme.typography.headlineSmall,
                modifier = modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ScoreDetailsCardRow(
    labelPrefix: String = "",
    @StringRes
    label: Int,
    data: String,
    dataTextStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, color = MaterialTheme.colorScheme.outlineVariant)
    ) {
        Text(
            text = stringResource(label, labelPrefix),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = data,
            style = dataTextStyle,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun GameFinishedScreenPreview() {
    GameForGASIRTheme {
        GameFinishedScreen(
            state = GameFinishedState(
                questionType = R.string.abbreviation
            ),
            onReplay = {},
            onExit = {}
        )
    }
}

