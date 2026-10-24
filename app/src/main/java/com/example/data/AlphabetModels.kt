package com.example.data

data class Waypoint(
    val x: Float,
    val y: Float
)

data class Stroke(
    val points: List<Waypoint>
)

enum class LetterType {
    BANGLA_VOWEL,
    BANGLA_CONSONANT,
    ENGLISH_ALPHABET
}

data class LetterItem(
    val id: String,
    val character: String,
    val subCharacter: String = "",
    val name: String,
    val pronunciation: String,
    val word: String,
    val wordMeaning: String,
    val emoji: String,
    val type: LetterType,
    val strokes: List<Stroke> = emptyList()
)

data class NumberItem(
    val value: Int,
    val banglaNumeral: String,
    val englishNumeral: String,
    val banglaWord: String,
    val englishWord: String,
    val emoji: String,
    val strokes: List<Stroke> = emptyList()
)

data class RhymeLine(
    val banglaText: String,
    val englishText: String,
    val durationMs: Long = 3000L
)

data class RhymeItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val language: String, // "bn" or "en"
    val emoji: String,
    val colorHex: Long,
    val lines: List<RhymeLine>,
    val melodyNotes: List<Pair<Double, Int>> = emptyList()
)

data class ProgressState(
    val totalStars: Int = 0,
    val completedLetterIds: Set<String> = emptySet(),
    val completedNumbers: Set<Int> = emptySet(),
    val rhymesListened: Set<String> = emptySet(),
    val streakDays: Int = 3,
    val totalPracticeMinutes: Int = 24,
    val letterStars: Map<String, Int> = emptyMap(),
    val soundEffectsEnabled: Boolean = true,
    val ttsVoiceRate: Float = 0.85f,
    val unlockedBadgeIds: Set<String> = emptySet(),
    val totalQuizzesCompleted: Int = 0,
    val lastQuizDate: String = "",
    val todayQuizCompleted: Boolean = false
)
