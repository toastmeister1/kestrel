package com.toastmeister1.kestrel.core.ui

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.toastmeister1.kestrel.core.designsystem.theme.KestrelTheme

@Composable
fun ControlPad(
    leftContent: @Composable BoxScope.() -> Unit,
    rightContent: @Composable BoxScope.() -> Unit,
) {
    KestrelTheme {
        SplitComponent(
            modifier = Modifier.fillMaxSize(),
            leftContent = leftContent,
            rightContent = rightContent,
        )
    }
}
