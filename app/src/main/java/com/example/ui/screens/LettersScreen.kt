package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.KidsAudioService
import com.example.data.AlphabetData
import com.example.data.LetterItem
import com.example.data.LetterType
import com.example.data.ProgressRepository
import com.example.ui.theme.KidsCoral
import com.example.ui.theme.KidsMint
import com.example.ui.theme.KidsOrange
import com.example.ui.theme.KidsPurple
import com.example.ui.theme.KidsYellow
import com.example.ui.theme.PastelPalette

@Composable
fun LettersScreen(
    audioService: KidsAudioService,
    progressRepository: ProgressRepository,
    onNavigateToTrace: (LetterItem) -> Unit,
    onOpenRewards: () -> Unit = {},
    onOpenParent: () -> Unit = {},
    onOpenQuiz: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val progressState by progressRepository.progress.collectAsState()

    val tabs = listOf(
        "স্বরবর্ণ (Vowels)" to AlphabetData.banglaVowels,
        "ব্যঞ্জনবর্ণ (Consonants)" to AlphabetData.banglaConsonants,
        "English (A-Z)" to AlphabetData.englishLetters
    )

    val currentLetters = tabs[selectedTabIndex].second

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8FD))
    ) {
        // Top banner
        Card(
            shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp),
            colors = CardDefaults.cardColors(containerColor = KidsPurple),
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
                            text = "বর্ণমালা ও বর্ণ পরিচিতি 🎨",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Tap letter for sound, tap pencil to trace!",
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Stars & Medals button
                        Box(
                            modifier = Modifier
                                .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(20.dp))
                                .clickable {
                                    audioService.playPop()
                                    onOpenRewards()
                                }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "🏆", fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${progressState.totalStars} ⭐",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 14.sp
                                )
                            }
                        }

                        // Parent Lock button
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .background(Color.White.copy(alpha = 0.2f), CircleShape)
                                .clickable {
                                    audioService.playPop()
                                    onOpenParent()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🔒", fontSize = 16.sp)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Daily Quick Quiz Hero Card
        ElevatedCard(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = if (progressState.todayQuizCompleted) Color(0xFFF3FBF8) else Color(0xFFFFF9E6)
            ),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp)
                .clip(RoundedCornerShape(22.dp))
                .clickable {
                    audioService.playPop()
                    onOpenQuiz()
                }
                .testTag("daily_quiz_banner_card")
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(if (progressState.todayQuizCompleted) KidsMint else KidsOrange, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = if (progressState.todayQuizCompleted) "✅" else "⚡", fontSize = 22.sp)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "দৈনিক কুইজ (Daily Quick Quiz)",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color(0xFF22223B)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "+10 ⭐",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = KidsOrange
                            )
                        }

                        Text(
                            text = if (progressState.todayQuizCompleted) "আজকের কুইজ সম্পন্ন! আবার অনুশীলন করো" else "৫টি সহজ বর্ণ ও সংখ্যা চেনো এবং তারা জেতো!",
                            fontSize = 11.sp,
                            color = Color(0xFF6B7280)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .background(if (progressState.todayQuizCompleted) KidsMint else KidsPurple, RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = if (progressState.todayQuizCompleted) "খেলো ➔" else "শুরু করো ➔",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Category Tabs
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent,
            contentColor = KidsPurple,
            edgePadding = 16.dp,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = KidsPurple,
                    height = 3.dp
                )
            },
            divider = {}
        ) {
            tabs.forEachIndexed { index, (title, list) ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        audioService.playPop()
                    },
                    text = {
                        Text(
                            text = "$title (${list.size})",
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 14.sp,
                            color = if (selectedTabIndex == index) KidsPurple else Color(0xFF6B7280)
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Letter Cards Grid
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 105.dp),
            contentPadding = PaddingValues(start = 14.dp, end = 14.dp, bottom = 80.dp, top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxSize()
                .testTag("letters_grid")
        ) {
            itemsIndexed(currentLetters) { index, letter ->
                val cardColor = PastelPalette[index % PastelPalette.size]
                val isCompleted = progressState.completedLetterIds.contains(letter.id)
                val starsEarned = progressState.letterStars[letter.id] ?: if (isCompleted) 3 else 0

                LetterCardItem(
                    letter = letter,
                    accentColor = cardColor,
                    stars = starsEarned,
                    onPronounce = {
                        audioService.playPop()
                        val isBengali = letter.type != LetterType.ENGLISH_ALPHABET
                        val speechText = if (isBengali) {
                            "${letter.character}, ${letter.word}"
                        } else {
                            "${letter.character}, ${letter.word}"
                        }
                        audioService.speak(speechText, isBengali = isBengali)
                    },
                    onTrace = {
                        audioService.playPop()
                        onNavigateToTrace(letter)
                    }
                )
            }
        }
    }
}

@Composable
fun LetterCardItem(
    letter: LetterItem,
    accentColor: Color,
    stars: Int,
    onPronounce: () -> Unit,
    onTrace: () -> Unit
) {
    ElevatedCard(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .clickable { onPronounce() }
            .testTag("letter_card_${letter.id}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top row: Stars / Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (stars > 0) {
                    Row {
                        repeat(stars) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = KidsYellow,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                } else {
                    Spacer(modifier = Modifier.size(12.dp))
                }

                Text(
                    text = letter.emoji,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Big Letter Glyph with subtle background circle
            Box(
                modifier = Modifier
                    .size(62.dp)
                    .background(accentColor.copy(alpha = 0.12f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = letter.character,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = accentColor,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Word example
            Text(
                text = letter.word,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF22223B),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = letter.pronunciation,
                fontSize = 11.sp,
                color = Color(0xFF9CA3AF),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Actions: Sound & Trace
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onPronounce,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Pronounce",
                        tint = accentColor,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(accentColor, CircleShape)
                        .clickable { onTrace() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = "Trace Letter",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
