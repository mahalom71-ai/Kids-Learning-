package com.example.ui.components

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke as CanvasStroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.KidsAudioService
import com.example.data.LetterItem
import com.example.ui.theme.KidsCoral
import com.example.ui.theme.KidsMint
import com.example.ui.theme.KidsPurple
import com.example.ui.theme.KidsYellow
import kotlin.math.hypot

@Composable
fun TracingCanvas(
    letter: LetterItem,
    brushColor: Color,
    showHint: Boolean,
    audioService: KidsAudioService,
    onCompleted: (stars: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val vibrator = remember {
        try {
            context.getSystemService(android.content.Context.VIBRATOR_SERVICE) as? Vibrator
        } catch (_: Exception) {
            null
        }
    }

    // List of lines drawn by the user (each line is a list of points)
    val userPaths = remember(letter.id) { mutableStateListOf<List<Offset>>() }
    var currentPathPoints by remember(letter.id) { mutableStateOf<List<Offset>>(emptyList()) }

    // Set of indices of waypoints reached by the user
    val visitedWaypoints = remember(letter.id) { mutableStateListOf<Int>() }
    var isCompletedFired by remember(letter.id) { mutableStateOf(false) }

    // Total waypoints in this letter
    val allWaypoints = remember(letter.id) {
        letter.strokes.flatMap { it.points }
    }

    // Animation for guide hint (bouncing star)
    val infiniteTransition = rememberInfiniteTransition(label = "hint_anim")
    val hintProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "hint_progress"
    )

    fun triggerHaptic() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(VibrationEffect.createOneShot(25, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(25)
            }
        } catch (_: Exception) {}
    }

    Box(
        modifier = modifier
            .shadow(12.dp, RoundedCornerShape(32.dp))
            .clip(RoundedCornerShape(32.dp))
            .background(Color.White)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Large background letter watermark
        Text(
            text = letter.character,
            fontSize = 180.sp,
            fontWeight = FontWeight.Black,
            color = Color(0xFFF1F3FB),
            modifier = Modifier.align(Alignment.Center)
        )

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(letter.id) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            currentPathPoints = listOf(offset)
                        },
                        onDrag = { change, _ ->
                            change.consume()
                            val newPoint = change.position
                            currentPathPoints = currentPathPoints + newPoint

                            // Check proximity to waypoints
                            val canvasW = size.width.toFloat()
                            val canvasH = size.height.toFloat()

                            allWaypoints.forEachIndexed { index, wp ->
                                if (!visitedWaypoints.contains(index)) {
                                    val targetX = wp.x * canvasW
                                    val targetY = wp.y * canvasH
                                    val dist = hypot(newPoint.x - targetX, newPoint.y - targetY)
                                    if (dist < 72f) { // Touch target radius
                                        visitedWaypoints.add(index)
                                        audioService.playSparkle()
                                        triggerHaptic()

                                        // Check if completed
                                        val totalCount = allWaypoints.size
                                        val reachedCount = visitedWaypoints.size
                                        if (totalCount > 0 && reachedCount >= (totalCount * 0.75f).toInt() && !isCompletedFired) {
                                            isCompletedFired = true
                                            audioService.playSuccess()
                                            audioService.playStarEarned()
                                            onCompleted(3)
                                        }
                                    }
                                }
                            }
                        },
                        onDragEnd = {
                            if (currentPathPoints.isNotEmpty()) {
                                userPaths.add(currentPathPoints)
                                currentPathPoints = emptyList()
                            }
                        },
                        onDragCancel = {
                            if (currentPathPoints.isNotEmpty()) {
                                userPaths.add(currentPathPoints)
                                currentPathPoints = emptyList()
                            }
                        }
                    )
                }
        ) {
            val canvasW = size.width
            val canvasH = size.height

            // 1. Draw guide stroke dotted outline connecting waypoints
            letter.strokes.forEach { stroke ->
                if (stroke.points.size > 1) {
                    val guidePath = Path()
                    stroke.points.forEachIndexed { i, p ->
                        val px = p.x * canvasW
                        val py = p.y * canvasH
                        if (i == 0) guidePath.moveTo(px, py) else guidePath.lineTo(px, py)
                    }
                    drawPath(
                        path = guidePath,
                        color = Color(0xFFDDE3F0),
                        style = CanvasStroke(
                            width = 24f,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                }
            }

            // 2. Draw waypoint circles with numbers
            var globalWpIndex = 0
            letter.strokes.forEach { stroke ->
                stroke.points.forEach { wp ->
                    val isVisited = visitedWaypoints.contains(globalWpIndex)
                    val center = Offset(wp.x * canvasW, wp.y * canvasH)

                    // Outer pulse or glow if visited
                    if (isVisited) {
                        drawCircle(
                            color = KidsMint.copy(alpha = 0.35f),
                            radius = 28f,
                            center = center
                        )
                        drawCircle(
                            color = KidsMint,
                            radius = 16f,
                            center = center
                        )
                    } else {
                        drawCircle(
                            color = KidsPurple.copy(alpha = 0.15f),
                            radius = 22f,
                            center = center
                        )
                        drawCircle(
                            color = KidsPurple,
                            radius = 12f,
                            center = center
                        )
                    }
                    globalWpIndex++
                }
            }

            // 3. Draw animated guide hint star if enabled
            if (showHint && allWaypoints.isNotEmpty()) {
                val pointIndexFloat = hintProgress * (allWaypoints.size - 1)
                val baseIdx = pointIndexFloat.toInt().coerceIn(0, allWaypoints.size - 1)
                val nextIdx = (baseIdx + 1).coerceAtMost(allWaypoints.size - 1)
                val fraction = pointIndexFloat - baseIdx

                val p1 = allWaypoints[baseIdx]
                val p2 = allWaypoints[nextIdx]

                val hintX = (p1.x + (p2.x - p1.x) * fraction) * canvasW
                val hintY = (p1.y + (p2.y - p1.y) * fraction) * canvasH

                drawCircle(
                    color = KidsYellow.copy(alpha = 0.4f),
                    radius = 32f,
                    center = Offset(hintX, hintY)
                )
                drawCircle(
                    color = KidsYellow,
                    radius = 16f,
                    center = Offset(hintX, hintY)
                )
            }

            // 4. Draw user's traced lines
            val drawBrush = Brush.linearGradient(
                listOf(brushColor, KidsCoral, brushColor)
            )

            // Saved paths
            userPaths.forEach { points ->
                if (points.size > 1) {
                    val p = Path()
                    points.forEachIndexed { i, pt ->
                        if (i == 0) p.moveTo(pt.x, pt.y) else p.lineTo(pt.x, pt.y)
                    }
                    drawPath(
                        path = p,
                        brush = drawBrush,
                        style = CanvasStroke(
                            width = 32f,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                }
            }

            // In-progress current stroke
            if (currentPathPoints.size > 1) {
                val currentP = Path()
                currentPathPoints.forEachIndexed { i, pt ->
                    if (i == 0) currentP.moveTo(pt.x, pt.y) else currentP.lineTo(pt.x, pt.y)
                }
                drawPath(
                    path = currentP,
                    brush = drawBrush,
                    style = CanvasStroke(
                        width = 32f,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            }
        }
    }
}
