package com.toastmeister1.kestrel.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SplitComponent(
    modifier: Modifier,
    leftContent: @Composable BoxScope.() -> Unit,
    rightContent: @Composable BoxScope.() -> Unit,
) {
    Row(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = modifier
                .widthIn(0.dp)
                .weight(1F)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center,
        ) {
            leftContent()
        }

        Box(
            modifier = modifier
                .widthIn(0.dp)
                .weight(1F)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center,
        ) {
            rightContent()
        }
    }
}
