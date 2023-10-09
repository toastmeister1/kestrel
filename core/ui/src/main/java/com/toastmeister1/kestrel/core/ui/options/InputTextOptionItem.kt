package com.toastmeister1.kestrel.core.ui.options

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.toastmeister1.kestrel.core.designsystem.components.InputText

@Composable
fun InputTextOptionComponents(
    modifier: Modifier = Modifier,
    label: String,
    initialValue: Float = 0.0F,
    onValueChange: (Float) -> Unit
) {
    OptionItem(
        modifier = modifier,
        label = label
    ) {
        InputText(
            initialValue = initialValue,
            onValueChange = onValueChange,
        )
    }
}