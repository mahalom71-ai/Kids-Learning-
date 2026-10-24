package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

data class ConfettiParticle(
    val x: Float,
    val y: Float,
    val vx: Float,
    val vy: Float,
    val size: Float,
    val color: Color,
    val isCircle: Boolean
)

@Composable
fun ConfettiEffect(
    modifier: Modifier = Modifier,
    particleCount: Int = 50
) {
    val progress = remember { Animatable(0f) }

    val colors = listOf(
        Color(0xFFFF6B6B),
        Color(0xFF4ECDC4),
        Color(0xFFFFD166),
        Color(0xFF6C5CE7),
        Color(0xFFFA8231),
        Color(0xFF20BF6B),
        Color(0xFF0FB9B1),
        Color(0xFFA55EEA)
    )

    val particles = remember {
        List(particleCount) {
            ConfettiParticle(
                x = Random.nextFloat(),
                y = Random.nextFloat() * 0.2f, // start near top
                vx = (Random.nextFloat() - 0.5f) * 0.4f,
                vy = 0.5f + Random.nextFloat() * 0.5f,
                size = 12f + Random.nextFloat() * 16f,
                color = colors[Random.nextInt(colors.size)],
                isCircle = Random.nextBoolean()
            )
        }
    }

    LaunchedEffect(Unit) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 2400, easing = LinearEasing)
        )
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height
        val t = progress.value

        particles.forEach { p ->
            val currX = (p.x + p.vx * t) * w
            val currY = (p.y + p.vy * t + 0.3f * t * t) * h
            val alpha = (1f - (t - 0.4f).coerceAtLeast(0f) / 0.6f).coerceIn(0f, 1f)

            if (p.isCircle) {
                drawCircle(
                    color = p.color.copy(alpha = alpha),
                    radius = p.size / 2f,
                    center = Offset(currX, currY)
                )
            } else {
                drawRect(
                    color = p.color.copy(alpha = alpha),
                    topLeft = Offset(currX, currY),
                    size = Size(p.size, p.size * 0.6f)
                )
            }
        }
    }
}
