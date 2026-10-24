package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.AlphabetData
import com.example.data.NumbersData
import com.example.data.ProgressRepository
import com.example.data.RewardData
import com.example.data.RhymesData
import com.example.ui.screens.QuizGenerator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Kids Bornomala", appName)
  }

  @Test
  fun `verify alphabet dataset integrity`() {
    assertEquals(11, AlphabetData.banglaVowels.size)
    assertEquals(39, AlphabetData.banglaConsonants.size)
    assertEquals(26, AlphabetData.englishLetters.size)
    assertEquals(100, NumbersData.numbers1To100.size)
    assertTrue(RhymesData.banglaRhymes.isNotEmpty())
    assertTrue(RhymesData.englishRhymes.isNotEmpty())
  }

  @Test
  fun `verify badge and medal reward catalog`() {
    assertEquals(4, RewardData.medals.size)
    assertTrue(RewardData.achievementBadges.size >= 7)
    assertEquals(11, RewardData.allRewards.size)

    val bronze = RewardData.medals.find { it.id == "medal_bronze" }
    assertNotNull(bronze)
    assertEquals(3, bronze!!.targetCount)

    val silver = RewardData.medals.find { it.id == "medal_silver" }
    assertNotNull(silver)
    assertEquals(10, silver!!.targetCount)

    val quizBadge = RewardData.achievementBadges.find { it.id == "badge_quiz_champion" }
    assertNotNull(quizBadge)

    val lvl1 = RewardData.getLevelForStars(5)
    assertEquals(1, lvl1.levelNumber)

    val lvl3 = RewardData.getLevelForStars(40)
    assertEquals(3, lvl3.levelNumber)
  }

  @Test
  fun `verify reward unlocking logic in repository`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val repo = ProgressRepository(context)
    val state = repo.progress.value
    assertTrue(state.totalStars >= 0)

    val newlyUnlocked = repo.recordLetterCompletion("bn_v_3", 3)
    val updatedState = repo.progress.value
    assertTrue(updatedState.completedLetterIds.contains("bn_v_3"))
    assertEquals(3, updatedState.letterStars["bn_v_3"])
  }

  @Test
  fun `verify daily quick quiz generation and completion rewards`() {
    val questions = QuizGenerator.generateDailyQuiz(5)
    assertEquals(5, questions.size)
    questions.forEach { q ->
      assertEquals(4, q.options.size)
      assertTrue(q.correctIndex in 0..3)
      assertTrue(q.titleBangla.isNotEmpty())
    }

    val context = ApplicationProvider.getApplicationContext<Context>()
    val repo = ProgressRepository(context)
    val initialStars = repo.progress.value.totalStars

    val (earnedStars, newBadges) = repo.recordQuizCompletion(score = 5, total = 5)
    assertTrue(earnedStars >= 10) // 5*2 + 10 daily bonus = 20
    val updatedState = repo.progress.value
    assertTrue(updatedState.totalStars >= initialStars + earnedStars)
    assertTrue(updatedState.todayQuizCompleted)
    assertEquals(1, updatedState.totalQuizzesCompleted)
  }
}
