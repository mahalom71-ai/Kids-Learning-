package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.data.BadgeReward
import com.example.data.ProgressRepository
import com.example.data.RewardData
import com.example.data.RewardType
import com.example.ui.theme.KidsCoral
import com.example.ui.theme.KidsMint
import com.example.ui.theme.KidsOrange
import com.example.ui.theme.KidsPurple
import com.example.ui.theme.KidsYellow

@Composable
fun RewardsScreen(
    audioService: KidsAudioService,
    progressRepository: ProgressRepository,
    onOpenQuiz: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val progressState by progressRepository.progress.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Medals, 1 = Badges
    var selectedRewardForDetail by remember { mutableStateOf<BadgeReward?>(null) }

    val currentLevel = RewardData.getLevelForStars(progressState.totalStars)
    val nextLevel = RewardData.levels.getOrNull(currentLevel.levelNumber)

    val unlockedCount = progressState.unlockedBadgeIds.size
    val totalRewardsCount = RewardData.allRewards.size

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8FD))
    ) {
        // Top Banner
        Card(
            shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp),
            colors = CardDefaults.cardColors(containerColor = KidsYellow),
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
                            text = "মেডেল ও পুরস্কার 🏆",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF22223B)
                        )
                        Text(
                            text = "Earn shiny medals by tracing letters and collecting stars!",
                            fontSize = 13.sp,
                            color = Color(0xFF4B5563)
                        )
                    }

                    // Level Badge
                    Box(
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.85f), RoundedCornerShape(20.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = currentLevel.iconEmoji, fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Lv ${currentLevel.levelNumber}",
                                fontWeight = FontWeight.Bold,
                                color = KidsPurple,
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Kid Level Card
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = currentLevel.iconEmoji, fontSize = 26.sp)
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "${currentLevel.titleBangla} (${currentLevel.titleEnglish})",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = Color(0xFF22223B)
                                    )
                                    Text(
                                        text = "${progressState.totalStars} Stars Earned ⭐",
                                        fontSize = 12.sp,
                                        color = KidsYellow
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .background(KidsPurple.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "$unlockedCount / $totalRewardsCount অর্জিত",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = KidsPurple
                                )
                            }
                        }

                        if (nextLevel != null) {
                            Spacer(modifier = Modifier.height(10.dp))
                            val progressFraction = (
                                (progressState.totalStars - currentLevel.minStars).toFloat() /
                                (nextLevel.minStars - currentLevel.minStars).toFloat()
                            ).coerceIn(0f, 1f)

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

                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Next rank: ${nextLevel.titleBangla} ${nextLevel.iconEmoji} at ${nextLevel.minStars} stars",
                                fontSize = 11.sp,
                                color = Color(0xFF6B7280)
                            )
                        }
                    }
                }
            }
        }

        // Tabs
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.Transparent,
            contentColor = KidsPurple,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = KidsPurple,
                    height = 3.dp
                )
            },
            divider = {}
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = {
                    selectedTab = 0
                    audioService.playPop()
                },
                text = {
                    Text(
                        text = "মেডেল সংগ্রহ (Medals)",
                        fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 14.sp,
                        color = if (selectedTab == 0) KidsPurple else Color(0xFF6B7280)
                    )
                }
            )

            Tab(
                selected = selectedTab == 1,
                onClick = {
                    selectedTab = 1
                    audioService.playPop()
                },
                text = {
                    Text(
                        text = "অর্জন ব্যাজ (Badges)",
                        fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 14.sp,
                        color = if (selectedTab == 1) KidsPurple else Color(0xFF6B7280)
                    )
                }
            )
        }

        val itemsToDisplay = if (selectedTab == 0) RewardData.medals else RewardData.achievementBadges

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 14.dp, end = 14.dp, top = 12.dp, bottom = 90.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .testTag("rewards_grid")
        ) {
            items(itemsToDisplay) { reward ->
                val isUnlocked = progressState.unlockedBadgeIds.contains(reward.id)
                val currentProgress = calculateCurrentProgress(reward, progressState)

                RewardCardItem(
                    reward = reward,
                    isUnlocked = isUnlocked,
                    currentProgress = currentProgress,
                    onClick = {
                        selectedRewardForDetail = reward
                        if (isUnlocked) {
                            audioService.playStarEarned()
                        } else {
                            audioService.playPop()
                        }
                    }
                )
            }
        }
    }

    // Detail dialog when tapping any reward
    if (selectedRewardForDetail != null) {
        val reward = selectedRewardForDetail!!
        val isUnlocked = progressState.unlockedBadgeIds.contains(reward.id)
        val currentProgress = calculateCurrentProgress(reward, progressState)

        AlertDialog(
            onDismissRequest = { selectedRewardForDetail = null },
            icon = {
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .background(
                            if (isUnlocked) Color(reward.colorHex).copy(alpha = 0.2f) else Color(0xFFE5E7EB),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = reward.iconEmoji, fontSize = 40.sp)
                }
            },
            title = {
                Text(
                    text = "${reward.titleBangla}\n${reward.titleEnglish}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = reward.descriptionBangla,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF22223B),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = reward.descriptionEnglish,
                        fontSize = 13.sp,
                        color = Color(0xFF6B7280),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                    )

                    if (isUnlocked) {
                        Row(
                            modifier = Modifier
                                .background(KidsMint.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = KidsMint, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("অর্জিত হয়েছে! (Unlocked)", color = KidsMint, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    } else {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val fraction = (currentProgress.toFloat() / reward.targetCount.toFloat()).coerceIn(0f, 1f)
                            LinearProgressIndicator(
                                progress = { fraction },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                color = Color(reward.colorHex),
                                trackColor = Color(0xFFE5E7EB)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "অগ্রগতি: $currentProgress / ${reward.targetCount} সম্পন্ন",
                                fontSize = 12.sp,
                                color = Color(0xFF6B7280)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier
                            .background(KidsYellow.copy(alpha = 0.25f), RoundedCornerShape(12.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = KidsYellow, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "পুরস্কার: +${reward.bonusStars} বোনাস তারা",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF9A6700)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val speech = "${reward.titleBangla}. ${reward.descriptionBangla}"
                        audioService.speak(speech, isBengali = true)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = KidsPurple),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(imageVector = Icons.Default.VolumeUp, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("শুনুন (Listen)", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedRewardForDetail = null }) {
                    Text("Close")
                }
            }
        )
    }
}

@Composable
fun RewardCardItem(
    reward: BadgeReward,
    isUnlocked: Boolean,
    currentProgress: Int,
    onClick: () -> Unit
) {
    ElevatedCard(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (isUnlocked) Color.White else Color(0xFFF9FAFB)
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = if (isUnlocked) 4.dp else 1.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .clickable { onClick() }
            .testTag("reward_item_${reward.id}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Status icon / Top row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isUnlocked) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Unlocked",
                        tint = KidsMint,
                        modifier = Modifier.size(18.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Locked",
                        tint = Color(0xFF9CA3AF),
                        modifier = Modifier.size(16.dp)
                    )
                }

                Text(
                    text = "+${reward.bonusStars} ⭐",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = KidsYellow
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Big Shiny Emoji Badge
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        if (isUnlocked) Color(reward.colorHex).copy(alpha = 0.15f) else Color(0xFFE5E7EB).copy(alpha = 0.5f),
                        CircleShape
                    )
                    .border(
                        width = if (isUnlocked) 2.dp else 0.dp,
                        color = if (isUnlocked) Color(reward.colorHex) else Color.Transparent,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = reward.iconEmoji,
                    fontSize = 32.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = reward.titleBangla,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (isUnlocked) Color(0xFF22223B) else Color(0xFF6B7280),
                textAlign = TextAlign.Center,
                maxLines = 1
            )

            Text(
                text = reward.titleEnglish,
                fontSize = 11.sp,
                color = Color(0xFF9CA3AF),
                textAlign = TextAlign.Center,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Progress bar
            val fraction = (currentProgress.toFloat() / reward.targetCount.toFloat()).coerceIn(0f, 1f)
            LinearProgressIndicator(
                progress = { fraction },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = if (isUnlocked) Color(reward.colorHex) else Color(0xFFD1D5DB),
                trackColor = Color(0xFFE5E7EB)
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (isUnlocked) "অর্জিত! (Done)" else "$currentProgress / ${reward.targetCount}",
                fontSize = 10.sp,
                fontWeight = if (isUnlocked) FontWeight.Bold else FontWeight.Normal,
                color = if (isUnlocked) KidsMint else Color(0xFF6B7280)
            )
        }
    }
}

private fun calculateCurrentProgress(reward: BadgeReward, state: com.example.data.ProgressState): Int {
    val vowelsCount = state.completedLetterIds.count { it.startsWith("bn_v_") }
    val consonantsCount = state.completedLetterIds.count { it.startsWith("bn_c_") }
    val englishCount = state.completedLetterIds.count { it.startsWith("en_") }
    val totalLetters = state.completedLetterIds.size

    return when (reward.id) {
        "medal_bronze", "medal_silver", "medal_gold", "medal_diamond" -> totalLetters
        "badge_vowel_master" -> vowelsCount
        "badge_tiger_scribe" -> consonantsCount
        "badge_abc_explorer" -> englishCount
        "badge_star_collector", "badge_grand_scholar" -> state.totalStars
        "badge_streak_champion" -> state.streakDays
        "badge_quiz_champion" -> state.totalQuizzesCompleted
        else -> 0
    }
}
