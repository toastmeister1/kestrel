package com.toastmeister1.kestrel.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun KestrelTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalTypography provides Typography
    ) {
        MaterialTheme(content = content)
    }
}

object KestrelTheme {
    val typography: KestrelTypography
        @Composable
        get() = LocalTypography.current
}