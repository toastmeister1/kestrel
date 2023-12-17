package com.toastmeister1.kestrel.core.ui.text

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.toastmeister1.kestrel.core.designsystem.preview.LandscapePreview
import com.toastmeister1.kestrel.core.designsystem.theme.KestrelTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import kotlin.math.absoluteValue
import kotlin.math.max

@Composable
internal fun AnimatedCounterText(
    count: Int,
    style: TextStyle,
    color: Color = Color.Black
) {
    val alpha = remember { Animatable(0F) }
    var number by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = count) {
        val diff = (count - number).absoluteValue
        if (diff == 1) {
            number = count
        } else {
            launch {
                alpha.animateTo(1F, animationSpec = tween(durationMillis = 300))
            }
            launch {
                for (i in 0..count) {
                    delay(40)
                    number = i
                }
            }
        }
    }

    AnimatedContent(
        modifier = Modifier.graphicsLayer { this.alpha = alpha.value },
        label = "Counter Slide Animation",
        targetState = number,
        transitionSpec = {
            (slideInVertically(animationSpec = tween(durationMillis = 200)) { -it })
                .togetherWith(slideOutVertically(animationSpec = tween(durationMillis = 200)) { it } + fadeOut())
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

val DECIMAL_FORMAT = DecimalFormat("#,###")

@Composable
fun Counter(
    count: Int,
    modifier: Modifier = Modifier,
    style: TextStyle,
    color: Color = Color.Black
) {
    var localText by remember { mutableStateOf(count.toString()) }

    LaunchedEffect(count) {
        DECIMAL_FORMAT.format(count)
            .forEachIndexed { index, char ->
                delay(20)
                localText += char
            }
    }

    Row(
        modifier = modifier
            .animateContentSize(animationSpec = tween())
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        localText.forEach {
            if (it.isDigit()) {
                AnimatedCounterText(it.digitToInt(), style, color)
            } else {
                Text(text = ",", style = style, color = color)
            }
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            style = KestrelTheme.typography.h2,
            text = "원"
        )
    }
}

private fun String.swap(targetIndex: Int, value: Char): String {
    val builder = StringBuilder()
    this.forEachIndexed { index, c ->
        if (targetIndex == index) {
            builder.append(value)
        } else {
            builder.append(c)
        }
    }

    return builder.toString()
}

sealed interface NumberDigit {
    val value: Char

    data object Separator : NumberDigit {
        override val value: Char = ','
    }

    data class Number(
        override val value: Char
    ) : NumberDigit

    companion object {
        fun of(value: Char): NumberDigit {
            return if (value.isDigit()) {
                Number(value = value)
            } else {
                Separator
            }
        }
    }
}

sealed class NumberDigitChangeState {
    data object Removed : NumberDigitChangeState()

    data class Created(val createdValue: NumberDigit) : NumberDigitChangeState()

    data class Changed(val from: NumberDigit, val to: NumberDigit) : NumberDigitChangeState()
}

class NumberCalculator {

    fun calculate(
        localValue: Int,
        updateValue: Int
    ): List<NumberDigitChangeState> {
        val changeStates = mutableListOf<NumberDigitChangeState>()

        val formattedLocalValue = DECIMAL_FORMAT.format(localValue)
        val formattedUpdateValue = DECIMAL_FORMAT.format(updateValue)

        val maxLength = max(formattedLocalValue.length, formattedUpdateValue.length)

        for (index in 0 until maxLength) {
            val localDigit = formattedLocalValue.getOrNull(index)
            val updateDigit = formattedUpdateValue.getOrNull(index)

            when {
                localDigit != null && updateDigit != null -> {
                    if (localDigit != updateDigit) {
                        changeStates.add(NumberDigitChangeState.Changed(from = NumberDigit.of(localDigit), to = NumberDigit.of(updateDigit)))
                    }
                }

                localDigit != null && updateDigit == null -> {
                    changeStates.add(NumberDigitChangeState.Removed)
                }

                localDigit == null && updateDigit != null -> {
                    changeStates.add(NumberDigitChangeState.Created(createdValue = NumberDigit.of(updateDigit)))
                }
            }
        }

        return changeStates
    }
}
