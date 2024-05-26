package com.toastmeister1.kestrel.core.ui.options

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OptionBox(
    optionItems: List<OptionItemState>,
    bottomContent: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier.width(IntrinsicSize.Max),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        optionItems.forEach { option ->
            when (option) {
                is OptionItemState.Toggle -> {
                    ToggleOptionComponent(label = option.label, items = option.items, onChangeActiveItem = option.onClickToggleItem)
                }

                is OptionItemState.InputText -> {
                    InputTextOptionComponents(label = option.label, initialValue = option.initialValue, onValueChange = option.onChangeValue)
                }
            }
        }

        bottomContent()
    }
}
