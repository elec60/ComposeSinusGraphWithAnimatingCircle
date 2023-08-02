package hashem.mousavi.composesinusgraphwithanimatingcircle

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import kotlin.math.PI
import kotlin.math.sin

@Composable
fun SinusGraph() {
    val animatable = remember {
        Animatable(initialValue = 0f)
    }
    LaunchedEffect(key1 = Unit) {
        animatable.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 10000,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            )
        )
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f)
        ) {
            val xSamples = mutableListOf<Float>()
            val ySamples = mutableListOf<Float>()
            val width = this.size.width
            val steps = 1f
            val totalSamplesCount = (width / steps).toInt()
            var x = -width / 2f
            for (i in 1..totalSamplesCount) {
                xSamples.add(x)
                val sin = sin(2 * x * PI / 180f).toFloat()
                val y = -sin * this.size.height / 3
                ySamples.add(y)
                x += steps
            }

            val h = this.size.height
            val w = this.size.width


            translate(left = this.size.width / 2f, top = this.size.height / 2) {
                val path = Path().apply {
                    moveTo(x = -width / 2f, y = 0f)
                }
                xSamples.forEachIndexed { index, x ->
                    val y = ySamples[index]
                    path.lineTo(x, y)
                }
                drawPath(path = path, color = Color.Gray, style = Stroke(width = 10f))

                drawLine(
                    color = Color.Red,
                    start = Offset(x = 0f, y = -h / 2),
                    end = Offset(x = 0f, y = h / 2),
                    strokeWidth = 5f
                )
                drawLine(
                    color = Color.Red,
                    start = Offset(x = -w / 2, y = 0f),
                    end = Offset(x = w / 2, y = 0f),
                    strokeWidth = 5f
                )

                val index = (animatable.value * (xSamples.size - 1)).toInt()
                drawCircle(
                    color = Color.Blue,
                    radius = 25f,
                    center = Offset(x = xSamples[index], y = ySamples[index])
                )
            }

        }
    }
}