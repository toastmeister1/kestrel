package com.toastmeister1.kestrel.feature.animation

import androidx.compose.animation.core.AnimationVector
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toastmeister1.kestrel.core.designsystem.preview.LandscapePreview
import com.toastmeister1.kestrel.core.designsystem.theme.KestrelTheme
import com.toastmeister1.kestrel.core.ui.ControlPad
import com.toastmeister1.kestrel.core.ui.options.OptionBox
import com.toastmeister1.kestrel.core.ui.options.OptionItemState

@LandscapePreview
@Composable
private fun AScreenTest() {
    var isAnimationStart by remember { mutableStateOf(false) }

    var dampingRatio by remember { mutableFloatStateOf(0.5F) }
    var stiffness by remember { mutableFloatStateOf(0F) }
    var visibilityThresholds by remember { mutableFloatStateOf(0.0F) }

    val testState by remember { mutableStateOf(SpringAnimationTestState(initialState = 0.dp, targetState = 128.dp)) }
    var size by remember { mutableStateOf(testState.initialState) }

    val springAnimation by remember(key1 = dampingRatio, key2 = stiffness) { mutableStateOf(spring<Int>(dampingRatio, stiffness)) }

    LaunchedEffect(isAnimationStart) {
        if (isAnimationStart) {
            animate(
                initialValue = testState.initialState.value.toInt(),
                targetValue = testState.targetState.value.toInt(),
                typeConverter = TwoWayConverter<Int, AnimationVector1D>(
                    convertFromVector = { vector ->
                        vector.value.toInt()
                    },
                    convertToVector = { value ->
                        AnimationVector(value.toFloat())
                    },
                ),
                animationSpec = springAnimation,
            ) { value, velocity ->
                size = value.dp
            }

            isAnimationStart = false
        }
    }

    KestrelTheme {
        ControlPad(
            leftContent = {
                OptionBox(
                    optionItems = listOf(
                        OptionItemState.InputText(
                            label = "dumpingRatio",
                            initialValue = 0.5F,
                        ).apply
                            {
                                dampingRatio = initialValue
                                onChangeValue = { dampingRatio = it }
                            },
                        OptionItemState.InputText(
                            label = "stiffness",
                            initialValue = 500F,
                        ).apply {
                            stiffness = initialValue
                            onChangeValue = { stiffness = it }
                        },
                        OptionItemState.InputText(
                            label = "visibilityThresholds",
                            initialValue = 0.0F,
                        ).apply {
                            onChangeValue = { visibilityThresholds = it }
                        },
                        OptionItemState.Toggle(label = "Suffix", items = listOf("A", "B")).apply {
                            onClickToggleItem = {
                                stiffness = 0F
                                dampingRatio = 0F
                            }
                        },
                    ),
                ) {
                    Row(horizontalArrangement = Arrangement.Center) {
                        Button(
                            onClick = { isAnimationStart = true },
                        ) {
                            Text(text = "Start")
                        }

                        Button(
                            onClick = { size = testState.initialState },
                        ) {
                            Text(text = "Reset")
                        }
                    }
                }
            },
            rightContent = {
                Box(
                    modifier = Modifier
                        .background(Color.DarkGray)
//                        .animateContentSize(
//                            spring(
//                                dampingRatio = dampingRatio,
//                                stiffness = stiffness
//                            )
//                        )
                        .size(size),
                )
            },
        )
    }
}

data class SpringAnimationTestState(
    override val initialState: Dp,
    override val targetState: Dp,

) : AnimationTestState<Dp> {

    data class State(
        val dumpingRatio: Float,
        val stiffness: Float,
        val visibilityThresholds: Float,
    )
}

interface AnimationTestState<T> {
    val initialState: T
    val targetState: T
}
