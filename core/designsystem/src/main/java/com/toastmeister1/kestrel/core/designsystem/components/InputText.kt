package com.toastmeister1.kestrel.core.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.toastmeister1.kestrel.core.designsystem.preview.LandscapePreview
import com.toastmeister1.kestrel.core.designsystem.theme.Gray600
import com.toastmeister1.kestrel.core.designsystem.theme.KestrelTheme
import java.util.Locale

@Composable
fun InputText(
    modifier: Modifier = Modifier,
    initialValue: Float,
    onValueChange: (value: Float) -> Unit
) {
    val format = String.format(locale = Locale.KOREA, "%.1f")
    var text by remember { mutableStateOf(format.format(initialValue)) }

    Surface(
        modifier = modifier,
        color = Color.White,
        contentColor = Gray600,
        shape = RoundedCornerShape(6.dp)
    ) {
        Row(
            modifier = Modifier
                .widthIn(96.dp)
                .height(32.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = text,
                textStyle = KestrelTheme.typography.body2.copy(
                    textAlign = TextAlign.Center,
                    color = Gray600
                ),
                onValueChange = {
                    val number = if (it.isBlank()) {
                        0F
                    } else {
                        it.toFloatOrNull()
                    }

                    number?.let { input ->
                        text = it
                        onValueChange(input)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
}

@LandscapePreview
@Composable
private fun InputTextPreview() {
    KestrelTheme {
        Surface(
            border = BorderStroke(width = 1.dp, color = Gray600),
            shape = RoundedCornerShape(6.dp)
        ) {
            Box {
                InputText(
                    initialValue = 0.0F,
                    onValueChange = {}
                )
            }
        }
    }
}