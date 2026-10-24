package com.example.data

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProgressRepository(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("kids_bornomala_prefs", Context.MODE_PRIVATE)

    private val _progress = MutableStateFlow(loadProgress())
    val progress: StateFlow<ProgressState> = _progress.asStateFlow()

    private fun loadProgress(): ProgressState {
        val stars = prefs.getInt("total_stars", 12)
        val letterIds = prefs.getStringSet("completed_letters", setOf("bn_v_1", "bn_v_2", "en_a", "bn_c_1")) ?: emptySet()
        val numStrings = prefs.getStringSet("completed_numbers", setOf("1", "2", "3", "4", "5")) ?: emptySet()
        val completedNums = numStrings.mapNotNull { it.toIntOrNull() }.toSet()
        val rhymes = prefs.getStringSet("rhymes_listened", setOf("rhyme_bn_1", "rhyme_en_1")) ?: emptySet()
        val streak = prefs.getInt("streak_days", 3)
        val minutes = prefs.getInt("practice_minutes", 18)
        val soundEnabled = prefs.getBoolean("sound_effects_enabled", true)
        val ttsRate = prefs.getFloat("tts_voice_rate", 0.85f)
        val badges = prefs.getStringSet("unlocked_badges", setOf("medal_bronze")) ?: emptySet()
        val totalQuizzes = prefs.getInt("total_quizzes_completed", 0)
        val lastQuizDate = prefs.getString("last_quiz_date", "") ?: ""
        val todayStr = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.US).format(java.util.Date())
        val isTodayQuizDone = lastQuizDate == todayStr

        val letterStars = mutableMapOf<String, Int>()
        for (id in letterIds) {
            letterStars[id] = prefs.getInt("star_letter_$id", 3)
        }

        return ProgressState(
            totalStars = stars,
            completedLetterIds = letterIds,
            completedNumbers = completedNums,
            rhymesListened = rhymes,
            streakDays = streak,
            totalPracticeMinutes = minutes,
            letterStars = letterStars,
            soundEffectsEnabled = soundEnabled,
            ttsVoiceRate = ttsRate,
            unlockedBadgeIds = badges,
            totalQuizzesCompleted = totalQuizzes,
            lastQuizDate = lastQuizDate,
            todayQuizCompleted = isTodayQuizDone
        )
    }

    /**
     * Records letter completion and evaluates if any new medal or badge is unlocked.
     * Returns the list of newly earned rewards to trigger celebration in UI.
     */
    fun recordLetterCompletion(letterId: String, starsEarned: Int): List<BadgeReward> {
        val currentStars = prefs.getInt("star_letter_$letterId", 0)
        val newStars = maxOf(currentStars, starsEarned)
        val starDiff = newStars - currentStars

        val editor = prefs.edit()
        editor.putInt("star_letter_$letterId", newStars)

        val updatedLetterIds = (prefs.getStringSet("completed_letters", emptySet()) ?: emptySet()) + letterId
        editor.putStringSet("completed_letters", updatedLetterIds)

        var totalStars = prefs.getInt("total_stars", 12) + starDiff
        val streak = prefs.getInt("streak_days", 3)

        val practiceMinutes = prefs.getInt("practice_minutes", 18) + 1
        editor.putInt("practice_minutes", practiceMinutes)

        // Check for newly unlocked badges & medals
        val currentBadgeIds = prefs.getStringSet("unlocked_badges", emptySet()) ?: emptySet()
        val newlyEarned = mutableListOf<BadgeReward>()

        val vowelsCount = updatedLetterIds.count { it.startsWith("bn_v_") }
        val consonantsCount = updatedLetterIds.count { it.startsWith("bn_c_") }
        val englishCount = updatedLetterIds.count { it.startsWith("en_") }
        val totalLetters = updatedLetterIds.size

        for (reward in RewardData.allRewards) {
            if (!currentBadgeIds.contains(reward.id)) {
                val conditionMet = when (reward.id) {
                    "medal_bronze" -> totalLetters >= 3
                    "medal_silver" -> totalLetters >= 10
                    "medal_gold" -> totalLetters >= 25
                    "medal_diamond" -> totalLetters >= 45
                    "badge_vowel_master" -> vowelsCount >= 11
                    "badge_tiger_scribe" -> consonantsCount >= 5
                    "badge_abc_explorer" -> englishCount >= 10
                    "badge_star_collector" -> totalStars >= 25
                    "badge_streak_champion" -> streak >= 3
                    "badge_grand_scholar" -> totalStars >= 50
                    else -> false
                }

                if (conditionMet) {
                    newlyEarned.add(reward)
                    totalStars += reward.bonusStars
                }
            }
        }

        val updatedBadgeIds = currentBadgeIds + newlyEarned.map { it.id }.toSet()
        editor.putStringSet("unlocked_badges", updatedBadgeIds)
        editor.putInt("total_stars", totalStars)
        editor.apply()

        _progress.update {
            it.copy(
                totalStars = totalStars,
                completedLetterIds = updatedLetterIds,
                letterStars = it.letterStars + (letterId to newStars),
                totalPracticeMinutes = practiceMinutes,
                unlockedBadgeIds = updatedBadgeIds
            )
        }

        return newlyEarned
    }

    fun recordNumberPractice(num: Int) {
        val editor = prefs.edit()
        val numStrings = (prefs.getStringSet("completed_numbers", emptySet()) ?: emptySet()) + num.toString()
        editor.putStringSet("completed_numbers", numStrings)
        val totalStars = prefs.getInt("total_stars", 12) + 1
        editor.putInt("total_stars", totalStars)
        editor.apply()

        _progress.update {
            it.copy(
                totalStars = totalStars,
                completedNumbers = it.completedNumbers + num
            )
        }
    }

    fun recordRhymePlayed(rhymeId: String) {
        val editor = prefs.edit()
        val rhymes = (prefs.getStringSet("rhymes_listened", emptySet()) ?: emptySet()) + rhymeId
        editor.putStringSet("rhymes_listened", rhymes)
        val totalStars = prefs.getInt("total_stars", 12) + 1
        editor.putInt("total_stars", totalStars)
        editor.apply()

        _progress.update {
            it.copy(
                totalStars = totalStars,
                rhymesListened = rhymes
            )
        }
    }

    fun setSoundEffectsEnabled(enabled: Boolean) {
        prefs.edit().putBoolean("sound_effects_enabled", enabled).apply()
        _progress.update { it.copy(soundEffectsEnabled = enabled) }
    }

    fun setTtsVoiceRate(rate: Float) {
        prefs.edit().putFloat("tts_voice_rate", rate).apply()
        _progress.update { it.copy(ttsVoiceRate = rate) }
    }

    fun recordQuizCompletion(score: Int, total: Int): Pair<Int, List<BadgeReward>> {
        val todayStr = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.US).format(java.util.Date())
        val lastDate = prefs.getString("last_quiz_date", "") ?: ""
        val alreadyDoneToday = lastDate == todayStr

        val baseStars = score * 2
        val bonusStars = if (!alreadyDoneToday) 10 else 4
        val totalEarnedStars = baseStars + bonusStars

        var totalStars = prefs.getInt("total_stars", 12) + totalEarnedStars
        var streak = prefs.getInt("streak_days", 3)
        if (!alreadyDoneToday) {
            streak += 1
        }
        val totalQuizzes = prefs.getInt("total_quizzes_completed", 0) + 1

        val editor = prefs.edit()
        editor.putInt("total_stars", totalStars)
        editor.putInt("streak_days", streak)
        editor.putInt("total_quizzes_completed", totalQuizzes)
        editor.putString("last_quiz_date", todayStr)

        val currentBadgeIds = prefs.getStringSet("unlocked_badges", emptySet()) ?: emptySet()
        val newlyEarned = mutableListOf<BadgeReward>()

        for (reward in RewardData.allRewards) {
            if (!currentBadgeIds.contains(reward.id)) {
                val conditionMet = when (reward.id) {
                    "badge_quiz_champion" -> totalQuizzes >= 1
                    "badge_star_collector" -> totalStars >= 25
                    "badge_grand_scholar" -> totalStars >= 50
                    "badge_streak_champion" -> streak >= 3
                    else -> false
                }
                if (conditionMet) {
                    newlyEarned.add(reward)
                    totalStars += reward.bonusStars
                }
            }
        }

        val updatedBadgeIds = currentBadgeIds + newlyEarned.map { it.id }.toSet()
        editor.putStringSet("unlocked_badges", updatedBadgeIds)
        editor.putInt("total_stars", totalStars)
        editor.apply()

        _progress.update {
            it.copy(
                totalStars = totalStars,
                streakDays = streak,
                unlockedBadgeIds = updatedBadgeIds,
                totalQuizzesCompleted = totalQuizzes,
                lastQuizDate = todayStr,
                todayQuizCompleted = true
            )
        }

        return Pair(totalEarnedStars, newlyEarned)
    }

    fun resetProgress() {
        prefs.edit().clear().apply()
        _progress.value = ProgressState()
    }
}
