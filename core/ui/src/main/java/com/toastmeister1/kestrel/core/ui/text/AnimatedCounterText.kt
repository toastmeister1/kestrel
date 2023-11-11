package com.toastmeister1.kestrel.core.ui.text

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.toastmeister1.kestrel.core.designsystem.preview.LandscapePreview
import com.toastmeister1.kestrel.core.designsystem.theme.KestrelTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlin.math.absoluteValue

@Composable
internal fun AnimatedCounterText(
    count: Int,
    style: TextStyle,
    color: Color = Color.Black
) {
    var number by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = count) {
        val diff = (count - number).absoluteValue
        if (diff == 1) {
            number = count
        } else {
            for (i in 0..count) {
                delay(50L)
                number = i
            }
        }
    }

    AnimatedContent(
        label = "Counter Slide Animation",
        targetState = number,
        transitionSpec = {
            (slideInVertically { -it } + fadeIn())
                .togetherWith(slideOutVertically { it } + fadeOut())
        }
    ) { char ->
        Text(
            text = char.toString(),
            style = style,
            color = color,
            softWrap = false
        )
    }
}

@LandscapePreview
@Composable
private fun AnimatedCounterTextPreview() {
    KestrelTheme {
        var number by remember { mutableIntStateOf(0) }

        LaunchedEffect(key1 = true) {
            flowOf(3344, 3342, 55345, 23333, 330, 3555, 441234)
                .collect {
                    delay(1000)
                    number = it
                }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.align(Alignment.Center)) {
                Counter(count = number, style = KestrelTheme.typography.h2)

                Button(onClick = { number += 100 }) {
                    Text(text = "증가")
                }
                Button(onClick = { number -= 1 }) {
                    Text(text = "감소")
                }
            }
        }
    }
}

@Composable
private fun Counter(
    count: Int,
    modifier: Modifier = Modifier,
    style: TextStyle,
    color: Color = Color.Black
) {
    Row(
        modifier = modifier.animateContentSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        count.toString()
            .forEach {
                AnimatedCounterText(it.digitToInt(), style, color)
            }

        Spacer(modifier = Modifier.width(4.dp))
        Text(
            style = KestrelTheme.typography.h2,
            text = "원"
        )
    }
}