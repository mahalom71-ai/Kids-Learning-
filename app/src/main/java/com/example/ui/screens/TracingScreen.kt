package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.KidsAudioService
import com.example.data.AlphabetData
import com.example.data.LetterItem
import com.example.data.LetterType
import com.example.data.ProgressRepository
import com.example.ui.components.CelebrationDialog
import com.example.ui.components.TracingCanvas
import com.example.ui.theme.KidsBlue
import com.example.ui.theme.KidsCoral
import com.example.ui.theme.KidsMint
import com.example.ui.theme.KidsOrange
import com.example.ui.theme.KidsPurple
import com.example.ui.theme.KidsYellow

@Composable
fun TracingScreen(
    initialLetter: LetterItem,
    audioService: KidsAudioService,
    progressRepository: ProgressRepository,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    val allLetters = remember {
        AlphabetData.banglaVowels + AlphabetData.banglaConsonants + AlphabetData.englishLetters
    }

    var currentIndex by remember {
        mutableIntStateOf(allLetters.indexOfFirst { it.id == initialLetter.id }.coerceAtLeast(0))
    }

    val currentLetter = allLetters[currentIndex]

    val brushColors = listOf(
        KidsPurple,
        KidsCoral,
        KidsMint,
        KidsYellow,
        KidsOrange,
        KidsBlue
    )

    var selectedColorIndex by remember { mutableIntStateOf(0) }
    var showHint by remember { mutableStateOf(true) }
    var showCelebration by remember { mutableStateOf(false) }
    var newlyEarnedRewards by remember { mutableStateOf<List<com.example.data.BadgeReward>>(emptyList()) }
    var canvasKey by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8FD))
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
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

            // Word Info Card
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = currentLetter.emoji,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${currentLetter.character} - ${currentLetter.word}",
                        fontWeight = FontWeight.Bold,
                        color = KidsPurple,
                        fontSize = 17.sp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    IconButton(
                        onClick = {
                            val isBn = currentLetter.type != LetterType.ENGLISH_ALPHABET
                            audioService.speak("${currentLetter.character}, ${currentLetter.word}", isBengali = isBn)
                        },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Pronounce",
                            tint = KidsCoral,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Next Letter Icon
            IconButton(
                onClick = {
                    if (currentIndex < allLetters.size - 1) {
                        currentIndex++
                        canvasKey++
                        audioService.playPop()
                    }
                },
                enabled = currentIndex < allLetters.size - 1,
                modifier = Modifier
                    .size(44.dp)
                    .background(Color.White, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Next",
                    tint = if (currentIndex < allLetters.size - 1) KidsPurple else Color.LightGray
                )
            }
        }

        // Tracing Area Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 20.dp, vertical = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.runtime.key(canvasKey) {
                TracingCanvas(
                    letter = currentLetter,
                    brushColor = brushColors[selectedColorIndex],
                    showHint = showHint,
                    audioService = audioService,
                    onCompleted = { stars ->
                        progressRepository.recordLetterCompletion(currentLetter.id, stars)
                        showCelebration = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )
            }
        }

        // Bottom Controls Container
        Card(
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp)
            ) {
                // Brush Color Selector
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Magic Color 🎨:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF6B7280)
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        brushColors.forEachIndexed { index, color ->
                            val isSelected = selectedColorIndex == index
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .border(
                                        width = if (isSelected) 3.dp else 0.dp,
                                        color = if (isSelected) Color(0xFF22223B) else Color.Transparent,
                                        shape = CircleShape
                                    )
                                    .clickable {
                                        selectedColorIndex = index
                                        audioService.playPop()
                                    }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Action buttons: Clear, Hint, Next
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            canvasKey++
                            audioService.playPop()
                        },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("clear_canvas_button")
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null, tint = KidsCoral, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("মুছুন (Clear)", fontSize = 13.sp, color = KidsCoral, fontWeight = FontWeight.SemiBold)
                    }

                    OutlinedButton(
                        onClick = {
                            showHint = !showHint
                            audioService.playPop()
                        },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("toggle_hint_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = if (showHint) KidsYellow else Color.Gray,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (showHint) "ইঙ্গিত চালু" else "ইঙ্গিত বন্ধ",
                            fontSize = 13.sp,
                            color = if (showHint) KidsPurple else Color.Gray,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Button(
                        onClick = {
                            if (currentIndex < allLetters.size - 1) {
                                currentIndex++
                                canvasKey++
                                audioService.playPop()
                            }
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = KidsMint),
                        modifier = Modifier
                            .weight(1.2f)
                            .height(48.dp)
                            .testTag("next_letter_trace_button")
                    ) {
                        Text("পরের বর্ণ ➔", fontSize = 14.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

    if (showCelebration) {
        CelebrationDialog(
            letterName = "${currentLetter.character} (${currentLetter.word})",
            stars = 3,
            newlyEarnedRewards = newlyEarnedRewards,
            onNextLetter = {
                showCelebration = false
                newlyEarnedRewards = emptyList()
                if (currentIndex < allLetters.size - 1) {
                    currentIndex++
                    canvasKey++
                }
            },
            onTryAgain = {
                showCelebration = false
                newlyEarnedRewards = emptyList()
                canvasKey++
            },
            onDismiss = {
                showCelebration = false
                newlyEarnedRewards = emptyList()
            }
        )
    }
}
