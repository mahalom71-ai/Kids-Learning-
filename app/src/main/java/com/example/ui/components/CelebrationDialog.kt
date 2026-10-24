package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.BadgeReward
import com.example.ui.theme.KidsCoral
import com.example.ui.theme.KidsMint
import com.example.ui.theme.KidsPurple
import com.example.ui.theme.KidsYellow

@Composable
fun CelebrationDialog(
    letterName: String,
    stars: Int = 3,
    newlyEarnedRewards: List<BadgeReward> = emptyList(),
    onNextLetter: () -> Unit,
    onTryAgain: () -> Unit,
    onDismiss: () -> Unit
) {
    val scaleAnim = remember { Animatable(0.4f) }

    LaunchedEffect(Unit) {
        scaleAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
        )
    }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .scale(scaleAnim.value)
        ) {
            ConfettiEffect(modifier = Modifier.matchParentSize(), particleCount = 55)

            Card(
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .testTag("celebration_card")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val hasNewMedal = newlyEarnedRewards.isNotEmpty()
                    val primaryReward = newlyEarnedRewards.firstOrNull()

                    // Trophy / celebration badge
                    Box(
                        modifier = Modifier
                            .size(if (hasNewMedal) 82.dp else 72.dp)
                            .background(
                                if (hasNewMedal) Color(primaryReward!!.colorHex).copy(alpha = 0.2f) else KidsYellow.copy(alpha = 0.2f),
                                CircleShape
                            )
                            .border(
                                width = if (hasNewMedal) 3.dp else 0.dp,
                                color = if (hasNewMedal) Color(primaryReward!!.colorHex) else Color.Transparent,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (hasNewMedal) primaryReward!!.iconEmoji else "🎉",
                            fontSize = if (hasNewMedal) 46.sp else 38.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = if (hasNewMedal) "নতুন পদক অর্জিত! 🎉" else "দারুণ হয়েছে! শাবাশ!",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (hasNewMedal) KidsPurple else KidsPurple,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = if (hasNewMedal) {
                            "You unlocked the ${primaryReward?.titleEnglish}!"
                        } else {
                            "Super Job! You traced $letterName perfectly!"
                        },
                        fontSize = 14.sp,
                        color = Color(0xFF6B7280),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                    )

                    // Newly earned medal banner if present
                    if (hasNewMedal && primaryReward != null) {
                        Card(
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(primaryReward.colorHex).copy(alpha = 0.12f)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 14.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "${primaryReward.titleBangla} (${primaryReward.titleEnglish})",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF22223B),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = primaryReward.descriptionEnglish,
                                    fontSize = 12.sp,
                                    color = Color(0xFF4B5563),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(top = 2.dp)
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Row(
                                    modifier = Modifier
                                        .background(KidsYellow.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                                        .padding(horizontal = 10.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = KidsYellow,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "+${primaryReward.bonusStars} বোনাস তারা! (+${primaryReward.bonusStars} Bonus Stars)",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF9A6700)
                                    )
                                }
                            }
                        }
                    }

                    // Stars row
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(3) { index ->
                            val earned = index < stars
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = if (earned) "Earned star" else "Empty star",
                                tint = if (earned) KidsYellow else Color(0xFFE5E7EB),
                                modifier = Modifier.size(if (index == 1) 46.dp else 38.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = onNextLetter,
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = KidsMint),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("next_letter_button")
                    ) {
                        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "পরের বর্ণ (Next Letter)",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedButton(
                        onClick = onTryAgain,
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .testTag("retry_tracing_button")
                    ) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = null, tint = KidsPurple)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "আবার আঁকো (Try Again)",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = KidsPurple
                        )
                    }
                }
            }
        }
    }
}
