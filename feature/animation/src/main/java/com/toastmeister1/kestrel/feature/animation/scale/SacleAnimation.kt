package com.toastmeister1.kestrel.feature.animation.scale

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toastmeister1.kestrel.core.designsystem.theme.KestrelTheme
import com.toastmeister1.kestrel.core.ui.text.Counter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ScaleAnimatedText(
    number: Int,
) {
    var localNumber by remember { mutableIntStateOf(number) }
    val alpha = remember { Animatable(initialValue = 0F) }

    LaunchedEffect(key1 = number) {
        launch {
            alpha.animateTo(1F, animationSpec = tween(durationMillis = 200))
            delay(100L)
            localNumber = number
        }
    }

    Text(
        modifier = Modifier.animateContentSize(animationSpec = tween(durationMillis = 500)),
        text = localNumber.toString(),
        style = KestrelTheme.typography.h2,
        color = Color.Black
    )
}

@Preview
@Composable
fun ScaleAnimationTest() {
    KestrelTheme {
        var number by remember { mutableStateOf(0) }

        LaunchedEffect(key1 = false) {
            delay(1000L)
            number = 0
            delay(1000L)
            number = 23452
            delay(1000L)
            number = 234522
            delay(1000L)
            number = 2345223
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(modifier = Modifier.align(Alignment.Center)) {
                Row {
                    Counter(
                        count = number,
                        style = KestrelTheme.typography.h1
                    )
                    Spacer(Modifier.width(2.dp))
                }
            }
        }
    }
}
