package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.KidsAudioService
import com.example.data.NumbersData
import com.example.data.ProgressRepository
import com.example.ui.components.ConfettiEffect
import com.example.ui.theme.KidsBlue
import com.example.ui.theme.KidsCoral
import com.example.ui.theme.KidsMint
import com.example.ui.theme.KidsOrange
import com.example.ui.theme.KidsPurple
import com.example.ui.theme.KidsYellow
import com.example.ui.theme.PastelPalette
import kotlin.random.Random

@Composable
fun NumbersScreen(
    audioService: KidsAudioService,
    progressRepository: ProgressRepository,
    modifier: Modifier = Modifier
) {
    var isBanglaLang by remember { mutableStateOf(true) }
    var selectedModeTab by remember { mutableIntStateOf(0) } // 0 = 1-100 Chart, 1 = Counting Game
    var activeFilter by remember { mutableIntStateOf(0) } // 0 = 1-100, 1 = 1-20, 2 = 10s, 3 = 5s
    val progressState by progressRepository.progress.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8FD))
    ) {
        // Top Header
        Card(
            shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp),
            colors = CardDefaults.cardColors(containerColor = KidsMint),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (isBanglaLang) "সংখ্যা ও গণনা ১-১০০ 🔢" else "Numbers & Counting 1-100 🔢",
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = if (isBanglaLang) "ট্যাপ করে উচ্চারণ শোনো ও গুণতে শেখো!" else "Tap to hear pronunciation and count objects!",
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }

                    // Language Switcher Pill
                    Box(
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.25f), RoundedCornerShape(20.dp))
                            .clickable {
                                isBanglaLang = !isBanglaLang
                                audioService.playPop()
                            }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = if (isBanglaLang) "বাংলা (১-১০০)" else "English (1-100)",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        // Submode Tabs: Chart vs Counting Game
        TabRow(
            selectedTabIndex = selectedModeTab,
            containerColor = Color.Transparent,
            contentColor = KidsMint,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedModeTab]),
                    color = KidsMint,
                    height = 3.dp
                )
            },
            divider = {}
        ) {
            Tab(
                selected = selectedModeTab == 0,
                onClick = {
                    selectedModeTab = 0
                    audioService.playPop()
                },
                text = {
                    Text(
                        text = if (isBanglaLang) "সংখ্যা চার্ট (১-১০০)" else "Number Chart (1-100)",
                        fontWeight = if (selectedModeTab == 0) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 14.sp,
                        color = if (selectedModeTab == 0) KidsMint else Color(0xFF6B7280)
                    )
                }
            )

            Tab(
                selected = selectedModeTab == 1,
                onClick = {
                    selectedModeTab = 1
                    audioService.playPop()
                },
                text = {
                    Text(
                        text = if (isBanglaLang) "গুণতে শেখো! (Game)" else "Count With Me! (Game)",
                        fontWeight = if (selectedModeTab == 1) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 14.sp,
                        color = if (selectedModeTab == 1) KidsMint else Color(0xFF6B7280)
                    )
                }
            )
        }

        if (selectedModeTab == 0) {
            // Filter chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val filters = listOf(
                    if (isBanglaLang) "সব (১-১০০)" else "All (1-100)",
                    if (isBanglaLang) "১ - ২০" else "1 - 20",
                    if (isBanglaLang) "১০ এর গুণিতক" else "Tens (10, 20..)",
                    if (isBanglaLang) "৫ এর গুণিতক" else "Fives (5, 10..)"
                )
                filters.forEachIndexed { idx, label ->
                    FilterChip(
                        selected = activeFilter == idx,
                        onClick = {
                            activeFilter = idx
                            audioService.playPop()
                        },
                        label = { Text(label, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = KidsMint.copy(alpha = 0.2f),
                            selectedLabelColor = KidsMint
                        )
                    )
                }
            }

            val filteredNumbers = remember(activeFilter) {
                when (activeFilter) {
                    1 -> NumbersData.numbers1To100.filter { it.value <= 20 }
                    2 -> NumbersData.numbers1To100.filter { it.value % 10 == 0 }
                    3 -> NumbersData.numbers1To100.filter { it.value % 5 == 0 }
                    else -> NumbersData.numbers1To100
                }
            }

            // Number Chart Grid
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 75.dp),
                contentPadding = PaddingValues(start = 14.dp, end = 14.dp, bottom = 80.dp, top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("numbers_grid")
            ) {
                items(filteredNumbers) { numItem ->
                    val color = PastelPalette[(numItem.value - 1) % PastelPalette.size]
                    val isPracticed = progressState.completedNumbers.contains(numItem.value)

                    NumberGridCard(
                        numberItem = numItem,
                        isBangla = isBanglaLang,
                        accentColor = color,
                        isPracticed = isPracticed,
                        onTap = {
                            audioService.playPop()
                            progressRepository.recordNumberPractice(numItem.value)
                            val speechText = if (isBanglaLang) {
                                "${numItem.banglaNumeral}, ${numItem.banglaWord}"
                            } else {
                                "${numItem.englishNumeral}, ${numItem.englishWord}"
                            }
                            audioService.speak(speechText, isBengali = isBanglaLang)
                        }
                    )
                }
            }
        } else {
            // Interactive Counting Mini-Game
            CountingGameView(
                isBangla = isBanglaLang,
                audioService = audioService,
                progressRepository = progressRepository
            )
        }
    }
}

@Composable
fun NumberGridCard(
    numberItem: com.example.data.NumberItem,
    isBangla: Boolean,
    accentColor: Color,
    isPracticed: Boolean,
    onTap: () -> Unit
) {
    ElevatedCard(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .clickable { onTap() }
            .testTag("number_card_${numberItem.value}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = numberItem.emoji, fontSize = 12.sp)
                if (isPracticed) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Practiced",
                        tint = KidsMint,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }

            Text(
                text = if (isBangla) numberItem.banglaNumeral else numberItem.englishNumeral,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = accentColor,
                textAlign = TextAlign.Center
            )

            Text(
                text = if (isBangla) numberItem.banglaWord else numberItem.englishWord,
                fontSize = 11.sp,
                color = Color(0xFF6B7280),
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}

@Composable
fun CountingGameView(
    isBangla: Boolean,
    audioService: KidsAudioService,
    progressRepository: ProgressRepository
) {
    var targetCount by remember { mutableIntStateOf(Random.nextInt(3, 10)) }
    val emojiChoices = listOf("🍎", "🎈", "⭐", "🦋", "🍬", "🐥", "🧁")
    var currentEmoji by remember { mutableStateOf(emojiChoices[Random.nextInt(emojiChoices.size)]) }

    val tappedIndices = remember { mutableStateListOf<Int>() }
    var gameWon by remember { mutableStateOf(false) }

    val options = remember(targetCount) {
        val wrong1 = (targetCount + Random.nextInt(1, 3)).coerceAtMost(12)
        val wrong2 = (targetCount - Random.nextInt(1, 3)).coerceAtLeast(1)
        listOf(targetCount, wrong1, wrong2).distinct().shuffled()
    }

    fun startNewGame() {
        targetCount = Random.nextInt(2, 10)
        currentEmoji = emojiChoices[Random.nextInt(emojiChoices.size)]
        tappedIndices.clear()
        gameWon = false
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (gameWon) {
            ConfettiEffect(modifier = Modifier.fillMaxSize(), particleCount = 40)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Question Banner
            Card(
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (isBangla) "কয়টি $currentEmoji দেখতে পাচ্ছ? ট্যাপ করে গুণো!" else "How many $currentEmoji do you see? Tap each to count!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = KidsPurple,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = if (isBangla) "তুমি গুণেছ: ${NumbersData.toBanglaNumeral(tappedIndices.size)} / ${NumbersData.toBanglaNumeral(targetCount)}"
                        else "You counted: ${tappedIndices.size} / $targetCount",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = KidsMint
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Play Garden Canvas of Objects
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F4FF)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(targetCount) { idx ->
                        val isTapped = tappedIndices.contains(idx)
                        Box(
                            modifier = Modifier
                                .size(76.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(if (isTapped) KidsYellow.copy(alpha = 0.3f) else Color.White)
                                .clickable {
                                    if (!isTapped) {
                                        tappedIndices.add(idx)
                                        audioService.playPop()
                                        val countNum = tappedIndices.size
                                        val speech = if (isBangla) NumbersData.toBanglaNumeral(countNum) else countNum.toString()
                                        audioService.speak(speech, isBengali = isBangla)
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = currentEmoji,
                                    fontSize = 32.sp
                                )
                                if (isTapped) {
                                    val countPos = tappedIndices.indexOf(idx) + 1
                                    Text(
                                        text = if (isBangla) NumbersData.toBanglaNumeral(countPos) else countPos.toString(),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = KidsPurple
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Choice Buttons: Pick correct total
            Text(
                text = if (isBangla) "মোট কতগুলো? সঠিক সংখ্যাটি স্পর্শ করো:" else "How many in total? Choose the right number:",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF4B5563)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                options.forEach { opt ->
                    val isCorrect = opt == targetCount
                    Button(
                        onClick = {
                            if (isCorrect) {
                                gameWon = true
                                audioService.playSuccess()
                                audioService.playStarEarned()
                                progressRepository.recordNumberPractice(targetCount)
                                val winPraise = if (isBangla) "দারুণ হয়েছে! শাবাশ!" else "Great job! Exactly right!"
                                audioService.speak(winPraise, isBengali = isBangla)
                            } else {
                                audioService.playPop()
                            }
                        },
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (gameWon && isCorrect) KidsMint else KidsPurple
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(54.dp)
                            .testTag("counting_option_$opt")
                    ) {
                        Text(
                            text = if (isBangla) NumbersData.toBanglaNumeral(opt) else opt.toString(),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }

            if (gameWon) {
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = { startNewGame() },
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = KidsYellow),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("play_again_counting")
                ) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = null, tint = Color(0xFF22223B))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isBangla) "পরের খেলা খেলো (Play Again)" else "Play Next Round",
                        color = Color(0xFF22223B),
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}
