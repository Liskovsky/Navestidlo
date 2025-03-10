package eu.dlnauka.navestidlo.ui.classes

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.dlnauka.navestidlo.ui.datastore.NavestiRepository
import kotlinx.coroutines.launch

// Definuje ViewModel pro obrazovku testu
class TestViewModel(private val repository: NavestiRepository) : ViewModel() {

    var state by mutableStateOf(TestScreenState())
        private set

    // Funkce pro nastavení počtu otázek
    fun setQuestionCount(count: Int) {
        state = state.copy(maxQuestionCount = count, questionCount = 0, correctAnswers = 0, showQuestionSelect = false)
        loadNextQuestion()
    }
    // Funkce pro výběr odpovědi
    fun selectAnswer(answer: String) {

        // Nic se neprovádí, pokud již je vybraná odpověď nebo není otázka aktuální
        if (state.selectedAnswer != null || state.currentQuestion == null) return

        // Zjistí, zda je odpověď správná
        val isCorrect = answer == state.currentQuestion?.correctAnswer
        updateState {
            copy(
                selectedAnswer = answer,
                correctAnswers = if (isCorrect) correctAnswers + 1 else correctAnswers,
                showAnswers = true
            )
        }
        viewModelScope.launch {

            // Po krátké prodlevě se načte další otázka nebo se zobrazí výsledky celého testu
            kotlinx.coroutines.delay(1000L)
            if (state.questionCount + 1 >= state.maxQuestionCount) {
                state = state.copy(showResults = true)
            } else {
                updateState { copy(showAnswers = false) }
                loadNextQuestion()
            }
        }
    }
    // Funkce pro načtení další otázky
    private fun loadNextQuestion() {
        viewModelScope.launch {
            repository.getRandomQuestion()?.let { (event, answers) ->
                updateState {
                    copy(
                        currentQuestion = event,
                        answerOptions = answers,
                        selectedAnswer = null,
                        showAnswers = false,
                        questionCount = questionCount + 1
                    )
                }
            } ?: updateState { copy(showResults = true) }
        }
    }
    // Funkce aktualizující stav
    private fun updateState(update: TestScreenState.() -> TestScreenState) {
        state = update(state)
    }
    // Funkce pro restartování celého testutestu
    fun restartTest() {
        state = TestScreenState()
    }
}
