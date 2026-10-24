package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.KidsAudioService
import com.example.data.ProgressRepository
import com.example.data.RhymeItem
import com.example.data.RhymesData
import com.example.ui.components.ConfettiEffect
import com.example.ui.theme.KidsCoral
import com.example.ui.theme.KidsMint
import com.example.ui.theme.KidsPurple
import com.example.ui.theme.KidsYellow
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RhymesScreen(
    audioService: KidsAudioService,
    progressRepository: ProgressRepository,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Bangla, 1 = English
    var activeRhyme by remember { mutableStateOf<RhymeItem?>(null) }
    val progressState by progressRepository.progress.collectAsState()

    if (activeRhyme != null) {
        RhymeDetailPlayer(
            rhyme = activeRhyme!!,
            audioService = audioService,
            progressRepository = progressRepository,
            onBack = {
                audioService.stopMelody()
                activeRhyme = null
            }
        )
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFF7F8FD))
        ) {
            // Header
            Card(
                shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp),
                colors = CardDefaults.cardColors(containerColor = KidsCoral),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = "ছড়া ও গানের আসর 🎶",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Sing along with fun tunes and illustrations!",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }

            // Tabs
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.Transparent,
                contentColor = KidsCoral,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = KidsCoral,
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
                            text = "বাংলা ছড়া (Bangla)",
                            fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 14.sp,
                            color = if (selectedTab == 0) KidsCoral else Color(0xFF6B7280)
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
                            text = "English Rhymes",
                            fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 14.sp,
                            color = if (selectedTab == 1) KidsCoral else Color(0xFF6B7280)
                        )
                    }
                )
            }

            val rhymesList = if (selectedTab == 0) RhymesData.banglaRhymes else RhymesData.englishRhymes

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("rhymes_list")
            ) {
                items(rhymesList) { rhyme ->
                    val isPlayed = progressState.rhymesListened.contains(rhyme.id)
                    RhymeListItemCard(
                        rhyme = rhyme,
                        isPlayed = isPlayed,
                        onClick = {
                            audioService.playPop()
                            activeRhyme = rhyme
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RhymeListItemCard(
    rhyme: RhymeItem,
    isPlayed: Boolean,
    onClick: () -> Unit
) {
    ElevatedCard(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable { onClick() }
            .testTag("rhyme_card_${rhyme.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Emoji circle
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color(rhyme.colorHex).copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = rhyme.emoji, fontSize = 32.sp)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = rhyme.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF22223B)
                    )
                    if (isPlayed) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Played",
                            tint = KidsYellow,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Text(
                    text = rhyme.subtitle,
                    fontSize = 12.sp,
                    color = Color(0xFF6B7280),
                    modifier = Modifier.padding(top = 2.dp)
                )

                Text(
                    text = "${rhyme.lines.size} Lines • Sing-Along 🎵",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(rhyme.colorHex),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Play Icon circle
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(Color(rhyme.colorHex), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun RhymeDetailPlayer(
    rhyme: RhymeItem,
    audioService: KidsAudioService,
    progressRepository: ProgressRepository,
    onBack: () -> Unit
) {
    BackHandler { onBack() }

    val coroutineScope = rememberCoroutineScope()
    var isPlayingMelody by remember { mutableStateOf(false) }
    var activeLineIndex by remember { mutableIntStateOf(0) }
    var singJob by remember { mutableStateOf<Job?>(null) }
    var showConfetti by remember { mutableStateOf(false) }

    DisposableEffect(rhyme.id) {
        onDispose {
            singJob?.cancel()
            audioService.stopMelody()
        }
    }

    // Floating note animation
    val infiniteTransition = rememberInfiniteTransition(label = "note_bounce")
    val noteScale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "note_scale"
    )

    fun startSingAlong() {
        singJob?.cancel()
        audioService.stopMelody()
        isPlayingMelody = true
        progressRepository.recordRhymePlayed(rhyme.id)

        singJob = coroutineScope.launch {
            // Play musical accompaniment
            if (rhyme.melodyNotes.isNotEmpty()) {
                audioService.playMelody(rhyme.melodyNotes)
            }

            // Iterate lines with karaoke sync
            for (idx in rhyme.lines.indices) {
                activeLineIndex = idx
                val line = rhyme.lines[idx]

                // Also speak line via TTS
                val isBn = rhyme.language == "bn"
                val speechText = if (isBn) line.banglaText else line.englishText
                audioService.speak(speechText, isBengali = isBn)

                delay(line.durationMs)
            }

            isPlayingMelody = false
            showConfetti = true
            audioService.playSuccess()
            audioService.playStarEarned()
        }
    }

    fun stopSingAlong() {
        singJob?.cancel()
        audioService.stopMelody()
        isPlayingMelody = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8FD))
    ) {
        if (showConfetti) {
            ConfettiEffect(modifier = Modifier.fillMaxSize(), particleCount = 45)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Top Bar
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

                Text(
                    text = rhyme.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF22223B)
                )

                IconButton(
                    onClick = {
                        audioService.playPop()
                        val isBn = rhyme.language == "bn"
                        audioService.speak(rhyme.title, isBengali = isBn)
                    },
                    modifier = Modifier
                        .size(44.dp)
                        .background(Color.White, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Pronounce title",
                        tint = Color(rhyme.colorHex)
                    )
                }
            }

            // Hero Illustration Card
            Card(
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(rhyme.colorHex).copy(alpha = 0.15f)
                ),
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
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .background(Color.White, CircleShape)
                            .scale(if (isPlayingMelody) noteScale else 1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = rhyme.emoji, fontSize = 48.sp)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = rhyme.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(rhyme.colorHex),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = rhyme.subtitle,
                        fontSize = 13.sp,
                        color = Color(0xFF4B5563),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Karaoke Lyrics Card
            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(rhyme.lines.indices.toList()) { idx ->
                        val line = rhyme.lines[idx]
                        val isActive = isPlayingMelody && idx == activeLineIndex

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(
                                    if (isActive) Color(rhyme.colorHex).copy(alpha = 0.18f) else Color.Transparent
                                )
                                .border(
                                    width = if (isActive) 2.dp else 0.dp,
                                    color = if (isActive) Color(rhyme.colorHex) else Color.Transparent,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(12.dp)
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    if (isActive) {
                                        Icon(
                                            imageVector = Icons.Default.MusicNote,
                                            contentDescription = null,
                                            tint = Color(rhyme.colorHex),
                                            modifier = Modifier
                                                .size(20.dp)
                                                .padding(end = 4.dp)
                                        )
                                    }
                                    Text(
                                        text = line.banglaText,
                                        fontSize = if (isActive) 20.sp else 16.sp,
                                        fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isActive) Color(rhyme.colorHex) else Color(0xFF22223B)
                                    )
                                }

                                Text(
                                    text = line.englishText,
                                    fontSize = 13.sp,
                                    color = if (isActive) Color(0xFF4B5563) else Color(0xFF9CA3AF),
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Player Controls
            Card(
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Play/Pause Main Sing Button
                    Button(
                        onClick = {
                            if (isPlayingMelody) {
                                stopSingAlong()
                            } else {
                                startSingAlong()
                            }
                        },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(rhyme.colorHex)),
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .testTag("rhyme_play_pause_button")
                    ) {
                        Icon(
                            imageVector = if (isPlayingMelody) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isPlayingMelody) "Pause" else "Play",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isPlayingMelody) "গান থামাও (Pause)" else "একসাথে গাও! (Sing Along)",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    // Reset button
                    IconButton(
                        onClick = {
                            stopSingAlong()
                            activeLineIndex = 0
                            startSingAlong()
                        },
                        modifier = Modifier
                            .size(52.dp)
                            .background(Color(0xFFF1F3FB), RoundedCornerShape(16.dp))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Restart",
                            tint = KidsPurple
                        )
                    }
                }
            }
        }
    }
}
