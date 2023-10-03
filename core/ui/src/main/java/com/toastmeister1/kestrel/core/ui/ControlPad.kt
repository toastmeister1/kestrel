package com.toastmeister1.kestrel.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.toastmeister1.kestrel.core.designsystem.preview.LandscapePreview
import com.toastmeister1.kestrel.core.designsystem.theme.KestrelTheme

@LandscapePreview
@Composable
private fun ControlPadPreview() {
    KestrelTheme {
        Row(modifier = Modifier.fillMaxSize()) {
            SectionLeft {
                ControlPadOption(label = "Suffix") {}
            }
            SectionRight {}
        }
    }
}

@Composable
fun RowScope.SectionLeft(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier.weight(1F)) {
        content()
    }
}

@Composable
fun RowScope.SectionRight(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier.weight(1F)) {
        content()
    }
}

@Composable
fun ControlPadOption(
    label: String,
    content: @Composable () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = KestrelTheme.typography.subtitle2
        )

        content()
    }
}