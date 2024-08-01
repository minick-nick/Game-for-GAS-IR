package com.example.gameforgasir.ui

import androidx.annotation.DrawableRes

data class IdentifyAbbreviationOfStateState(
    val stateName: String = "",
    val userInputAbbreviation: String = "",
    val isAnswerCorrect: Boolean = false,
    val isCorrectAnswerShown: Boolean = false,
    val gameStatus: GameStatusState = GameStatusState(),
)

data class IdentifyStateNicknameState(
    val stateName: String = "",
    val stateAbbreviation: String = "",
    val userInputNickname: String = "",
    val choices: List<Choice> = emptyList(),
    val isCorrectAnswerShown: Boolean = false,
    val correctAnswerIndex: Int? = null,
    val userAnswerIndex: Int? = null,
    val gameStatus: GameStatusState =  GameStatusState()
)

data class IdentifyLicensePlateStateState(
    @DrawableRes val currentLicensePlate: Int? = null,
    val choices: List<Choice> = emptyList(),
    val isCorrectAnswerShown: Boolean = false,
    val correctAnswerIndex: Int? = null,
    val userAnswerIndex: Int? = null,
    val gameStatus: GameStatusState =  GameStatusState()
)

data class GameFinishedState(
    val score: Int = 0,
    val questionType: Int = 0,
    val numberOfCorrectAnswers: Int = 0,
    val numberOfQuestions: Int = 0,
)

data class GameStatusState(
    val numberOfAnsweredQuestions: Int = 0,
    val numberOfCorrectAnswers: Int = 0,
    val numberOfQuestions: Int = 0,
    val remainingTime: Long = 0,
    val currentScore: Int = 0,
    val isGameOver: Boolean = false,
)
data class Choice(
    val text: String,
    val isTheAnswer: Boolean = false
)