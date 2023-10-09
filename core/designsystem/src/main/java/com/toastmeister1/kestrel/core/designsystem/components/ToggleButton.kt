package com.toastmeister1.kestrel.core.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.toastmeister1.kestrel.core.designsystem.preview.LandscapePreview
import com.toastmeister1.kestrel.core.designsystem.theme.Gray600
import com.toastmeister1.kestrel.core.designsystem.theme.KestrelTheme

@Composable
fun ToggleButton(
    label: String,
    checked: Boolean,
    onCheckedChange: (isChecked: Boolean) -> Unit
) {
    val backgroundColor = if (checked) Gray600 else Color.Transparent
    val contentColor = if (checked) Color.White else Gray600

    Surface(
        checked = false,
        onCheckedChange = onCheckedChange,
        color = backgroundColor,
        contentColor = contentColor,
        shape = RoundedCornerShape(6.dp)
    ) {
        Box(
            Modifier
                .height(32.dp)
                .padding(horizontal = 26.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = label,
                style = KestrelTheme.typography.body2
            )
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
                ToggleButton(label = "원", checked = false, onCheckedChange = {})
                ToggleButton(label = "%", checked = true, onCheckedChange = {})
            }
        }
    }
}