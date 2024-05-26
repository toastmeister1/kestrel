package com.toastmeister1.kestrel.feature.animation.sequencial

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
private fun rememberScreenAState() = remember { ScreenAState() }

private class ScreenAState {

    private val _aComponentAlpha = Animatable(INVISIBLE)
    val aComponentAlpha: Float
        get() = _aComponentAlpha.value

    private val _bComponentAlpha = Animatable(INVISIBLE)
    val bComponentAlpha
        get() = _bComponentAlpha.value

    private val _middleComponentAlpha = Animatable(INVISIBLE)
    val middleComponentAlpha
        get() = _middleComponentAlpha.value

    suspend fun animateEnterAnimation() {
        _aComponentAlpha.animateTo(VISIBLE, tween(durationMillis = 500))
        _bComponentAlpha.animateTo(VISIBLE, tween(durationMillis = 500))
        _middleComponentAlpha.animateTo(VISIBLE, tween(durationMillis = 500))
    }

    private companion object {
        const val INVISIBLE = 0F
        const val VISIBLE = 1F
    }
}

/**
 * Animation Preview는 현재 Animatable을 지원하지 않고
 * [updateTransition], [AnimatedVisibility], animate*AsState,
 * [CrossFade], [rememberInfiniteTransition], [AnimatedContent] 만 지원한다.
 */
@Preview
@Composable
private fun AnimationSequenceAlphaUsingTransitionPreview() {
    val screenAState = rememberScreenAState()

    LaunchedEffect(true) {
        println("Launched!")
        delay(300L)
        screenAState.animateEnterAnimation()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ComponentA(
            modifier = Modifier.graphicsLayer { this.alpha = screenAState.aComponentAlpha },
        )
        MiddleComponent(
            modifier = Modifier.graphicsLayer { this.alpha = screenAState.bComponentAlpha },
        )
        ComponentB(
            modifier = Modifier.graphicsLayer { this.alpha = screenAState.middleComponentAlpha },
        )
    }
}

@Composable
private fun ComponentA(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(Color.Red),
    )
}

@Composable
private fun ComponentB(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(Color.Blue),
    )
}

@Composable
private fun ColumnScope.MiddleComponent(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(0.dp)
            .weight(1F)
            .background(Color.DarkGray),
    )
}
