package com.example.gameforgasir.ui.IdentifyStateNickname

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.gameforgasir.CORRECT_ANSWER_VISIBILITY_TIME_DURATION
import com.example.gameforgasir.DEFAULT_NUMBER_OF_QUESTIONS
import com.example.gameforgasir.GameOfGasIrApplication
import com.example.gameforgasir.NUMBER_OF_CHOICES
import com.example.gameforgasir.SCORE_INCREASE
import com.example.gameforgasir.TIME_DURATION_FOR_EACH_QUESTION_IN_SECONDS
import com.example.gameforgasir.data.SoundEffectsRepository
import com.example.gameforgasir.data.Timer
import com.example.gameforgasir.data.local.LocalGameForGasIrData
import com.example.gameforgasir.data.local.UsState
import com.example.gameforgasir.ui.Choice
import com.example.gameforgasir.ui.GameStatusState
import com.example.gameforgasir.ui.IdentifyStateNicknameState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IdentifyStateNicknameViewModel(
    private val usStates: List<UsState>,
    private val soundEffectsRepository: SoundEffectsRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(IdentifyStateNicknameState())
    val uiState = _uiState.asStateFlow()

    private var remainingQuestions: MutableList<UsState> = mutableListOf()
    private var currentChoices: List<Choice> = listOf()

    private lateinit var currentQuestion: UsState
    private var correctAnswerIndex: Int? = null
    private var userAnswerIndex: Int? = null

    val timer = object : Timer(
        coroutineScope = viewModelScope,
        startingTimeInSeconds = TIME_DURATION_FOR_EACH_QUESTION_IN_SECONDS
    ) {
        override fun onTick(remainingTimeInSeconds: Long) {
            updateRemainingTime(remainingTimeInSeconds)
        }
        override fun onFinish() {
            soundEffectsRepository.playTimesUpSoundEffect()
            if (remainingQuestions.isNotEmpty()) {
                Log.i("OnFinishTimer", userAnswerIndex.toString())
                setCorrectAnswerIndex()
                showCorrectAnswer()
                executeAfter(CORRECT_ANSWER_VISIBILITY_TIME_DURATION) {
                    hideCorrectAnswer()
                    nextQuestion()
                }
                this.restart()
            } else {
                this.stop()
                setCorrectAnswerIndex()
                showCorrectAnswer()
                executeAfter(CORRECT_ANSWER_VISIBILITY_TIME_DURATION) {
                    gameOver()
                }
            }
        }
    }

    private fun executeAfter(delayMillis: Long, action: () -> Unit) {
        viewModelScope.launch {
            delay(delayMillis)
            action()
        }
    }

    fun resetGame(numberOfQuestions: Int = DEFAULT_NUMBER_OF_QUESTIONS ) {
        remainingQuestions = usStates
            .shuffled()
            .subList(0, numberOfQuestions)
            .toMutableList()

        val pickedUsState = pickRandomUsState()

        _uiState.value = IdentifyStateNicknameState(
            stateName = pickedUsState.name,
            stateAbbreviation = pickedUsState.abbreviation,
            choices = getChoices(),
            gameStatus = GameStatusState(
                currentQuestionNumber = numberOfQuestions - remainingQuestions.size,
                numberOfQuestions = numberOfQuestions,
                remainingTime = TIME_DURATION_FOR_EACH_QUESTION_IN_SECONDS
            )
        )
        timer.reset()
    }

    fun checkNicknameAnswer(userAnswer: Choice) {
        setUserAnswerIndex(userAnswer)
        setCorrectAnswerIndex()

        if (getNumberOfAnsweredQuestions() <= uiState.value.gameStatus.numberOfQuestions) {
            if(userAnswer.isTheAnswer) {
                soundEffectsRepository.playCorrectSoundEffect()
                increaseScoreAndNumberOfCorrectAnswer()
            } else {
                soundEffectsRepository.playIncorrectSoundEffect()
            }
            if (remainingQuestions.isNotEmpty()) {
                showCorrectAnswer()
                executeAfter(CORRECT_ANSWER_VISIBILITY_TIME_DURATION) {
                    hideCorrectAnswer()
                    nextQuestion()
                }
                timer.restart()
            } else {
                timer.stop()
                showCorrectAnswer()
                executeAfter(CORRECT_ANSWER_VISIBILITY_TIME_DURATION) {
                    gameOver()
                }
            }
        }
    }

    private fun increaseScoreAndNumberOfCorrectAnswer() {
        _uiState.update {
            it.copy(
                gameStatus = it.gameStatus.copy(
                    currentScore = it.gameStatus.currentScore + SCORE_INCREASE,
                    numberOfCorrectAnswers = it.gameStatus.numberOfCorrectAnswers + 1
                )
            )
        }
    }

    private fun nextQuestion() {
        val pickedUsState = pickRandomUsState()

        _uiState.update {
            it.copy(
                stateName = pickedUsState.name,
                stateAbbreviation = pickedUsState.abbreviation,
                choices = getChoices(),
                gameStatus = it.gameStatus.copy(
                    currentQuestionNumber = getNumberOfAnsweredQuestions()
                )
            )
        }
    }

    private fun pickRandomUsState(): UsState {
        if (remainingQuestions.isNotEmpty()) {
            return remainingQuestions.random().also { pickedUsState ->
                remainingQuestions.remove(pickedUsState)
                currentQuestion = pickedUsState
            }
        }

        return currentQuestion
    }

    private fun getChoices(): List<Choice> {
        val nicknameAnswer = currentQuestion.nickname
        val usStatesNicknames = usStates
            .map { it.nickname }
            .toMutableList()
            .also { it.remove(nicknameAnswer) }

        val choices = mutableListOf(
            Choice(nicknameAnswer, true)
        )

        while(choices.size != NUMBER_OF_CHOICES) {
            usStatesNicknames.random().also {
                choices.add(Choice(it))
                usStatesNicknames.remove(it)
            }
        }

        return choices
            .shuffled()
            .also { currentChoices = it }
    }

    private fun gameOver() {
        _uiState.update {
            it.copy(
                gameStatus = it.gameStatus.copy(
                    isGameOver = true
                )
            )
        }
    }

    private fun updateRemainingTime(remainingTimeInSeconds: Long) {
        _uiState.update {
            it.copy(
                gameStatus = it.gameStatus.copy(
                    remainingTime = remainingTimeInSeconds
                )
            )
        }
    }

    private fun showCorrectAnswer() {
        _uiState.update {
            it.copy(
                isCorrectAnswerShown = true,
                correctAnswerIndex = correctAnswerIndex,
                userAnswerIndex = userAnswerIndex
            )
        }
    }

    private fun hideCorrectAnswer() {
        _uiState.update {
            it.copy(
                isCorrectAnswerShown = false,
            )
        }
        userAnswerIndex = null
        correctAnswerIndex = null
    }

    private fun setUserAnswerIndex(userAnswer: Choice) {
        userAnswerIndex = currentChoices.indexOf(userAnswer)
    }

    private fun setCorrectAnswerIndex() {
        currentChoices.forEachIndexed { index, choice ->
            if (choice.isTheAnswer) {
                correctAnswerIndex = index
            }
        }
    }

    private fun getNumberOfAnsweredQuestions() = uiState.value.gameStatus.numberOfQuestions - remainingQuestions.size

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                IdentifyStateNicknameViewModel(
                    LocalGameForGasIrData.usStates,
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                            as GameOfGasIrApplication)
                        .container.soundEffectsRepository
                )
            }
        }
    }
}
