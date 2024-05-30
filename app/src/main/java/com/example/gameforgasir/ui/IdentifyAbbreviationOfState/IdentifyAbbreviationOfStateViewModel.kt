package com.example.gameforgasir.ui.IdentifyAbbreviationOfState

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameforgasir.CORRECT_ANSWER_VISIBILITY_TIME_DURATION
import com.example.gameforgasir.TIME_DURATION_FOR_EACH_QUESTION_IN_SECONDS
import com.example.gameforgasir.MAX_QUESTIONS
import com.example.gameforgasir.SCORE_INCREASE
import com.example.gameforgasir.data.SoundEffectsRepository
import com.example.gameforgasir.data.Timer
import com.example.gameforgasir.data.local.UsState
import com.example.gameforgasir.ui.GameStatusState
import com.example.gameforgasir.ui.IdentifyAbbreviationOfStateState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IdentifyAbbreviationOfStateViewModel(
    private val usStates: List<UsState>,
    private val soundEffectsRepository: SoundEffectsRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(IdentifyAbbreviationOfStateState())
    val uiState = _uiState.asStateFlow()

    private var remainingQuestions: MutableList<UsState> = mutableListOf()
    private lateinit var currentQuestion: UsState

    val timer = object : Timer(
        coroutineScope = viewModelScope,
        startingTimeInSeconds = TIME_DURATION_FOR_EACH_QUESTION_IN_SECONDS
    ) {
        override fun onTick(remainingTimeInSeconds: Long) {
            updateRemainingTime(remainingTimeInSeconds)
        }
        override fun onFinish() {
            if (isNoAnswer()) {
                soundEffectsRepository.playTimesUpSoundEffect()
            } else {
                if (isAnswerCorrect()) {
                    soundEffectsRepository.playCorrectSoundEffect()
                    setIsAnswerCorrectValue(true)
                } else {
                    soundEffectsRepository.playIncorrectSoundEffect()
                }
            }
            if (remainingQuestions.isNotEmpty()) {
                showCorrectAnswer()
                executeAfter(CORRECT_ANSWER_VISIBILITY_TIME_DURATION) {
                    hideCorrectAnswer()
                    resetUserInputAbbreviation()
                    nextQuestion()
                }
                this.restart()
            } else {
                this.stop()
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

    init { resetGame() }

    fun resetGame() {
        remainingQuestions = usStates
            .shuffled()
            .subList(0, MAX_QUESTIONS)
            .toMutableList()

        _uiState.value = IdentifyAbbreviationOfStateState(
            stateName = pickRandomUsState().name,
            gameStatus = GameStatusState(
                numberOfAnsweredQuestions = getNumberOfAnsweredQuestions(),
                numberOfQuestions = MAX_QUESTIONS,
                remainingTime = TIME_DURATION_FOR_EACH_QUESTION_IN_SECONDS
            )
        )
        timer.reset()
    }

    fun isAnswerCorrect(): Boolean {
        return currentQuestion.abbreviation.equals(
            uiState.value.userInputAbbreviation, ignoreCase = true
        )
    }

    fun isNoAnswer(): Boolean {
        return uiState.value.userInputAbbreviation.isBlank()
    }

    fun checkAbbreviationAnswer() {
        if (getNumberOfAnsweredQuestions() <= MAX_QUESTIONS) {
            if (isAnswerCorrect()) {
                soundEffectsRepository.playCorrectSoundEffect()
                setIsAnswerCorrectValue(true)
                increaseScoreAndNumberOfCorrectAnswer()
            } else {
                soundEffectsRepository.playIncorrectSoundEffect()
                setIsAnswerCorrectValue(false)
            }
            if (remainingQuestions.isNotEmpty()) {
                showCorrectAnswer()
                executeAfter(CORRECT_ANSWER_VISIBILITY_TIME_DURATION) {
                    hideCorrectAnswer()
                    resetUserInputAbbreviation()
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

    fun nextQuestion() {
        _uiState.update {
            it.copy(
                stateName = pickRandomUsState().name,
                gameStatus = it.gameStatus.copy(
                    numberOfAnsweredQuestions = getNumberOfAnsweredQuestions()
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

    fun updateAbbreviationAnswer(value: String) {
        val formattedValue = (if(value.length > 2) value.substring(0, 2) else value)
            .uppercase()

        _uiState.update {
            it.copy(userInputAbbreviation = formattedValue)
        }
    }

    fun resetUserInputAbbreviation() {
        _uiState.update {
            it.copy(userInputAbbreviation = "")
        }
    }

    fun showCorrectAnswer() {
        _uiState.update {
            it.copy(
                isCorrectAnswerShown = true
            )
        }
    }

    fun hideCorrectAnswer() {
        _uiState.update {
            it.copy(
                isCorrectAnswerShown = false,
                isAnswerCorrect = false
            )
        }
    }

    fun setIsAnswerCorrectValue(value: Boolean) {
        _uiState.update {
            it.copy(
                isAnswerCorrect = value
            )
        }
    }

    private fun getNumberOfAnsweredQuestions() = MAX_QUESTIONS - remainingQuestions.size
}
