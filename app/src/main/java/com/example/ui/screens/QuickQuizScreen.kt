package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.KidsAudioService
import com.example.data.AlphabetData
import com.example.data.BadgeReward
import com.example.data.LetterItem
import com.example.data.LetterType
import com.example.data.NumbersData
import com.example.data.ProgressRepository
import com.example.ui.components.ConfettiEffect
import com.example.ui.theme.KidsCoral
import com.example.ui.theme.KidsMint
import com.example.ui.theme.KidsOrange
import com.example.ui.theme.KidsPurple
import com.example.ui.theme.KidsYellow
import com.example.ui.theme.PastelPalette
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

data class QuizQuestion(
    val titleBangla: String,
    val titleEnglish: String,
    val targetAudioPrompt: String,
    val isBengaliAudio: Boolean,
    val visualEmoji: String? = null,
    val visualClue: String? = null,
    val options: List<String>,
    val correctIndex: Int
)

object QuizGenerator {
    fun generateDailyQuiz(count: Int = 5): List<QuizQuestion> {
        val questions = mutableListOf<QuizQuestion>()

        // 1. Bangla Vowel Question
        val targetVowel = AlphabetData.banglaVowels.random()
        val otherVowels = AlphabetData.banglaVowels.filter { it.id != targetVowel.id }.shuffled().take(3)
        val vowelOptions = (listOf(targetVowel) + otherVowels).shuffled()
        questions.add(
            QuizQuestion(
                titleBangla = "নিচের কোনটি '${targetVowel.character}' (${targetVowel.word} ${targetVowel.emoji}) বর্ণ?",
                titleEnglish = "Find letter '${targetVowel.character}' (${targetVowel.word})",
                targetAudioPrompt = "${targetVowel.character}, ${targetVowel.word}",
                isBengaliAudio = true,
                visualEmoji = targetVowel.emoji,
                visualClue = targetVowel.word,
                options = vowelOptions.map { it.character },
                correctIndex = vowelOptions.indexOf(targetVowel)
            )
        )

        // 2. Bangla Consonant Question
        val targetConsonant = AlphabetData.banglaConsonants.take(20).random()
        val otherConsonants = AlphabetData.banglaConsonants.filter { it.id != targetConsonant.id }.shuffled().take(3)
        val consOptions = (listOf(targetConsonant) + otherConsonants).shuffled()
        questions.add(
            QuizQuestion(
                titleBangla = "${targetConsonant.word} ${targetConsonant.emoji} কোন বর্ণ দিয়ে শুরু?",
                titleEnglish = "Which letter starts ${targetConsonant.wordMeaning} ${targetConsonant.emoji}?",
                targetAudioPrompt = "${targetConsonant.character}, ${targetConsonant.word}",
                isBengaliAudio = true,
                visualEmoji = targetConsonant.emoji,
                visualClue = targetConsonant.word,
                options = consOptions.map { it.character },
                correctIndex = consOptions.indexOf(targetConsonant)
            )
        )

        // 3. English Alphabet Question
        val targetEng = AlphabetData.englishLetters.random()
        val otherEng = AlphabetData.englishLetters.filter { it.id != targetEng.id }.shuffled().take(3)
        val engOptions = (listOf(targetEng) + otherEng).shuffled()
        questions.add(
            QuizQuestion(
                titleBangla = "ইংরেজি '${targetEng.character}' (${targetEng.word} ${targetEng.emoji}) কোনটি?",
                titleEnglish = "Where is letter '${targetEng.character}' for ${targetEng.word} ${targetEng.emoji}?",
                targetAudioPrompt = "${targetEng.character}, ${targetEng.word}",
                isBengaliAudio = false,
                visualEmoji = targetEng.emoji,
                visualClue = targetEng.word,
                options = engOptions.map { it.character },
                correctIndex = engOptions.indexOf(targetEng)
            )
        )

        // 4. Number Identification Question (1 to 20)
        val targetNum = NumbersData.numbers1To100.take(20).random()
        val otherNums = NumbersData.numbers1To100.take(20).filter { it.value != targetNum.value }.shuffled().take(3)
        val numOptions = (listOf(targetNum) + otherNums).shuffled()
        questions.add(
            QuizQuestion(
                titleBangla = "'${targetNum.banglaNumeral}' (${targetNum.banglaWord} ${targetNum.emoji}) সংখ্যাটি স্পর্শ করো:",
                titleEnglish = "Find number '${targetNum.banglaNumeral}' / '${targetNum.englishNumeral}' (${targetNum.englishWord})",
                targetAudioPrompt = "${targetNum.banglaNumeral}, ${targetNum.banglaWord}",
                isBengaliAudio = true,
                visualEmoji = targetNum.emoji,
                visualClue = "${targetNum.banglaWord} / ${targetNum.englishWord}",
                options = numOptions.map { "${it.banglaNumeral} (${it.englishNumeral})" },
                correctIndex = numOptions.indexOf(targetNum)
            )
        )

        // 5. Listening Sound Identification Question
        val listenTarget = (AlphabetData.banglaVowels + AlphabetData.banglaConsonants.take(15)).random()
        val listenOthers = (AlphabetData.banglaVowels + AlphabetData.banglaConsonants.take(15))
            .filter { it.id != listenTarget.id }.shuffled().take(3)
        val listenOptions = (listOf(listenTarget) + listenOthers).shuffled()
        questions.add(
            QuizQuestion(
                titleBangla = "মন দিয়ে শোনো! কোন বর্ণটির উচ্চারণ হলো? 🎧",
                titleEnglish = "Listen closely! Which letter was pronounced? 🎧",
                targetAudioPrompt = listenTarget.character,
                isBengaliAudio = true,
                visualEmoji = "🎧",
                visualClue = "ট্যাপ করে শোনো (Listen)",
                options = listenOptions.map { it.character },
                correctIndex = listenOptions.indexOf(listenTarget)
            )
        )

        return questions.take(count)
    }
}

@Composable
fun QuickQuizScreen(
    audioService: KidsAudioService,
    progressRepository: ProgressRepository,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    val coroutineScope = rememberCoroutineScope()
    var questions by remember { mutableStateOf(QuizGenerator.generateDailyQuiz()) }
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
    var isAnswerCorrect by remember { mutableStateOf<Boolean?>(null) }
    var quizCompleted by remember { mutableStateOf(false) }
    var earnedBonusStars by remember { mutableIntStateOf(0) }
    var newlyUnlockedBadges by remember { mutableStateOf<List<BadgeReward>>(emptyList()) }

    val currentQ = questions.getOrNull(currentQuestionIndex)

    // Play question audio automatically when question changes
    LaunchedEffect(currentQuestionIndex) {
        if (!quizCompleted && currentQ != null) {
            delay(300)
            audioService.speak(currentQ.targetAudioPrompt, isBengali = currentQ.isBengaliAudio)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8FD))
    ) {
        if (quizCompleted) {
            ConfettiEffect(modifier = Modifier.fillMaxSize(), particleCount = 50)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Header Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(44.dp)
                        .background(Color.White, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = KidsPurple
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "দৈনিক কুইজ ⚡",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF22223B)
                    )
                }

                // Stars score pill
                Box(
                    modifier = Modifier
                        .background(KidsYellow.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = KidsYellow, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "+${score * 2} ⭐",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF9A6700),
                            fontSize = 13.sp
                        )
                    }
                }
            }

            if (!quizCompleted && currentQ != null) {
                // Progress Bar
                val progressFraction = (currentQuestionIndex + 1).toFloat() / questions.size.toFloat()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "প্রশ্ন ${NumbersData.toBanglaNumeral(currentQuestionIndex + 1)} / ${NumbersData.toBanglaNumeral(questions.size)}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = KidsPurple
                        )
                        Text(
                            text = "Question ${currentQuestionIndex + 1} of ${questions.size}",
                            fontSize = 12.sp,
                            color = Color(0xFF6B7280)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    LinearProgressIndicator(
                        progress = { progressFraction },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = KidsPurple,
                        trackColor = Color(0xFFE5E7EB),
                        strokeCap = StrokeCap.Round
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Question Prompt Card
                ElevatedCard(
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Visual clue / Emoji
                        if (currentQ.visualEmoji != null) {
                            Box(
                                modifier = Modifier
                                    .size(76.dp)
                                    .background(KidsPurple.copy(alpha = 0.12f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = currentQ.visualEmoji, fontSize = 42.sp)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        if (currentQ.visualClue != null) {
                            Text(
                                text = currentQ.visualClue,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = KidsCoral
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = currentQ.titleBangla,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF22223B),
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = currentQ.titleEnglish,
                            fontSize = 13.sp,
                            color = Color(0xFF6B7280),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 2.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Listen Sound Button
                        Button(
                            onClick = {
                                audioService.playPop()
                                audioService.speak(currentQ.targetAudioPrompt, isBengali = currentQ.isBengaliAudio)
                            },
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = KidsPurple),
                            modifier = Modifier.height(44.dp)
                        ) {
                            Icon(imageVector = Icons.Default.VolumeUp, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("শব্দ শোনো (Listen)", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Options 2x2 Grid
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val optionRows = currentQ.options.chunked(2)
                    optionRows.forEachIndexed { rowIndex, rowItems ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            rowItems.forEachIndexed { colIndex, optText ->
                                val optIndex = rowIndex * 2 + colIndex
                                val isSelected = selectedOptionIndex == optIndex
                                val isCorrect = optIndex == currentQ.correctIndex

                                val cardBg = when {
                                    isSelected && isCorrect -> KidsMint.copy(alpha = 0.2f)
                                    isSelected && !isCorrect -> KidsCoral.copy(alpha = 0.2f)
                                    else -> Color.White
                                }

                                val borderColor = when {
                                    isSelected && isCorrect -> KidsMint
                                    isSelected && !isCorrect -> KidsCoral
                                    else -> Color.Transparent
                                }

                                ElevatedCard(
                                    shape = RoundedCornerShape(22.dp),
                                    colors = CardDefaults.elevatedCardColors(containerColor = cardBg),
                                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(84.dp)
                                        .border(2.dp, borderColor, RoundedCornerShape(22.dp))
                                        .clip(RoundedCornerShape(22.dp))
                                        .clickable(enabled = isAnswerCorrect != true) {
                                            selectedOptionIndex = optIndex
                                            if (isCorrect) {
                                                isAnswerCorrect = true
                                                score++
                                                audioService.playSuccess()
                                                audioService.playStarEarned()

                                                coroutineScope.launch {
                                                    delay(850)
                                                    if (currentQuestionIndex < questions.size - 1) {
                                                        currentQuestionIndex++
                                                        selectedOptionIndex = null
                                                        isAnswerCorrect = null
                                                    } else {
                                                        // Quiz completed! Record in repo
                                                        val (bonus, newBadges) = progressRepository.recordQuizCompletion(score, questions.size)
                                                        earnedBonusStars = bonus
                                                        newlyUnlockedBadges = newBadges
                                                        quizCompleted = true
                                                    }
                                                }
                                            } else {
                                                isAnswerCorrect = false
                                                audioService.playPop()
                                            }
                                        }
                                        .testTag("quiz_option_$optIndex")
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = optText,
                                            fontSize = if (optText.length > 3) 20.sp else 32.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected && isCorrect) KidsMint else Color(0xFF22223B),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (quizCompleted) {
                // Completed Summary Screen
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .background(KidsYellow.copy(alpha = 0.2f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🏆", fontSize = 48.sp)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "অভিনন্দন! কুইজ সম্পন্ন! 🎉",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = KidsPurple,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Awesome Job! You got $score / ${questions.size} correct!",
                        fontSize = 15.sp,
                        color = Color(0xFF6B7280),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp, bottom = 18.dp)
                    )

                    // Rewards Card
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "তোমার আজকের পুরস্কার (Daily Rewards)",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF6B7280)
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = KidsYellow,
                                    modifier = Modifier.size(32.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "+$earnedBonusStars বোনাস তারা!",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF9A6700)
                                )
                            }

                            if (newlyUnlockedBadges.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(12.dp))
                                val badge = newlyUnlockedBadges.first()
                                Row(
                                    modifier = Modifier
                                        .background(KidsPurple.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
                                        .padding(horizontal = 14.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = badge.iconEmoji, fontSize = 24.sp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(
                                            text = "নতুন ব্যাজ: ${badge.titleBangla}!",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            color = KidsPurple
                                        )
                                        Text(
                                            text = badge.descriptionEnglish,
                                            fontSize = 11.sp,
                                            color = Color(0xFF6B7280)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            questions = QuizGenerator.generateDailyQuiz()
                            currentQuestionIndex = 0
                            score = 0
                            selectedOptionIndex = null
                            isAnswerCorrect = null
                            quizCompleted = false
                            audioService.playPop()
                        },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = KidsMint),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("play_again_quiz_button")
                    ) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("আবার খেলো (Play Again)", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedButton(
                        onClick = onBack,
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text("বর্ণমালায় ফিরে যাও (Back to Learning)", color = KidsPurple, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}
