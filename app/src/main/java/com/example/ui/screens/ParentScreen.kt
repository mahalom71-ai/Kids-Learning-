package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.scale
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.KidsAudioService
import com.example.data.AlphabetData
import com.example.data.LetterType
import com.example.data.ProgressRepository
import com.example.ui.components.ParentalGateDialog
import com.example.ui.theme.KidsBlue
import com.example.ui.theme.KidsCoral
import com.example.ui.theme.KidsMint
import com.example.ui.theme.KidsOrange
import com.example.ui.theme.KidsPurple
import com.example.ui.theme.KidsYellow

@Composable
fun ParentScreen(
    audioService: KidsAudioService,
    progressRepository: ProgressRepository,
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    androidx.activity.compose.BackHandler { onBack() }
    var isUnlocked by remember { mutableStateOf(false) }
    var showGateDialog by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }

    val progressState by progressRepository.progress.collectAsState()

    // Calculate category breakdown
    val vowelsCompleted = AlphabetData.banglaVowels.count { progressState.completedLetterIds.contains(it.id) }
    val vowelsTotal = AlphabetData.banglaVowels.size

    val consonantsCompleted = AlphabetData.banglaConsonants.count { progressState.completedLetterIds.contains(it.id) }
    val consonantsTotal = AlphabetData.banglaConsonants.size

    val englishCompleted = AlphabetData.englishLetters.count { progressState.completedLetterIds.contains(it.id) }
    val englishTotal = AlphabetData.englishLetters.size

    val numbersPracticed = progressState.completedNumbers.size
    val numbersTotal = 100

    if (!isUnlocked) {
        // Locked Gate Screen
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFF7F8FD))
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .background(KidsPurple.copy(alpha = 0.12f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Parent Gate",
                            tint = KidsPurple,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "অভিভাবক কর্নার (Parents Only)",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF22223B)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Track your child's alphabet learning progress, streaks, accuracy, and adjust sound and speech settings.",
                        fontSize = 14.sp,
                        color = Color(0xFF6B7280),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { showGateDialog = true },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = KidsPurple),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("unlock_parent_gate_button")
                    ) {
                        Icon(imageVector = Icons.Default.LockOpen, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("লক খুলুন (Verify as Parent)", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedButton(
                        onClick = onBack,
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.fillMaxWidth().height(48.dp)
                    ) {
                        Text("ফিরে যান (Back to Kids Mode)", color = Color.Gray, fontSize = 14.sp)
                    }
                }
            }
        }

        if (showGateDialog) {
            ParentalGateDialog(
                onSuccess = {
                    showGateDialog = false
                    isUnlocked = true
                    audioService.playSuccess()
                },
                onDismiss = { showGateDialog = false }
            )
        }
    } else {
        // Unlocked Dashboard
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFF7F8FD))
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Card(
                shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp),
                colors = CardDefaults.cardColors(containerColor = KidsPurple),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = onBack,
                                modifier = Modifier
                                    .size(38.dp)
                                    .background(Color.White.copy(alpha = 0.2f), CircleShape)
                            ) {
                                Icon(imageVector = Icons.Default.Lock, contentDescription = "Back", tint = Color.White)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "অভিভাবক ড্যাশবোর্ড 📊",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "Learning Analytics & Progress",
                                    fontSize = 12.sp,
                                    color = Color.White.copy(alpha = 0.85f)
                                )
                            }
                        }

                        IconButton(
                            onClick = { isUnlocked = false },
                            modifier = Modifier
                                .size(38.dp)
                                .background(Color.White.copy(alpha = 0.2f), CircleShape)
                        ) {
                            Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock", tint = Color.White)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Stat Cards Grid
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(
                    title = "মোট তারা (Stars)",
                    value = "${progressState.totalStars} ⭐",
                    color = KidsYellow,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "ধারাবাহিকতা (Streak)",
                    value = "${progressState.streakDays} দিন 🔥",
                    color = KidsCoral,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(
                    title = "অধ্যয়ন সময় (Time)",
                    value = "${progressState.totalPracticeMinutes} মিনিট ⏱️",
                    color = KidsMint,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "ছড়া ও গান (Rhymes)",
                    value = "${progressState.rhymesListened.size} টি 🎵",
                    color = KidsBlue,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Earned Medals & Badges Showcase Card
            Text(
                text = "অর্জিত পদক ও মেডেল (Medals & Badges: ${progressState.unlockedBadgeIds.size} / ${com.example.data.RewardData.allRewards.size})",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF22223B),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            ElevatedCard(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    val currentLevel = com.example.data.RewardData.getLevelForStars(progressState.totalStars)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "বর্তমান স্তর: ${currentLevel.titleBangla} ${currentLevel.iconEmoji}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = KidsPurple
                        )
                        Text(
                            text = "Level ${currentLevel.levelNumber}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = KidsYellow
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        com.example.data.RewardData.medals.forEach { medal ->
                            val isEarned = progressState.unlockedBadgeIds.contains(medal.id)
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(46.dp)
                                        .background(
                                            if (isEarned) Color(medal.colorHex).copy(alpha = 0.2f) else Color(0xFFF3F4F6),
                                            CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = medal.iconEmoji,
                                        fontSize = 22.sp,
                                        modifier = Modifier.scale(if (isEarned) 1f else 0.8f)
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = medal.titleBangla,
                                    fontSize = 11.sp,
                                    fontWeight = if (isEarned) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isEarned) Color(0xFF22223B) else Color.Gray
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Mastery Progress Section
            Text(
                text = "শেখার অগ্রগতি (Mastery Breakdown)",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF22223B),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            ElevatedCard(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    ProgressRow(
                        label = "বাংলা স্বরবর্ণ (Bangla Vowels)",
                        completed = vowelsCompleted,
                        total = vowelsTotal,
                        color = KidsPurple
                    )

                    ProgressRow(
                        label = "বাংলা ব্যঞ্জনবর্ণ (Bangla Consonants)",
                        completed = consonantsCompleted,
                        total = consonantsTotal,
                        color = KidsCoral
                    )

                    ProgressRow(
                        label = "ইংরেজি বর্ণমালা (English Alphabet)",
                        completed = englishCompleted,
                        total = englishTotal,
                        color = KidsMint
                    )

                    ProgressRow(
                        label = "সংখ্যা ১-১০০ (Numbers 1-100)",
                        completed = numbersPracticed,
                        total = numbersTotal,
                        color = KidsOrange
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Sound & Voice Settings Section
            Text(
                text = "অডিও ও কন্ঠ সেটিংস (Audio & Voice)",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF22223B),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            ElevatedCard(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    // Sound effects toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "শব্দ ও বাদ্য (Sound Effects & Music)",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF22223B)
                            )
                            Text(
                                text = "Play chimes, sparkles, pops and tunes",
                                fontSize = 12.sp,
                                color = Color(0xFF6B7280)
                            )
                        }

                        Switch(
                            checked = progressState.soundEffectsEnabled,
                            onCheckedChange = {
                                progressRepository.setSoundEffectsEnabled(it)
                                audioService.isMuted = !it
                            },
                            colors = SwitchDefaults.colors(checkedThumbColor = KidsPurple, checkedTrackColor = KidsPurple.copy(alpha = 0.3f))
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Voice Speech Rate
                    Text(
                        text = "উচ্চারণের গতি (Speech Rate: ${(progressState.ttsVoiceRate * 100).toInt()}%)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF22223B)
                    )
                    Text(
                        text = "Slower rate helps young children grasp phonetics",
                        fontSize = 12.sp,
                        color = Color(0xFF6B7280)
                    )

                    Slider(
                        value = progressState.ttsVoiceRate,
                        onValueChange = {
                            progressRepository.setTtsVoiceRate(it)
                        },
                        valueRange = 0.6f..1.2f,
                        steps = 5,
                        colors = SliderDefaults.colors(thumbColor = KidsPurple, activeTrackColor = KidsPurple)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Reset Progress Action
            OutlinedButton(
                onClick = { showResetDialog = true },
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(48.dp)
                    .testTag("reset_progress_button")
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null, tint = KidsCoral)
                Spacer(modifier = Modifier.width(8.dp))
                Text("অগ্রগতি রিসেট করুন (Reset Progress)", color = KidsCoral, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(90.dp))
        }

        if (showResetDialog) {
            AlertDialog(
                onDismissRequest = { showResetDialog = false },
                title = { Text("অগ্রগতি রিসেট?", fontWeight = FontWeight.Bold) },
                text = { Text("Are you sure you want to reset all earned stars and traced letters? This cannot be undone.") },
                confirmButton = {
                    Button(
                        onClick = {
                            progressRepository.resetProgress()
                            showResetDialog = false
                            audioService.playPop()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = KidsCoral)
                    ) {
                        Text("Reset", color = Color.White)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showResetDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Text(text = title, fontSize = 12.sp, color = Color(0xFF6B7280))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = color)
        }
    }
}

@Composable
fun ProgressRow(
    label: String,
    completed: Int,
    total: Int,
    color: Color
) {
    val fraction = if (total > 0) (completed.toFloat() / total.toFloat()).coerceIn(0f, 1f) else 0f
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF22223B))
            Text(text = "$completed / $total (${(fraction * 100).toInt()}%)", fontSize = 12.sp, color = color, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(6.dp))
        LinearProgressIndicator(
            progress = { fraction },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = color,
            trackColor = color.copy(alpha = 0.15f),
            strokeCap = StrokeCap.Round
        )
    }
}
