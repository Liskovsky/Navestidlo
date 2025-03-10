package eu.dlnauka.navestidlo.ui.classes

import eu.dlnauka.navestidlo.ui.datastore.Event

// Definuje datovou třídu pro počáteční stav obrazovky testu
data class TestScreenState(
    val questionCount: Int = 0,
    val correctAnswers: Int = 0,
    val maxQuestionCount: Int = 10,
    val showQuestionSelect: Boolean = true,
    val showResults: Boolean = false,
    val currentQuestion: Event? = null,
    val answerOptions: List<String> = emptyList(),
    val selectedAnswer: String? = null,
    val showAnswers: Boolean = false
)
