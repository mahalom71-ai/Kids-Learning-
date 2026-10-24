package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.KidsAudioService
import com.example.data.AlphabetData
import com.example.data.LetterItem
import com.example.data.ProgressRepository
import com.example.ui.theme.KidsPurple

enum class BottomTab(
    val titleBangla: String,
    val titleEnglish: String,
    val icon: ImageVector,
    val tag: String
) {
    LETTERS("বর্ণমালা", "Letters", Icons.Default.School, "tab_letters"),
    TRACING("হাত ঘোরানো", "Tracing", Icons.Default.Create, "tab_tracing"),
    REWARDS("মেডেল", "Rewards", Icons.Default.EmojiEvents, "tab_rewards"),
    NUMBERS("সংখ্যা", "Numbers", Icons.Default.FormatListNumbered, "tab_numbers"),
    RHYMES("ছড়া", "Rhymes", Icons.Default.MusicNote, "tab_rhymes")
}

@Composable
fun MainScreen(
    audioService: KidsAudioService,
    progressRepository: ProgressRepository,
    modifier: Modifier = Modifier
) {
    var currentTab by remember { mutableStateOf(BottomTab.LETTERS) }
    var selectedTraceLetter by remember { mutableStateOf<LetterItem>(AlphabetData.banglaVowels.first()) }
    var isParentZoneOpen by remember { mutableStateOf(false) }
    var isQuizOpen by remember { mutableStateOf(false) }

    if (isParentZoneOpen) {
        ParentScreen(
            audioService = audioService,
            progressRepository = progressRepository,
            onBack = { isParentZoneOpen = false },
            modifier = modifier
        )
    } else if (isQuizOpen) {
        QuickQuizScreen(
            audioService = audioService,
            progressRepository = progressRepository,
            onBack = { isQuizOpen = false },
            modifier = modifier
        )
    } else {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            bottomBar = {
                NavigationBar(
                    containerColor = Color.White,
                    tonalElevation = 8.dp,
                    modifier = Modifier
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .testTag("main_navigation_bar")
                ) {
                    BottomTab.entries.forEach { tab ->
                        val isSelected = currentTab == tab
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                if (currentTab != tab) {
                                    currentTab = tab
                                    audioService.playPop()
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = tab.icon,
                                    contentDescription = tab.titleEnglish,
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = tab.titleBangla,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = KidsPurple,
                                selectedTextColor = KidsPurple,
                                indicatorColor = KidsPurple.copy(alpha = 0.15f),
                                unselectedIconColor = Color(0xFF9CA3AF),
                                unselectedTextColor = Color(0xFF6B7280)
                            ),
                            modifier = Modifier.testTag(tab.tag)
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (currentTab) {
                    BottomTab.LETTERS -> {
                        LettersScreen(
                            audioService = audioService,
                            progressRepository = progressRepository,
                            onNavigateToTrace = { letter ->
                                selectedTraceLetter = letter
                                currentTab = BottomTab.TRACING
                            },
                            onOpenRewards = {
                                currentTab = BottomTab.REWARDS
                            },
                            onOpenParent = {
                                isParentZoneOpen = true
                            },
                            onOpenQuiz = {
                                isQuizOpen = true
                            }
                        )
                    }

                    BottomTab.TRACING -> {
                        TracingScreen(
                            initialLetter = selectedTraceLetter,
                            audioService = audioService,
                            progressRepository = progressRepository,
                            onBack = {
                                currentTab = BottomTab.LETTERS
                            }
                        )
                    }

                    BottomTab.REWARDS -> {
                        RewardsScreen(
                            audioService = audioService,
                            progressRepository = progressRepository,
                            onOpenQuiz = {
                                isQuizOpen = true
                            }
                        )
                    }

                    BottomTab.NUMBERS -> {
                        NumbersScreen(
                            audioService = audioService,
                            progressRepository = progressRepository
                        )
                    }

                    BottomTab.RHYMES -> {
                        RhymesScreen(
                            audioService = audioService,
                            progressRepository = progressRepository
                        )
                    }
                }
            }
        }
    }
}
