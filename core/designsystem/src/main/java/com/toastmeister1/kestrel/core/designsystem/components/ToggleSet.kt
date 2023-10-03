package com.toastmeister1.kestrel.core.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.toastmeister1.kestrel.core.designsystem.preview.LandscapePreview
import com.toastmeister1.kestrel.core.designsystem.theme.Gray600
import com.toastmeister1.kestrel.core.designsystem.theme.KestrelTheme

@Composable
fun ToggleSet(
    itemStates: List<ToggleButtonState>
) {
    Row(
        modifier = Modifier.height(32.dp)
    ) {
        itemStates.forEach {
            ToggleButton(label = it.label, checked = it.isChecked)
        }
    }
}

@LandscapePreview
@Composable
private fun ToggleButtonPreview() {
    KestrelTheme {
        Surface(
            border = BorderStroke(width = 1.dp, color = Gray600),
            shape = RoundedCornerShape(6.dp)
        ) {
            Row(
                modifier = Modifier.height(32.dp)
            ) {
                ToggleButton(label = "원", checked = false)
                ToggleButton(label = "%", checked = true)
            }
        }
    }
}